package io.github.lkorss.universalEc.commands;

import io.github.lkorss.universalEc.UniversalEc;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class UnecCommand implements CommandExecutor {
    private final UniversalEc plugin;

    public UnecCommand(UniversalEc plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("universalec.admin")) {
            if (!plugin.getConfig().getBoolean("silent-mode.on-command-admin", false)) {
                sender.sendMessage(plugin.getMessageManager().getMessage("no-permission"));
            }
            return true;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            plugin.reloadPluginConfig(); // Метод перезагрузки (напишем ниже)
            sender.sendMessage(plugin.getMessageManager().getMessage("config-reloaded"));
            return true;
        }

        sender.sendMessage("§dUniversalEc §7| Используйте: §e/unec reload");
        return true;
    }
}
