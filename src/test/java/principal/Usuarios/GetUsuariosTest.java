package principal.Usuarios;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import principal.TestUtil;
import principal.models.UsuarioModel;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class GetUsuariosTest {
    private static final UsuarioModel modelTest = TestUtil.buildTestUsuario();
    private static final String URI = "http://localhost:8000/usuarios";

    @Before
    public void createUsuario() {
        Map<String, Object> body = new HashMap<>();
        body.put("cedulaUsuario", modelTest.getCedulaUsuario());
        body.put("usuario", modelTest.getUsuario());
        body.put("emailUsuario", modelTest.getEmailUsuario());
        body.put("nombreUsuario", modelTest.getNombreUsuario());
        body.put("password", modelTest.getPassword());

        RestTemplate request = new RestTemplate();
        request.postForEntity(URI, body, Map.class);
    }

    @After
    public void deleteUsuario() {
        RestTemplate request = new RestTemplate();
        request.delete(URI + "/-1");
    }

    @Test
    public void testGetFoundUsuario() {
        RestTemplate request = new RestTemplate();
        var response = request.getForEntity(URI + "/-1", UsuarioModel.class);

        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCode().value());
        assertTrue(BCrypt.checkpw(modelTest.getPassword(), response.getBody().getPassword()));
        assertEquals(modelTest, response.getBody());
    }

    @Test(expected = HttpClientErrorException.NotFound.class)
    public void testGetNotFoundUsuario() {
        RestTemplate request = new RestTemplate();
        var response = request.getForEntity(URI + "/-3", UsuarioModel.class);
    }

    @Test
    public void testGetAllUsuarios() {
        RestTemplate request = new RestTemplate();
        var response = request.getForEntity(URI, UsuarioModel[].class);

        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().length > 0);
        assertEquals(modelTest, response.getBody()[0]);
    }

    @Test(expected = HttpClientErrorException.NotFound.class)
    public void testNotFoundRoute() {
        RestTemplate request = new RestTemplate();
        var response = request.getForEntity(URI + "/-1/-1", UsuarioModel.class);
    }
}
