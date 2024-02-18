package me.onlyjordon.changeskin;

import me.onlyjordon.changeskin.commands.PlayerOnlyCommand;
import me.onlyjordon.changeskin.util.ColorUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.color.ICC_ColorSpace;
import java.awt.color.ICC_Profile;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

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
        ChangeSkin gen = ChangeSkin.getPlugin(ChangeSkin.class);
        if (args.length > 1) {
            Color first = Color.decode(args[0]);
            Color second = Color.decode(args[1]);
            try {



                BufferedImage originalSkin = ImageIO.read(gen.getOriginalSkinFile());;
                BufferedImage alteredSkin = ColorUtils.colorImage(originalSkin, first, second);
                gen.setAlteredSkinFile(new File(gen.getDataFolder(), "skins/alteredSkin.png"));
                ImageIO.write(alteredSkin, "png", gen.getAlteredSkinFile());
                player.sendMessage("Skin has been altered!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {

            player.sendMessage("Please provide a color to change the skin to!");
        }
        return true;
    }
}
