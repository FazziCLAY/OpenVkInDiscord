package ru.fazziclay.openvkindiscord.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.fazziclay.openvkindiscord.bot.VkBot;
import ru.fazziclay.openvkindiscord.console.Debugger;
import ru.fazziclay.openvkindiscord.openvkapi.ResponsePacket;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Utils {
    public static String getUserNameById(int userId) {
        Debugger debugger = new Debugger("Utils", "getUserNameById", "userId="+userId);
        ResponsePacket responsePacket = VkBot.vkBot.callRawMethod("users.get", "&v=5.130&user_ids="+userId);
        if (responsePacket.isError) {
            debugger.error("responsePacket.isError: true! responsePacket.errorMessage="+responsePacket.errorMessage);
            return null;
        }
        JSONObject response = ((JSONArray) responsePacket.response).getJSONObject(0);
        return response.getString("first_name") + " " + response.getString("last_name");
    }

    public static HttpURLConnection httpPost(String urlAddress, String postArgs) throws IOException {
        URL url = new URL(urlAddress);
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        http.setRequestProperty("Accept", "application/json");
        http.setRequestProperty("Content-Type", "application/json");

        OutputStream stream = http.getOutputStream();
        stream.write(postArgs.getBytes(StandardCharsets.UTF_8));

        http.disconnect();
        return http;
    }

    public static int getAuthorIdByMessageId(int messageId) {
        ResponsePacket responsePacket = VkBot.vkBot.callRawMethod("messages.getById", "&v=5.130&message_ids="+messageId);
        JSONObject response = (JSONObject) responsePacket.response;
        if (response.getInt("count") == 0) {
            return -1;
        }

        return response.getJSONArray("items").getJSONObject(0).getInt("from_id");
    }

    public static int convertDialogId(int dialogType, int dialogId) {
        if (dialogType == 0) {
            return dialogId;
        }

        if (dialogType == 1) {
            return (dialogId-2000000000);
        }

        if (dialogType == 2) {
            return dialogId;
        }
        return -1;
    }

    public static int getDialogTypeById(int vkType) {
        if (vkType > 2000000000) {
            return 1;
        } else if (vkType < 0) {
            return 2;
        } else {
            return 0;
        }
    }

    public static int getRandom(int minimum, int maximum) { // Получение случайного числа в диапазоне
        Random random = new Random(System.currentTimeMillis());
        return random.nextInt(maximum - minimum + 1) + minimum;
    }
}
