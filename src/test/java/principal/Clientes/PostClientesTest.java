package principal.Clientes;

import com.google.gson.Gson;
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

public class PostClientesTest {

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
    public void testCreateClient() {
        Map<String, Object> body = new HashMap<>();
        body.put("cedulaCliente", -2);
        body.put("direccionCliente", "Crr Test#-2");
        body.put("emailCliente", "test+1@test.com");
        body.put("nombreCliente", "test2");
        body.put("telefonoCliente", "-2");

        RestTemplate request = new RestTemplate();
        var response = request.postForEntity(URI, body, Map.class);

        assertNotNull(response.getBody());
        assertEquals(201, response.getStatusCode().value());
        assertEquals("ok", response.getBody().get("type"));

        request.delete(URI + "/-2");
    }

    @Test
    public void testDuplicateClient() {
        RestTemplate request = new RestTemplate();
        try {
            var response = request.postForEntity(URI, modelTest, Map.class);
            fail("Expected Exception: org.springframework.web.client.HttpClientErrorException$Conflict");
        } catch (HttpClientErrorException.Conflict e) {
            Gson gson = new Gson();
            var response = gson.fromJson(e.getResponseBodyAsString(), Map.class);
            assertNotNull(response);
            assertEquals(409, (int) (double) response.get("status"));
            assertEquals("already exist", response.get("type"));
            assertEquals("telefonoCliente", response.get("conflictParam"));
        }
    }

    @Test
    public void testMissingArgument() {
        RestTemplate request = new RestTemplate();
        try {
            Map<String, Object> body = new HashMap<>();
            var response = request.postForEntity(URI, body, Map.class);
            fail("Expected Exception: org.springframework.web.client.HttpClientErrorException$BadRequest");
        } catch (HttpClientErrorException.BadRequest e) {
            Gson gson = new Gson();
            var response = gson.fromJson(e.getResponseBodyAsString(), Map.class);
            assertEquals(400, (int) (double) response.get("status"));
            assertEquals("cedulaCliente", response.get("nullField"));
            assertEquals("missing or null 'cedulaCliente' argument", response.get("message"));
        }
    }

    @Test(expected = HttpClientErrorException.MethodNotAllowed.class)
    public void testMethodNotAllowed() {
        RestTemplate request = new RestTemplate();
        request.postForEntity(URI + "/-1", null, Object.class);
    }
}
