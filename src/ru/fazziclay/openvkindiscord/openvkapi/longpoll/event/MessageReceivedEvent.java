package ru.fazziclay.openvkindiscord.openvkapi.longpoll.event;

import java.util.List;

public class MessageReceivedEvent extends Event {
    public int message_id;
    public int flags;
    public int peer_id;
    public int timestamp;
    public String text;
    public int random_id;

    public MessageReceivedEvent(int type, int message_id, int flags, int peer_id, int timestamp, String text, int random_id) {
        super(type);

        this.message_id = message_id;
        this.flags = flags;
        this.peer_id = peer_id;
        this.timestamp = timestamp;
        this.text = text;
        this.random_id = random_id;
    }

    public String toString() {
        return "<LongPollEvent type="+type+", flags="+flags+", message_id="+message_id+", peer_id="+peer_id+", timestamp="+timestamp+", text="+text+", random_id="+random_id+">";
    }
}
