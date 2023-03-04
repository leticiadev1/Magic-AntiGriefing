package net.winxxdeveloper.winxxantigriefing;

import lombok.Getter;
import net.winxxdeveloper.winxxantigriefing.manager.PluginManager;
import net.winxxdeveloper.winxxantigriefing.manager.WebhookManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Terminal extends JavaPlugin {

    @Getter
    private static Terminal instance;

    @Override
    public void onEnable() {
        long timeAtStart = System.currentTimeMillis();
        instance=this;
        saveDefaultConfig();
        PluginManager.registerCommands();
        PluginManager.registerListeners();

        long timeAtEnd = System.currentTimeMillis();
        long ms = timeAtEnd - timeAtStart;
        getLogger().info("Has been started in "+ms+"ms");

        if(getConfig().getBoolean("discord-integration.enabled")){
            if(!getConfig().getString("discord-integration.webhook-url")
                    .startsWith("https://discord.com/api/webhooks/")) {
                getLogger().info("Plugin disabled, please put webhook link in config.yml");
                Bukkit.getPluginManager().disablePlugin(this);
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown
        saveConfig();
    }


}
