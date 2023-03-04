package net.winxxdeveloper.winxxantigriefing.manager;

import net.winxxdeveloper.winxxantigriefing.Terminal;
import net.winxxdeveloper.winxxantigriefing.utils.ActionBar;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AntiGriefingManager {

    private static List<UUID> authenticatedPlayers = new ArrayList<>();
    private static HashMap<UUID, Integer> commandExplainer = new HashMap<>();
    private static FileConfiguration config = Terminal.getInstance().getConfig();

    public static boolean isAuthenticated(Player player){
        if(authenticatedPlayers.contains(player.getUniqueId())){
            return true;
        }
        return false;
    }

    public static void addIntoAuthList(Player player){
        if(!isAuthenticated(player)){
            authenticatedPlayers.add(player.getUniqueId());
        }
    }

    public static void removeIntoAuthList(Player player){
        if(isAuthenticated(player)){
            authenticatedPlayers.remove(player.getUniqueId());
        }
    }

    public static boolean isCommandExp(Player player){
        if(commandExplainer.containsKey(player.getUniqueId())){
            return true;
        }
        return false;
    }

    public static boolean threeTimes(Player player){
        if(commandExplainer.get(player.getUniqueId()) == 3){
            return true;
        }
        return false;
    }

    public static void addValueIntoCmdExp(Player player){
        if(isCommandExp(player)){
            commandExplainer.put(player.getUniqueId(), commandExplainer.get(player.getUniqueId())+1);
        }
    }

    public static void addIntoCmdExp(Player player){
        if(!isCommandExp(player)){
            commandExplainer.putIfAbsent(player.getUniqueId(), 0);
        }
    }

    public static void removeIntoCmdExp(Player player){
        if(isCommandExp(player)){
            commandExplainer.remove(player.getUniqueId());
        }
    }

    public static Integer getCmdExpValue(Player player){
        return commandExplainer.get(player.getUniqueId());
    }

    public static void sendToStaff(Player player, String message){
        for(Player var1 : Bukkit.getOnlinePlayers()){
            if(var1.hasPermission(config.getString("view-logs.view-perm"))){

                List<String> list = config.getStringList("view-logs.view-message");
                List<String> var2 = list.stream().map(s -> s.replace("&", "§")
                        .replace("{player}", player.getDisplayName())
                        .replace("{command}", message)).collect(Collectors.toList());
                for(String var3 : var2){
                    var1.sendMessage(var3);
                }
                ActionBar.sendActionbar(var1, "§aNova log gerada pelo AntiGriefing.");
            }
        }
    }

}
