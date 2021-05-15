package ru.fazziclay.openvkindiscord.openvkapi.longpoll;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.fazziclay.openvkindiscord.Config;
import ru.fazziclay.openvkindiscord.openvkapi.VkApi;
import ru.fazziclay.openvkindiscord.openvkapi.longpoll.event.Event;
import ru.fazziclay.openvkindiscord.openvkapi.longpoll.event.MessageReceivedEvent;

public class LongPollThread extends Thread {
    public static String longPollServer;
    public static String longPollKey;
    public static int longPollTs;
    public static VkApi vkApi;
    public static VkEventListener eventListener;

    @Override
    public void run() {
        String a = vkApi.callRawMethod("messages.getLongPollServer", "v=5.130");
        JSONObject b = new JSONObject(a);
        if (b.has("error")) {
            System.out.println("Error in key from VkApi: " + b.toString(4));
            return;
        }
        if (b.has("response")) {
            JSONObject c = b.getJSONObject("response");
            longPollServer = c.getString("server");
            longPollKey = c.getString("key");
            longPollTs = c.getInt("ts");
        } else {
            return;
        }


        while (true) {
            String response = vkApi.callRawUrl(String.format("https://%s?act=a_check&key=%s&ts=%s&wait=0&mode=128&version=3", longPollServer, longPollKey, longPollTs));
            JSONObject responseJson = new JSONObject(response);
            longPollTs = responseJson.getInt("ts");
            JSONArray updates = responseJson.getJSONArray("updates");
            for (Object update : updates) {
                JSONArray updateJson = (JSONArray) update;
                int type = updateJson.getInt(0);

                if (type == 4) {
                    int message_id = updateJson.getInt(1);
                    int flags = updateJson.getInt(2);
                    int peer_id = updateJson.getInt(3);
                    int timestamp = updateJson.getInt(4);
                    String text = updateJson.getString(5);
                    int random_id = updateJson.getInt(6);
                    eventListener.onEvent(new MessageReceivedEvent(type, message_id,flags,peer_id,timestamp,text,random_id));

                } else {
                    eventListener.onEvent(new Event(type));
                }
            }

            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
