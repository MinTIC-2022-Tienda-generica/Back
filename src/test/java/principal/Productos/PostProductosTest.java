package principal.Productos;

import com.google.gson.Gson;
import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import principal.models.ProductoModel;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class PostProductosTest extends TestProductosSuite {

    @Test
    public void testCreateProductos() {
        Map<String, Object> body1 = new HashMap<>();
        body1.put("codigoProducto", "-1");
        body1.put("nombreProducto", "test 5");
        body1.put("nitProveedor", "-1");
        body1.put("ivaCompra", "19");
        body1.put("precioCompra", 500L);
        body1.put("precioVenta", 5000L);

        Map<String, Object> body2 = new HashMap<>();
        body2.put("codigoProducto", "-2");
        body2.put("nombreProducto", "test 6");
        body2.put("nitProveedor", "-2");
        body2.put("ivaCompra", "19");
        body2.put("precioCompra", 600L);
        body2.put("precioVenta", 6000L);

        RestTemplate request = new RestTemplate();
        var response = request.postForEntity(URI, new Object[]{body1, body2}, Map.class);

        assertNotNull(response.getBody());
        assertEquals(201, response.getStatusCode().value());
        assertEquals("ok", response.getBody().get("type"));

        var __response = request.getForEntity(URI + "/-1", ProductoModel.class);
        assertNotNull(__response.getBody());
        assertEquals(body1.get("nombreProducto"), __response.getBody().getNombreProducto());
        assertEquals(body1.get("precioCompra"), __response.getBody().getPrecioCompra());
    }

    @Test
    public void testZeroLengthArray() {
        RestTemplate request = new RestTemplate();
        try {
            var response = request.postForEntity(URI, new ProductoModel[]{}, Map.class);
            fail("Expected Exception: org.springframework.web.client.HttpClientErrorException$BadRequest");
        } catch (HttpClientErrorException.BadRequest e) {
            Gson gson = new Gson();
            var response = gson.fromJson(e.getResponseBodyAsString(), Map.class);
            System.out.println(response);
            assertEquals(400, (int) (double) response.get("status"));
            assertEquals("provided zero length array", response.get("message"));
        }
    }

    @Test
    public void testMissingArgument() {
        RestTemplate request = new RestTemplate();
        try {
            var body = new ProductoModel[]{new ProductoModel()};
            var response = request.postForEntity(URI, body, Map.class);
            fail("Expected Exception: org.springframework.web.client.HttpClientErrorException$BadRequest");
        } catch (HttpClientErrorException.BadRequest e) {
            Gson gson = new Gson();
            var response = gson.fromJson(e.getResponseBodyAsString(), Map.class);
            System.out.println(response);
            assertEquals(400, (int) (double) response.get("status"));
            assertEquals("codigoProducto", response.get("nullField"));
            assertEquals("missing or null 'codigoProducto' argument", response.get("message"));
        }
    }

    @Test(expected = HttpClientErrorException.MethodNotAllowed.class)
    public void testMethodNotAllowed() {
        RestTemplate request = new RestTemplate();
        request.postForEntity(URI + "/-1", null, Object.class);
    }
}
