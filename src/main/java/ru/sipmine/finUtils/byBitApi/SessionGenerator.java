package ru.sipmine.finUtils.byBitApi;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import jakarta.websocket.ClientEndpoint;

@ClientEndpoint
public class SessionGenerator {
    private String apiKeySecret;
    private String apiKey;

    public SessionGenerator(String apiKeySecret, String apiKey) {
        this.apiKeySecret = apiKeySecret;
        this.apiKey = apiKey;

    }

    public  int getConnect() {
        int statusCode = -1;
        try {
            URL url = new URL("https://api.bybit.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            statusCode = connection.getResponseCode();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return statusCode;
    }
    
    // create header for connection [] []


}
