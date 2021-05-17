package ru.fazziclay.openvkindiscord.tests;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import ru.fazziclay.openvkindiscord.Config;
import ru.fazziclay.openvkindiscord.bot.DiscordBot;
import ru.fazziclay.openvkindiscord.bot.VkBot;
import ru.fazziclay.openvkindiscord.openvkapi.VkApi;
import ru.fazziclay.openvkindiscord.tests.vkapi1.TestListener;
import ru.fazziclay.openvkindiscord.universal.UniversalDialog;
import ru.fazziclay.openvkindiscord.utils.DiscordWebhookUtils;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Scanner;

public class MainTest {
    public static void main(String[] args) throws IOException {
        while (true) {
            // Input
            Scanner scanner = new Scanner(System.in);
            System.out.print("> ");
            String input = scanner.nextLine();

            // Var
            String[] commandArgs = input.split(" ");
            String command = commandArgs[0];
            System.out.println(Arrays.toString(commandArgs));

            //
            if (command.equals("exit")) {
                break;
            }

            if (command.equalsIgnoreCase("vkApi1")) {
                VkApi vkApi = new VkApi(Config.vkToken)
                        .addEventListeners(new TestListener()
                        );
                vkApi.sendUserMessage(394267918, "Hello! from test mode vkApi1", 0);
            }

            if (command.equalsIgnoreCase("ud")) {
                if (commandArgs[1].equalsIgnoreCase("loadInFile")) {
                    UniversalDialog.loadInFile();
                } else if (commandArgs[1].equalsIgnoreCase("saveToFile")) {
                    UniversalDialog.saveToFile();
                } else if (commandArgs[1].equalsIgnoreCase("listToJson")) {
                    System.out.println(UniversalDialog.listToJson().toString(4));
                } else if (commandArgs[1].equalsIgnoreCase("createNew")) {
                    UniversalDialog a = UniversalDialog.createNewUniversalDialog(Integer.parseInt(commandArgs[2]), Integer.parseInt(commandArgs[3]), commandArgs[4]);
                    System.out.println("Created: \n" + a.toJson());
                } else if (commandArgs[1].equalsIgnoreCase("test1")) {
                    UniversalDialog.universalDialogs.get(0).vkSend("VkMessage");
                } else if (commandArgs[1].equalsIgnoreCase("test2")) {
                    UniversalDialog a = UniversalDialog.universalDialogs.get(0);
                    a.discordSend(a.vkId, "VkMessage");
                } else {
                    System.out.println("Unknown args.");
                }
            }

            if (command.equalsIgnoreCase("dwu")) {
                String id = "842711389037396018";
                String token = "ya2jDE1yCPmX8YTWd1WqkT3xnHnmfa7VTBWg-BaRNJ93Wrn1xudvqTuGTpyBEwawAgKv";

                if (commandArgs[1].equalsIgnoreCase("test1")) {
                    String message = "Hello";
                    boolean res = false;
                    try {
                        res = DiscordWebhookUtils.sendWebhookMessage(id, token, message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Response="+res);
                }

                if (commandArgs[1].equalsIgnoreCase("test2")) {
                    String message = "Hello test2 messageContent";
                    EmbedBuilder embedBuilder1 = new EmbedBuilder();
                    embedBuilder1.setTitle("test2");
                    boolean res = false;
                    try {
                        res = DiscordWebhookUtils.sendWebhookMessage(id, token, new MessageEmbed[]{embedBuilder1.build()}, message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Response="+res);
                }

                if (commandArgs[1].equalsIgnoreCase("test3")) {
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder.setTitle("test3");
                    boolean res = false;
                    try {
                        res = DiscordWebhookUtils.sendWebhookMessage(id, token, new MessageEmbed[]{embedBuilder.build()});
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Response="+res);
                }
            }

            if (command.equalsIgnoreCase("loadConfig")) {
                Config.loadConfig();
            }

            if (command.equalsIgnoreCase("loadDiscordBot")) {
                try {
                    DiscordBot.loadDiscordBot();
                } catch (LoginException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (command.equalsIgnoreCase("loadVkBot")) {
                VkBot.loadVkBot();
            }

            if (command.equalsIgnoreCase("testCreateWebhook") || command.equalsIgnoreCase("testCW")) {
                URL url = new URL("https://discord.com/api/channels/751678172378169358");
                HttpURLConnection http = (HttpURLConnection)url.openConnection();
                http.setRequestMethod("GET");
                http.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");;
                http.setRequestProperty("Accept", "application/json");
                http.setRequestProperty("Authorization", "Bot ODQyMDY4NTM5MTk5MzI0MjAw.YJv7cA.2R20pXfQ6Ls2Il2b1aIRbjMfm-U");

                System.out.println(http.getResponseCode() + " " + http.getResponseMessage() + " " + http.getContent().toString());
                http.disconnect();


            }
        }
    }
}
