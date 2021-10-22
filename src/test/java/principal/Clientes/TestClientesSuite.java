package principal.Clientes;

import org.junit.After;
import org.junit.Before;
import org.springframework.web.client.RestTemplate;
import principal.TestUtil;
import principal.models.ClienteModel;

import java.util.HashMap;
import java.util.Map;

//TODO: Implementar el uso de la suite en los test de CLientes :P

public abstract class TestClientesSuite {
    protected static final ClienteModel modelTest = TestUtil.buildTestCliente();
    protected static final String URI = "http://localhost:8000/clientes";

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
}

