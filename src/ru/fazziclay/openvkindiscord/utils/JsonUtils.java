package ru.fazziclay.openvkindiscord.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.fazziclay.openvkindiscord.console.Debugger;

import java.io.IOException;

public class JsonUtils {
    public static Object get(final JSONObject source, String key, Object defaultValue) {
        Debugger debugger = new Debugger("JsonUtils", "get", "source="+source.toString()+"; key="+key+"; defaultValue="+defaultValue);
        if (!source.has(key)) {
            debugger.log("Key is not exist!");
            source.put(key, defaultValue);
        }
        return source.get(key);
    }

    public static JSONObject readJSONObjectFile(String path) {
        String content = FileUtils.read(path);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(content);
        } catch (JSONException exception) {
            try {
                FileUtils.write(path, jsonObject.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    public static JSONArray readJSONArrayFile(String path) {
        String content = FileUtils.read(path);
        JSONArray jsonArray = new JSONArray();
        try {
            jsonArray = new JSONArray(content);
        } catch (JSONException exception) {
            try {
                FileUtils.write(path, jsonArray.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonArray;
    }
}
