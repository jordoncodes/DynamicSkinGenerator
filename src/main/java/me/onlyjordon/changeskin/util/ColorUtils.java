package me.onlyjordon.changeskin.util;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class ColorUtils {
    public static BufferedImage colorImage(BufferedImage image, HashMap<Color,Color> replacingColor) {
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
                for (Color originalColor : replacingColor.keySet()) {
                    Color replaceColor = replacingColor.get(originalColor);
                    if (ogColor.equals(originalColor)) {
                        newImg.setRGB(xx, yy, replaceColor.getRGB());
                    } else {
                        newImg.setRGB(xx, yy, ogColor.getRGB());
                    }
                }
            }
        }
        return newImg;
    }

    public static Color blendBetweenTwoColors(int argb1, int argb2) {
        Color c1 = new Color(argb1, true);
        Color c2 = new Color(argb2, true);
        int alpha = 255 - ((255 - c1.getAlpha()) * (255 - c2.getAlpha()) / 255);
        int red   = (c1.getRed() * (255 - c2.getAlpha()) + c2.getRed() * c2.getAlpha()) / 255;
        int green = (c1.getGreen() * (255 - c2.getAlpha()) + c2.getGreen() * c2.getAlpha()) / 255;
        int blue  = (c1.getBlue() * (255 - c2.getAlpha()) + c2.getBlue() * c2.getAlpha()) / 255;
        return new Color(red, green, blue, alpha);
    }
}
