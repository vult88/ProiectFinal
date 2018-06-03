package Ads;

import Forms.AdsForm;

import javax.swing.*;
import java.io.File;

/**
 * Created by Vult on 02-Jun-18.
 */

public class Ads extends Thread {
    private static boolean flagShowAds = true;
    private static ImageIcon banner;
    private static ImageIcon bannerPrevious;
    private File[] ads;

    public static boolean isFlagShowAds() {
        return flagShowAds;
    }

    public static void setFlagShowAds(boolean flagShowAds) {
        Ads.flagShowAds = flagShowAds;
    }

    private static File[] getBannersPaths() {
        File directory = new File(new File("").getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator + "Ads");
        return directory.listFiles(f -> f.getName().toLowerCase().endsWith(".jpg"));
    }

    public void run() {
        ads = getBannersPaths();

        while (flagShowAds) {
//            Just to make the ads not repeat...
            do {
                banner = new ImageIcon(ads[(int) (ads.length * Math.random())].toString());
            } while (banner.equals(bannerPrevious));
            bannerPrevious = banner;

            AdsForm.adsPicture.setIcon(banner);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!flagShowAds) {
            AdsForm.adsPicture.setIcon(new ImageIcon(""));
        }
    }
}

