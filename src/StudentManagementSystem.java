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
    private HashMap<String, List<String>> studentCourseEnrollments = new HashMap<>(); // Data structure for storing course enrollments


    public StudentManagementSystem() {
        setTitle("Student Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize the table model
        studentTableModel = new DefaultTableModel(new Object[]{"ID", "Name"}, 0);
        JTable studentTable = new JTable(studentTableModel);

        // Create a panel for the title
        JPanel titlePanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Student Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel, BorderLayout.CENTER);

        // Create a panel for the menu
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
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
        menuPanel.add(menuBar);

        // Create a panel for the student table
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(studentTable), BorderLayout.CENTER);

        // Add the panels to the frame
        add(titlePanel, BorderLayout.NORTH);
        add(menuPanel, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void showStudentDetails() {
    // Dynamically fetch the current list of students from the studentTableModel
    int rowCount = studentTableModel.getRowCount();
    String[] columnNames = {"ID", "Name", "Enrolled Courses", "Grades"};
    Object[][] data = new Object[rowCount][4];

    for (int i = 0; i < rowCount; i++) {
        String studentId = (String) studentTableModel.getValueAt(i, 0); 
        String studentName = (String) studentTableModel.getValueAt(i, 1); 
        List<String> enrolledCourses = studentCourseEnrollments.getOrDefault(studentId, new ArrayList<>());
        String coursesString = String.join(", ", enrolledCourses);

        // Prepare a map to store course-grade 
        HashMap<String, String> courseGrades = new HashMap<>();
        for (String course : enrolledCourses) {
            String key = studentId + "_" + course; 
            String grade = studentGrades.getOrDefault(key, "N/A"); 
            courseGrades.put(course, grade);
        }

        String gradesString = courseGrades.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(", "));

        data[i][0] = studentId;
        data[i][1] = studentName;
        data[i][2] = coursesString; 
        data[i][3] = gradesString; 
    }

    JTable studentTable = new JTable(data, columnNames);
    JScrollPane scrollPane = new JScrollPane(studentTable);
    JOptionPane.showMessageDialog(this, scrollPane, "Student Details", JOptionPane.INFORMATION_MESSAGE);
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
        List<String> studentNames = getAllStudentNames();
        EnrollStudentForm form = new EnrollStudentForm(this, studentNames);
        form.setVisible(true);
    }    
    public List<String> getAllStudentNames() {
        List<String> studentNames = new ArrayList<>();
        for (int i = 0; i < studentTableModel.getRowCount(); i++) {
            studentNames.add((String) studentTableModel.getValueAt(i, 1));
        }
        return studentNames;
    }
    private void showAssignGradeForm() {
        AssignGradeForm form = new AssignGradeForm(this);
        form.setVisible(true);
    }
    public void addStudentToTable(String id, String name) {
        studentTableModel.addRow(new Object[]{id, name});
    }
    public void updateStudentInTable(int rowIndex, String id, String name) {
        studentTableModel.setValueAt(id, rowIndex, 0);
        studentTableModel.setValueAt(name, rowIndex, 1);
    }
    public void removeStudentFromTable(int rowIndex) {
        studentTableModel.removeRow(rowIndex);
    }
    public void enrollStudentInCourse(String studentId, String course) {
        // Check if the student is already enrolled in any courses
        if (!studentCourseEnrollments.containsKey(studentId)) {
            studentCourseEnrollments.put(studentId, new ArrayList<>());
        }
        studentCourseEnrollments.get(studentId).add(course);
        System.out.println("Enrolled student " + studentId + " in course " + course);
    }
    // Find a student's row index by their ID
    public int findRow(String id) {
        for (int i = 0; i < studentTableModel.getRowCount(); i++) {
            if (id.equals(studentTableModel.getValueAt(i, 0))) { 
                return i;
            }
        }
        return -1; 
    }    
    public void addNewStudent(String id, String name) {
        addStudentToTable(id, name);
    }
    public void assignGrade(String studentId, String courseName, String grade) {
        String key = studentId + "_" + courseName; 
        studentGrades.put(key, grade); 
        System.out.println("Grade assigned: " + grade + " for student " + studentId + " in course " + courseName);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentManagementSystem());
    }
}

