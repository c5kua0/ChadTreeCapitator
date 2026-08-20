package me.reno.treecapitator;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TreeCommand implements CommandExecutor {

    private final TreeChadItem chadItem;

    public TreeCommand(TreeChadItem chadItem) {
        this.chadItem = chadItem;
    }

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(
                    Component.text("Only players can use this command.")
                            .color(NamedTextColor.RED)
            );

            return true;
        }

        if (!player.hasPermission("treecapitator.chad")) {

            player.sendMessage(
                    Component.text("You don't have permission to use /chad.")
                            .color(NamedTextColor.RED)
            );

            return true;
        }

        player.getInventory().addItem(
                chadItem.createChad()
        );

        player.sendMessage(
                Component.text("You received ")
                        .color(NamedTextColor.YELLOW)
                        .append(
                                Component.text("Chad")
                                        .color(NamedTextColor.GOLD)
                        )
                        .append(
                                Component.text("!")
                                        .color(NamedTextColor.YELLOW)
                        )
        );

        return true;
    }
}