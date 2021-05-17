package ru.fazziclay.openvkindiscord.openvkapi.longpoll;

import ru.fazziclay.openvkindiscord.openvkapi.longpoll.event.Event;
import ru.fazziclay.openvkindiscord.openvkapi.longpoll.event.MessageDeleteEvent;
import ru.fazziclay.openvkindiscord.openvkapi.longpoll.event.MessageEditEvent;
import ru.fazziclay.openvkindiscord.openvkapi.longpoll.event.MessageReceivedEvent;

public class VkEventListener {
    public void onMessageReceived(MessageReceivedEvent event) {
    }

    public void onMessageEdit(MessageEditEvent event) {
    }

    public void onMessageDelete(MessageDeleteEvent event) {
    }

    public void onEvent(Event event) {
        if (event.type == 4) {
            onMessageReceived((MessageReceivedEvent) event);
        } else if (event.type == 5) {
            onMessageEdit((MessageEditEvent) event);
        } else if (event.type == 2) {
            onMessageDelete((MessageDeleteEvent) event);
        }
    }
}
