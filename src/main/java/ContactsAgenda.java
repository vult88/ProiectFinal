import ads.Ads;
import com.intellij.uiDesigner.core.GridConstraints;
import forms.AdsForm;
import forms.FileMenuForm;
import model.Agenda;
import model.Contact;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static ads.Ads.isFlagShowAds;
import static constants.ActivationKey.verifyActivationKey;

/**
 * Created by Vult on 01-Jun-18.
 * The main body... the soul... the mind...
 */
// TODO Replace all listeners with Lambda functions
public class ContactsAgenda {
    private static FileMenuForm fileMenuForm = new FileMenuForm();
    private static AdsForm adsForm = new AdsForm();
    private static DefaultListModel<Contact> contactsListModel;
    private JPanel mainPanel;
    private JButton filterButton;
    private JTextField customFilterTextField;
    private JComboBox filterComboBox;
    private JButton orderButton;
    private JComboBox orderComboBox;
    private JButton addContactButton;
    private JButton deleteContactButton;
    private JButton editContactButton;
    private JScrollPane contactsScrollPane;
    private JList<Contact> contactsList;
    private String customFilterText;

    private ContactsAgenda() {
        contactsListModel = new DefaultListModel<>();
        contactsList.setModel(contactsListModel);

        filterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        FileMenuForm.getExitApp().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (confirmExitApplication() == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        FileMenuForm.getRegisterApp().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String inputActivationKey = JOptionPane.showInputDialog("Enter activation key:");
                if (verifyActivationKey(inputActivationKey)) {
                    Ads.setFlagShowAds(false);
                    JOptionPane.showConfirmDialog(null, "Product is now active !", "Activation OK",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showConfirmDialog(null, "Activation key is incorrect !", "Invalid key",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        orderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        addContactButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ContactWindow contactWindow = new ContactWindow(null, ContactWindow.NEW_CONTACT);
                contactWindow.pack();
                contactWindow.setVisible(true);
                refreshModel();
            }
        });
        customFilterTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                customFilterText = customFilterTextField.getText();
                refreshModel();
            }
        });
        contactsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (e.getClickCount() == 2) {
                    modifyContact();
                }
            }
        });
        editContactButton.addActionListener((e) -> modifyContact());

        deleteContactButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Contact contact = contactsList.getSelectedValue();
                int dialogOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove " +
                                contact.getFirstName() + " " +
                                contact.getLastName() + "from the list ?", "Confirm delete",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (dialogOption == JOptionPane.YES_OPTION) {
                    Agenda.removeContact(contact);
                    refreshModel();
                }
            }
        });
    }

    public static void main(String[] args) {
        AdsForm adsForm = new AdsForm();
        adsForm.addAdsPictureToPanel();
        JFrame frame = new JFrame("Contacts Agenda");
        frame.setContentPane(new ContactsAgenda().mainPanel);
        frame.setJMenuBar(FileMenuForm.getMenuBar());
        frame.add(adsForm, new GridConstraints(4, 0, 1, 5, 0, 3, 1, 4, new Dimension(-1, 100), new Dimension(-1, 100), new Dimension(-1, 100)));
        frame.setLocation(200, 200);
        frame.pack();
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                if (confirmExitApplication() == JOptionPane.YES_OPTION) {
                    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                } else {
                    frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                }
            }
        });

        if (isFlagShowAds()) {
            Thread ads = new Thread(new Ads(), "Thread-ads");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ads.start();
            Ads.setModeShareware(!isFlagShowAds());
        }
    }

    private static int confirmExitApplication() {
        return JOptionPane.showConfirmDialog(null, "Are you sure you want to exit ?", "Exit",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

    private void refreshModel() {
        contactsListModel.clear();

        if (Agenda.getContacts() != null && Agenda.getContacts().size() > 0) {
            for (Contact contact : Agenda.getContacts()) contactsListModel.addElement(contact);
        }
//        agenda.stream().filter().sorted;
    }

    private void modifyContact() {
        ContactWindow contactWindow = new ContactWindow(contactsList.getSelectedValue(), ContactWindow.MODIFY_CONTACT);
        contactWindow.pack();
        contactWindow.setVisible(true);
        refreshModel();
    }
}
