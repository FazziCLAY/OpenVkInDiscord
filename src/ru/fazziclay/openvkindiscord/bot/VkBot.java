package ru.fazziclay.openvkindiscord.bot;

import ru.fazziclay.openvkindiscord.Config;
import ru.fazziclay.openvkindiscord.console.Debugger;
import ru.fazziclay.openvkindiscord.openvkapi.VkApi;
import ru.fazziclay.openvkindiscord.openvkapi.longpoll.VkEventListener;
import ru.fazziclay.openvkindiscord.openvkapi.longpoll.event.MessageDeleteEvent;
import ru.fazziclay.openvkindiscord.openvkapi.longpoll.event.MessageEditEvent;
import ru.fazziclay.openvkindiscord.openvkapi.longpoll.event.MessageReceivedEvent;
import ru.fazziclay.openvkindiscord.universal.UniversalDialog;
import ru.fazziclay.openvkindiscord.utils.Utils;

public class VkBot extends VkEventListener {
    public static VkApi vkBot;

    public static void loadVkBot() {
        Debugger debugger = new Debugger("VkBot", "loadVkBot");
        vkBot = new VkApi(Config.authorizationVkToken)
            .addEventListeners(new VkBot());
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Debugger debugger = new Debugger("VkBot", "onMessageReceived", event.toString());
        UniversalDialog universalDialog = UniversalDialog.getUniversalDialogByVk(event.peer_id);

        if (universalDialog == null) {
            debugger.log("universalDialog == null");
            universalDialog = UniversalDialog.createNewUniversalDialog(Utils.getDialogTypeById(event.peer_id), event.peer_id, null);
        }
        universalDialog.discordSend(Utils.getAuthorIdByMessageId(event.message_id), event.text);
    }

    @Override
    public void onMessageEdit(MessageEditEvent event) {
        Debugger debugger = new Debugger("VkBot", "onMessageEdit", event.toString());
    }

    @Override
    public void onMessageDelete(MessageDeleteEvent event) {
        Debugger debugger = new Debugger("VkBot", "onMessageDelete", event.toString());
    }
}
