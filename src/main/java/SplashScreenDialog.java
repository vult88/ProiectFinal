import ads.Ads;

import javax.swing.*;
import java.util.TimerTask;

public class SplashScreenDialog extends JDialog {
    private JPanel contentPane;
    private JLabel pictureLabel;
    private JLabel creatorLabel;
    private JButton buttonOK;

    public SplashScreenDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        pictureLabel.setIcon(Ads.getSplashScreenImageIcon());

        TimerTask stopSplashScreen = new TimerTask() {
            @Override
            public void run() {
                dispose();
            }
        };
        java.util.Timer timer = new java.util.Timer();
        timer.schedule(stopSplashScreen, 1000);
    }
}
