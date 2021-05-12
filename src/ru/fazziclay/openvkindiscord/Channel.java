package ru.fazziclay.openvkindiscord;

import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;

public class Channel {

    // Object
    Debugger debugger;
    String vkDialogId;
    int vkDialogType;
    String discordChannelId;

    public Channel(String vkDialogId, int vkDialogType, String discordChannelId) {
        this.vkDialogId = vkDialogId;
        this.vkDialogType = vkDialogType;
        this.discordChannelId = discordChannelId;
    }

    public void sendToVk() {

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
}
