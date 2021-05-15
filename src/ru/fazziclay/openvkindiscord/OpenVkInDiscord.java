package ru.fazziclay.openvkindiscord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.Arrays;

public class OpenVkInDiscord {
    public static void main(String[] args) throws IOException {
        // DEBUG ZONE - START

        MemberWebhook memberWebhook = new MemberWebhook();
        memberWebhook.discordWebhookId = "842711389037396018";
        memberWebhook.discordWebhookToken = "ya2jDE1yCPmX8YTWd1WqkT3xnHnmfa7VTBWg-BaRNJ93Wrn1xudvqTuGTpyBEwawAgKv";

        EmbedBuilder embed1 = new EmbedBuilder();
        embed1.setTitle("1");
        EmbedBuilder embed2 = new EmbedBuilder();
        embed2.setTitle("2");

        MessageEmbed[] embeds = {embed1.build(), embed2.build()};

        memberWebhook.send("Hello", embeds);

        if (true) {
            return;
        }

        // DEBUG ZONE - END
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
