package ru.fazziclay.openvkindiscord.openvkapi;

import org.json.JSONObject;
import ru.fazziclay.openvkindiscord.console.Debugger;
import ru.fazziclay.openvkindiscord.utils.Utils;
import ru.fazziclay.openvkindiscord.openvkapi.longpoll.LongPollThread;
import ru.fazziclay.openvkindiscord.openvkapi.longpoll.VkEventListener;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class VkApi {
    String token;
    Thread longPollThread;


    public VkApi(String token) {
        this.token = token;
    }

    public VkApi addEventListeners(VkEventListener listener) {
        LongPollThread.vkApi = this;
        LongPollThread.eventListener = listener;
        LongPollThread.reloadLongPollServer();
        longPollThread = new LongPollThread();
        longPollThread.start();
        return this;
    }

    public JSONObject getLongPollServer() {
        ResponsePacket responsePacket = callRawMethod("messages.getLongPollServer", "&v=5.130");
        return (JSONObject) responsePacket.response;
    }

    // messages.send
    public ResponsePacket sendUserMessage(int userId, String message, int replyTo) {
        return sendMessage("user", userId, message, replyTo);
    }

    public ResponsePacket sendChatMessage(int chatId, String message, int replyTo) {
        return sendMessage("chat", chatId, message, replyTo);
    }

    public ResponsePacket sendMessage(String recipientType, int recipientId, String message, int replyTo) {
        String args = String.format("&v=5.130&random_id=%s&%s_id=%s&message=%s&reply_to=%s", Utils.getRandom(0, 10000), recipientType, recipientId, message, replyTo);
        return callRawMethod("messages.send", args);
    }

    // raw
    public ResponsePacket callRawMethod(String method, String args) {
        Debugger debugger = new Debugger("VkApi", "callRawMethod", "method="+method+", args="+args);
        String url = "https://api.vk.com/method/" + method + "?access_token=" + this.token + args;
        ResponsePacket responsePacket = new ResponsePacket(new JSONObject(callRawUrl(url)));
        debugger.log("Response="+ responsePacket);
        if (responsePacket.isError) {
            debugger.error("Error from VKApi! error_msg="+responsePacket.errorMessage+"; error_code="+responsePacket.errorCode);
        }
        return responsePacket;
    }

    public String callRawUrl(String url) {
        url = url.replace(" ", "%20");
        url = url.replace("\n", "\\n");
        try {
            InputStream inputStream = new URL(url).openStream();
            Scanner scanner = new Scanner(inputStream);
            return new String(scanner.nextLine().getBytes(StandardCharsets.UTF_8), StandardCharsets.US_ASCII);

        } catch (Exception e) {
            return "FAZZICLAY_ERROR:" + e;
        }
    }
}
