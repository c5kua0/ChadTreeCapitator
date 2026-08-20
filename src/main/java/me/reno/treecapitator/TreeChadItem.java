package me.reno.treecapitator;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public final class ChadAxeItem {

    private ChadAxeItem() {
    }

    public static ItemStack create() {
        ItemStack item = new ItemStack(Material.NETHERITE_AXE);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text("Chad Axe", NamedTextColor.GOLD)
                .decoration(TextDecoration.ITALIC, false));
        meta.lore(List.of(
                Component.text("Chops entire trees in one hit.", NamedTextColor.GRAY)
                        .decoration(TextDecoration.ITALIC, false)
        ));
        meta.getPersistentDataContainer().set(TreeCapitator.CHAD_AXE_KEY, PersistentDataType.BOOLEAN, true);

        item.setItemMeta(meta);
        return item;
    }

    public static boolean isChadAxe(ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        Boolean flag = meta.getPersistentDataContainer().get(TreeCapitator.CHAD_AXE_KEY, PersistentDataType.BOOLEAN);
        return Boolean.TRUE.equals(flag);
    }
}