package io.github.lkorss.universalEc;

import io.github.lkorss.universalEc.commands.UnecTabCompleter;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import io.github.lkorss.universalEc.commands.EcCommand;
import io.github.lkorss.universalEc.utils.MessageManager;
import io.github.lkorss.universalEc.listeners.EnderChestClickListener;
import io.github.lkorss.universalEc.commands.UnecCommand;

import java.io.File;

public final class UniversalEc extends JavaPlugin {

    private MessageManager messageManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        saveDefaultLangFiles();

        this.messageManager = new MessageManager(this);

        if (getCommand("ec") != null) {
            getCommand("ec").setExecutor(new EcCommand(this));
        }


        if (getCommand("unec") != null) {
            getCommand("unec").setExecutor(new UnecCommand(this));
            getCommand("unec").setTabCompleter(new UnecTabCompleter());
        }

        getServer().getPluginManager().registerEvents(new EnderChestClickListener(this), this);
    }

    public void reloadPluginConfig() {
        reloadConfig();
        this.messageManager.loadLang();
    }

    public void playEnderChestSound(Player player) {
        if (!getConfig().getBoolean("sound.enabled", true)) {
            return;
        }

        String soundName = getConfig().getString("sound.name", "BLOCK_ENDER_CHEST_OPEN");
        if (soundName.equalsIgnoreCase("NONE")) {
            return;
        }

        try {
            Sound sound = Sound.valueOf(soundName.toUpperCase());
            float volume = (float) getConfig().getDouble("sound.volume", 1.0);
            float pitch = (float) getConfig().getDouble("sound.pitch", 1.0);

            player.playSound(player.getLocation(), sound, volume, pitch);
        } catch (IllegalArgumentException e) {
            String errorMsg = getMessageManager().getMessage("sound-error");
            getLogger().warning(errorMsg + soundName);
        }
    }

    private void saveDefaultLangFiles() {
        File ruFile = new File(getDataFolder(), "lang/ru.yml");
        File enFile = new File(getDataFolder(), "lang/en.yml");
        if (!ruFile.exists()) saveResource("lang/ru.yml", false);
        if (!enFile.exists()) saveResource("lang/en.yml", false);
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }
}
