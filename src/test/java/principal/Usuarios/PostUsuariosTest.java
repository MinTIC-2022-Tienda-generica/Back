package principal.Usuarios;

import com.google.gson.Gson;
import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class PostUsuariosTest extends TestUsuariosSuite {

    @Test
    public void testCreateUsuario() {
        Map<String, Object> body = new HashMap<>();
        body.put("usuario", "test 2");
        body.put("emailUsuario", "tes@test+1.com");
        body.put("cedulaUsuario", -2L);
        body.put("nombreUsuario", "test test +2");
        body.put("password", "password test 1");

        RestTemplate request = new RestTemplate();
        var response = request.postForEntity(URI, body, Map.class);

        assertNotNull(response.getBody());
        assertEquals(201, response.getStatusCode().value());
        assertEquals("ok", response.getBody().get("type"));

        request.delete(URI + "/-2");
    }

    @Test
    public void testCreateDuplicateUsuario() {
        RestTemplate request = new RestTemplate();
        try {
            var response = request.postForEntity(URI, modelTest, Map.class);
            fail("Expected Exception: org.springframework.web.client.HttpClientErrorException$Conflict");
        } catch (HttpClientErrorException e) {
            Gson gson = new Gson();
            var response = gson.fromJson(e.getResponseBodyAsString(), Map.class);
            assertNotNull(response);
            assertEquals(409, (int) (double) response.get("status"));
            assertEquals("already exist", response.get("type"));
            assertEquals("usuario", response.get("conflictParam"));
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
            assertEquals("cedulaUsuario", response.get("nullField"));
            assertEquals("missing or null 'cedulaUsuario' argument", response.get("message"));
        }
    }

    @Test(expected = HttpClientErrorException.MethodNotAllowed.class)
    public void testMethodNotAllowed() {
        RestTemplate request = new RestTemplate();
        request.postForEntity(URI + "/-1", null, Object.class);
    }
}
