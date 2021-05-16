package ru.fazziclay.openvkindiscord.universal;

import net.dv8tion.jda.api.entities.TextChannel;
import org.json.JSONArray;
import ru.fazziclay.openvkindiscord.Config;
import ru.fazziclay.openvkindiscord.bot.DiscordBot;
import ru.fazziclay.openvkindiscord.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class UniversalDialog {
    // Static
    public static List<UniversalDialog> universalDialogs = new ArrayList<>();

    public static void save() { // Сохранить universalDialogs в файл
        JSONArray universalDialogsJson = new JSONArray();
        universalDialogsJson.put(0);
        for (UniversalDialog universalDialog: universalDialogs) {
            universalDialogsJson.put(universalDialog.toJson());
        }
        Utils.writeFile("./universalDialogs.json", universalDialogsJson.toString(4));
    }

    public static void load() { // Загрузить universalDialogs из файла
        JSONArray universalDialogsJson = new JSONArray(Utils.readJsonArrayFile("./universalDialogs.json"));
        int version = universalDialogsJson.getInt(0);
        if (version == 0) {
            int i = 1;
            while (i < universalDialogsJson.length()) {
                JSONArray universalDialogJson = universalDialogsJson.getJSONArray(i);
                JSONArray universalSendersJson = universalDialogJson.getJSONArray(3);
                List<UniversalSender> universalSenders = new ArrayList<>();
                int i1 = 0;
                while (i1 < universalSendersJson.length()) {
                    JSONArray universalSenderJson = universalSendersJson.getJSONArray(i1);
                    UniversalSender universalSender = new UniversalSender(universalSenderJson.getInt(0), universalSenderJson.getString(1));
                    universalSenders.add(universalSender);
                    i1++;
                }

                UniversalDialog universalDialog = new UniversalDialog(universalDialogJson.getInt(0),universalDialogJson.getString(1),universalDialogJson.getInt(2), universalSenders);
                universalDialogs.add(universalDialog);
                i++;
            }
        }
    }

    public static UniversalDialog create(int vkId, String discordId, int type) { // Создать универсальный канал
        UniversalDialog universalDialog = new UniversalDialog(vkId, discordId, type, new ArrayList<>());
        if (discordId == null) {
            discordId = "";
        }
        TextChannel textChannel = DiscordBot.discordBot.getTextChannelById(discordId);
        if (textChannel == null) {
            DiscordBot.discordBot.getGuildById(Config.discordGuildId).createTextChannel(String.valueOf(vkId)).queue((discord) -> {
                universalDialog.discordId = discord.getId();
                save();
            });
        }
        universalDialogs.add(universalDialog);
        save();
        return universalDialog;
    }

    public static UniversalDialog getByVkId(int vkId) { // Получить универсальный канал по айди ВК
        for (UniversalDialog universalDialog : universalDialogs) {
            if (universalDialog.vkId == vkId) {
                return universalDialog;
            }
        }
        return null;
    }

    public static UniversalDialog getByDiscordId(String discordId) { // Получить универсальный канал по айди Дискорд канала
        for (UniversalDialog universalDialog : universalDialogs) {
            if (universalDialog.discordId.equals(discordId)) {
                return universalDialog;
            }
        }
        return null;
    }

    // Object
    int       vkId;
    String    discordId;
    int       type;
    List<UniversalSender> universalSenders;

    public UniversalDialog(int vkId, String discordId, int type, List<UniversalSender> universalSenders) {
        this.vkId = vkId;
        this.discordId = discordId;
        this.type = type;
        this.universalSenders = universalSenders;
    }

    public JSONArray toJson() { // Конвертировать обьект в JSONArray
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(vkId);            // 0
        jsonArray.put(discordId);       // 1
        jsonArray.put(type);            // 2
        JSONArray universalSendersJson = jsonArray.put(new JSONArray()); // 3
        for (UniversalSender universalSender : universalSenders) {
            universalSendersJson.put(universalSender.toJson());
        }

        return jsonArray;
    }
}