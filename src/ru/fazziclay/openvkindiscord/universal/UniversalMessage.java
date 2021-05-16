package ru.fazziclay.openvkindiscord.universal;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class UniversalMessage {
    // Static
    public static List<UniversalMessage> universalMessages = new ArrayList<>();

    // Object
    int vkId;
    String discordId;

    public JSONArray toJson() {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(this.vkId);
        jsonArray.put(this.discordId);

        return jsonArray;
    }
}
