package Forms;

import com.intellij.uiDesigner.core.GridConstraints;

import javax.swing.*;

/**
 * Created by Vult on 02-Jun-18.
 * Class intended to separate functionality of Ads from main window
 */
public class AdsForm extends JPanel {
    public static JLabel adsPicture;

    public void addAdsPictureToPanel() {
        adsPicture = new JLabel();
        this.add(adsPicture, new GridConstraints());
        adsPicture.setIcon(new ImageIcon("D:\\git\\ProiectFinal\\src\\main\\java\\Ads\\ad6.jpg"));
    }
}
