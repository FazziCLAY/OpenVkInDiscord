package ru.fazziclay.openvkindiscord.openvkapi;

import org.json.JSONArray;
import ru.fazziclay.openvkindiscord.Debugger;
import ru.fazziclay.openvkindiscord.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class VkApi {
    String token;

    public VkApi(String token) {
        this.token = token;
    }


    // messages.send
    public String sendUserMessage(String userId, String message) {
        return callRawMethod("messages.send", "v=5.130&random_id="+ Utils.getRandom(0, 10000) +"&user_id="+userId+"&message="+message);
    }

    public String sendChatMessage(String chatId, String message) {
        return callRawMethod("messages.send", "v=5.130&random_id="+ Utils.getRandom(0, 10000) +"&chat_id="+chatId+"&message="+message);
    }

    public String sendMessage(String recipientType, String recipientId, String message, String replyTo) {
        String args = String.format("v=5.130&random_id=%s&%s_id=%s&message=%s&reply_to=%s", Utils.getRandom(0, 10000), recipientType, recipientId, message, replyTo);
        return callRawMethod("messages.send", args);
    }

    // raw
    public String callRawMethod(String method, String args) {
        try {
            String url = "https://api.vk.com/method/" + method + "?access_token=" + this.token + "&" + args;

            InputStream inputStream = new URL(url).openStream();
            Scanner scanner = new Scanner(inputStream);
            return scanner.nextLine();

        } catch (Exception e) {
            return "FAZZICLAY_ERROR:" + e.toString();
        }
    }
}