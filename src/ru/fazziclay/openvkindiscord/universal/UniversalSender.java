package ru.fazziclay.openvkindiscord.universal;

import net.dv8tion.jda.api.entities.MessageEmbed;
import org.json.JSONArray;
import ru.fazziclay.openvkindiscord.utils.DiscordWebhookUtils;

import java.io.IOException;

public class UniversalSender {
    // Static


    // Object
    public int vkId;
    public String discordId;
    public String discordToken;

    public UniversalSender(int vkId, String discordId, String discordToken) {
        this.vkId = vkId;
        this.discordId = discordId;
        this.discordToken = discordToken;
    }

    public void sendToDiscord(String message) {
        try {
            DiscordWebhookUtils.sendWebhookMessage(discordId, discordToken, message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToDiscord(MessageEmbed[] embeds) {
        try {
            DiscordWebhookUtils.sendWebhookMessage(discordId, discordToken, embeds);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToDiscord(String message, MessageEmbed[] embeds) {
        try {
            DiscordWebhookUtils.sendWebhookMessage(discordId, discordToken, embeds, message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONArray toJson() {
        return new JSONArray().put(this.vkId).put(this.discordId).put(this.discordToken);
    }
}
