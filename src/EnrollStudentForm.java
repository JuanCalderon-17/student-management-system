import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class EnrollStudentForm extends JDialog {
    private JTextField studentIdField;
    private JComboBox<String> courseSelector;
    private StudentManagementSystem parent;

    public EnrollStudentForm(JFrame parent, List<String> studentNames) {
        super(parent, "Enroll Student", true);
        this.parent = (StudentManagementSystem) parent; 
        setSize(400, 200);
        setLayout(new GridLayout(4, 2));
    
        add(new JLabel("Student ID:"));
        studentIdField = new JTextField();
        add(studentIdField);
        add(new JLabel("Course:"));
        List<String> courseNames = Arrays.asList("Mathematics", "Physics", "Chemistry");
        courseSelector = new JComboBox<>(courseNames.toArray(new String[0]));
        add(courseSelector);
        add(new JLabel("Eligible Students:"));
        JList<String> studentList = new JList<>(studentNames.toArray(new String[0]));
        add(new JScrollPane(studentList));
    
        // Create the Enroll button 
        JButton enrollButton = new JButton("Enroll");
        enrollButton.addActionListener(e -> handleEnrollButtonClick());
        add(enrollButton);
    
        setLocationRelativeTo(parent);
    }

    private void handleEnrollButtonClick() {
        String studentId = studentIdField.getText();
        String course = (String) courseSelector.getSelectedItem();

        parent.enrollStudentInCourse(studentId, course);
        setVisible(false); 
    }
}


