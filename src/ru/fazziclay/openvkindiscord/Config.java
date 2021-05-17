package ru.fazziclay.openvkindiscord;

import org.json.JSONObject;
import ru.fazziclay.openvkindiscord.console.Debugger;
import ru.fazziclay.openvkindiscord.console.Logger;
import ru.fazziclay.openvkindiscord.utils.Utils;

public class Config {
    public static JSONObject configFile;
    public static boolean isDebugEnable = true;

    public static String discordToken;
    public static String vkToken;
    public static String discordGuildId;

    public static void loadConfig() {
        Debugger debugger = new Debugger("Config", "loadConfig");

        // Config File
        configFile = Utils.readJsonObjectFile("./config.json");
        Utils.putIsNotExist(configFile,"discordToken", "");
        Utils.putIsNotExist(configFile,"vkToken", "");
        Utils.putIsNotExist(configFile,"discordGuildId", "");
        Utils.writeFile("./config.json", configFile.toString(4));
        debugger.log("configFile loaded!");

        // Variables
        discordToken    = configFile.getString("discordToken");
        vkToken         = configFile.getString("vkToken");
        discordGuildId  = configFile.getString("discordGuildId");
        debugger.log("Variables loaded!");

        if (discordToken.equals("") || vkToken.equals("") || discordGuildId.equals("")) {
            Logger.info("Please add DiscordBot token, VkApp token and/or discordGuildId to config.json file!");
            OpenVkInDiscord.stop(10);
        }
    }
}
