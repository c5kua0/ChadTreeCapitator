package me.reno.treecapitator;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class ChadAxeCommand implements CommandExecutor, TabCompleter {

    private final TreeCapitator plugin;

    public ChadAxeCommand(TreeCapitator plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("treecapitator.give")) {
            player.sendMessage("You don't have permission to do that.");
            return true;
        }

        player.getInventory().addItem(ChadAxeItem.create());
        player.sendMessage("You have received the Chad Axe.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}