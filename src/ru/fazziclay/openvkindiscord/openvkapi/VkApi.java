package ru.fazziclay.openvkindiscord.openvkapi;

import org.json.JSONObject;
import ru.fazziclay.openvkindiscord.utils.Utils;
import ru.fazziclay.openvkindiscord.openvkapi.longpoll.LongPollThread;
import ru.fazziclay.openvkindiscord.openvkapi.longpoll.VkEventListener;

import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class VkApi {
    String token;
    Thread longPollThread;


    public VkApi(String token) {
        this.token = token;
    }

    public VkApi addEventListeners(VkEventListener listener) {
        String response = callRawMethod("messages.getLongPollServer", "v=5.130");
        JSONObject responseJson = new JSONObject(response).getJSONObject("response");

        LongPollThread.longPollServer   = responseJson.getString("server");
        LongPollThread.longPollKey      = responseJson.getString("key");
        LongPollThread.longPollTs       = responseJson.getInt("ts");
        LongPollThread.eventListener    = listener;
        LongPollThread.vkApi            = this;
        longPollThread = new LongPollThread();
        longPollThread.start();
        return this;
    }


    // messages.send
    public String sendUserMessage(int userId, String message, int replyTo) {
        return sendMessage("user", userId, message, replyTo);
    }

    public String sendChatMessage(int chatId, String message, int replyTo) {
        return sendMessage("chat", chatId, message, replyTo);
    }

    public String sendMessage(String recipientType, int recipientId, String message, int replyTo) {
        String args = String.format("v=5.130&random_id=%s&%s_id=%s&message=%s&reply_to=%s", Utils.getRandom(0, 10000), recipientType, recipientId, message.replace(" ", "%20"), replyTo);
        return callRawMethod("messages.send", args);
    }

    // raw
    public String callRawMethod(String method, String args) {
        String url = "https://api.vk.com/method/" + method + "?access_token=" + this.token + "&" + args;
        return callRawUrl(url);
    }

    public String callRawUrl(String url) {
        url = url.replace(" ", "%20");
        url = url.replace("\n", "\\n");
        try {
            InputStream inputStream = new URL(url).openStream();
            Scanner scanner = new Scanner(inputStream);
            return scanner.nextLine();

        } catch (Exception e) {
            return "FAZZICLAY_ERROR:" + e;
        }
    }
}
