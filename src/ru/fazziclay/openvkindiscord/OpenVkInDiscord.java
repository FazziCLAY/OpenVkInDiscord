package ru.fazziclay.openvkindiscord;

import ru.fazziclay.openvkindiscord.bot.DiscordBot;
import ru.fazziclay.openvkindiscord.bot.VkBot;
import ru.fazziclay.openvkindiscord.console.Logger;
import ru.fazziclay.openvkindiscord.universal.UniversalDialog;

public class OpenVkInDiscord {
    public static void main(String[] args) {
        Logger.info("Loading libraries...");
        try {
            if (LibsLoader.downloadLibs()) {
                Logger.info("Libraries downloaded! Please restart app.");
                return;
            }
        } catch (Exception e) {
            Logger.info("If there are no more files and you can't download them, find them on the Internet and put them in the 'libs' folder with the exact name as we showed above.");
            Logger.info("Loading libraries error. STACKTRACE:");
            e.printStackTrace();
            return;
        }
        Logger.info("Libraries loaded!");
        Logger.info("Starting...");
        try {
            Config.loadConfig();             // Load config
            UniversalDialog.loadFromFile();  // Load universal dialogs
            //UniversalMessage.loadFromFile(); // Load universal messages
            DiscordBot.loadDiscordBot();     // Load discord bot
            VkBot.loadVkBot();               // Load vk bot

        } catch (Exception exception) {
            Logger.info("Starting error. STACKTRACE:");
            exception.printStackTrace();
            stop(9);
            return;
        }
        Logger.info("Started!");
    }

    public static void stop(int status) {
        Logger.info("Stopping...");
        try {
            DiscordBot.discordBot.shutdownNow();
        } catch (Exception ignored) {}

        try {
            VkBot.vkBot.longPollThread.stop();
        } catch (Exception ignored) {}


        System.exit(status);
    }
}
