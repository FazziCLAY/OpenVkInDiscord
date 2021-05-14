package ru.fazziclay.openvkindiscord;

import java.util.List;

public class UniversalChannel {

    // Object
    int vkDialogId;                 // Айди диалога во ВКонтакте (для пользователя айди пользователя, для чата айди чата)
    String discordChannelId;        // Айди канала в дискорде
    byte type;                      // Тип диалога (50 - личное сообщение, 1 - чат)
    List<UserWebhook> webhooks;     // Вебхуки пользователей

}

class UserWebhook {

}