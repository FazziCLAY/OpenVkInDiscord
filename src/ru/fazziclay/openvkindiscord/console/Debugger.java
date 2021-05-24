package ru.fazziclay.openvkindiscord.console;

import ru.fazziclay.openvkindiscord.Config;

import java.util.Random;

public class Debugger {


    // Static
    public static void printDebugMessage(String message) {
        if (!Config.isDebugEnable) {
            return;
        }

        Logger.info("[DEBUG] " + message);
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

    public Debugger(String fileName) {
        this.fileName       = fileName;
        this.functionName   = null;

        printDebugMessage(String.format(Color.GREEN + "[%s.%s] Called!", fileName, random));
    }

    public void log(String message) {
        printDebugMessage(String.format(Color.YELLOW + "[%s:%s.%s]: %s", fileName, functionName, random, message));
    }

    public void error(String message) {
        printDebugMessage(String.format(Color.RED + "[%s:%s.%s] [ERROR]: %s", fileName, functionName, random, message));
    }
}
