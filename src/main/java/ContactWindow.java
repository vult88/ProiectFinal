import enums.TelephoneType;
import exceptions.InvalidDateFormatException;
import model.Agenda;
import model.Contact;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ContactWindow extends JDialog {
    static final int NEW_CONTACT = 1;
    static final int MODIFY_CONTACT = 2;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField telephoneNumberTextField;
    private JTextField dateOfBirthTextField;
    private JComboBox<TelephoneType> telephoneTypeComboBox;

    ContactWindow(Contact contact, int windowOption) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setTitle(windowOption == NEW_CONTACT ? "New Contact" : "Modify contact");
        setLocation(300, 300);

        setComboBoxModel();

        if (windowOption == MODIFY_CONTACT) {
            populateFieldsForUpdate(contact);
        }

        buttonOK.addActionListener((e) -> onOK(contact, windowOption));

        buttonCancel.addActionListener((e) -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction((e) -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK(Contact contact, int windowOption) {
        try {
            checkValidations();
            if (windowOption == NEW_CONTACT) {
                Contact newContact = new Contact(firstNameTextField.getText(),
                        lastNameTextField.getText(),
                        convertStringToLocalDate(dateOfBirthTextField.getText()),
                        telephoneNumberTextField.getText(),
                        (TelephoneType) telephoneTypeComboBox.getModel().getSelectedItem());
                Agenda.addContact(newContact);
                if (newContact.getDateOfBirth().getDayOfMonth() == LocalDate.now().getDayOfMonth() &&
                        newContact.getDateOfBirth().getMonth() == LocalDate.now().getMonth()) {
                    JOptionPane.showConfirmDialog(null, "La multi ani " + newContact.getFirstName() + " " + newContact.getLastName() + " !", "Happy birthday!",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                contact.setFirstName(firstNameTextField.getText());
                contact.setLastName(lastNameTextField.getText());
                contact.setDateOfBirth(convertStringToLocalDate(dateOfBirthTextField.getText()));
                contact.setPhoneNumber(telephoneNumberTextField.getText(), (TelephoneType) telephoneTypeComboBox.getModel().getSelectedItem());
                contact.setTelephoneType((TelephoneType) telephoneTypeComboBox.getModel().getSelectedItem());
                if (contact.getDateOfBirth().getDayOfMonth() == LocalDate.now().getDayOfMonth() &&
                        contact.getDateOfBirth().getMonth() == LocalDate.now().getMonth()) {
                    JOptionPane.showConfirmDialog(null, "La multi ani " + contact.getFirstName() + " " + contact.getLastName() + " !", "Happy birthday!",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                }
            }
            dispose();
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e.getMessage(), "Invalid data",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private LocalDate convertStringToLocalDate(String date) {
        String[] splitterDate = date.split("\\.");
        int day = Integer.parseInt(splitterDate[0]);
        int month = Integer.parseInt(splitterDate[1]);
        int year = Integer.parseInt(splitterDate[2]);
        return LocalDate.of(year, month, day);
    }

    private void checkValidations() throws Exception {

        String regex = "\\d{2}\\.\\d{2}\\.\\d{4}";

        if (!dateOfBirthTextField.getText().matches(regex)) {
            System.out.println(regex);
            throw new InvalidDateFormatException("Date of birth is not a valid format. Please input a date using the format DD.MM.YYYY");
        }
    }

    private void setComboBoxModel() {
        DefaultComboBoxModel<TelephoneType> telephoneTypeComboBoxModel = new DefaultComboBoxModel<>();
        telephoneTypeComboBoxModel.addElement(TelephoneType.Mobile);
        telephoneTypeComboBoxModel.addElement(TelephoneType.Fixed);
        telephoneTypeComboBox.setModel(telephoneTypeComboBoxModel);
    }

    private void populateFieldsForUpdate(Contact contact) {
        firstNameTextField.setText(contact.getFirstName());
        lastNameTextField.setText(contact.getLastName());
        dateOfBirthTextField.setText(contact.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        telephoneNumberTextField.setText(contact.getPhoneNumber());
        telephoneTypeComboBox.setSelectedItem(contact.getTelephoneType());
    }
}
