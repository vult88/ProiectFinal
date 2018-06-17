package ads;

import forms.FileMenuForm;

import javax.swing.*;
import java.io.File;

import static forms.AdsForm.setAdsPictureIcon;

/**
 * Created by Vult on 02-Jun-18.
 * Class to deal with the ads
 */

public class Ads extends Thread {
    private static boolean flagShowAds = true;
    private static ImageIcon banner;
    private static File adsFilePath = new File(File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "ads");
    private File[] ads;
    private int currentBannerPosition = 0;
    private int previousBannerPosition = 0;

    public static boolean isFlagShowAds() {
        return flagShowAds;
    }

    public static void setFlagShowAds(boolean flagShowAds) {
        Ads.flagShowAds = flagShowAds;
    }

    public static ImageIcon getSplashScreenImageIcon() {
        return new ImageIcon(new File(new File("").getAbsolutePath() + adsFilePath + File.separator + "logo" + File.separator + "SplashScreenPicture.jpg").toString());
    }

    private static File[] getBannersPaths() {
        File directory = new File(new File("").getAbsolutePath() + adsFilePath);
        return directory.listFiles(f -> f.getName().toLowerCase().endsWith(".jpg"));
    }

    public static void setModeShareware(boolean isModeShareware) {
        FileMenuForm.setEnabledOpenFile(isModeShareware);
        FileMenuForm.setEnabledSaveFile(isModeShareware);
    }

    public void run() {
        ads = getBannersPaths();

        while (flagShowAds) {
//            Just to make the ads not repeat...
            while (currentBannerPosition == previousBannerPosition) {
                currentBannerPosition = (int) (ads.length * Math.random());
            }
            previousBannerPosition = currentBannerPosition;
            banner = new ImageIcon(ads[currentBannerPosition].toString());

            setAdsPictureIcon(banner);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//      There is no need to condition this because it is sort of an else of the WHILE condition
        setAdsPictureIcon(new ImageIcon(""));
        setModeShareware(!flagShowAds);
    }
}

