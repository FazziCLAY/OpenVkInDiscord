package ru.fazziclay.openvkindiscord.utils;

import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Utils {
    public static void putIsNotExist(final JSONObject source, String key, Object value) {
        if (!source.has(key)) {
            source.put(key, value);
        }
    }

    public static int getRandom(int minimum, int maximum) { // Получение случайного числа в диапозоне
        Random random = new Random(System.currentTimeMillis());
        return random.nextInt(maximum - minimum + 1) + minimum;
    }

    // File Utils
    public static void createDirIfNotExists(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
    }


    private static void createNewFile(String path) {
        int lastSep = path.lastIndexOf(File.separator);
        if (lastSep > 0) {
            String dirPath = path.substring(0, lastSep);
            createDirIfNotExists(dirPath);
            File folder = new File(dirPath);
            folder.mkdirs();
        }

        File file = new File(path);

        try {
            if (!file.exists())
                //noinspection ResultOfMethodCallIgnored
                file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String readFile(String path) {
        Utils.createNewFile(path);

        StringBuilder stringBuilder = new StringBuilder();
        FileReader fileReader = null;

        try {
            fileReader = new FileReader(path);

            char[] buff = new char[1024];
            int length;

            while ((length = fileReader.read(buff)) > 0) {
                stringBuilder.append(new String(buff, 0, length));
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return stringBuilder.toString();
    }

    public static JSONObject readJsonObjectFile(String path) {
        String fileContent = readFile(path);
        JSONObject json;
        try {
            json = new JSONObject(fileContent);
        } catch (Exception e) {
            json = new JSONObject();
        }

        return json;
    }

    public static void writeFile(String path, String content) {
        Utils.createNewFile(path);
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(path, false);
            fileWriter.write(content);
            fileWriter.flush();

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                if (fileWriter != null)
                    fileWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isFileExist(String path) {
        File file = new File(path);
        return file.isFile();
    }
}
