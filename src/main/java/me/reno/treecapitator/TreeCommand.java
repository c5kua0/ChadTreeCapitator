package me.reno.treecapitator;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ChadAxeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        ItemStack axe = new ItemStack(Material.NETHERITE_AXE);

        ItemMeta meta = axe.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(ChatColor.GOLD + "Chad");
            axe.setItemMeta(meta);
        }

        player.getInventory().addItem(axe);

        player.sendMessage(
                ChatColor.GREEN + "You received the " +
                ChatColor.GOLD + "Chad Axe!"
        );

        return true;
    }
    }
