package principal.Productos;

import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import principal.models.ProductoModel;
import principal.models.UsuarioModel;

import static org.junit.Assert.*;

public class GetProductosTest extends TestProductosSuite {

    @Test
    public void testGetFoundProducto() {
        RestTemplate request = new RestTemplate();
        var response = request.getForEntity(URI + "/-1", ProductoModel.class);

        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCode().value());
        assertEquals(modelTest[0], response.getBody());
        assertEquals("test 1", response.getBody().getNombreProducto());
    }

    @Test(expected = HttpClientErrorException.NotFound.class)
    public void testGetNotFoundUsuario() {
        RestTemplate request = new RestTemplate();
        var response = request.getForEntity(URI + "/-5", ProductoModel.class);
    }

    @Test
    public void testGetAllProductos() {
        RestTemplate request = new RestTemplate();
        var response = request.getForEntity(URI, ProductoModel[].class);

        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().length >= 4);
        assertEquals(modelTest[0], response.getBody()[0]);
        assertEquals(modelTest[1], response.getBody()[1]);
        assertEquals(modelTest[2], response.getBody()[2]);
        assertEquals(modelTest[3], response.getBody()[3]);
    }

    @Test(expected = HttpClientErrorException.NotFound.class)
    public void testNotFoundRoute() {
        RestTemplate request = new RestTemplate();
        var response = request.getForEntity(URI + "/-1/-1", UsuarioModel.class);
    }
}
