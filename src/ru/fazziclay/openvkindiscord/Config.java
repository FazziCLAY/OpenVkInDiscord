package ru.fazziclay.openvkindiscord;

import org.json.JSONException;
import org.json.JSONObject;

public class Config {
    public static JSONObject configFile;
    public static boolean isDebugEnable = true;

    public static String discordToken;
    public static String vkToken;
    public static String discordGuildId;

    public static void loadConfig() {
        Debugger debugger = new Debugger("Config", "loadConfig");

        // Config File
        try {
            configFile = new JSONObject(Utils.readFile("./config.json"));
            configFile.putOpt("test", "1");

        } catch (JSONException e1) {
            configFile = new JSONObject();
            configFile.put("discordToken", "");
            configFile.put("vkToken", "");
            Utils.writeFile("./config.json", configFile.toString(4));
            Logger.info("Please add Discord bot token, and Vk app token to config.json");
            System.exit(10);
            return;

        } catch (Exception e) {
            Logger.info("Error to load config.json. JavaError: "+e);
            debugger.error("configFile error: "+e);
        }
        debugger.log("configFile loaded!");

        // Variables
        try {
            discordToken = configFile.getString("discordToken");
            vkToken = configFile.getString("vkToken");
        } catch (Exception e) {
            Logger.info("Error to convert config to variables.");
            System.exit(11);
        }
        debugger.log("Variables loaded!");
    }
}
