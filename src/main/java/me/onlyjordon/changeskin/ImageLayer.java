package me.onlyjordon.changeskin;

import me.onlyjordon.changeskin.util.ColorUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.List;

public class ImageLayer {

    protected BufferedImage image;


    protected ImageLayer(BufferedImage image) {
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * Draws the layer on top of the current image
     * @param layer the layer to draw over this one
     */
    public void combineOnTop(ImageLayer layer, boolean blend) {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < layer.image.getWidth(); x++) {
            for (int y = 0; y < layer.image.getHeight(); y++) {
                Color otherRGB = new Color(layer.image.getRGB(x, y), true);
                if (blend) otherRGB = ColorUtils.blendBetweenTwoColors(image.getRGB(x,y), layer.image.getRGB(x, y));
                // check alpha;
                newImage.setRGB(x, y, otherRGB.getRGB());
                if (image.getRGB(x,y) != layer.image.getRGB(x,y)) {
                    System.out.println(Integer.toHexString(newImage.getRGB(x, y)) + " <-- " + Integer.toHexString(image.getRGB(x, y)) + " + " + Integer.toHexString(layer.image.getRGB(x, y)));
                    net.kyori.adventure.text.TextComponent component = net.kyori.adventure.text.Component.text("■").color(TextColor.color(image.getRGB(x,y)))
                            .append(net.kyori.adventure.text.Component.text("■").color(TextColor.color(ColorUtils.blendBetweenTwoColors(image.getRGB(x,y), layer.image.getRGB(x,y)).getRGB())))
                            .append(Component.text("■").color(TextColor.color(layer.image.getRGB(x,y))));
                    Bukkit.getServer().broadcastMessage(LegacyComponentSerializer.legacySection().serialize(component));
                }
//                System.out.println(newColor.getRed() +", " + newColor.getGreen() + ", " + newColor.getBlue() + ", " + newColor.getAlpha());
            }
        }
        this.image = newImage;
    }


    public ImageLayer copy() {
        return new ImageLayer(image);
    }

    /**
     * merges all the layers into one, list is ordered from bottom to top
     * @param layers the layers to merge
     * @return the merged layer
     */
    @Nullable
    public static ImageLayer merge(ImageLayer... layers) {
        if (layers == null) return null;
        if (layers.length == 0) return null;

        for (int i = 1; i < layers.length; i++) {
            layers[0].combineOnTop(layers[i], true);
        }
        return layers[0];
    }

    /**
     * merges all the layers into one, list is ordered from bottom to top
     * @param layers the layers to merge
     * @return the merged layer
     */
    @Nullable
    public static ImageLayer merge(List<ImageLayer> layers) {
        if (layers == null || layers.isEmpty()) return null;
        return merge(layers.toArray(new ImageLayer[]{}));
    }

    public static ImageLayer fromImage(BufferedImage image) {
        return new ImageLayer(image);
    }

    public static List<ImageLayer> layersFromFolder(File path) throws FileNotFoundException {
        if (!path.exists()) path.mkdirs();
        List<ImageLayer> layers = new ArrayList<>();

        String[] list = path.list((dir, name) -> name.endsWith(".png"));
        if (list == null) throw new FileNotFoundException("there is no skin layers in the folder!");
        System.out.println(String.join(", ", list));
        list = Arrays.stream(list).sorted().toArray(String[]::new);
        for (String s : list) {
            File pngFile = new File(path, s);
            try {
                BufferedImage image = ImageIO.read(pngFile);
                layers.add(new ImageLayer(image));
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        return layers;
    }


}
