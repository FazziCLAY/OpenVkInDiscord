package ru.fazziclay.openvkindiscord.console;

import ru.fazziclay.openvkindiscord.utils.FileUtils;
import ru.fazziclay.openvkindiscord.utils.Utils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    public static boolean onFirstStarted = true;
    public static String firstStartedTime = "";

    public static void info(String message) {
        String resultMessage = String.format("[%s INFO] %s", getCurrentTime(), message);
        toConsole(resultMessage);
        toFile(resultMessage);
    }

    public static void error(String message) {
        String resultMessage = Color.RED + String.format("[%s ERROR] %s", getCurrentTime(), message);
        toConsole(resultMessage);
        toFile(resultMessage);
    }

    public static void warn(String message) {
        String resultMessage = Color.YELLOW + String.format("[%s WARN] %s", getCurrentTime(), message);
        toConsole(resultMessage);
        toFile(resultMessage);
    }

    private static String getCurrentTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss"); // "yyyy/MM/dd HH:mm:ss:SSS"
        LocalDateTime localDateTime = LocalDateTime.now();
        return dateTimeFormatter.format(localDateTime);
    }

    private static void toFile(String message) {
        if (onFirstStarted) {
            firstStartedTime = getCurrentTime();
            onFirstStarted = false;
        }
        String path = "logs/"+firstStartedTime+".txt";
        try {
            FileUtils.write(path, FileUtils.read(path)+"\n"+message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void toConsole(String message) {
        System.out.println(Color.RESET + message + Color.RESET);
    }
}

