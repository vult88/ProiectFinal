package files;

import interfaces.ThrowConsumer;
import model.FileDefinition;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import static files.ReadTabDelimited.fileDefinitions;

/**
 * Created by Vult on 2018-06-18.
 */
public class FilesHandler extends JFileChooser {
    private static File selectedFile;

    public static void saveFile() {
        JFileChooser fileChooser = createFileChooser();
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            if (selectedFile.exists()) {
                if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(null, "Are you sure you want to overwrite " + selectedFile.getAbsolutePath() + "?", "Confirm Save",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) ;
            }
            treatFileException(selectedFile, file -> writeToFile(selectedFile));
        }
    }

    public static Path openFile() {
        JFileChooser fileChooser = createFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            treatFileException(selectedFile, file -> {
                try {
                    ReadTabDelimited.readTabDelimitedFile(selectedFile);
                } catch (IllegalArgumentException e) {
                    JOptionPane.showConfirmDialog(null, e.getMessage(), "Error",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                }
            });
            return Paths.get(fileChooser.getSelectedFile().toString());
        }
        return null;
    }

    //    Method that implements a Consumer to treat errors from File Open/Save operations.
//    It's to group them together and reuse code.
    private static void treatFileException(File file, ThrowConsumer fileWork) {
        try {
            fileWork.accept(file);
        } catch (FileNotFoundException e) {
            JOptionPane.showConfirmDialog(null, "File " + file.getAbsoluteFile() + " not found !", "Error",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showConfirmDialog(null, "XXXX " + file.getAbsoluteFile(), "Error",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showConfirmDialog(null, e.getMessage(), "Error",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void writeToFile(File selectedFile) throws IOException {
        if (fileDefinitions.size() == 0) {
            throw new IOException("Source file is empty !");
        }
        FileWriter fileWriter = new FileWriter(selectedFile.getAbsoluteFile());
        int count = 0;
        for (FileDefinition fileDefinition : fileDefinitions) {
            if (count < fileDefinitions.size()) {
                fileWriter.write(fileDefinition.toStringNewLine());
            } else {
                fileWriter.write(fileDefinition.toStringEndOfFile());
            }
            count++;
        }
        fileWriter.close();
        JOptionPane.showConfirmDialog(null, "File " + selectedFile.getAbsoluteFile() + " successfully written !", "Information",
                JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
    }

    private static void readFromFile(File selectedFile) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(selectedFile.getAbsolutePath());
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
//        Set<Contact> contactsRead = (Set<Contact>) objectInputStream.readObject();
        objectInputStream.close();
//        Populating the agenda
//        agenda.renewContacts(contactsRead);
    }

    private static JFileChooser createFileChooser() {
        javax.swing.filechooser.FileFilter fileFilter = new FileNameExtensionFilter("Text (Tab delimited)", "txt");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(fileFilter);
        return fileChooser;
    }
//
//    public static void setAgenda(Agenda agenda) {
//        FilesHandler.agenda = agenda;
//    }
//
//    public static void autoSave(Agenda agenda) {
//        setAgenda(agenda);
//        treatFileException(selectedFile, file -> writeToFile(selectedFile));
//    }
}
