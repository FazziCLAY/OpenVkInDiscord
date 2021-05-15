package ru.fazziclay.openvkindiscord.openvkapi.longpoll.event;

public class Event {
    public int type;

    public Event(int type) {
        this.type = type;
    }

    public String toString() {
        return "<Event type="+type+">";
    }
}
