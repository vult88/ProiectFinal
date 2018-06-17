package forms;

import javax.swing.*;

/**
 * Created by Vult on 02-Jun-18.
 * Class intended for creating the menu bar of the main window as the
 * designer does not support jMenuBar, jMenu or jMenuItem
 */
public class FileMenuForm {
    private static JMenuBar menuBar;
    private static JMenu fileMenu;
    private static JMenu helpMenu;
    private static JMenuItem openFile;
    private static JMenuItem saveFile;
    private static JMenuItem exitApp;
    private static JMenuItem registerApp;
    private static JMenuItem aboutApp;

    public FileMenuForm() {
// Create the menuBar
        menuBar = new JMenuBar();

// Create the menus
        fileMenu = new JMenu("File");
        helpMenu = new JMenu("Help");

// Add menus to menuBar
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

// Create menuItems
        openFile = new JMenuItem("Open File");
        saveFile = new JMenuItem("Save File");
        exitApp = new JMenuItem("Exit");

        registerApp = new JMenuItem("Register");
        aboutApp = new JMenuItem("About");

// Add menuItems to menus
        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        fileMenu.add(exitApp);

        helpMenu.add(registerApp);
        helpMenu.add(aboutApp);
    }

    public static JMenuBar getMenuBar() {
        return menuBar;
    }

    public static JMenuItem getExitApp() {
        return exitApp;
    }

    public static JMenuItem getRegisterApp() {
        return registerApp;
    }

    public static JMenuItem getAboutApp() {
        return aboutApp;
    }

    public static void setEnabledOpenFile(boolean setterEnabled) {
        openFile.setEnabled(setterEnabled);
    }

    public static void setEnabledSaveFile(boolean setterEnabled) {
        saveFile.setEnabled(setterEnabled);
    }
}