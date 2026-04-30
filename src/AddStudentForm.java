import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddStudentForm extends JDialog {
    private JTextField idField;
    private JTextField nameField;
    private StudentManagementSystem parent;

    public AddStudentForm(StudentManagementSystem parent) {
        super(parent, "Add Student", true);
        this.parent = parent;
        setSize(400, 200);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);
        add(new JLabel("ID:"));
        idField = new JTextField();
        add(idField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmitButtonClick();
            }
        });
        add(addButton);

        setLocationRelativeTo(parent);
    }

    private void handleSubmitButtonClick() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();

        // Bug fix #1: validate ID is not empty and not a duplicate
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (parent.studentExists(id)) {
            JOptionPane.showMessageDialog(this, "A student with this ID already exists.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (name.isEmpty() || !name.matches("[a-zA-Z\\s]+")) {
            JOptionPane.showMessageDialog(this, "Invalid name. Please enter a name without numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        parent.addStudentToTable(id, name);
        setVisible(false);
    }
}
