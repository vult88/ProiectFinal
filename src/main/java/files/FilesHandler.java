package files;

import interfaces.ThrowConsumer;
import model.Agenda;
import model.Contact;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.Set;

/**
 * Created by Vult on 2018-06-18.
 */
public class FilesHandler extends JFileChooser {
    private static File selectedFile;
    private static Agenda agenda;

    public static void saveFile() {
        JFileChooser fileChooser = createFileChooser();
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            if (selectedFile.exists()) {
                if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(null, "Are you sure you want to overwrite " + selectedFile.getAbsolutePath() + "?", "Confirm Save",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) ;
            }
            treatFileException(selectedFile, file -> writeToFile(file));
        }
    }

    public static void openFile() {
        JFileChooser fileChooser = createFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            treatFileException(selectedFile, file -> readFromFile(selectedFile));
        }
    }

    //    Method that implements a Consumer to treat errors from File Open/Save operations.
//    It's to group them together and reuse code.
    private static void treatFileException(File file, ThrowConsumer fileWork) {
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

    private static void writeToFile(File selectedFile) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(selectedFile.getAbsolutePath());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(agenda.getContacts());
        objectOutputStream.close();
    }

    private static void readFromFile(File selectedFile) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(selectedFile.getAbsolutePath());
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Set<Contact> contactsRead = (Set<Contact>) objectInputStream.readObject();
        objectInputStream.close();
//        Populating the agenda
        agenda.renewContacts(contactsRead);
    }

    private static JFileChooser createFileChooser() {
        javax.swing.filechooser.FileFilter fileFilter = new FileNameExtensionFilter("agenda File", "agenda");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(fileFilter);
        return fileChooser;
    }

    public static void setAgenda(Agenda agenda) {
        FilesHandler.agenda = agenda;
    }
}
