package me.onlyjordon.changeskin;

import me.onlyjordon.changeskin.commands.PlayerOnlyCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class CommandMineskin extends PlayerOnlyCommand {
    public CommandMineskin() {
        super("mineskin", "mineskin.use");
    }
    @Override
    public HashMap<String, Integer> completeTab(CommandSender sender, String alias, String[] args) {
        return null;
    }

    @Override
    protected boolean execute(Player player, String[] args) {
        ChangeSkin gen = ChangeSkin.getPlugin(ChangeSkin.class);
        try {
            gen.getMineskinClient().generateUpload(gen.getAlteredSkinFile()).thenAccept(skin -> {
                player.sendMessage("skin is " + skin.data.uuid);
                gen.getNicknamer().setSkin(player, new me.onlyjordon.nicknamingapi.utils.Skin(skin.data.texture.value, skin.data.texture.signature));
                gen.getNicknamer().refreshPlayer(player);
                player.sendMessage("Skin has been set!");
            });
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
