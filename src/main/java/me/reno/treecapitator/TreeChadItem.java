package me.reno.treecapitator;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class TreeChadItem {

    private final NamespacedKey chadKey;

    public TreeChadItem(TreeCapitatorPlugin plugin) {
        this.chadKey = new NamespacedKey(plugin, "chad_axe");
    }

    public ItemStack createChad() {
        ItemStack axe = new ItemStack(Material.GOLDEN_AXE);

        ItemMeta meta = axe.getItemMeta();

        meta.displayName(
                Component.text("Chad")
                        .color(NamedTextColor.GOLD)
        );

        meta.setUnbreakable(true);

        axe.setItemMeta(meta);

        // Mark the item as Chad
        axe.editPersistentDataContainer(pdc ->
                pdc.set(
                        chadKey,
                        PersistentDataType.BYTE,
                        (byte) 1
                )
        );

        return axe;
    }

    public boolean isChad(ItemStack item) {

        if (item == null || item.getType() != Material.GOLDEN_AXE) {
            return false;
        }

        // Primary identification: persistent data
        Byte value = item.getPersistentDataContainer()
                .get(chadKey, PersistentDataType.BYTE);

        if (value != null && value == (byte) 1) {
            return true;
        }

        /*
         * Backwards compatibility:
         * Older Chad items may have lost their PDC data while
         * being transferred through another system.
         *
         * Only accept an unbreakable Golden Axe named "Chad".
         */
        ItemMeta meta = item.getItemMeta();

        if (meta == null || !meta.isUnbreakable()) {
            return false;
        }

        Component displayName = meta.displayName();

        if (displayName == null) {
            return false;
        }

        return displayName.equals(
                Component.text("Chad")
                        .color(NamedTextColor.GOLD)
        );
    }
}