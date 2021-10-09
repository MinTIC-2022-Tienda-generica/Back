package principal.Usuarios;

import com.google.gson.Gson;
import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import principal.models.UsuarioModel;

import java.util.Map;

import static org.junit.Assert.*;

public class DeleteUsuariosTest extends TestUsuariosSuite {

    @Test
    public void testBasicDelete() {
        Gson gson = new Gson();
        RestTemplate request = new RestTemplate();
        try {
            var response = request.getForEntity(URI + "/-2", Map.class);
            fail("Expected Exception: org.springframework.web.client.HttpClientErrorException$NotFound");
        } catch (HttpClientErrorException.NotFound e) {
            var response = gson.fromJson(e.getResponseBodyAsString(), Map.class);
            assertNull(response);
        }

        UsuarioModel __modelTest = new UsuarioModel();
        __modelTest.setCedulaUsuario(-2L);
        __modelTest.setUsuario("otro test");
        __modelTest.setEmailUsuario("test@test+1.com");
        __modelTest.setNombreUsuario("test test +1");
        __modelTest.setPassword("test test test");

        var user = request.postForEntity(URI, __modelTest, Map.class);
        assertNotNull(user.getBody());
        assertEquals(201, user.getBody().get("status"));

        var response = request.getForEntity(URI + "/-2", UsuarioModel.class);
        assertNotNull(response.getBody());
        assertEquals("test test +1", response.getBody().getNombreUsuario());
        assertEquals(-2, (int) (double) response.getBody().getCedulaUsuario());
        assertEquals("test@test+1.com", response.getBody().getEmailUsuario());

        request.delete(URI + "/-2");

        try {
            var __response = request.getForEntity(URI + "/-2", Map.class);
            fail("Expected Exception: org.springframework.web.client.HttpClientErrorException$NotFound");
        } catch (HttpClientErrorException.NotFound e) {
            var response_ = gson.fromJson(e.getResponseBodyAsString(), Map.class);
            assertNull(response_);
        }
    }

    @Test(expected = HttpClientErrorException.NotFound.class)
    public void testNotFoundDelete() {
        RestTemplate request = new RestTemplate();
        request.delete(URI + "/-3");
    }

    @Test(expected = HttpClientErrorException.MethodNotAllowed.class)
    public void testMethodNotAllowed() {
        RestTemplate request = new RestTemplate();
        request.delete(URI);
    }
}
