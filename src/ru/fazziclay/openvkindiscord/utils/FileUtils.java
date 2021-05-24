package ru.fazziclay.openvkindiscord.utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {
    public static void createDirIfNotExists(String path) {
        File file = new File(path);
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.mkdirs();
        }
    }

    private static void createNew(String path) {
        int lastSep = path.lastIndexOf(File.separator);
        if (lastSep > 0) {
            String dirPath = path.substring(0, lastSep);
            createDirIfNotExists(dirPath);
            File folder = new File(dirPath);
            //noinspection ResultOfMethodCallIgnored
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


    public static String read(String path) {
        path = path.replace("/", File.separator);
        createNew(path);

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

    public static void write(String path, String content) throws IOException {
        path = path.replace("/", File.separator);
        createNew(path);
        FileWriter fileWriter = new FileWriter(path, false);
        fileWriter.write(content);
        fileWriter.flush();
        fileWriter.close();
    }

    public static boolean isExist(String path) {
        File file = new File(path.replace("/", File.separator));
        return file.isFile();
    }
}
