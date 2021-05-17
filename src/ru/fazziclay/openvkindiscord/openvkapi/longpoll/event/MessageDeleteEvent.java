package ru.fazziclay.openvkindiscord.openvkapi.longpoll.event;

public class MessageDeleteEvent extends Event {
    public int message_id;
    public int flags;
    public int peer_id;

    public MessageDeleteEvent(int type, int message_id, int flags, int peer_id) {
        super(type);

        this.message_id = message_id;
        this.flags = flags;
        this.peer_id = peer_id;

    }

    public String toString() {
        return "<MessageDeleteEvent type="+type+", flags="+flags+", message_id="+message_id+", peer_id="+peer_id+">";
    }
}
