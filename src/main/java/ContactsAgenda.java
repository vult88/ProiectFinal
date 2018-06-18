import ads.Ads;
import com.intellij.uiDesigner.core.GridConstraints;
import forms.AdsForm;
import forms.FileMenuForm;
import interfaces.ThrowConsumer;
import model.Agenda;
import model.Contact;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

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
    private static JFrame frame;
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

        filterButton.addActionListener(e -> filterContacts());
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
                                "Enjoy testing it out !", "About application",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE));
        FileMenuForm.getOpenFile().addActionListener(e -> openFile());
        FileMenuForm.getSaveFile().addActionListener(e -> saveFile());
        orderButton.addActionListener(e -> orderContacts());
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
        editContactButton.addActionListener(e -> modifyContact());
        deleteContactButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Contact contact = contactsList.getSelectedValue();
                int dialogOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove " +
                                contact.getFirstName() + " " +
                                contact.getLastName() + " from the list ?", "Confirm delete",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (dialogOption == JOptionPane.YES_OPTION) {
                    Agenda.removeContact(contact);
                    refreshModel();
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
        frame.add(adsForm, new GridConstraints(4, 0, 1, 5, 0, 3, 1, 4, new Dimension(-1, 100), new Dimension(-1, 100), new Dimension(-1, 100)));
        frame.setLocation(200, 200);
        frame.pack();
        frame.setVisible(true);

//      For debugging purposes only
        Ads.setFlagShowAds(false);
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

    private void saveFile() {
        JFileChooser fileChooser = createFileChooser();
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile.exists()) {
                if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(null, "Are you sure you want to overwrite " + selectedFile.getAbsolutePath() + "?", "Confirm Save",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) ;
            }
            treatFileException(selectedFile, file -> writeToFile(file));
        }
    }

    private void openFile() {
        JFileChooser fileChooser = createFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            treatFileException(selectedFile, file -> readFromFile(selectedFile));
        }
    }

    //    Method that implements a Consumer to treat errors from File Open/Save operations.
//    It's to group them together and reuse code.
    private void treatFileException(File file, ThrowConsumer fileWork) {
        try {
            fileWork.accept(file);
        } catch (FileNotFoundException e) {
            JOptionPane.showConfirmDialog(null, "File " + file.getAbsoluteFile() + " not found !", "Error",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showConfirmDialog(null, "Not enough rights over file " + file.getAbsoluteFile(), "Error",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showConfirmDialog(null, "XXXX " + file.getAbsoluteFile(), "Error",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
        }
    }

    private void writeToFile(File selectedFile) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(selectedFile.getAbsolutePath());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(Agenda.getContacts());
        objectOutputStream.close();
    }

    private void readFromFile(File selectedFile) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(selectedFile.getAbsolutePath());
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Set<Contact> contactsRead = (Set<Contact>) objectInputStream.readObject();
        objectInputStream.close();
//        Populating the agenda
        Agenda.renewContacts(contactsRead);
        refreshModel();
    }

    private JFileChooser createFileChooser() {
        javax.swing.filechooser.FileFilter fileFilter = new FileNameExtensionFilter("Agenda File", "agenda");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(fileFilter);
        return fileChooser;
    }

    private void orderContacts() {

    }

    private void filterContacts() {

    }
}
