package principal.Usuarios;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import principal.models.UsuarioModel;

import static org.junit.Assert.*;

public class GetUsuariosTest extends TestUsuariosSuite {
    
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
