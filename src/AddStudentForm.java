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

        // Create the "Add" button and add an action listener
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
        String id = idField.getText();
        String name = nameField.getText();

        // Validate inputs
        if (name.isEmpty() || !name.matches("[a-zA-Z\\s]+")) {
            JOptionPane.showMessageDialog(this, "Invalid name. Please enter a name without numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            return; 
        }
        parent.addNewStudent(id, name);
        setVisible(false); 
    }

    public String getEnteredId() {
        return idField.getText();
    }
    public String getEnteredName() {
        return nameField.getText();
    }
}

