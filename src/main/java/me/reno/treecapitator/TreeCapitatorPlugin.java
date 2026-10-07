package me.reno.treecapitator;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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

    @Override
    public boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {
        if (command.getName().equalsIgnoreCase("chad")) {

            if (!(sender instanceof Player player)) {
                sender.sendMessage("Only players can use this command.");
                return true;
            }

            if (!player.hasPermission("treecapitator.chad")) {
                player.sendMessage("§cYou don't have permission to use /chad.");
                return true;
            }

            ItemStack chadAxe = chadItem.createChad();

            player.getInventory().addItem(chadAxe);
            player.sendMessage("§6§lCHAD §r§eYou received the Chad Tree Capitator!");

            return true;
        }

        return false;
    }

    public TreeChadItem getChadItem() {
        return chadItem;
    }
}