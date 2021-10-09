package principal.Clientes;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import principal.TestUtil;
import principal.models.ClienteModel;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class GetClientesTest {

    private static final ClienteModel modelTest = TestUtil.buildTestCliente();
    private static final String URI = "http://localhost:8000/clientes";

    @Before
    public void createClient() {
        Map<String, Object> body = new HashMap<>();
        body.put("cedulaCliente", modelTest.getCedulaCliente());
        body.put("direccionCliente", modelTest.getDireccionCliente());
        body.put("emailCliente", modelTest.getEmailCliente());
        body.put("nombreCliente", modelTest.getNombreCliente());
        body.put("telefonoCliente", modelTest.getTelefonoCliente());

        RestTemplate request = new RestTemplate();
        request.postForEntity(URI, body, Map.class);
    }

    @After
    public void deleteClient() {
        RestTemplate request = new RestTemplate();
        request.delete(URI + "/-1");
    }

    @Test
    public void testGetFoundClient() {
        RestTemplate request = new RestTemplate();
        var response = request.getForEntity(URI + "/-1", ClienteModel.class);

        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCode().value());

        assertEquals(modelTest, response.getBody());
    }

    @Test(expected = HttpClientErrorException.NotFound.class)
    public void testGetNotFoundClient() {
        RestTemplate request = new RestTemplate();
        request.getForEntity(URI + "/-2", ClienteModel.class);
    }

    @Test
    public void testGetAllClients() {
        RestTemplate request = new RestTemplate();
        var response = request.getForEntity(URI, ClienteModel[].class);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());

        assertTrue(response.getBody().length > 0);
        assertEquals(response.getBody()[0], modelTest);
    }

    @Test(expected = HttpClientErrorException.NotFound.class)
    public void testRouteNotFound() {
        RestTemplate request = new RestTemplate();
        request.getForEntity(URI + "/-1/1", Object.class);
    }
}
