package principal.Usuarios;

import com.google.gson.Gson;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import principal.models.UsuarioModel;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class PutUsuariosTest extends TestUsuariosSuite {

    @Test
    public void testBasicUpdate() {
        Gson gson = new Gson();

        Map<String, Object> body = new HashMap<>();
        body.put("cedulaUsuario", -1);
        body.put("nombreUsuario", "test update");
        body.put("emailusuario", "test update email");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate request = new RestTemplate();
        HttpEntity<String> httpEntity = new HttpEntity<>(gson.toJson(body), headers);
        var response = request.exchange(URI, HttpMethod.PUT, httpEntity, Map.class);

        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCode().value());
        assertEquals("ok", response.getBody().get("type"));

        var currentData = request.getForEntity(URI + "/-1", UsuarioModel.class);

        assertNotNull(currentData.getBody());
        assertEquals("test update", currentData.getBody().getNombreUsuario());
        assertEquals(modelTest.getEmailUsuario(), currentData.getBody().getEmailUsuario());
    }

    @Test
    public void testNotIdUpdate() {
        Gson gson = new Gson();

        Map<String, Object> body = new HashMap<>();
        body.put("nombreUsuario", "test update");
        body.put("emailusuario", "test update email");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate request = new RestTemplate();
        HttpEntity<String> httpEntity = new HttpEntity<>(gson.toJson(body), headers);

        try {
            var response = request.exchange(URI, HttpMethod.PUT, httpEntity, Map.class);
            fail("Expected Exception: org.springframework.web.client.HttpClientErrorException$BadRequest");
        } catch (HttpClientErrorException.BadRequest e) {
            var response = gson.fromJson(e.getResponseBodyAsString(), Map.class);
            assertNotNull(response);
            assertEquals(400, (int) (double) response.get("status"));
            assertEquals("bad request", response.get("type"));
            assertEquals("missing or null 'cedulaUsuario' argument", response.get("message"));
            assertEquals("cedulaUsuario", response.get("nullField"));
        }
    }

    @Test
    public void testDuplicateFieldsUpdate() {
        Gson gson = new Gson();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(gson.toJson(modelTest), headers);
        RestTemplate request = new RestTemplate();

        try {
            var response = request.exchange(URI, HttpMethod.PUT, httpEntity, Map.class);
            fail("Expected Exception: org.springframework.web.client.HttpClientErrorException$Conflict");
        } catch (HttpClientErrorException.Conflict e) {
            var response = gson.fromJson(e.getResponseBodyAsString(), Map.class);
            assertEquals(409, (int) (double) response.get("status"));
            assertEquals("already exist", response.get("type"));
            assertEquals("usuario", response.get("conflictParam"));
        }
    }

    @Test
    public void testNotFoundUpdate() {
        Gson gson = new Gson();

        Map<String, Object> body = new HashMap<>();
        body.put("cedulaUsuario", -2L);
        body.put("nombreUsuario", "test update");
        body.put("emailusuario", "test update email");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate request = new RestTemplate();
        HttpEntity<String> httpEntity = new HttpEntity<>(gson.toJson(body), headers);
        try {
            var response = request.exchange(URI, HttpMethod.PUT, httpEntity, Map.class);
            fail("Expected Exception: org.springframework.web.client.HttpClientErrorException$NotFound");
        } catch (HttpClientErrorException.NotFound e) {
            var response = gson.fromJson(e.getResponseBodyAsString(), Map.class);
            assertEquals(404, (int) (double) response.get("status"));
            assertEquals("not found", response.get("type"));
        }
    }

    @Test(expected = HttpClientErrorException.MethodNotAllowed.class)
    public void testMethodNotAllowed() {
        RestTemplate request = new RestTemplate();
        request.put(URI + "/1", null);
    }
}
