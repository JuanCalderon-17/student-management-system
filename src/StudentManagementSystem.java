import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.table.DefaultTableModel;

public class StudentManagementSystem extends JFrame {
    private DefaultTableModel studentTableModel;
    private HashMap<String, String> studentGrades = new HashMap<>();
    private HashMap<String, List<String>> studentCourseEnrollments = new HashMap<>();

    public StudentManagementSystem() {
        setTitle("Student Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        studentTableModel = new DefaultTableModel(new Object[]{"ID", "Name"}, 0);
        JTable studentTable = new JTable(studentTableModel);

        JLabel titleLabel = new JLabel("Student Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JMenuBar menuBar = new JMenuBar();
        JMenu studentMenu = new JMenu("Student");

        JMenuItem addStudentItem = new JMenuItem("Add Student");
        addStudentItem.addActionListener(e -> showAddStudentForm());
        studentMenu.add(addStudentItem);

        JMenuItem viewStudentDetailsItem = new JMenuItem("View Student Details");
        viewStudentDetailsItem.addActionListener(e -> showStudentDetails());
        studentMenu.add(viewStudentDetailsItem);

        JMenuItem enrollStudentItem = new JMenuItem("Enroll Student");
        enrollStudentItem.addActionListener(e -> showEnrollStudentForm());
        studentMenu.add(enrollStudentItem);

        JMenuItem assignGradeItem = new JMenuItem("Assign Grade");
        assignGradeItem.addActionListener(e -> showAssignGradeForm());
        studentMenu.add(assignGradeItem);

        JMenuItem updateStudentItem = new JMenuItem("Update Student");
        updateStudentItem.addActionListener(e -> showUpdateStudentForm());
        studentMenu.add(updateStudentItem);

        menuBar.add(studentMenu);

        // Bug fix #5: table in CENTER so it resizes with the window; title+menu in NORTH
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(titleLabel, BorderLayout.NORTH);
        northPanel.add(menuBar, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);
        add(new JScrollPane(studentTable), BorderLayout.CENTER);

        setVisible(true);
    }

    private void showStudentDetails() {
        int rowCount = studentTableModel.getRowCount();
        String[] columnNames = {"ID", "Name", "Enrolled Courses", "Grades"};
        Object[][] data = new Object[rowCount][4];

        for (int i = 0; i < rowCount; i++) {
            String studentId = (String) studentTableModel.getValueAt(i, 0);
            String studentName = (String) studentTableModel.getValueAt(i, 1);
            List<String> enrolledCourses = studentCourseEnrollments.getOrDefault(studentId, new ArrayList<>());
            String coursesString = String.join(", ", enrolledCourses);

            HashMap<String, String> courseGrades = new HashMap<>();
            for (String course : enrolledCourses) {
                String key = studentId + "_" + course;
                courseGrades.put(course, studentGrades.getOrDefault(key, "N/A"));
            }

            String gradesString = courseGrades.entrySet().stream()
                    .map(entry -> entry.getKey() + ": " + entry.getValue())
                    .collect(Collectors.joining(", "));

            data[i][0] = studentId;
            data[i][1] = studentName;
            data[i][2] = coursesString;
            data[i][3] = gradesString;
        }

        JTable detailsTable = new JTable(data, columnNames);
        JOptionPane.showMessageDialog(this, new JScrollPane(detailsTable), "Student Details", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showUpdateStudentForm() {
        List<String> studentIds = getAllStudentIds();
        UpdateStudentForm form = new UpdateStudentForm(this, studentIds);
        form.setVisible(true);
    }

    public List<String> getAllStudentIds() {
        List<String> studentIds = new ArrayList<>();
        for (int i = 0; i < studentTableModel.getRowCount(); i++) {
            studentIds.add((String) studentTableModel.getValueAt(i, 0));
        }
        return studentIds;
    }

    public void showAddStudentForm() {
        AddStudentForm form = new AddStudentForm(this);
        form.setVisible(true);
    }

    public void showEnrollStudentForm() {
        List<String> studentIds = getAllStudentIds();
        EnrollStudentForm form = new EnrollStudentForm(this, studentIds);
        form.setVisible(true);
    }

    private void showAssignGradeForm() {
        AssignGradeForm form = new AssignGradeForm(this);
        form.setVisible(true);
    }

    public void addStudentToTable(String id, String name) {
        studentTableModel.addRow(new Object[]{id, name});
    }

    // Bug fix #4: migrates enrollment and grade data when a student's ID changes
    public void updateStudentInTable(int rowIndex, String newId, String newName) {
        String oldId = (String) studentTableModel.getValueAt(rowIndex, 0);
        studentTableModel.setValueAt(newId, rowIndex, 0);
        studentTableModel.setValueAt(newName, rowIndex, 1);

        if (!oldId.equals(newId) && studentCourseEnrollments.containsKey(oldId)) {
            List<String> courses = studentCourseEnrollments.remove(oldId);
            studentCourseEnrollments.put(newId, courses);
            for (String course : courses) {
                String oldKey = oldId + "_" + course;
                String newKey = newId + "_" + course;
                if (studentGrades.containsKey(oldKey)) {
                    studentGrades.put(newKey, studentGrades.remove(oldKey));
                }
            }
        }
    }

    public void removeStudentFromTable(int rowIndex) {
        studentTableModel.removeRow(rowIndex);
    }

    public void enrollStudentInCourse(String studentId, String course) {
        studentCourseEnrollments.computeIfAbsent(studentId, k -> new ArrayList<>()).add(course);
    }

    public void assignGrade(String studentId, String courseName, String grade) {
        studentGrades.put(studentId + "_" + courseName, grade);
    }

    // Bug fix #1 & #3: helpers used by forms to validate student existence and enrollment
    public boolean studentExists(String id) {
        return findRow(id) != -1;
    }

    public boolean isEnrolledInCourse(String studentId, String course) {
        List<String> courses = studentCourseEnrollments.get(studentId);
        return courses != null && courses.contains(course);
    }

    public int findRow(String id) {
        for (int i = 0; i < studentTableModel.getRowCount(); i++) {
            if (id.equals(studentTableModel.getValueAt(i, 0))) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentManagementSystem());
    }
}
