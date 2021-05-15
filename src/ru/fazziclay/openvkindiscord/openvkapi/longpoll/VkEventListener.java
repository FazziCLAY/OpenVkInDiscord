package ru.fazziclay.openvkindiscord.openvkapi.longpoll;

import ru.fazziclay.openvkindiscord.openvkapi.longpoll.event.Event;
import ru.fazziclay.openvkindiscord.openvkapi.longpoll.event.MessageReceivedEvent;

public class VkEventListener {
    public void onMessageReceived(MessageReceivedEvent event) {
    }

    public void onEvent(Event event) {
        if (event.type == 4) {
            onMessageReceived((MessageReceivedEvent) event);
        }
    }
}
