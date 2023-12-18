import static org.junit.Assert.assertEquals;

import java.io.IOException;
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
    public void checkConnectionToEndpointGETTest() throws InvalidKeyException, NoSuchAlgorithmException {
        SessionGenerator sessionGenerator = new SessionGenerator(null, null);
        Map<String, Object> map = new HashMap<>();
        map.put("accountType", "UNIFIED");
        HttpURLConnection connection = sessionGenerator.ConnectionGET("/v5/account/wallet-balance", map);
        try {
            assertEquals(connection.getResponseCode(), 200);
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void checkConnectionToEndpointPOSTTest() throws InvalidKeyException, NoSuchAlgorithmException {
        SessionGenerator sessionGenerator = new SessionGenerator(null, null);
        Map <String, Object> map = new HashMap<>();
        HttpURLConnection connection = sessionGenerator.ConnectionPOST("", map);
        try {
            assertEquals(connection.getResponseCode(), 200);
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
