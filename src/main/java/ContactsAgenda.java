import Ads.Ads;
import Forms.AdsForm;
import Forms.FileMenuForm;
import Model.Agenda;
import com.intellij.uiDesigner.core.GridConstraints;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import static Ads.Ads.isFlagShowAds;

/**
 * Created by Vult on 01-Jun-18.
 */
public class ContactsAgenda {
    private static FileMenuForm fileMenuForm = new FileMenuForm();
    private static AdsForm adsForm = new AdsForm();
    private static DefaultListModel contactsListModel;
    private JComboBox filterComboBox;
    private JComboBox orderComboBox;
    private JButton filterButton;
    private JButton orderButton;
    private JTextField customFilterTextField;
    private JButton addContactButton;
    private JButton deleteContactButton;
    private JButton editContactButton;
    private JScrollPane contactsScrollPane;
    private JPanel mainPanel;
    private JList contactsList;
    private String customFilterText;
    private List<Agenda> agenda;

    public ContactsAgenda() {
        contactsListModel = new DefaultListModel();
        contactsList.setModel(contactsListModel);

        filterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        FileMenuForm.exitApp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        FileMenuForm.registerApp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Ads.setFlagShowAds(false);
            }
        });
        orderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        editContactButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        deleteContactButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        addContactButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        customFilterTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                customFilterText = customFilterTextField.getText();
                System.out.println(customFilterText);
                refreshModel();
            }
        });
    }

    public static void main(String[] args) {
        AdsForm adsForm = new AdsForm();
        adsForm.addAdsPictureToPanel();
        JFrame frame = new JFrame("ContactsAgenda");
        frame.setContentPane(new ContactsAgenda().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(FileMenuForm.menuBar);
        frame.add(adsForm, new GridConstraints(4, 0, 1, 5, 0, 3, 1, 4, new Dimension(-1, 100), new Dimension(-1, 100), new Dimension(-1, 100)));
        frame.pack();
        frame.setVisible(true);

        if (isFlagShowAds()) {
            Thread ads = new Thread(new Ads(), "Thread-Ads");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ads.start();
        }
    }

    private void refreshModel() {
        contactsList.removeAll();
//        agenda.stream().filter().sorted;
    }
}
