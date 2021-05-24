package ru.fazziclay.openvkindiscord;

import org.json.JSONObject;
import ru.fazziclay.openvkindiscord.console.Debugger;
import ru.fazziclay.openvkindiscord.utils.FileUtils;
import ru.fazziclay.openvkindiscord.utils.JsonUtils;

public class Config {
    public static JSONObject configFile;

    public static String authorizationDiscordToken;
    public static String authorizationVkToken;

    public static int savableFilesJsonIndent;
    public static String savableFilesPathToUniversalDialogs;
    public static String savableFilesPathToUniversalMessages = "./tests/UniversalMessages.json";

    public static String discordGuildId;
    public static Boolean isDebugEnable = true;

    public static void loadConfig() throws Exception {
        String CONFIG_PATH = "./config.json";

        Debugger debugger = new Debugger("Config", "loadConfig");

        configFile = JsonUtils.readJSONObjectFile(CONFIG_PATH);
        debugger.log("CONFIG_PATH="+CONFIG_PATH);
        debugger.log("configFile="+configFile.toString(4));

        // Authorization
        JSONObject authorization    = (JSONObject) JsonUtils.get(configFile, "authorization", new JSONObject());
        authorizationDiscordToken   = (String) JsonUtils.get(authorization, "discordToken", "");
        authorizationVkToken        = (String) JsonUtils.get(authorization, "vkToken", "");

        // SavableFiles
        JSONObject savableFiles             = (JSONObject) JsonUtils.get(configFile, "savableFiles", new JSONObject());
        savableFilesJsonIndent              = (Integer) JsonUtils.get(savableFiles, "jsonIndent", 4);
        savableFilesPathToUniversalDialogs  = (String) JsonUtils.get(savableFiles, "pathToUniversalDialogs", "universalDialogs.json");

        // Index
        discordGuildId = (String) JsonUtils.get(configFile, "discordGuildId", "");
        isDebugEnable = (Boolean) JsonUtils.get(configFile, "isDebugEnable", false);

        debugger.log("Loaded!");
        debugger.log("configFile="+configFile.toString(4));

        FileUtils.write(CONFIG_PATH, configFile.toString(4));
        debugger.log("File written!");
    }
}
