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

        // Create the Assign button 
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
        String studentId = studentIdField.getText();
        String course = courseField.getText();
        String grade = gradeField.getText();

        parent.assignGrade(studentId, course, grade);
        setVisible(false); 
    }
}
