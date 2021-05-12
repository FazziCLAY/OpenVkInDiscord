package ru.fazziclay.openvkindiscord;

import ru.fazziclay.openvkindiscord.Config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Debugger {
    // Static
    public static void printDebugMessage(String message) {
        if (!Config.isDebugEnable) {
            return;
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss:SSS");
        LocalDateTime now = LocalDateTime.now();

        System.out.println(Color.RESET + "["+dtf.format(now)+"] [DEBUG] " + message);
    }

    public static int getRandom(int minimum, int maximum) {
        Random random = new Random(System.currentTimeMillis());
        return random.nextInt(maximum - minimum + 1) + minimum;
    }

    // Object
    private final String fileName;
    private final String functionName;
    private final int random = getRandom(0, 999);

    public Debugger(String fileName, String functionName, String args) {
        this.fileName       = fileName;
        this.functionName   = functionName;

        printDebugMessage(String.format(Color.GREEN + "[%s:%s.%s] Called! Args=(%s)", fileName, functionName, random, args));
    }

    public Debugger(String fileName, String functionName) {
        this.fileName       = fileName;
        this.functionName   = functionName;

        printDebugMessage(String.format(Color.GREEN + "[%s:%s.%s] Called!", fileName, functionName, random));
    }

    public void log(String message) {
        printDebugMessage(String.format(Color.YELLOW + "[%s:%s.%s]: %s", fileName, functionName, random, message));
    }

    public void error(String message) {
        printDebugMessage(String.format(Color.RED + "[%s:%s.%s] [ERROR]: %s", fileName, functionName, random, message));
    }
}

class Color {
    public static String RESET="\u001B[0m";
    public static String RED="\u001B[31m";
    public static String GREEN="\u001B[32m";
    public static String YELLOW="\u001B[33m";
}
