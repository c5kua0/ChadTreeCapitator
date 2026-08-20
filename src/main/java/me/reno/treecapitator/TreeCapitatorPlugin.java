package me.reno.treecapitator;

import org.bukkit.plugin.java.JavaPlugin;

public class TreeCapitatorPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(
                new TreeListener(this),
                this
        );

        getLogger().info("Chad Tree Capitator enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Chad Tree Capitator disabled!");
    }
}
