import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AssignGradeForm extends JDialog {
    private JTextField studentIdField;
    private JTextField courseField;
    private JTextField gradeField;
    private StudentManagementSystem parent;

    public AssignGradeForm(JFrame parent) {
        super(parent, "Assign Grade", true);
        this.parent = (StudentManagementSystem) parent;
        setSize(400, 200);
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Student ID:"));
        studentIdField = new JTextField();
        add(studentIdField);
        add(new JLabel("Course:"));
        courseField = new JTextField();
        add(courseField);
        add(new JLabel("Grade:"));
        gradeField = new JTextField();
        add(gradeField);

        JButton assignButton = new JButton("Assign");
        assignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAssignButtonClick();
            }
        });
        add(assignButton);

        setLocationRelativeTo(parent);
    }

    private void handleAssignButtonClick() {
        String studentId = studentIdField.getText().trim();
        String course = courseField.getText().trim();
        String grade = gradeField.getText().trim();

        // Bug fix #3: validate all fields, student existence, and course enrollment
        if (studentId.isEmpty() || course.isEmpty() || grade.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!parent.studentExists(studentId)) {
            JOptionPane.showMessageDialog(this, "Student ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!parent.isEnrolledInCourse(studentId, course)) {
            JOptionPane.showMessageDialog(this, "Student is not enrolled in " + course + ".", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        parent.assignGrade(studentId, course, grade);
        JOptionPane.showMessageDialog(this, "Grade assigned successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        setVisible(false);
    }
}
