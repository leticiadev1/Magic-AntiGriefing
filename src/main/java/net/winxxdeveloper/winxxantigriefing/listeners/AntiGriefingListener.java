package net.winxxdeveloper.winxxantigriefing.listeners;

import net.winxxdeveloper.winxxantigriefing.Terminal;
import net.winxxdeveloper.winxxantigriefing.manager.AntiGriefingManager;
import net.winxxdeveloper.winxxantigriefing.manager.WebhookManager;
import net.winxxdeveloper.winxxantigriefing.utils.DiscordWebhook;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AntiGriefingListener extends AntiGriefingManager implements Listener {

    Player player;
    FileConfiguration config = Terminal.getInstance().getConfig();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){

        player=event.getPlayer();

        if(isAuthenticated(player)){
            removeIntoAuthList(player);
        }

        if(isCommandExp(player)){
            removeIntoCmdExp(player);
        }
        addIntoCmdExp(player);

    }

    @EventHandler
    public void onPrepocessCommand(PlayerCommandPreprocessEvent event){

        player=event.getPlayer();
        List<String> var8 = new ArrayList<>();
        String message = event.getMessage();

        for(String var2 : config.getStringList("antigriefing.blocked-commands")){
            var8.add(var2);
        }

        Iterator var1 = var8.iterator();

        if(!isAuthenticated(player)){
            while(var1.hasNext()) {
                String var3 = (String) var1.next();
                if(event.getMessage().startsWith(var3)) {
                    if(!event.isCancelled()){
                        event.setCancelled(true);
                        sendToStaff(player, message);

                        if(config.getBoolean("discord-integration.enabled")){
                            if(config.getString("discord-integration.webhook-url")
                                    .startsWith("https://discord.com/api/webhooks/")) {
                                if(config.getBoolean("discord-integration.enabled")){
                                    DiscordWebhook webhook = new DiscordWebhook(WebhookManager.getWebhook_url());
                                    webhook.addEmbed(new DiscordWebhook.EmbedObject()
                                            .setTitle(WebhookManager.getEmbedTitle())
                                            .setDescription(WebhookManager.getEmbedDescription()
                                                    .replace("{player}", player.getDisplayName())
                                                    .replace("{command}", message)
                                                    .replace("{date}", "\\nData: "+ WebhookManager.getDate()+
                                                            "\\nHorário: "+WebhookManager.getHour()))
                                            .setColor(Color.PINK)
                                            .setFooter(WebhookManager.getEmbedFooter(), WebhookManager.getEmbedImage()));
                                    try{
                                        webhook.execute();
                                    }catch (IOException e){
                                        Bukkit.getConsoleSender().sendMessage("§cNão foi possivel enviar a embed no discord, configure a integração dentro da config.yml");
                                    }
                                }
                            }
                        }

                        player.sendMessage("§cFaça a autentificação no AntiGriefing para executar este comando.");

                    }
                }
            }
        }

    }

}
