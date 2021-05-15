package ru.fazziclay.openvkindiscord;

import ru.fazziclay.openvkindiscord.bot.DiscordBot;
import ru.fazziclay.openvkindiscord.bot.VkBot;
import ru.fazziclay.openvkindiscord.console.Logger;

public class OpenVkInDiscord {
    public static void main(String[] args) {
        Logger.info("Starting...");
        try {
            Config.loadConfig();            // Load config
            DiscordBot.loadDiscordBot();    // Load discord bot
            VkBot.loadVkBot();              // Load vk bot

        } catch (Exception exception) {
            Logger.info("Starting error. Error: " + exception);
            stop(1);
            return;
        }
        Logger.info("Started!");
    }

    public static void stop(int status) {
        Logger.info("Stopping...");
        try {
            DiscordBot.discordBot.shutdownNow();
        } catch (Exception ignored) {}
        System.exit(status);
    }
}
