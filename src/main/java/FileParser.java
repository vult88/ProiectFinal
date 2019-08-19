import constants.Exclusions;
import files.FilesHandler;
import model.ExclusionDefinition;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

public class FileParser extends JDialog {
    private static final String[] header = {"Banque", "Agence"};
    private static JFrame frame;
    private static Exclusions exclusions = new Exclusions();
    private static DefaultTableModel defaultTableModel = new DefaultTableModel(0, 0);
    private JPanel contentPane;
    private JTextField textSourceFile;
    private JButton openButton;
    private JTable tableAgencyBank;
    private JButton saveFileButton;
    private JButton excludeDataFromTableButton;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel mainPanel;
    private String sourceFilePath;

    private FileParser() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        defaultTableModel.setColumnIdentifiers(header);
        tableAgencyBank.setModel(defaultTableModel);

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sourceFilePath = FilesHandler.openFile().toString();
                textSourceFile.setText(sourceFilePath);
                refreshModel();
            }
        });
        saveFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FilesHandler.saveFile();
            }
        });
    }

    private static int confirmExitApplication() {
        return JOptionPane.showConfirmDialog(null, "Are you sure you want to exit ?", "Exit",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

    public static void main(String[] args) {
        frame = new JFrame("File parser");
        frame.setContentPane(new FileParser().contentPane);
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
    }

    private static void refreshModel() {

        if (exclusions != null && exclusions.getExclusionList().isEmpty()) {
            exclusions.initializeExclusionList();
        }

        for (ExclusionDefinition exclusionDefinition : exclusions.getExclusionList()) {
            Vector<Object> data = new Vector<Object>();
            data.add(exclusionDefinition.getEtab().equals("99999") ? "ALL" : exclusionDefinition.getEtab());
            data.add(exclusionDefinition.getAgence().equals("99999") ? "ALL" : exclusionDefinition.getAgence());
            defaultTableModel.addRow(data);
        }
    }
}
