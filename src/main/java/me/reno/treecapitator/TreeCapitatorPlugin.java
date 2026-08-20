package me.reno.treecapitator;

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

        TreeCommand chadCommand = new TreeCommand(chadItem);

        if (getCommand("chad") != null) {
            getCommand("chad").setExecutor(chadCommand);
            getCommand("chad").setTabCompleter(chadCommand);
        }

        getLogger().info("TreeCapitator enabled!");
    }

    public TreeChadItem getChadItem() {
        return chadItem;
    }
}