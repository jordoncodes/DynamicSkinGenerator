package me.onlyjordon.changeskin.util;


import java.awt.*;
import java.awt.image.BufferedImage;

public class ColorUtils {
    public static BufferedImage colorImage(BufferedImage image, Color originalColor, Color replaceColor) {
        BufferedImage newImg = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );

        int width = image.getWidth();
        int height = image.getHeight();
        for (int yy = 0; yy < width; yy++) {
            for (int xx = 0; xx < height; xx++) {
                Color ogColor = new Color(image.getRGB(xx, yy), true);
                if (ogColor.equals(originalColor)) {
                    newImg.setRGB(xx, yy, replaceColor.getRGB());
                } else {
                    newImg.setRGB(xx, yy, ogColor.getRGB());
                }
            }
        }
        return newImg;
    }
}
