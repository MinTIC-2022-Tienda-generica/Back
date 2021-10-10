package principal.Proveedores;

import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import principal.models.ProveedorModel;

import static org.junit.Assert.*;

public class GetProveedoresTest extends TestProveedoresSuite {


    @Test
    public void testGetAllProveedores() {
        RestTemplate request = new RestTemplate();
        var response = request.getForEntity(URI, ProveedorModel[].class);

        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().length > 0);
        assertEquals("test test", response.getBody()[0].getNombreProveedor());
    }

    @Test(expected = HttpClientErrorException.NotFound.class)
    public void testGetNotFoundProveedor() {
        RestTemplate request = new RestTemplate();
        request.getForEntity(URI + "/-2", ProveedorModel.class);
    }

    @Test
    public void testGetFoundProveedor() {
        RestTemplate request = new RestTemplate();
        var response = request.getForEntity(URI + "/-1", ProveedorModel.class);

        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCode().value());

        assertEquals(proveedortest, response.getBody());
    }

    @Test(expected = HttpClientErrorException.NotFound.class)
    public void testRouteNotFound() {
        RestTemplate request = new RestTemplate();
        request.getForEntity(URI + "/-1/1", Object.class);
    }
}
