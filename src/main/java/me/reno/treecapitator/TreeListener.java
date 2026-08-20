package me.reno.treecapitator;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TreeChopListener implements Listener {

    private static final Set<Material> LOG_TYPES = logTypes();
    private static final int MAX_BLOCKS = 128;

    private final TreeCapitator plugin;

    public TreeChopListener(TreeCapitator plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack tool = player.getInventory().getItemInMainHand();

        if (!ChadAxeItem.isChadAxe(tool)) {
            return;
        }

        Block origin = event.getBlock();
        if (!LOG_TYPES.contains(origin.getType())) {
            return;
        }

        List<Block> logs = findConnectedLogs(origin);

        int broken = 0;
        for (Block log : logs) {
            if (log.equals(origin)) {
                continue;
            }
            log.breakNaturally(tool);
            broken++;
        }

        if (broken > 0) {
            damageTool(player, tool, broken);
        }
    }

    private List<Block> findConnectedLogs(Block origin) {
        List<Block> result = new ArrayList<>();
        Set<Block> visited = new HashSet<>();
        Deque<Block> queue = new ArrayDeque<>();

        queue.add(origin);
        visited.add(origin);

        while (!queue.isEmpty() && result.size() < MAX_BLOCKS) {
            Block current = queue.poll();
            result.add(current);

            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        if (dx == 0 && dy == 0 && dz == 0) {
                            continue;
                        }
                        Block neighbor = current.getRelative(dx, dy, dz);
                        if (visited.contains(neighbor)) {
                            continue;
                        }
                        if (LOG_TYPES.contains(neighbor.getType())) {
                            visited.add(neighbor);
                            queue.add(neighbor);
                        }
                    }
                }
            }
        }

        return result;
    }

    private void damageTool(Player player, ItemStack tool, int extraUses) {
        if (!(tool.getItemMeta() instanceof Damageable damageable)) {
            return;
        }
        if (damageable.isUnbreakable()) {
            return;
        }

        int newDamage = damageable.getDamage() + extraUses;
        int maxDurability = tool.getType().getMaxDurability();

        if (newDamage >= maxDurability) {
            player.getInventory().setItemInMainHand(null);
            player.getWorld().playSound(player.getLocation(), org.bukkit.Sound.ENTITY_ITEM_BREAK, 1f, 1f);
            return;
        }

        damageable.setDamage(newDamage);
        tool.setItemMeta((org.bukkit.inventory.meta.ItemMeta) damageable);
    }

    private static Set<Material> logTypes() {
        return EnumSet.of(
                Material.OAK_LOG,
                Material.SPRUCE_LOG,
                Material.BIRCH_LOG,
                Material.JUNGLE_LOG,
                Material.ACACIA_LOG,
                Material.DARK_OAK_LOG,
                Material.MANGROVE_LOG,
                Material.CHERRY_LOG,
                Material.PALE_OAK_LOG,
                Material.CRIMSON_STEM,
                Material.WARPED_STEM
        );
    }
}