package ru.fazziclay.openvkindiscord.openvkapi.longpoll;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.fazziclay.openvkindiscord.openvkapi.VkApi;
import ru.fazziclay.openvkindiscord.openvkapi.longpoll.event.Event;
import ru.fazziclay.openvkindiscord.openvkapi.longpoll.event.MessageDeleteEvent;
import ru.fazziclay.openvkindiscord.openvkapi.longpoll.event.MessageEditEvent;
import ru.fazziclay.openvkindiscord.openvkapi.longpoll.event.MessageReceivedEvent;

public class LongPollThread extends Thread {
    public static String longPollServer;
    public static String longPollKey;
    public static int longPollTs;
    public static VkApi vkApi;
    public static VkEventListener eventListener;

    @Override
    public void run() {
        while (true) {
            try {
                check();
                Thread.sleep(3 * 1002);
            } catch (InterruptedException ignored) {}
        }
    }

    public static void check() {
        String response = vkApi.callRawUrl(String.format("https://%s?act=a_check&key=%s&ts=%s&wait=0&mode=128&version=3", longPollServer, longPollKey, longPollTs));
        JSONObject responseJson = new JSONObject(response);

        if (responseJson.has("failed")) {
            int failedCode = responseJson.getInt("failed");
            if (failedCode == 1) {
                longPollTs = responseJson.getInt("ts");
            } else {
                reloadLongPollServer();
            }
            return;
        }
        longPollTs = responseJson.getInt("ts");
        JSONArray updates = responseJson.getJSONArray("updates");
        for (Object update : updates) {
            JSONArray updateJson = (JSONArray) update;
            int type = updateJson.getInt(0);

            if (type == 4) {
                int message_id  = updateJson.getInt(1);
                int flags       = updateJson.getInt(2);
                int peer_id     = updateJson.getInt(3);
                int timestamp   = updateJson.getInt(4);
                String text     = updateJson.getString(5);
                int random_id   = updateJson.getInt(6);
                eventListener.onEvent(new MessageReceivedEvent(type, message_id, flags, peer_id, timestamp, text, random_id));

            } else if (type == 5) {
                int message_id  = updateJson.getInt(1);
                int flags       = updateJson.getInt(2);
                int peer_id     = updateJson.getInt(3);
                int timestamp   = updateJson.getInt(4);
                String text     = updateJson.getString(5);
                int random_id   = updateJson.getInt(6);
                eventListener.onEvent(new MessageEditEvent(type, message_id, flags, peer_id, timestamp, text, random_id));

            } else if (type == 2) {
                int message_id  = updateJson.getInt(1);
                int flags       = updateJson.getInt(2);
                int peer_id     = updateJson.getInt(3);
                eventListener.onEvent(new MessageDeleteEvent(type, message_id, flags, peer_id));

            } else {
                eventListener.onEvent(new Event(type));
            }
        }
    }

    public static void reloadLongPollServer() {
        JSONObject c = vkApi.getLongPollServer();
        longPollServer = c.getString("server");
        longPollKey = c.getString("key");
        longPollTs = c.getInt("ts");
    }
}
