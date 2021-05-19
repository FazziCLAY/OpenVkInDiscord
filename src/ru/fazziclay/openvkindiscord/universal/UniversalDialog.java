package ru.fazziclay.openvkindiscord.universal;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.Webhook;
import org.json.JSONArray;
import ru.fazziclay.openvkindiscord.Config;
import ru.fazziclay.openvkindiscord.bot.DiscordBot;
import ru.fazziclay.openvkindiscord.bot.VkBot;
import ru.fazziclay.openvkindiscord.utils.FileUtils;
import ru.fazziclay.openvkindiscord.utils.JsonUtils;
import ru.fazziclay.openvkindiscord.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UniversalDialog {
    // Static
    public static List<UniversalDialog> universalDialogs; // List of UniversalDialog`s

    // Получить Универсальный Диалог по id ВК
    public static UniversalDialog getUniversalDialogByVk(int vkId) {
        int type = Utils.getDialogTypeById(vkId);

        for (UniversalDialog universalDialog : universalDialogs) {
            if (universalDialog.vkId == Utils.convertDialogId(type, vkId)) {
                return universalDialog;
            }
        }
        return null;
    }

    // Получить Универсальный Диалог по id discord
    public static UniversalDialog getUniversalDialogByDiscord(String discordId) {
        for (UniversalDialog universalDialog : universalDialogs) {
            if (universalDialog.discordId.equals(discordId)) {
                return universalDialog;
            }
        }
        return null;
    }

    // Загрузить в переменную universalDialogs данные из файла
    public static void loadFromFile() {
        universalDialogs = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(JsonUtils.readJSONArrayFile(Config.savableFilesPathToUniversalDialogs));
        for (Object object : jsonArray) {
            // Slots
            int type = ((JSONArray)object).getInt(0);
            int vkId = ((JSONArray)object).getInt(1);
            String discordId = ((JSONArray)object).getString(2);
            JSONArray universalSendersJson = ((JSONArray)object).getJSONArray(3);

            // Load UniversalSender`s
            List<UniversalSender> universalSenders = new ArrayList<>();
            for (Object o : universalSendersJson) {
                JSONArray universalSender = (JSONArray) o;
                universalSenders.add(new UniversalSender(universalSender.getInt(0), universalSender.getString(1),universalSender.getString(2)));
            }
            // add
            universalDialogs.add(new UniversalDialog(type, vkId, discordId, universalSenders));
        }
    }

    // Выгрузить переменную universalDialogs в файл
    public static void saveToFile() {
        try {
            FileUtils.write(Config.savableFilesPathToUniversalDialogs, listToJson().toString(Config.savableFilesJsonIndent));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Конвертировать список Универсальных Диалогов в JSONArray
    public static JSONArray listToJson() {
        JSONArray jsonArray = new JSONArray();
        for (UniversalDialog universalDialog : universalDialogs) {
            JSONArray universalDialogJson = universalDialog.toJson();
            jsonArray.put(universalDialogJson);
        }
        return jsonArray;
    }

    // Создать новый Универсальный Диалог
    public static UniversalDialog createNewUniversalDialog(int type, int vkId, String discordId) {
        UniversalDialog universalDialog = new UniversalDialog(type, Utils.convertDialogId(type, vkId), discordId, new ArrayList<>());

        if (discordId == null) {
            Guild guild = DiscordBot.discordBot.getGuildById(Config.discordGuildId);
            // TODO: 19.05.2021 При создании канала в дискорде, если тип чат то делать имя чата
            String channelName = null;
            if (type == 0) {
                channelName = Utils.getUserNameById(vkId);
            }
            if (channelName == null) {
                channelName = String.valueOf(vkId);
            }
            TextChannel createdChannel = guild.createTextChannel(channelName).complete();
            universalDialog.discordId = createdChannel.getId();
        }
        universalDialogs.add(universalDialog);
        saveToFile();
        return universalDialog;
    }

    // Object
    public int       type;
    public int       vkId;
    public String    discordId;
    public List<UniversalSender> universalSenders;

    public UniversalDialog(int type, int vkId, String discordId, List<UniversalSender> universalSenders) {
        this.vkId = vkId;
        this.discordId = discordId;
        this.type = type;
        this.universalSenders = universalSenders;
    }

    public UniversalSender getUniversalSenderByVkId(int vkId) {
        for (UniversalSender universalSender : this.universalSenders) {
            if (universalSender.vkId == vkId) {
                return universalSender;
            }
        }
        return null;
    }

    public UniversalSender createNewUniversalSender(int vkId) {
        TextChannel textChannel = DiscordBot.discordBot.getTextChannelById(this.discordId);

        Webhook createdWebhook = textChannel.createWebhook(Utils.getUserNameById(vkId)).complete();
        UniversalSender universalSender = new UniversalSender(vkId, createdWebhook.getId(), createdWebhook.getToken());
        this.universalSenders.add(universalSender);
        saveToFile();
        return universalSender;
    }

    public void discordSend(int vkSenderId, String message) {
        UniversalSender universalSender = getUniversalSenderByVkId(vkSenderId);
        if (universalSender == null) {
            universalSender = createNewUniversalSender(vkSenderId);
        }

        universalSender.sendToDiscord(message);
    }

    public void vkSend(String message) {
        if (this.type == 0) {
            VkBot.vkBot.sendUserMessage(this.vkId, message, 0);
        }

        if (this.type == 1) {
            VkBot.vkBot.sendChatMessage(this.vkId, message, 0);
        }
    }

    // Конвертировать объект в JSONArray
    public JSONArray toJson() {
        // Json objects indexes
        // 0 - type(integer)
        // 1 - vkId(integer)
        // 2 - discordId(string)
        // 3 - universalSenders

        JSONArray universalSendersJson = new JSONArray();
        for (UniversalSender universalSender : this.universalSenders) {
            universalSendersJson.put(new JSONArray().put(universalSender.vkId).put(universalSender.discordId).put(universalSender.discordToken));
        }

        return new JSONArray().put(this.type).put(this.vkId).put(this.discordId).put(universalSendersJson);
    }
}