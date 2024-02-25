package me.onlyjordon.changeskin;

import me.onlyjordon.changeskin.commands.PlayerOnlyCommand;
import me.onlyjordon.changeskin.util.ColorUtils;
import me.onlyjordon.changeskin.util.SkinColor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CommandAlterSkin extends PlayerOnlyCommand {
    public CommandAlterSkin() {
        super("alterskin", "alterskin.use");
    }
    @Override
    public HashMap<String, Integer> completeTab(CommandSender sender, String alias, String[] args) {
        return null;
    }

    @Override
    protected boolean execute(Player player, String[] args) {
        if (args == null) return false;

        int argb1 = 0xFF00FF00;
        int argb2 = 0x7F0000FF;

        TextComponent component = Component.text("■").color(TextColor.color(argb1))
                .append(Component.text("■").color(TextColor.color(ColorUtils.blendBetweenTwoColors(argb1, argb2).getRGB())))
                .append(Component.text("■").color(TextColor.color(argb2)));
        player.sendMessage(LegacyComponentSerializer.legacySection().serialize(component));


        try {
            ChangeSkin gen = ChangeSkin.getPlugin(ChangeSkin.class);
            List<ImageLayer> layers = ImageLayer.layersFromFolder(new File(gen.getDataFolder().getAbsolutePath()+File.separator+"skins"+File.separator+"baby"));
//            SkinColor color = SkinColor.random();
//            layers.forEach((l) -> l.setImage(ColorUtils.colorImage(l.getImage(), color.getMap())));
            ImageLayer merged = ImageLayer.merge(layers);

            gen.setAlteredSkinFile(new File(gen.getDataFolder(), "skins/alteredSkin.png"));
            ImageIO.write(merged.getImage(), "png", gen.getAlteredSkinFile());
            player.sendMessage("Skin has been altered!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
