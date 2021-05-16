package ru.fazziclay.openvkindiscord.universal;

import org.json.JSONArray;

public class UniversalSender {
    // Static


    // Object
    int vkId;
    String discordId;

    public UniversalSender(int vkId, String discordId) {
        this.vkId = vkId;
        this.discordId = discordId;
    }

    public JSONArray toJson() {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(this.vkId);
        jsonArray.put(this.discordId);

        return jsonArray;
    }
}
