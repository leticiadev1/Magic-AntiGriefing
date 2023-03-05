package net.winxxdeveloper.winxxantigriefing.commands;

import net.winxxdeveloper.winxxantigriefing.Terminal;
import net.winxxdeveloper.winxxantigriefing.manager.AntiGriefingManager;
import net.winxxdeveloper.winxxantigriefing.manager.AuthWebhManager;
import net.winxxdeveloper.winxxantigriefing.manager.WebhookManager;
import net.winxxdeveloper.winxxantigriefing.utils.ActionBar;
import net.winxxdeveloper.winxxantigriefing.utils.DiscordWebhook;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.awt.*;
import java.io.IOException;

public class AntiGriefingCommand extends AntiGriefingManager implements CommandExecutor {

    FileConfiguration config = Terminal.getInstance().getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player))return true;

        Player player=(Player)sender;

        if(command.getName().equalsIgnoreCase("antigriefing")){
            if(args.length == 0){
                player.sendMessage(config.getString("antigriefing.syntax-error")
                        .replace("&", "§"));
                return true;
            }
            if(args.length==1) {
                if(args[0].equals(config.getString("antigriefing.password"))) {
                    if (!isAuthenticated(player)) {
                        if (config.getBoolean("2fa.enabled")) {
                            if (player.hasPermission(config.getString("2fa.permission"))) {
                                addIntoAuthList(player);
                                ActionBar.sendActionbar(player, config.getString("antigriefing.logged-in")
                                        .replace("&", "§"));

                                if(config.getBoolean("discord-integration.enabled")){
                                    DiscordWebhook webhook = new DiscordWebhook(WebhookManager.getWebhook_url());
                                    webhook.addEmbed(new DiscordWebhook.EmbedObject()
                                            .setTitle(AuthWebhManager.getAuthSucessTitle())
                                            .setDescription(AuthWebhManager.getAuthSucessDescription()
                                                    .replace("{player}", player.getDisplayName())
                                                    .replace("{password}", args[0])
                                                    .replace("{date}", "\\nData: "+ WebhookManager.getDate()+
                                                            "\\nHorário: "+WebhookManager.getHour()))
                                            .setColor(Color.GREEN)
                                            .setFooter(AuthWebhManager.getAuthSucessFooter(), AuthWebhManager.getAuthSucessImage()));
                                    try{
                                        webhook.execute();
                                    }catch (IOException e){
                                        Bukkit.getConsoleSender().sendMessage("§cNão foi possivel enviar a embed no discord, configure a integração dentro da config.yml");
                                    }
                                }

                                return true;
                            }
                            player.sendMessage(config.getString("2fa.no-permission")
                                    .replace("&", "§"));
                            return true;
                        }
                        addIntoAuthList(player);
                        ActionBar.sendActionbar(player, config.getString("antigriefing.logged-in")
                                .replace("&", "§"));

                        if(config.getBoolean("discord-integration.enabled")){
                            DiscordWebhook webhook = new DiscordWebhook(WebhookManager.getWebhook_url());
                            webhook.addEmbed(new DiscordWebhook.EmbedObject()
                                    .setTitle(AuthWebhManager.getAuthSucessTitle())
                                    .setDescription(AuthWebhManager.getAuthSucessDescription()
                                            .replace("{player}", player.getDisplayName())
                                            .replace("{password}", args[0])
                                            .replace("{date}", "\\nData: "+ WebhookManager.getDate()+
                                                    "\\nHorário: "+WebhookManager.getHour()))
                                    .setColor(Color.GREEN)
                                    .setFooter(AuthWebhManager.getAuthSucessFooter(), AuthWebhManager.getAuthSucessImage()));
                            try{
                                webhook.execute();
                            }catch (IOException e){
                                Bukkit.getConsoleSender().sendMessage("§cNão foi possivel enviar a embed no discord, configure a integração dentro da config.yml");
                            }
                        }

                        return true;
                    }
                    player.sendMessage("§aVocê já está autentificado no AntiGriefing.");
                    return true;
                }
                if(isCommandExp(player)){
                    addValueIntoCmdExp(player);

                    if(threeTimes(player)){
                        String kick_msg=config.getString("antigriefing.kick-message")
                                .replace("&", "§")
                                .replace("{newline}", "\n");

                        player.kickPlayer(kick_msg);
                    }

                }


                player.sendMessage(config.getString("antigriefing.incorrect-password")
                        .replace("&", "§")
                        .replace("{times}", ""+getCmdExpValue(player)+""));

                if(config.getBoolean("discord-integration.enabled")){
                    DiscordWebhook webhook = new DiscordWebhook(WebhookManager.getWebhook_url());
                    webhook.addEmbed(new DiscordWebhook.EmbedObject()
                            .setTitle(AuthWebhManager.getAuthErrorTitle())
                            .setDescription(AuthWebhManager.getAuthErrorDescription()
                                    .replace("{player}", player.getDisplayName())
                                    .replace("{password}", args[0])
                                    .replace("{date}", "\\nData: "+ WebhookManager.getDate()+
                                            "\\nHorário: "+WebhookManager.getHour()))
                            .setColor(Color.RED)
                            .setFooter(AuthWebhManager.getAuthErrorFooter(), AuthWebhManager.getAuthErrorImage()));
                    try{
                        webhook.execute();
                    }catch (IOException e){
                        Bukkit.getConsoleSender().sendMessage("§cNão foi possivel enviar a embed no discord, configure a integração dentro da config.yml");
                    }
                }

            }

        }

        return false;
    }
}
