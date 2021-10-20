package principal.Proveedores;

import com.google.gson.Gson;
import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class PostProveedoresTest extends TestProveedoresSuite {


    @Test
    public void testCreateProveedor() {
        Map<String, Object> body = new HashMap<>();
        body.put("nitProveedor", -2);
        body.put("ciudadProveedor", "CiudadTest");
        body.put("direccionProveedor", "Crr Test#-2");
        body.put("nombreProveedor", "test2");
        body.put("telefonoProveedor", "-2");

        RestTemplate request = new RestTemplate();
        var response = request.postForEntity(URI, body, Map.class);

        assertNotNull(response.getBody());
        assertEquals(201, response.getStatusCode().value());
        assertEquals("ok", response.getBody().get("type"));

        request.delete(URI + "/-2");
    }

    @Test
    public void testDuplicateProveedor() {
        RestTemplate request = new RestTemplate();
        try {
            var response = request.postForEntity(URI, proveedortest, Map.class);
            fail("Expected Exception: org.springframework.web.client.HttpClientErrorException$Conflict");
        } catch (HttpClientErrorException.Conflict e) {
            Gson gson = new Gson();
            var response = gson.fromJson(e.getResponseBodyAsString(), Map.class);
            assertNotNull(response);
            assertEquals(409, (int) (double) response.get("status"));
            assertEquals("already exist", response.get("type"));
            assertEquals("telefonoProveedor", response.get("conflictParam"));
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
            assertEquals("nitProveedor", response.get("nullField"));
            assertEquals("missing or null 'nitProveedor' argument", response.get("message"));
        }
    }

    @Test(expected = HttpClientErrorException.MethodNotAllowed.class)
    public void testMethodNotAllowed() {
        RestTemplate request = new RestTemplate();
        request.postForEntity(URI + "/-1", null, Object.class);
    }
}