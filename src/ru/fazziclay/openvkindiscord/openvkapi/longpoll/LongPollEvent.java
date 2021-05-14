package ru.fazziclay.openvkindiscord.openvkapi.longpoll;

public class LongPollEvent {
    public int type;
    public int flags;
    public int message_id;
    public int peer_id;
    public int timestamp;
    public String text;
    public int random_id;

    public LongPollEvent() {}

    public String toString() {
        return "<LongPollEvent type="+type+", flags="+flags+", message_id="+message_id+", peer_id="+peer_id+", timestamp="+timestamp+", text="+text+", random_id="+random_id+">";
    }
}
