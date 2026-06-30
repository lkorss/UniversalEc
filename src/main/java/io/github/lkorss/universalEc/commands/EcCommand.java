package io.github.lkorss.universalEc.commands;

import io.github.lkorss.universalEc.UniversalEc;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EcCommand implements CommandExecutor {

    private final UniversalEc plugin;

    public EcCommand(UniversalEc plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.getMessageManager().getMessage("only-players"));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("universalec.command.ec")) {
            if (!plugin.getConfig().getBoolean("silent-mode.no-permission.on-command-ec", false)) {
                player.sendMessage(plugin.getMessageManager().getMessage("no-permission"));
            }
            return true;
        }

        if (!player.getInventory().contains(Material.ENDER_CHEST)) {
            player.sendMessage(plugin.getMessageManager().getMessage("must-have-chest"));
            return true;
        }

        player.openInventory(player.getEnderChest());
        plugin.playEnderChestSound(player);
        if (!plugin.getConfig().getBoolean("silent-mode.on-success-open.on-command-ec", false)) {
            player.sendMessage(plugin.getMessageManager().getMessage("opened-via-command"));
        }
        return true;
    }
}