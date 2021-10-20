package principal.Productos;

import org.junit.After;
import org.junit.Before;
import org.springframework.web.client.RestTemplate;
import principal.TestUtil;
import principal.models.ProductoModel;

import java.util.HashMap;
import java.util.Map;

public abstract class TestProductosSuite {

    protected static final ProductoModel[] modelTest = TestUtil.buildTestProductos();
    protected static final String URI = "http://localhost:8000/productos";
    protected static final String URI_PROVEEDORES = "http://localhost:8000/proveedores";

    @Before
    public void createProductos() {
        RestTemplate request = new RestTemplate();
        Map<String, Object> body1 = new HashMap<>();
        body1.put("nitProveedor", "-1");
        body1.put("ciudadProveedor", "test ciudad 1");
        body1.put("direccionProveedor", "test direccion 1");
        body1.put("nombreProveedor", "test nombre proveedor 1");
        body1.put("telefonoProveedor", "-1");

        Map<String, Object> body2 = new HashMap<>();
        body2.put("nitProveedor", "-2");
        body2.put("ciudadProveedor", "test ciudad 2");
        body2.put("direccionProveedor", "test direccion 2");
        body2.put("nombreProveedor", "test nombre proveedor 2");
        body2.put("telefonoProveedor", "-2");

        request.postForEntity(URI_PROVEEDORES, body1, Map.class);
        request.postForEntity(URI_PROVEEDORES, body2, Map.class);

        request.postForEntity(URI, modelTest, Map.class);
    }

    @After
    public void deleteProductos() {
        RestTemplate request = new RestTemplate();
        request.delete(URI);
        request.delete(URI_PROVEEDORES + "/-1");
        request.delete(URI_PROVEEDORES + "/-2");
    }
}
