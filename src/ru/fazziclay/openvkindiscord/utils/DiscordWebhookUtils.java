package ru.fazziclay.openvkindiscord.utils;

import net.dv8tion.jda.api.entities.MessageEmbed;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;

public class DiscordWebhookUtils {
    public static boolean sendWebhookMessage(String discordWebhookId, String discordWebhookToken, MessageEmbed[] embeds) throws IOException {
        return sendWebhookMessage(discordWebhookId, discordWebhookToken, embeds, null);
    }

    public static boolean sendWebhookMessage(String discordWebhookId, String discordWebhookToken, String messageContent) throws IOException {
        return sendWebhookMessage(discordWebhookId, discordWebhookToken, null, messageContent);
    }

    public static boolean sendWebhookMessage(String discordWebhookId, String discordWebhookToken, MessageEmbed[] embeds, String messageContent) throws IOException {
        String url = "https://discord.com/api/webhooks/"+discordWebhookId+"/"+discordWebhookToken;
        JSONObject dataJson = new JSONObject();
        JSONArray embedsJson = new JSONArray();

        if (embeds != null) {
            int i = 0;
            while (i < embeds.length) {
                embedsJson.put(new JSONObject(embeds[i].toData().toString()));
                i++;
            }
            dataJson.put("embeds", embedsJson);
        }
        if (messageContent != null) {
            dataJson.put("content", messageContent);
        }

        System.out.println(dataJson.toString(4));

        HttpURLConnection res = Utils.httpPost(url, dataJson.toString());
        if (res.getResponseCode() == 204) {
            return true;
        }
        return false;
    }
}
