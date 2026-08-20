package me.reno.treecapitator;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class TreeListener implements Listener {

    private final TreeCapitatorPlugin plugin;

    public TreeListener(TreeCapitatorPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // Must be holding an axe
        if (!isAxe(item)) {
            return;
        }

        // Axe must be named "Chad"
        if (!isChadAxe(item)) {
            return;
        }

        Block brokenBlock = event.getBlock();

        // Must be a log
        if (!isLog(brokenBlock.getType())) {
            return;
        }

        // Find and break connected logs
        Set<Block> logs = findTreeLogs(brokenBlock);

        for (Block log : logs) {
            if (!log.equals(brokenBlock)) {
                log.breakNaturally(item);
            }
        }
    }

    private boolean isChadAxe(ItemStack item) {

        if (item == null || item.getType() == Material.AIR) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();

        if (meta == null || !meta.hasDisplayName()) {
            return false;
        }

        return meta.getDisplayName().equals("Chad");
    }

    private boolean isAxe(ItemStack item) {

        if (item == null) {
            return false;
        }

        String name = item.getType().name();

        return name.endsWith("_AXE");
    }

    private boolean isLog(Material material) {

        String name = material.name();

        return name.endsWith("_LOG")
                || name.endsWith("_STEM")
                || name.equals("CRIMSON_HYPHAE")
                || name.equals("WARPED_HYPHAE");
    }

    private Set<Block> findTreeLogs(Block start) {

        Set<Block> visited = new HashSet<>();
        Queue<Block> queue = new LinkedList<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {

            Block current = queue.poll();

            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {

                        if (x == 0 && y == 0 && z == 0) {
                            continue;
                        }

                        Block next = current.getRelative(x, y, z);

                        if (!visited.contains(next)
                                && isLog(next.getType())) {

                            visited.add(next);
                            queue.add(next);
                        }
                    }
                }
            }
        }

        return visited;
    }
            }
