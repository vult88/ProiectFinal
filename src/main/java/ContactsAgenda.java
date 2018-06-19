import ads.Ads;
import com.intellij.uiDesigner.core.GridConstraints;
import enums.FilterCriteria;
import enums.OrderCriteria;
import files.FilesHandler;
import forms.AdsForm;
import forms.FileMenuForm;
import model.Agenda;
import model.Contact;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

import static ads.Ads.isFlagShowAds;
import static constants.ActivationKey.verifyActivationKey;

/**
 * Created by Vult on 01-Jun-18.
 * The main body... the soul... the mind...
 */
public class ContactsAgenda {
    private static FileMenuForm fileMenuForm = new FileMenuForm();
    private static AdsForm adsForm = new AdsForm();
    private static DefaultListModel<Contact> contactsListModel;
    private static JFrame frame;
    private JPanel mainPanel;
    private JComboBox<String> filterComboBox;
    private JTextField customFilterTextField;
    private String customFilterText;
    private JButton orderButton;
    private JComboBox<String> orderComboBox;
    private JButton addContactButton;
    private JButton editContactButton;
    private JButton deleteContactButton;
    private JScrollPane contactsScrollPane;
    private JList<Contact> contactsList;
    private JCheckBox orderDescendingCheckBox;
    private OrderCriteria orderCriteria;

    private Agenda agenda = new Agenda();

