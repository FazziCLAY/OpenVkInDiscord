# OpenVkInDiscord
* Java app. `Vk` <---> `discord` translator.
* Буду рад помощи в разработке. Писать https://fazziclay.ru/

# Docs / Todo list (russian language)
## Exit Codes
* 9 - Main Starting error!
* 10 - discordToken or vkToken is empty!
* 11 - aborted automatically create guild.

* Итак, как оно работает, или же должно работать:
## --- Config ---
* У нас запускается загрузка конфига, она проверяет наличие файла config.json и если его нет, то создаёт его.
* После чего проверяется есть ли в config.json нужные поля. Если какого-то поля нет, то она его добавляет.
* Далее значения из конфига засовываются в статические переменные Config.class 
* И в самом конце проверяется пустые ли значения discordToken и/или vkToken, и если они
  пустые то, закрыть программу с кодом 10 попутно написав в консоль о необходимости редактирования конфига.

## --- Универсальные штуки (Universal) ---
* Далее запускается загрузка штук ниже из файлов.
* Универсальные штуки — это некие ассоциации, или же штуки которые есть и в VK и в Discord.

### ----- UniversalDialog`s -----
* Начнём с универсального диалога
* Универсальный диалог — это объект содержащий и диалог ВКонтакте, и диалог(канал на сервере) дискорд (Ну вернее он будет содержать только ID диалогов.)
* Зачем вообще он нужен?:
* * Ну, во-первых, вк и дискорд немного разные штуки.
* * Вк просто содержит диалоги, так называемые личные сообщения
    в которых может быть как личная переписка с человеком, так и допустим чат(беседа) которая, содержит
    несколько пользователей. Ну и сообщение от сообщества(практически ни как от личного диалога не отличается)

* * Дискорд же тоже имеет дополнительный функционал в том что там можно создать сервер. 
    Сервер — это такая штука, которая может содержать каналы, нас интересуют только текстовые. Им можно задать имя, описание,
    а так же написать в них сообщение.
* Для этого и нужен универсальный диалог. Для синхронизации Вк с discord. 

### ----- UniversalMessage`s -----
* Универсальное сообщение. Аналогично UniversalDialog это штука содержит:
* * ID сообщения во ВКонтакте
* * ID сообщения в discord
* Зачем нужно это?:
* * Для редактирования сообщения синхронно(и там и там)
* * Для удаления сообщения синхронно
    
## --- Bots ---
* У нас запускается 2 бота, первый бот это Discord Bot, а так же бот вк (так называемый self bot, а по-правильному standalone приложение)

### ----- DiscordBot -----
* Бот discord.
* Его задача обрабатывать сообщения полученные из дискорд сервера который у вас будет заполнен диалогами ВКонтакте
* Вот примерный его сценарий:
```text
событие::сообщение_в_дискорде() {
    Получить ID канала где сообщение было отправлено;
    Проверить есть ли универсальный канал который привязан к этому ID?;
    |true(условие выполнено)-
        |
        Отправить в этот универсальный канал сообщение в ВКонтакте;
}
```

### ----- VkBot -----
* Бот ВКонтакте.
* Вот примерный его сценарий:
```text
событие::сообщение_в_вконтакте() {
    Получить ID диалога где сообщение было отправлено;
    Проверить есть ли универсальный канал который привязан к этому ID?;
    |true(условие выполнено)-
        |
        Отправить в этот универсальный канал сообщение в discord;
    | false-
        |
        /*Создать универсальный канал*/
        Создать канал в discord с именем диалога; 
        Добавить в список универсальных каналов, созданный канал;
}
```