package ru.fazziclay.openvkindiscord.universal;

import org.json.JSONArray;
import ru.fazziclay.openvkindiscord.Config;
import ru.fazziclay.openvkindiscord.utils.FileUtils;
import ru.fazziclay.openvkindiscord.utils.JsonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UniversalMessage {
    // Static
    public static List<UniversalMessage> universalMessages = new ArrayList<>();

    public static UniversalMessage getByVkId(int vkId) {
        for (UniversalMessage universalMessage : universalMessages) {
            if (universalMessage.vkId == vkId) {
                return universalMessage;
            }
        }
        return null;
    }

    public static UniversalMessage getByDiscordId(String discordId) {
        for (UniversalMessage universalMessage : universalMessages) {
            if (universalMessage.discordId.equals(discordId)) {
                return universalMessage;
            }
        }
        return null;
    }

    public static UniversalMessage createNewUniversalMessage(int vkId, String discordId) {
        UniversalMessage universalMessage = new UniversalMessage(vkId, discordId);
        universalMessages.add(universalMessage);
        return universalMessage;
    }

    // Загрузить в переменную universalDialogs данные из файла
    public static void loadFromFile() {
        universalMessages = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(JsonUtils.readJSONArrayFile(Config.savableFilesPathToUniversalMessages));
        for (Object object : jsonArray) {
            // Slots
            int vkId = ((JSONArray)object).getInt(0);
            String discordId = ((JSONArray)object).getString(1);

            // add
            universalMessages.add(new UniversalMessage(vkId, discordId));
        }
    }

    // Выгрузить переменную universalDialogs в файл
    public static void saveToFile() {
        try {
            FileUtils.write(Config.savableFilesPathToUniversalMessages, listToJson().toString(Config.savableFilesJsonIndent));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Конвертировать список Универсальных Диалогов в JSONArray
    public static JSONArray listToJson() {
        JSONArray jsonArray = new JSONArray();
        for (UniversalMessage universalMessage : universalMessages) {
            JSONArray universalMessageJson = universalMessage.toJson();
            jsonArray.put(universalMessageJson);
        }
        return jsonArray;
    }

    // Object
    public int vkId;
    public String discordId;

    public UniversalMessage(int vkId, String discordId) {
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
