package ru.fazziclay.openvkindiscord;

import net.dv8tion.jda.api.entities.TextChannel;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Channel {
    //Static
    public static List<Channel> channels = new ArrayList<>();

    public static Channel getChannelByDiscord(String value) {
        for (Channel a : channels) {
            if (a.discordChannelId.equals(value)) {
                return a;
            }
        }
        return null;
    }

    public static Channel getChannelByVk(int value, int vkDialogType) {
        for (Channel a : channels) {
            if (vkDialogType == 0) {
                if (a.vkDialogId == value) {
                    return a;
                }
            }
            if (vkDialogType == 1) {
                if (a.vkDialogId == value-2000000000) {
                    return a;
                }
            }
        }
        return null;
    }

    public static Channel createChannel(int vkDialogId, String discordChannelId, int vkDialogType) {
        Channel channel = new Channel(vkDialogId, discordChannelId, vkDialogType);
        channels.add(channel);
        JSONArray channelsJson = new JSONArray();
        for (Channel b : channels) {
            JSONArray channelJson = new JSONArray();
            channelJson.put(b.vkDialogId);
            channelJson.put(b.discordChannelId);
            channelJson.put(b.vkDialogType);
            channelsJson.put(channelJson);
        }
        Utils.writeFile("./data/channels.json", channelsJson.toString(4));

        return channel;
    }


    // Object
    Debugger debugger;
    int vkDialogId;
    String discordChannelId;
    int vkDialogType;
    List<JSONArray> webhooks;

    public Channel(int vkDialogId, String discordChannelId, int vkDialogType) {
        this.vkDialogId = vkDialogId;
        this.vkDialogType = vkDialogType;
        this.discordChannelId = discordChannelId;
    }

    public void sendToVk(String message) {
        this.debugger = new Debugger("Channel", "sendToDiscord");
        if (vkDialogType == 0) {
            VkBot.vkApi.sendUserMessage(String.valueOf(vkDialogId), message);
        }
        if (vkDialogType == 1) {
            VkBot.vkApi.sendChatMessage(String.valueOf(vkDialogId), message);
        }
    }

    public void sendToDiscord(String message) {
        this.debugger = new Debugger("Channel", "sendToDiscord");
        TextChannel channel = DiscordBot.bot.getTextChannelById(this.discordChannelId);
        if (channel == null) {
            debugger.error("channel == null");
            return;
        }
        channel.sendMessage(message).queue();
    }

    public String toString() {
        return "<Channel vkDialogId="+vkDialogId+", discordChannelId="+discordChannelId+", vkDialogType="+vkDialogType+">";
    }
}
