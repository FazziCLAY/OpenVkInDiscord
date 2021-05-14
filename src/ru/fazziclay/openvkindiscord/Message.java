package ru.fazziclay.openvkindiscord;

import java.util.ArrayList;
import java.util.List;

public class Message {
    //Static
    static List<Message> messages = new ArrayList<>();

    // Object
    public String vkId;
    public String discordId;
    public Message(String vkId, String discordId) {
        this.vkId = vkId;
        this.discordId = discordId;
    }
}
