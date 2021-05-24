package ru.fazziclay.openvkindiscord.openvkapi;

import org.json.JSONException;
import org.json.JSONObject;
import ru.fazziclay.openvkindiscord.console.Debugger;
import ru.fazziclay.openvkindiscord.utils.Utils;
import ru.fazziclay.openvkindiscord.openvkapi.longpoll.LongPollThread;
import ru.fazziclay.openvkindiscord.openvkapi.longpoll.VkEventListener;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class VkApi {
    String token;
    public Thread longPollThread;


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
        try {
            message = URLEncoder.encode(message, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
        InputStream is = null;
        try {
            is = new URL(url).openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            return readAll(rd);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            /*
            InputStream inputStream = new URL(url).openStream();
            Scanner scanner = new Scanner(inputStream);
            return new String(scanner.nextLine().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
*/
        return null;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
