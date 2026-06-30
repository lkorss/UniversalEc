package io.github.lkorss.universalEc.listeners;

import io.github.lkorss.universalEc.UniversalEc;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class EnderChestClickListener implements Listener {

    private final UniversalEc plugin;

    public EnderChestClickListener(UniversalEc plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        ItemStack item = event.getItem();
        if (item == null || item.getType() != Material.ENDER_CHEST) {
            return;
        }

        if (event.getAction() == Action.RIGHT_CLICK_AIR) {

            if (!player.hasPermission("universalec.use.click")) {
                if (!plugin.getConfig().getBoolean("silent-mode.no-permission.on-click-hand", true)) {
                    player.sendMessage(plugin.getMessageManager().getMessage("no-permission"));
                }
                return;
            }

            event.setCancelled(true);
            player.openInventory(player.getEnderChest());
            plugin.playEnderChestSound(player);

            if (!plugin.getConfig().getBoolean("silent-mode.on-success-open.on-click-hand", true)) {
                player.sendMessage(plugin.getMessageManager().getMessage("opened-via-click"));
            }
        }
    }
}