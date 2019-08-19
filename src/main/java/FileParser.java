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

import static files.ReadTabDelimited.removeAllFileDefinitionByExclusionList;

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
    private JTextField textFieldBank;
    private JTextField textFieldAgency;
    private JButton removeButton;
    private JButton addButton;
    private JButton buttonOK;
    private String sourceFilePath;

    private FileParser() {
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        defaultTableModel.setColumnIdentifiers(header);
        tableAgencyBank.setModel(defaultTableModel);
        textFieldBank.setColumns(5);
        textFieldAgency.setColumns(5);
        refreshModel();

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
        excludeDataFromTableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sourceFilePath != null && !sourceFilePath.isEmpty()) {
                    removeAllFileDefinitionByExclusionList(exclusions.getExclusionList());
                    JOptionPane.showConfirmDialog(null, "Lines were excluded from source file.\nPlease save the result via the Save button!", "Execution complete",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showConfirmDialog(null, "Please select a source file ! ", "Error",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExclusionDefinition exclusionDefinition = new ExclusionDefinition(textFieldBank.getText(), textFieldAgency.getText());
                exclusions.removeExclusionFromList(exclusionDefinition);
                refreshModel();
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ExclusionDefinition exclusionDefinition = new ExclusionDefinition(textFieldBank.getText(), textFieldAgency.getText());
                exclusions.addExclusionToList(exclusionDefinition);
                refreshModel();
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
        defaultTableModel.getDataVector().removeAllElements();
        if (exclusions == null || exclusions.getExclusionList().size() == 0) {
            exclusions.initializeExclusionList();
        }

        for (ExclusionDefinition exclusionDefinition : exclusions.getExclusionList()) {
            Vector<Object> data = new Vector<Object>();
            if (!(!exclusionDefinition.getAgence().isEmpty() &&
                    Integer.parseInt(exclusionDefinition.getAgence().trim()) > 0 &&
                    Integer.parseInt(exclusionDefinition.getAgence().trim()) <= 1999 &&
                    exclusionDefinition.getEtab().equals("99999"))) {
                data.add(exclusionDefinition.getEtab().equals("99999") ? "ALL" : exclusionDefinition.getEtab());
                data.add(exclusionDefinition.getAgence().equals("99999") ? "ALL" : exclusionDefinition.getAgence());
                defaultTableModel.addRow(data);
            }
        }
    }
}
