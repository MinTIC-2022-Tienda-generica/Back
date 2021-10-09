package principal.Usuarios;

import org.junit.After;
import org.junit.Before;
import org.springframework.web.client.RestTemplate;
import principal.TestUtil;
import principal.models.UsuarioModel;

import java.util.HashMap;
import java.util.Map;

public abstract class TestUsuariosSuite {

    protected static final UsuarioModel modelTest = TestUtil.buildTestUsuario();
    protected static final String URI = "http://localhost:8000/usuarios";

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
}
