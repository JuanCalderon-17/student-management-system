import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UpdateStudentForm extends JDialog {
    private JComboBox<String> studentSelector;
    private JTextField newNameField;
    private JTextField newIdField;
    private StudentManagementSystem parent;

    public UpdateStudentForm(JFrame parent, List<String> studentIds) {
        super(parent, "Update Student", true);
        this.parent = (StudentManagementSystem) parent;
        setSize(400, 200);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(new JLabel("Select Student:"));
        studentSelector = new JComboBox<>(studentIds.toArray(new String[0]));
        add(studentSelector);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(new JLabel("New Name:"));
        newNameField = new JTextField();
        add(newNameField);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(new JLabel("New ID:"));
        newIdField = new JTextField();
        add(newIdField);
        add(Box.createRigidArea(new Dimension(0, 10)));

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> handleUpdateButtonClick());
        add(updateButton);

        setLocationRelativeTo(parent);
    }

    private void handleUpdateButtonClick() {
        String selectedStudent = (String) studentSelector.getSelectedItem();
        String newName = newNameField.getText().trim();
        String newId = newIdField.getText().trim();

        if (selectedStudent == null) {
            JOptionPane.showMessageDialog(this, "No students available.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (newId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "New ID cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (newName.isEmpty() || !newName.matches("[a-zA-Z\\s]+")) {
            JOptionPane.showMessageDialog(this, "Invalid name. Please enter a name without numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Bug fix #4 (part 2): block duplicate IDs unless keeping the same ID
        if (!newId.equals(selectedStudent) && parent.studentExists(newId)) {
            JOptionPane.showMessageDialog(this, "A student with ID " + newId + " already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int rowIndex = parent.findRow(selectedStudent);
        if (rowIndex != -1) {
            parent.updateStudentInTable(rowIndex, newId, newName);
            JOptionPane.showMessageDialog(this, "Student updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "Student not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
