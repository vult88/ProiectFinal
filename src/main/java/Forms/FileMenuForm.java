package Forms;

import javax.swing.*;

/**
 * Created by Vult on 02-Jun-18.
 * Class intended for creating the menu bar of the main window as the
 * designer does not support jMenuBar, jMenu or jMenuItem
 */
public class FileMenuForm {
    public static JMenuBar menuBar;
    public static JMenu fileMenu;
    public static JMenu helpMenu;
    public static JMenuItem openFile;
    public static JMenuItem saveFile;
    public static JMenuItem exitApp;
    public static JMenuItem registerApp;
    public static JMenuItem aboutApp;

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
}