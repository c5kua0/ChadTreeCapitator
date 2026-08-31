package me.reno.treecapitator;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

public class TreeCapitatorPlugin extends JavaPlugin {

    private TreeChadItem chadItem;

    @Override
    public void onEnable() {
        // Create the Chad item handler
        chadItem = new TreeChadItem(this);

        // Register tree-capitator listener
        getServer().getPluginManager().registerEvents(
                new TreeListener(this, chadItem),
                this
        );

        // Register /chad command
        TreeCommand chadCommand = new TreeCommand(chadItem);
        if (getCommand("chad") != null) {
            getCommand("chad").setExecutor(chadCommand);
        }

        // Register Chad crafting recipe
        registerChadRecipe();

        getLogger().info("TreeCapitator enabled!");
    }

    private void registerChadRecipe() {
        NamespacedKey recipeKey = new NamespacedKey(this, "chad_axe");

        ShapedRecipe recipe = new ShapedRecipe(
                recipeKey,
                chadItem.createChad()
        );

        /*
         * Recipe:
         *
         * E E
         * E S
         *   S
         *
         * E = Echo Shard
         * S = Stick
         */
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
