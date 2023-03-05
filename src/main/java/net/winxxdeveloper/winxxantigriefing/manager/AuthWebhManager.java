package net.winxxdeveloper.winxxantigriefing.manager;

import lombok.Getter;
import net.winxxdeveloper.winxxantigriefing.Terminal;
import net.winxxdeveloper.winxxantigriefing.utils.DiscordWebhook;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.awt.*;
import java.io.IOException;

public class AuthWebhManager {

    private static FileConfiguration config = Terminal.getInstance().getConfig();
    private static String detailsPath="discord-integration.embed-details.auth-embed.";

    @Getter
    private static String authSucessTitle=config.getString(detailsPath+"sucess.title");
    @Getter
    private static String authSucessDescription=config.getString(detailsPath+"sucess.description")
            .replace("{newline}", "\\n");
    @Getter
    private static String authSucessFooter=config.getString(detailsPath+"sucess.footer");
    @Getter
    private static String authSucessImage=config.getString(detailsPath+"sucess.image");



    @Getter
    private static String authErrorTitle=config.getString(detailsPath+"error.title");
    @Getter
    private static String authErrorDescription=config.getString(detailsPath+"error.description")
            .replace("{newline}", "\\n");
    @Getter
    private static String authErrorFooter=config.getString(detailsPath+"error.footer");
    @Getter
    private static String authErrorImage=config.getString(detailsPath+"error.image");


}
