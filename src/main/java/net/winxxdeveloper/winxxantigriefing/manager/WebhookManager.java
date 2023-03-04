package net.winxxdeveloper.winxxantigriefing.manager;

import lombok.Getter;
import net.winxxdeveloper.winxxantigriefing.Terminal;
import org.bukkit.configuration.file.FileConfiguration;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WebhookManager {

    private static FileConfiguration config = Terminal.getInstance().getConfig();

    private static String detailsPath="discord-integration.embed-details.";
    @Getter
    private static String webhook_url=config.getString("discord-integration.webhook-url");

    @Getter
    private static String embedTitle=config.getString(detailsPath+"view-embed.title");
    @Getter
    private static String embedDescription=config.getString(detailsPath+"view-embed.description")
            .replace("{newline}", "\\n");
    @Getter
    private static String embedFooter=config.getString(detailsPath+"view-embed.footer");
    @Getter
    private static String embedImage=config.getString(detailsPath+"view-embed.image");

    private static Date var1 = new Date();
    private static Date var2 = new Date();
    private static SimpleDateFormat var3 = new SimpleDateFormat("dd/MM/yyyy");
    private static SimpleDateFormat var4 = new SimpleDateFormat("HH:mm:ss");

    public static String getDate(){
        return var3.format(var1);
    }

    public static String getHour(){
        return var4.format(var2);
    }

    @Getter
    String authSucessTitle=config.getString(detailsPath+"auth-embed.sucess.title");
    @Getter
    String authSucessDescription=config.getString(detailsPath+"auth-embed.sucess.description")
            .replace("{newline}", "\\n");
    @Getter
    String authSucessFooter=config.getString(detailsPath+"auth-embed.sucess.footer");
    @Getter
    String authSucessImage=config.getString(detailsPath+"auth-embed.sucess.image");

    @Getter
    String authErrorTitle=config.getString(detailsPath+"auth-embed.error.title");
    @Getter
    String authErrorDescription=config.getString(detailsPath+"auth-embed.error.description")
            .replace("{newline}", "\\n");
    @Getter
    String authErrorFooter=config.getString(detailsPath+"auth-embed.error.footer");
    @Getter
    String authErrorImage=config.getString(detailsPath+"auth-embed.error.image");


}
