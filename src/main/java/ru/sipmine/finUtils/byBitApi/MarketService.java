package ru.sipmine.finUtils.byBitApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class MarketService {
    private SessionGenerator sessionGenerator;

    public MarketService(SessionGenerator sessionGenerator) {
        this.sessionGenerator = sessionGenerator;
    }

    public void getServerTime() throws InvalidKeyException, NoSuchAlgorithmException, JsonSyntaxException, IOException {
        try {
            
            // ваше получение соединения
            HttpURLConnection connection = sessionGenerator.ConnectionGET("/v5/market/time");

            // Получаем поток данных из ответа
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Преобразуем JSON-строку в экземпляр класса JsonResponse
            Gson gson = new Gson();
            ResponseJson jsonResponse = gson.fromJson(response.toString(), ResponseJson.class);

            // Далее работаем с вашим объектом JsonResponse
            System.out.println("retCode: " + jsonResponse.getRetCode());
            System.out.println("retMsg: " + jsonResponse.getResult());
            System.out.println("time: " + jsonResponse.getTime());
            // Можно также обратиться к остальным полям и результатам

        } catch (Exception e) {
            e.printStackTrace();
        }
  
    }

}
