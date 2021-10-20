package principal.Productos;

import com.google.gson.Gson;
import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import principal.models.ProductoModel;

import java.util.Map;

import static org.junit.Assert.*;

public class DeleteProductosTest extends TestProductosSuite {

    @Test
    public void testBasicDelete() {
        Gson gson = new Gson();
        RestTemplate request = new RestTemplate();
        try {
            request.getForEntity(URI + "/-10", Map.class);
            fail("Expected Exception: org.springframework.web.client.HttpClientErrorException$NotFound");
        } catch (HttpClientErrorException.NotFound e) {
            var response = gson.fromJson(e.getResponseBodyAsString(), Map.class);
            assertNull(response);
        }

        ProductoModel __modelTest = new ProductoModel();
        __modelTest.setCodigoProducto(-10L);
        __modelTest.setNombreProducto("test 10");
        __modelTest.setNitProveedor(-1L);
        __modelTest.setIvaCompra(19);
        __modelTest.setPrecioCompra(1000L);
        __modelTest.setPrecioVenta(10000L);

        var product = request.postForEntity(URI, new Object[]{__modelTest}, Map.class);
        assertNotNull(product.getBody());
        assertEquals(201, product.getBody().get("status"));

        var response = request.getForEntity(URI + "/-10", ProductoModel.class);
        assertNotNull(response.getBody());
        assertEquals("test 10", response.getBody().getNombreProducto());
        assertEquals(-10, (int) (double) response.getBody().getCodigoProducto());

        request.delete(URI + "/-10");

        try {
            request.getForEntity(URI + "/-2", Map.class);
            fail("Expected Exception: org.springframework.web.client.HttpClientErrorException$NotFound");
        } catch (HttpClientErrorException.NotFound e) {
            var response_ = gson.fromJson(e.getResponseBodyAsString(), Map.class);
            assertNull(response_);
        }
    }

    @Test
    public void testAllDelete() {
        RestTemplate request = new RestTemplate();
        var response = request.getForEntity(URI, ProductoModel[].class);

        assertNotNull(response.getBody());
        assertTrue(response.getBody().length >= 4);

        request.delete(URI);
        var __response = request.getForEntity(URI, ProductoModel[].class);

        assertNotNull(__response.getBody());
        assertEquals(0, __response.getBody().length);
    }

    @Test(expected = HttpClientErrorException.NotFound.class)
    public void testNotFoundDelete() {
        RestTemplate request = new RestTemplate();
        request.delete(URI + "/-11");
    }
}
