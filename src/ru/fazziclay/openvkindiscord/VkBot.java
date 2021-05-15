package ru.fazziclay.openvkindiscord;

import net.dv8tion.jda.api.entities.TextChannel;
import org.json.JSONObject;
import ru.fazziclay.openvkindiscord.openvkapi.VkApi;
import ru.fazziclay.openvkindiscord.openvkapi.longpoll.LongPollEvent;
import ru.fazziclay.openvkindiscord.openvkapi.longpoll.LongPollThread;

import java.util.concurrent.atomic.AtomicReference;

public class VkBot {
    public static String longPollServer;
    public static String longPollKey;
    public static int longPollTs;

    public static VkApi vkApi;

    public static void loadVkBot() {
        Debugger debugger = new Debugger("VkBot", "loadVkBot");

        // VkApi
        vkApi = new VkApi(Config.vkToken);

        // Starting longPoll server
        String response = vkApi.callRawMethod("messages.getLongPollServer", "v=5.130");
        JSONObject responseJson = new JSONObject(response).getJSONObject("response");

        LongPollThread.longPollServer   = responseJson.getString("server");
        LongPollThread.longPollKey      = responseJson.getString("key");
        LongPollThread.longPollTs       = responseJson.getInt("ts");
        Thread longPollThread = new LongPollThread();
        longPollThread.start();
    }

    public static void onMessageReceived(LongPollEvent event) {
        UniversalChannel universalChannel = UniversalChannel.getUniversalChannelByVk(event.peer_id);
        if (universalChannel == null) {
            DiscordBot.bot.getGuildById("751678172378169354").createTextChannel(String.valueOf(event.peer_id)).queue((discordChannel) -> {
                Channel createdChannel = Channel.createChannel(event.peer_id-2000000000, discordChannel.getId(), 0);
                createdChannel.sendToDiscord(event.text);
            });
        }

        if (true) {
            return;
        }

        Debugger debugger = new Debugger("VkBot", "onMessageReceived", event.toString());

        int vkDialogType = 0;
        if (event.peer_id < 0) {
            vkDialogType = 2; // group
        } else if (event.peer_id > 2000000000) {
            vkDialogType = 1; // chat
        }

        Channel channel = Channel.getChannelByVk(event.peer_id, vkDialogType);


        if (channel == null) {
            debugger.error("channel == null. Creating channel...");

            int finalVkDialogType = vkDialogType;
            DiscordBot.bot.getGuildById("751678172378169354").createTextChannel(String.valueOf(event.peer_id)).queue((discordChannel) -> {

                Channel createdChannel = Channel.createChannel(event.peer_id-2000000000, discordChannel.getId(), finalVkDialogType);
                createdChannel.sendToDiscord(event.text);
            });
            return;
        }

        channel.sendToDiscord(event.text);
    }
}
