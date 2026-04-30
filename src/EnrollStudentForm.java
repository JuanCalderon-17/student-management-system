import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class EnrollStudentForm extends JDialog {
    // Bug fix #2: replaced manual text field + decorative JList with a real dropdown of IDs
    private JComboBox<String> studentSelector;
    private JComboBox<String> courseSelector;
    private StudentManagementSystem parent;

    public EnrollStudentForm(JFrame parent, List<String> studentIds) {
        super(parent, "Enroll Student", true);
        this.parent = (StudentManagementSystem) parent;
        setSize(400, 180);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Student ID:"));
        studentSelector = new JComboBox<>(studentIds.toArray(new String[0]));
        add(studentSelector);

        add(new JLabel("Course:"));
        List<String> courseNames = Arrays.asList("Mathematics", "Physics", "Chemistry");
        courseSelector = new JComboBox<>(courseNames.toArray(new String[0]));
        add(courseSelector);

        JButton enrollButton = new JButton("Enroll");
        enrollButton.addActionListener(e -> handleEnrollButtonClick());
        add(enrollButton);

        setLocationRelativeTo(parent);
    }

    private void handleEnrollButtonClick() {
        String studentId = (String) studentSelector.getSelectedItem();
        String course = (String) courseSelector.getSelectedItem();

        if (studentId == null) {
            JOptionPane.showMessageDialog(this, "No students available to enroll.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (parent.isEnrolledInCourse(studentId, course)) {
            JOptionPane.showMessageDialog(this, "Student is already enrolled in " + course + ".", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        parent.enrollStudentInCourse(studentId, course);
        JOptionPane.showMessageDialog(this, "Enrolled in " + course + " successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        setVisible(false);
    }
}
