package forms;

import javax.swing.*;

/**
 * Created by Vult on 02-Jun-18.
 * Class intended to separate functionality of ads from main window
 */
public class AdsForm extends JPanel {
    private static JLabel adsPicture;

    public static void setAdsPictureIcon(ImageIcon imageIcon) {
        adsPicture.setIcon(imageIcon);
    }

    public void addAdsPictureToPanel() {
        adsPicture = new JLabel();
        this.add(adsPicture);
    }
}
