package principal.Proveedores;

import org.junit.After;
import org.junit.Before;
import org.springframework.web.client.RestTemplate;
import principal.TestUtil;
import principal.models.ProveedorModel;

import java.util.HashMap;
import java.util.Map;

//TODO: Implementar el test Delete de los proveedores.

public abstract class TestProveedoresSuite {
    protected static final String URI = "http://localhost:8000/proveedores";
    protected static final ProveedorModel proveedortest = TestUtil.buildTestProveedor();

    @Before
    public void createTestProveedor() {
        RestTemplate request = new RestTemplate();
        Map<String, Object> body = new HashMap<>();
        body.put("nitProveedor", proveedortest.getNitProveedor());
        body.put("ciudadProveedor", proveedortest.getCiudadProveedor());
        body.put("direccionProveedor", proveedortest.getDireccionProveedor());
        body.put("nombreProveedor", proveedortest.getNombreProveedor());
        body.put("telefonoProveedor", proveedortest.getTelefonoProveedor());

        request.postForEntity(URI, body, Map.class);
    }

    @After
    public void deleteTestProveedor() {

        RestTemplate request = new RestTemplate();
        request.delete(URI + "/-1");
    }
}
