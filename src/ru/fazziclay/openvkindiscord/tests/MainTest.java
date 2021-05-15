package ru.fazziclay.openvkindiscord.tests;

import ru.fazziclay.openvkindiscord.Config;
import ru.fazziclay.openvkindiscord.openvkapi.VkApi;
import ru.fazziclay.openvkindiscord.tests.vkapi1.TestListener;

import java.util.Scanner;

public class MainTest {
    public static void main(String[] args) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("> ");
            String command = scanner.nextLine();

            if (command.equals("")) {
                break;
            }
            if (command.equals("vkApi1")) {
                VkApi vkApi = new VkApi(Config.vkToken)
                        .addEventListeners(new TestListener()
                        );
                vkApi.sendUserMessage(394267918, "Hello! from test mode vkApi1");
            }

            if (command.equals("loadConfig")) {
                Config.loadConfig();
            }
        }
    }
}
