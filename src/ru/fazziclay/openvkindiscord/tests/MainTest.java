package ru.fazziclay.openvkindiscord.tests;

import ru.fazziclay.openvkindiscord.Config;
import ru.fazziclay.openvkindiscord.bot.DiscordBot;
import ru.fazziclay.openvkindiscord.bot.VkBot;

import java.util.Scanner;

public class MainTest {
    public static void main(String[] args) throws Exception {
        while (true) {
            // Input
            Scanner scanner = new Scanner(System.in);
            System.out.print("> ");
            String input = scanner.nextLine();

            // Var
            String[] commandArgs = input.split(" ");
            String command = commandArgs[0];

            if (command.equals("exit")) {
                break;
            }

            onCommand(command, commandArgs);
        }
    }

    public static void onCommand(String command, String[] args) throws Exception {
        if (isCommand(command, "loadconfig", "loadcfg", "lconf", "loadconf", "lcfg")) {
            Config.loadConfig();
        }

        if (isCommand(command, "vkbotstart", "vkbotload", "vkbotloadbot")) {
            VkBot.loadVkBot();
        }

        if (isCommand(command, "dsbotload", "discordbotload", "discordbotstart", "dsbotstart")) {
            DiscordBot.loadDiscordBot();
        }
    }

    public static boolean isCommand(String input, String... c) {
        for (String d : c) {
            if (input.equalsIgnoreCase(d)) {
                return true;
            }
        }
        return false;
    }
}
