package ru.fazziclay.openvkindiscord;

import org.json.JSONObject;
import ru.fazziclay.openvkindiscord.openvkapi.VkApi;

public class OpenVkInDiscord {
    public static JSONObject data;

    public static void main(String[] args) {
        VkApi a = new VkApi("");
        a.sendMessage("user", "408401600", "TestUser", "0");
        a.sendMessage("chat", "91", "TestChat", "295803");

        if (true) {
            return;
        }
        try {
            Debugger debugger = new Debugger("OpenVkInDiscord", "main");
            debugger.log("Test Log");
            debugger.error("Test error");
            Config.loadConfig();
            DiscordBot.loadDiscordBot();
            VkBot.loadVkBot();

            data = new JSONObject(Utils.readFile("./data.json"));

        } catch (Exception e) {
            Debugger.printDebugMessage("STARTING ERROR: JAVAERROR: " + e);
        }

    }
}
