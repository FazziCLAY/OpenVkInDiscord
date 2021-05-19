package ru.fazziclay.openvkindiscord.openvkapi;

import org.json.JSONObject;

public class ResponsePacket {
    public boolean isError;
    public int errorCode;
    public String errorMessage;
    public Object response;

    public ResponsePacket(JSONObject source) {
        if (source.has("error")) {
            isError = true;
            errorCode = source.getJSONObject("error").getInt("error_code");
            errorMessage = source.getJSONObject("error").getString("error_msg");
            return;
        }

        if (source.has("response")) {
            response = source.get("response");
        } else {
            response = source;
        }
    }

    @Override
    public String toString() {
        return "ResponsePacket{" +
                "isError=" + isError +
                ", errorCode=" + errorCode +
                ", errorMessage='" + errorMessage + '\'' +
                ", response=" + response +
                '}';
    }
}
