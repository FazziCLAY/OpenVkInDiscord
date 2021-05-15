package ru.fazziclay.openvkindiscord;

import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.Webhook;
import net.dv8tion.jda.api.managers.WebhookManager;
import net.dv8tion.jda.api.requests.restaction.WebhookAction;
import net.dv8tion.jda.internal.managers.WebhookManagerImpl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class UniversalChannel {
    public static List<UniversalChannel> universalChannels = new ArrayList<>();

    public static UniversalChannel getUniversalChannelByVk(int vkDialogId) {
        for (UniversalChannel universalChannel : universalChannels) {
            if (universalChannel.vkDialogId == vkDialogId) {
                return universalChannel;
            }
        }
        return null;
    }

    public static UniversalChannel getUniversalChannelByDiscord(String discordChannelId) {
        for (UniversalChannel universalChannel : universalChannels) {
            if (universalChannel.discordChannelId.equals(discordChannelId)) {
                return universalChannel;
            }
        }
        return null;
    }

    public static UniversalChannel createUniversalChannel(int vkDialogId, String discordChannelId, byte type) {
        UniversalChannel universalChannel = new UniversalChannel();
        universalChannel.setVkDialogId(vkDialogId);
        universalChannel.setDiscordChannelId(discordChannelId);
        universalChannel.setType(type);

        universalChannels.add(universalChannel);
        return universalChannel;
    }

    // Object
    int vkDialogId;                 // Айди диалога во ВКонтакте (для пользователя айди пользователя, для чата айди чата)
    String discordChannelId;        // Айди канала в дискорде
    byte type;                      // Тип диалога (0 - личное сообщение, 1 - чат)
    List<MemberWebhook> webhooks;   // Вебхуки пользователей

    public void sendToVk(String message) {
        if (type == 0) {
            VkBot.vkApi.sendUserMessage(this.vkDialogId, message);

        } else if (type == 1) {
            VkBot.vkApi.sendChatMessage(this.vkDialogId, message);
        }
    }

    public void sendToDiscord(String message) {
        TextChannel textChannel = DiscordBot.bot.getGuildById(Config.discordGuildId).getTextChannelById(this.discordChannelId);
        textChannel.sendMessage(message).queue();
    }

    public MemberWebhook getMemberWebhookByVk(int vkDialogId) {
        for (MemberWebhook memberWebhook : this.webhooks) {
            if (memberWebhook.vkDialogId == vkDialogId) {
                return memberWebhook;
            }
        }
        return null;
    }

    // Get
    public int getVkDialogId() {
        return vkDialogId;
    }

    public String getDiscordChannelId() {
        return discordChannelId;
    }

    public byte getType() {
        return type;
    }

    public List<MemberWebhook> getWebhooks() {
        return webhooks;
    }

    // Set
    public void setVkDialogId(int vkDialogId) {
        this.vkDialogId = vkDialogId;
    }

    public void setDiscordChannelId(String discordChannelId) {
        this.discordChannelId = discordChannelId;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public void setWebhooks(List<MemberWebhook> webhooks) {
        this.webhooks = webhooks;
    }
}

class MemberWebhook {
    int vkDialogId;
    String discordWebhookId;
    String discordWebhookToken;

    public void send(String messageContent, MessageEmbed[] embeds) throws IOException {
        URL url = new URL("https://discord.com/api/webhooks/"+this.discordWebhookId+"/"+this.discordWebhookToken);
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Accept", "application/json");
        http.setRequestProperty("Content-Type", "application/json");

        String embedsString = "";
        int i = 0;
        while (i < embeds.length) {
            String prefix = ",";
            if (i == 0) {
                prefix = "";
            }
            embedsString = embedsString + (prefix + embeds[i].toData().toString());
            i++;
        }

        String data = "{\"content\": \""+messageContent+"\", \"embeds\": ["+embedsString+"]}";

        byte[] out = data.getBytes(StandardCharsets.UTF_8);

        OutputStream stream = http.getOutputStream();
        stream.write(out);

        System.out.println(data);
        System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
        http.disconnect();
    }
}