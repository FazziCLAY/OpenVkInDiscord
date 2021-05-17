package ru.fazziclay.openvkindiscord.openvkapi.longpoll.event;

public class MessageEditEvent extends Event {
    public int message_id;
    public int flags;
    public int peer_id;
    public int timestamp;
    public String new_text;
    public int random_id;

    public MessageEditEvent(int type, int message_id, int flags, int peer_id, int timestamp, String new_text, int random_id) {
        super(type);

        this.message_id = message_id;
        this.flags = flags;
        this.peer_id = peer_id;
        this.timestamp = timestamp;
        this.new_text = new_text;
        this.random_id = random_id;
    }

    public String toString() {
        return "<MessageEditEvent type="+type+", flags="+flags+", message_id="+message_id+", peer_id="+peer_id+", timestamp="+timestamp+", text="+new_text+", random_id="+random_id+">";
    }
}
