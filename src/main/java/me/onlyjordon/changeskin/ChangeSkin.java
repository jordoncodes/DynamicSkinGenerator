package me.onlyjordon.changeskin;

import me.onlyjordon.changeskin.commands.CommandManager;
import me.onlyjordon.changeskin.commands.SimpleCommandManager;
import me.onlyjordon.nicknamingapi.Nicknamer;
import me.onlyjordon.nicknamingapi.NicknamerAPI;
import org.bukkit.plugin.java.JavaPlugin;
import org.mineskin.MineskinClient;

import java.io.File;

public final class ChangeSkin extends JavaPlugin {
    
    private Nicknamer nicknamer;
    private CommandManager commandManager;
    private MineskinClient mineskinClient;
    private File skinFile;
    private File alteredSkinFile;

    @Override
    public void onEnable() {
        getLogger().info("ChangeSkin has been enabled!");
        nicknamer = NicknamerAPI.getNicknamer();
        skinFile = new File(getDataFolder(), "skins/testSkin.png");
        alteredSkinFile = skinFile;
        if (!skinFile.exists()) saveResource("skins/testSkin.png", false);
        mineskinClient = new MineskinClient("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36");
        SimpleCommandManager manager = new SimpleCommandManager();
        manager.initialise();
        manager.addCommand(new CommandMineskin());
        manager.addCommand(new CommandAlterSkin());
        manager.updateCommandMap();
        commandManager = manager;
    }


    @Override
    public void onDisable() {
        commandManager.removeCommands();
    }

    public MineskinClient getMineskinClient() {
        return mineskinClient;
    }

    public File getOriginalSkinFile() {
        return skinFile;
    }

    public File getAlteredSkinFile() {
        return alteredSkinFile;
    }

    public void setAlteredSkinFile(File alteredSkinFile) {
        this.alteredSkinFile = alteredSkinFile;
    }

    public Nicknamer getNicknamer() {
        return nicknamer;
    }
}
