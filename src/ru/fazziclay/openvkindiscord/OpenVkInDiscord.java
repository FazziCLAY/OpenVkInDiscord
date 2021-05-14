package ru.fazziclay.openvkindiscord;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Arrays;

public class OpenVkInDiscord {
    public static void main(String[] args) {
        Logger.info("Starting...");
        Debugger debugger = new Debugger("OpenVkInDiscord", "main", Arrays.toString(args));
        try {
            Config.loadConfig();
            DiscordBot.loadDiscordBot();
            VkBot.loadVkBot();
            OpenVkInDiscord.loadChannels();

        } catch (Exception e) {
            Logger.info("Starting error. JavaError: "+e);
            debugger.log("Starting error! JavaError: "+e);
            DiscordBot.bot.shutdownNow();
            return;
        }
        Logger.info("Started!");
    }

    public static void loadChannels() {
        Debugger debugger = new Debugger("OpenVkInDiscord", "loadChannels");
        JSONArray channelsJson;
        try {
            channelsJson = new JSONArray(Utils.readFile("./data/channels.json"));

        } catch (JSONException e) {
            channelsJson = new JSONArray();
            Utils.writeFile("./data/channels.json", channelsJson.toString(4));
        }

        for (Object a : channelsJson) {
            JSONArray channelJson = (JSONArray) a;
            int dialogId = channelJson.getInt(0);
            String discordId = channelJson.getString(1);
            int type = channelJson.getInt(2);
            Channel channel = new Channel(dialogId, discordId, type);
            Channel.channels.add(channel);
        }
    }
}
