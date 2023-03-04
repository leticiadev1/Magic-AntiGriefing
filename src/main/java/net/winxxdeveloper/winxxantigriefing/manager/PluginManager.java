package net.winxxdeveloper.winxxantigriefing.manager;

import net.winxxdeveloper.winxxantigriefing.Terminal;
import net.winxxdeveloper.winxxantigriefing.commands.AntiGriefingCommand;
import net.winxxdeveloper.winxxantigriefing.listeners.AntiGriefingListener;
import org.bukkit.Bukkit;

public class PluginManager {

    public static void registerCommands(){
        Terminal.getInstance().getCommand("antigriefing").setExecutor(new AntiGriefingCommand());
    }

    public static void registerListeners(){
        Bukkit.getPluginManager().registerEvents(new AntiGriefingListener(), Terminal.getInstance());
    }

}
