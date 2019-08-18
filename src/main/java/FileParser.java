import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FileParser extends JDialog {
    private static JFrame frame;
    private JPanel contentPane;
    private JTextField textSourceFile;
    private JButton openButton;
    private JTable tableAgencyBank;
    private JButton saveFileButton;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel mainPanel;

    public FileParser() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
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
}