    private ContactsAgenda() {
        contactsListModel = new DefaultListModel<>();
        contactsList.setModel(contactsListModel);
        customFilterTextField.setEnabled(false);

        setFilterComboBoxModel();
        setOrderComboBoxModel();
        orderContacts();

        filterComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    filterContacts();
                    customFilterTextField.setText("");
                    customFilterText = "";
                    if (FilterCriteria.fromString(filterComboBox.getModel().getSelectedItem().toString()) == FilterCriteria.CUSTOM_FILTER) {
                        customFilterTextField.setEnabled(true);
                    } else {
                        customFilterTextField.setEnabled(false);
                    }
                    refreshModel();
                }
            }
        });
        customFilterTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                customFilterText = customFilterTextField.getText();
                filterContacts();
                refreshModel();
            }
        });
        orderComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                orderContacts();
            }
        });
        orderDescendingCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                orderContacts();
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
        FileMenuForm.getAboutApp().addActionListener(e ->
                JOptionPane.showConfirmDialog(null, "Student : Hriscanu Andrei Silviu" + "\n" +
                                "The current application is an agenda book capable " + "\n" +
                                "of holding names, dates of birth and phone numbers." + "\n" + "\n" +
                                "Enjoy testing it out !" + "\n" + "\n" +
                                "Activation key is : activateproduct-1234", "About application",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE));
        FileMenuForm.getOpenFile().addActionListener(e -> {
            FilesHandler.setAgenda(agenda);
            FilesHandler.openFile();
            refreshModel();
        });
        FileMenuForm.getSaveFile().addActionListener(e -> {
            FilesHandler.setAgenda(agenda);
            FilesHandler.saveFile();
            startAutoSaving();
        });

        addContactButton.addActionListener(e -> contactWindow(null, ContactWindow.NEW_CONTACT));
        editContactButton.addActionListener(e -> contactWindow(contactsList.getSelectedValue(), ContactWindow.MODIFY_CONTACT));
        contactsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                if (e.getClickCount() == 2) {
                    contactWindow(contactsList.getSelectedValue(), ContactWindow.MODIFY_CONTACT);
                }
            }
        });
        deleteContactButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Contact contact = contactsList.getSelectedValue();
                if (contact == null) {
                    JOptionPane.showConfirmDialog(null, "Please select a contact from the list !", "Invalid selection",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                } else {
                    int dialogOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove " +
                                    contact.getFirstName() + " " +
                                    contact.getLastName() + " from the list ?", "Confirm delete",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (dialogOption == JOptionPane.YES_OPTION) {
                        agenda.removeContact(contact);
                        refreshModel();
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        adsForm = new AdsForm();
        adsForm.addAdsPictureToPanel();
        frame = new JFrame("Contacts Agenda");
        frame.setContentPane(new ContactsAgenda().mainPanel);
        frame.setJMenuBar(FileMenuForm.getMenuBar());
        frame.add(adsForm, new GridConstraints(4, 0, 1, 4, 0, 3, 1, 4, new Dimension(-1, 100), new Dimension(-1, 100), new Dimension(-1, 100)));
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

        splashScreen();
        removeAdsFormTimeTask();
    }

    private static int confirmExitApplication() {
        return JOptionPane.showConfirmDialog(null, "Are you sure you want to exit ?", "Exit",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

    private static void splashScreen() {
        SplashScreenDialog splashScreenDialog = new SplashScreenDialog();
        splashScreenDialog.setLocation(400, 400);
        splashScreenDialog.pack();
        splashScreenDialog.setVisible(true);
    }

    //    Because it is not possible to call methods of frame from another class that is located in another package, we
    //    have to verify from time to time that the product has been activated. If it was activated, we remove the form
    //    from the frame to make it look more bigger and natural.
    private static void removeAdsFormTimeTask() {
        TimerTask removeAdsForm = new TimerTask() {
            @Override
            public void run() {
                if (!isFlagShowAds()) {
                    frame.remove(adsForm);
                    frame.pack();
                    cancel();
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(removeAdsForm, 1000, 1000);
    }

    private void refreshModel() {
        contactsListModel.clear();
        if (agenda.getContacts() != null && agenda.getContacts().size() > 0) {
            if (orderDescendingCheckBox.isSelected()) {
                agenda.getContacts()
                        .stream()
                        .filter(agenda.getFilterCriteria())
                        .sorted(agenda.setAndGetCriteriaToComparator(orderCriteria).reversed())
                        .forEach(contactsListModel::addElement);
            } else {
                agenda.getContacts()
                        .stream()
                        .filter(agenda.getFilterCriteria())
                        .sorted(agenda.setAndGetCriteriaToComparator(orderCriteria))
                        .forEach(contactsListModel::addElement);
            }
        }
    }

    private void contactWindow(Contact contact, int windowType) {
        if (windowType == ContactWindow.MODIFY_CONTACT && contact == null) {
            JOptionPane.showConfirmDialog(null, "Please select a contact from the list !", "Invalid selection",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
        } else {
            ContactWindow contactWindow = new ContactWindow(contact, windowType);
            contactWindow.setAgenda(agenda);
            contactWindow.pack();
            contactWindow.setVisible(true);
            refreshModel();
        }
    }

    private void setFilterComboBoxModel() {
        DefaultComboBoxModel<String> filterCriteriaComboBoxModel = new DefaultComboBoxModel<>();
        for (FilterCriteria filterCriteria : FilterCriteria.values()
                ) {
            filterCriteriaComboBoxModel.addElement(FilterCriteria.enumToText(filterCriteria));
        }
        filterComboBox.setModel(filterCriteriaComboBoxModel);
    }

    private void setOrderComboBoxModel() {
        DefaultComboBoxModel<String> orderCriteriaComboBoxModel = new DefaultComboBoxModel<>();
        for (OrderCriteria orderCriteria : OrderCriteria.values()
                ) {
            orderCriteriaComboBoxModel.addElement(OrderCriteria.enumToText(orderCriteria));
        }
        orderComboBox.setModel(orderCriteriaComboBoxModel);
    }

    private void orderContacts() {
        orderCriteria = OrderCriteria.fromString(orderComboBox.getModel().getSelectedItem().toString());
        refreshModel();
    }

    private void filterContacts() {
        switch (FilterCriteria.fromString(filterComboBox.getModel().getSelectedItem().toString())) {
            case SHOW_TELEPHONE_TYPE_MOBILE:
                agenda.filterOnMobileTelephoneType();
                break;
            case SHOW_TELEPHONE_TYPE_FIXED:
                agenda.filterOnFixedTelephoneType();
                break;
            case SHOW_BORN_CURRENT_MONTH:
                agenda.filterOnBornThisMonth();
                break;
            case SHOW_BORN_TODAY:
                agenda.filterOnBornToday();
                break;
            case CUSTOM_FILTER:
                if (customFilterText != null && customFilterText.length() > 0) {
                    agenda.filterOnCustomFilter(customFilterText);
                } else {
                    agenda.filterOnNothing();
                }
                break;
            case SHOW_ALL:
            default:
                agenda.filterOnNothing();
        }
    }

    private void startAutoSaving() {
        TimerTask autoSaving = new TimerTask() {
            @Override
            public void run() {
                FilesHandler.autoSave(agenda);
            }
        };
        Timer timer = new Timer();
        timer.schedule(autoSaving, 60000, 60000);
    }
}
