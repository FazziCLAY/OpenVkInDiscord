package ru.fazziclay.openvkindiscord.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.fazziclay.openvkindiscord.bot.VkBot;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Utils {
    public static String getUserNameById(int userId) {
        String res = VkBot.vkBot.callRawMethod("users.get", "v=5.130&user_ids="+userId);
        JSONObject response = new JSONObject(res).getJSONArray("response").getJSONObject(0);
        return response.getString("first_name") + " " + response.getString("last_name");
    }

    public static HttpURLConnection httpPost(String urlAddress, String postArgs) throws IOException {
        URL url = new URL(urlAddress);
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");;
        http.setRequestProperty("Accept", "application/json");
        http.setRequestProperty("Content-Type", "application/json");

        OutputStream stream = http.getOutputStream();
        stream.write(postArgs.getBytes(StandardCharsets.UTF_8));

        http.disconnect();
        return http;
    }

    public static int getAuthorIdByMessageId(int messageId) {
        String response = VkBot.vkBot.callRawMethod("messages.getById", "v=5.130&message_ids="+messageId);
        JSONObject responseJson = new JSONObject(response).getJSONObject("response");
        if (responseJson.getInt("count") == 0) {
            return -1;
        }

        return responseJson.getJSONArray("items").getJSONObject(0).getInt("from_id");
    }

    public static int convertDialogId(int dialogType, int dialogId) {
        if (dialogType == 0) {
            return dialogId;
        }

        if (dialogType == 1) {
            return (dialogId-2000000000);
        }

        if (dialogType == 2) {
            return dialogId;
        }
        return -1;
    }

    public static int getDialogTypeById(int vkType) {
        if (vkType > 2000000000) {
            return 1;
        } else if (vkType < 0) {
            return 2;
        } else {
            return 0;
        }
    }

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

    public static JSONArray readJsonArrayFile(String path) {
        String fileContent = readFile(path);
        JSONArray json;
        try {
            json = new JSONArray(fileContent);
        } catch (Exception e) {
            json = new JSONArray();
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
