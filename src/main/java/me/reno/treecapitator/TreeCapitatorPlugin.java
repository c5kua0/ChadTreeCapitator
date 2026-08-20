package me.reno.treecapitator;

import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class TreeCapitator extends JavaPlugin {

    public static final NamespacedKey CHAD_AXE_KEY = new NamespacedKey("treecapitator", "chad_axe");
    private static TreeCapitator instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new TreeChopListener(this), this);

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