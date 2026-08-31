package me.reno.treecapitator;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class TreeCapitatorPlugin extends JavaPlugin {

    private TreeChadItem chadItem;

    @Override
    public void onEnable() {
        chadItem = new TreeChadItem(this);

        getServer().getPluginManager().registerEvents(
                new TreeListener(this, chadItem),
                this
        );

        registerChadRecipe();

        getLogger().info("TreeCapitator enabled!");
    }

    private void registerChadRecipe() {
        NamespacedKey recipeKey =
                new NamespacedKey(this, "chad_axe");

        ShapedRecipe recipe = new ShapedRecipe(
                recipeKey,
                chadItem.createChad()
        );

        recipe.shape(
                "EE",
                "ES",
                " S"
        );

        recipe.setIngredient('E', Material.ECHO_SHARD);
        recipe.setIngredient('S', Material.STICK);

        getServer().addRecipe(recipe);
    }

    public TreeChadItem getChadItem() {
        return chadItem;
    }
}
