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

        if (getCommand("chad") != null) {
            getCommand("chad").setExecutor(
                    new TreeCommand(chadItem)
            );
        }

        getLogger().info("TreeCapitator has been enabled!");
    }

    public TreeChadItem getChadItem() {
        return chadItem;
    }
} this);

        ChadAxeCommand chadAxeCommand = new ChadAxeCommand(this);
        getCommand("chadaxe").setExecutor(chadAxeCommand);
        getCommand("chadaxe").setTabCompleter(chadAxeCommand);

        getLogger().info("TreeCapitator enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("TreeCapitator disabled.");
    }

    public static TreeCapitator getInstance() {
        return instance;
    }
}