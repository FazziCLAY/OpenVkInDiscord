package ru.fazziclay.openvkindiscord;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    public static boolean onFirstStarted = true;
    public static String firstStartedTime = "";

    public static void info(String message) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss:SSS");
        LocalDateTime now = LocalDateTime.now();
        String resultMessage = "["+dtf.format(now)+"] " + message;

        System.out.println(Color.RESET + resultMessage + Color.RESET);
        if (onFirstStarted) {
            DateTimeFormatter dtf_ = DateTimeFormatter.ofPattern("yyyy.MM.dd_HH:mm:ss");
            LocalDateTime now_ = LocalDateTime.now();
            firstStartedTime = dtf_.format(now);
            onFirstStarted = false;
        }

        String path = "./logs/"+firstStartedTime+".txt";
        Utils.writeFile(path, Utils.readFile(path)+"\n"+resultMessage);
    }
}

class Color {
    public static String RESET="\u001B[0m";
    public static String RED="\u001B[31m";
    public static String GREEN="\u001B[32m";
    public static String YELLOW="\u001B[33m";
}