package ru.fazziclay.openvkindiscord.openvkapi.longpoll;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.fazziclay.openvkindiscord.Config;
import ru.fazziclay.openvkindiscord.VkBot;
import ru.fazziclay.openvkindiscord.openvkapi.VkApi;

public class LongPollThread extends Thread {
    VkApi vkApi = new VkApi(Config.vkToken);
    public static String longPollServer;
    public static String longPollKey;
    public static int longPollTs;

    @Override
    public void run() {
        String a = vkApi.callRawMethod("messages.getLongPollServer", "v=5.130");
        JSONObject b = new JSONObject(a);
        JSONObject c = b.getJSONObject("response");
        longPollServer = c.getString("server");
        longPollKey = c.getString("key");
        longPollTs = c.getInt("ts");

        while (true) {
            String s = vkApi.callRawUrl(String.format("https://%s?act=a_check&key=%s&ts=%s&wait=0&mode=128&version=3", longPollServer, longPollKey, longPollTs));
            JSONObject d = new JSONObject(s);
            longPollTs = d.getInt("ts");
            JSONArray updates = d.getJSONArray("updates");
            for (Object update : updates) {
                JSONArray updateJson = (JSONArray) update;

                int type = updateJson.getInt(0);
                if (type == 4) {
                    LongPollEvent event = new LongPollEvent();
                    event.type = type;
                    event.message_id = updateJson.getInt(1);
                    event.flags = updateJson.getInt(2);
                    event.peer_id = updateJson.getInt(3);
                    event.timestamp = updateJson.getInt(4);
                    event.text = updateJson.getString(5);
                    event.random_id = updateJson.getInt(6);

                    VkBot.onMessageReceived(event);
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
