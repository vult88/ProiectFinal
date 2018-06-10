import Enums.TelephoneType;
import Exceptions.DateInTheFutureException;

import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDate;

public class ContactWindow extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField telephoneNumberTextField;
    private JTextField dateOfBirthTextField;
    private JComboBox telephoneTypeComboBox;

    public ContactWindow() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setComboBoxModel();

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        try {
            checkValidations();
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e.getMessage(), "Invalid data",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public String getFirstNameValue() {
        return firstNameTextField.getText();
    }

    public String getLastNameValue() {
        return lastNameTextField.getText();
    }

    public String getTelephoneNumberValue() {
        return telephoneNumberTextField.getText();
    }

    public LocalDate getDateOfBirthTextField() {
        return convertStringToLocalDate(dateOfBirthTextField.getText());
    }

    public JComboBox getTelephoneTypeComboBox() {
        return telephoneTypeComboBox;
    }

    private LocalDate convertStringToLocalDate(String date) {
        String[] splitterDate = date.split("\\.");
        int day = Integer.parseInt(splitterDate[0]);
        int month = Integer.parseInt(splitterDate[1]);
        int year = Integer.parseInt(splitterDate[2]);
        return LocalDate.of(year, month, day);
    }

    private void checkValidations() throws Exception {

        LocalDate dateOfBirth = getDateOfBirthTextField();
        if (dateOfBirth.isAfter(LocalDate.now())) {
            throw new DateInTheFutureException(dateOfBirth);
        }
    }

    private void setComboBoxModel() {
        DefaultComboBoxModel telephoneTypeComboBoxModel = new DefaultComboBoxModel();
        telephoneTypeComboBoxModel.addElement(TelephoneType.Mobile);
        telephoneTypeComboBoxModel.addElement(TelephoneType.Fixed);
        telephoneTypeComboBox.setModel(telephoneTypeComboBoxModel);
    }
}
