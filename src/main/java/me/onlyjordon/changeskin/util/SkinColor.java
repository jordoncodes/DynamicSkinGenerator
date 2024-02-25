package me.onlyjordon.changeskin.util;

import java.awt.*;
import java.util.HashMap;

public class SkinColor {

    public final HashMap<Color, Color> skinColorMap = new HashMap<>();

    public SkinColor() {
        skinColorMap.put(new Color(255, 255, 255), new Color(255, 255, 255));
        skinColorMap.put(new Color(255, 255, 255), new Color(255, 255, 255));
        skinColorMap.put(new Color(255, 255, 255), new Color(255, 255, 255));
        skinColorMap.put(new Color(255, 255, 255), new Color(255, 255, 255));
        skinColorMap.put(new Color(255, 255, 255), new Color(255, 255, 255));
        skinColorMap.put(new Color(255, 255, 255), new Color(255, 255, 255));
        skinColorMap.put(new Color(255, 255, 255), new Color(255, 255, 255));
        skinColorMap.put(new Color(255, 255, 255), new Color(255, 255, 255));
        skinColorMap.put(new Color(255, 255, 255), new Color(255, 255, 255));
    }

    public static SkinColor random() {
        return new SkinColor();
    }

    public HashMap<Color, Color> getMap() {
        return skinColorMap;
    }
}
