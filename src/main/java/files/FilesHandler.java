package files;

import interfaces.ThrowConsumer;
import model.FileDefinition;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalTime;

import static files.ReadTabDelimited.fileDefinitions;

/**
 * Created by Vult on 2018-06-18.
 */
public class FilesHandler extends JFileChooser {
    private static File selectedFile;
    private static File currentPath = new File(FilesHandler.class.getProtectionDomain().getCodeSource().getLocation().getPath());

    public static void saveFile(Path sourceFileName) {
        JFileChooser fileChooser = createFileChooser();
        fileChooser.setSelectedFile(new File(sourceFileName.getFileName().toString().replace(".txt", "_parsed.txt")));
        fileChooser.setCurrentDirectory(selectedFile);
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            if (selectedFile.exists()) {
                if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(null, "Are you sure you want to overwrite " + selectedFile.getAbsolutePath() + "?", "Confirm Save",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)) {
                    parseFile(sourceFileName);
                }
            } else {
                parseFile(sourceFileName);
            }
        }
    }

    public static Path openFile() {
        JFileChooser fileChooser = createFileChooser();
        fileChooser.setCurrentDirectory(currentPath);
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            treatFileException(selectedFile, file -> {
            });
            return Paths.get(fileChooser.getSelectedFile().toString());
        }
        return null;
    }

    private static void readFile(File selectedFile) {
        try {
            ReadTabDelimited.readTabDelimitedFile(selectedFile);
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e.getClass() + " - " + e.getMessage(), "Error",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
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
    }

    private static JFileChooser createFileChooser() {
        javax.swing.filechooser.FileFilter fileFilter = new FileNameExtensionFilter("Text (Tab delimited)", "txt");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(fileFilter);
        return fileChooser;
    }

    private static void parseFile(Path sourceFileName) {
        LocalTime startLogTime = LocalTime.now();

        FilesHandler.readFile(new File(sourceFileName.toString()));
        treatFileException(selectedFile, file -> writeToFile(selectedFile));

        LocalTime endLogTime = LocalTime.now();
        Duration duration = Duration.between(startLogTime, endLogTime);
        double durationDouble = Double.parseDouble(duration.getSeconds() + "." + duration.getNano());

        JOptionPane.showMessageDialog(null, "File has been parsed in " + durationDouble + " seconds.");
    }
}
