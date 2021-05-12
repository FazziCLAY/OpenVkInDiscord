package ru.fazziclay.openvkindiscord;

import org.json.JSONObject;

public class Config {
    public static JSONObject configFile;
    public static boolean isDebugEnable = true;

    public static String discordToken;
    public static String vkToken;

    public static void loadConfig() {
        Debugger debugger = new Debugger("Config", "loadConfig");

        // Config File
        try {
            configFile = new JSONObject(Utils.readFile("./config.json"));
            configFile.putOpt("discordToken", "");
            configFile.putOpt("vkToken", "");
        } catch (Exception e) {
            debugger.error("configFile error: "+e);
        }
        debugger.log("configFile loaded!");
        // Variables
        discordToken = configFile.getString("discordToken");
        vkToken      = configFile.getString("vkToken");
        debugger.log("Variables loaded!");
    }
}
