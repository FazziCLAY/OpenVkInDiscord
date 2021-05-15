package ru.fazziclay.openvkindiscord.tests.vkapi1;

import ru.fazziclay.openvkindiscord.openvkapi.longpoll.VkEventListener;
import ru.fazziclay.openvkindiscord.openvkapi.longpoll.event.MessageReceivedEvent;

public class TestListener extends VkEventListener {
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.println("Event: " + event.toString());
    }
}
