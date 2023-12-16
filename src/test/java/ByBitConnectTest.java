import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import jakarta.websocket.EndpointConfig;
import jakarta.websocket.Session;
import ru.sipmine.finUtils.byBitApi.SessionGenerator;

public class ByBitConnectTest {
    @Test
    public void getConnectTest() {
        SessionGenerator sessionGenerator = new SessionGenerator(null, null);
        assertEquals(sessionGenerator.getConnect(), 200);

    }
    
   
}
