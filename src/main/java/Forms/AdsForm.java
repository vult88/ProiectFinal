package Forms;

import javax.swing.*;

/**
 * Created by Vult on 02-Jun-18.
 * Class intended to separate functionality of Ads from main window
 */
public class AdsForm extends JPanel {
    public static JLabel adsPicture;

    public static void setAdsPictureIcon(ImageIcon imageIcon) {
        adsPicture.setIcon(imageIcon);
    }

    public void addAdsPictureToPanel() {
        adsPicture = new JLabel();
        this.add(adsPicture);
    }
}
