package ru.fazziclay.openvkindiscord.bot;

import ru.fazziclay.openvkindiscord.Config;
import ru.fazziclay.openvkindiscord.console.Debugger;
import ru.fazziclay.openvkindiscord.openvkapi.VkApi;
import ru.fazziclay.openvkindiscord.openvkapi.longpoll.VkEventListener;
import ru.fazziclay.openvkindiscord.openvkapi.longpoll.event.MessageReceivedEvent;

public class VkBot extends VkEventListener {
    public static VkApi vkBot;

    public static void loadVkBot() {
        Debugger debugger = new Debugger("VkBot", "loadVkBot");
        vkBot = new VkApi(Config.vkToken)
            .addEventListeners(new VkBot());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Debugger debugger = new Debugger("VkBot", "onMessageReceived", event.toString());
    }
}
