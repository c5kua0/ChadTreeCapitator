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

        if (item == null) {
            return false;
        }

        if (item.getType() != Material.GOLDEN_AXE) {
            return false;
        }

        Byte value = item.getPersistentDataContainer()
                .get(chadKey, PersistentDataType.BYTE);

        return value != null && value == (byte) 1;
    }
}