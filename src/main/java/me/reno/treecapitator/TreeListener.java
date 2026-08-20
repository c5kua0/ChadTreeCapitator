package me.reno.treecapitator;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public class TreeListener implements Listener {

    private final TreeCapitatorPlugin plugin;
    private final TreeChadItem chadItem;

    public TreeListener(
            TreeCapitatorPlugin plugin,
            TreeChadItem chadItem
    ) {
        this.plugin = plugin;
        this.chadItem = chadItem;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        Player player = event.getPlayer();
        Block start = event.getBlock();

        ItemStack item = player.getInventory().getItemInMainHand();

        // Only Chad activates the ability
        if (!chadItem.isChad(item)) {
            return;
        }

        // Only logs
        if (!isLog(start.getType())) {
            return;
        }

        event.setCancelled(true);

        Set<Block> treeLogs = findTree(start);

        for (Block log : treeLogs) {

            log.breakNaturally(item);
        }
    }

    private Set<Block> findTree(Block start) {

        Set<Block> found = new HashSet<>();
        Queue<Block> queue = new ArrayDeque<>();

        found.add(start);
        queue.add(start);

        /*
         * Safety limit.
         * Prevents accidentally destroying huge connected
         * structures made from logs.
         */
        final int MAX_LOGS = 512;

        while (!queue.isEmpty() && found.size() < MAX_LOGS) {

            Block current = queue.poll();

            Block[] neighbors = {

                    current.getRelative(1, 0, 0),
                    current.getRelative(-1, 0, 0),

                    current.getRelative(0, 1, 0),
                    current.getRelative(0, -1, 0),

                    current.getRelative(0, 0, 1),
                    current.getRelative(0, 0, -1),

                    current.getRelative(1, 1, 0),
                    current.getRelative(-1, 1, 0),
                    current.getRelative(1, -1, 0),
                    current.getRelative(-1, -1, 0),

                    current.getRelative(1, 0, 1),
                    current.getRelative(-1, 0, 1),
                    current.getRelative(1, 0, -1),
                    current.getRelative(-1, 0, -1)
            };

            for (Block neighbor : neighbors) {

                if (found.contains(neighbor)) {
                    continue;
                }

                if (!isLog(neighbor.getType())) {
                    continue;
                }

                found.add(neighbor);
                queue.add(neighbor);

                if (found.size() >= MAX_LOGS) {
                    break;
                }
            }
        }

        return found;
    }

    private boolean isLog(Material material) {

        String name = material.name();

        return name.endsWith("_LOG")
                || name.endsWith("_WOOD");
    }
}