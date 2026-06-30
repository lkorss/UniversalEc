package io.github.lkorss.universalEc.utils;

import io.github.lkorss.universalEc.UniversalEc;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MessageManager {
    private final UniversalEc plugin;
    private FileConfiguration langConfig;
    private String prefix;

    public MessageManager(UniversalEc plugin) {
        this.plugin = plugin;
        loadLang();
    }

    public void loadLang() {
        String langStr = plugin.getConfig().getString("lang", "ru");

        File langFile = new File(plugin.getDataFolder(), "lang/" + langStr + ".yml");

        if (!langFile.exists()) {
            plugin.saveResource("lang/" + langStr + ".yml", false);
        }

        this.langConfig = YamlConfiguration.loadConfiguration(langFile);

        this.prefix = translateColor(langConfig.getString("prefix", "&8[&dUniversalEc&8] "));
    }

    public String getMessage(String key) {
        String msg = langConfig.getString(key);
        if (msg == null) {
            return "§c[Missing message: " + key + "]";
        }
        return prefix + translateColor(msg);
    }

    @SuppressWarnings("deprecation")
    private String translateColor(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
