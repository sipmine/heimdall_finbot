import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import ru.sipmine.finUtils.byBitApi.SessionGenerator;

public class ByBitConnectTest {
    @Test
    public void checkConnectToServerTest() {
        SessionGenerator sessionGenerator = new SessionGenerator(null, null);
        assertEquals(sessionGenerator.checkConnect(), 200);

    }

    @Test
    public void checkConnectionToEndpointTest() throws InvalidKeyException, NoSuchAlgorithmException {
        SessionGenerator sessionGenerator = new SessionGenerator("",
                "");
        Map<String, Object> map = new HashMap<>();
        map.put("accountType", "UNIFIED");
        HttpURLConnection connection = sessionGenerator.ConnectionGET("/v5/account/wallet-balance", map);
        try {
            assertEquals(connection.getResponseCode(), 200);

            // Получение InputStream для чтения ответа от сервера
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            // Закрытие потоков чтения
            reader.close();
            inputStream.close();

            // Ваш JSON-ответ
            String jsonResponse = response.toString();
            System.out.println(jsonResponse); // Вывод JSON-ответа в консоль

            // Запись ответа в файл
            FileWriter writer = new FileWriter("response.json");
            writer.write(jsonResponse);
            writer.close();

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
