package principal.Proveedores;

import com.google.gson.Gson;
import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import principal.models.ProveedorModel;

import java.util.Map;

import static org.junit.Assert.*;

public class DeleteProveedoresTest extends TestProveedoresSuite {

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


        ProveedorModel __proveedortest = new ProveedorModel();
        __proveedortest.setNitProveedor(-10L);
        __proveedortest.setCiudadProveedor("test 10");
        __proveedortest.setDireccionProveedor("test 10");
        __proveedortest.setNombreProveedor("test 10");
        __proveedortest.setTelefonoProveedor("test 10");


        var client = request.postForEntity(URI, __proveedortest, Map.class);
        assertNotNull(client.getBody());
        assertEquals(201, client.getBody().get("status"));

        var response = request.getForEntity(URI + "/-10", ProveedorModel.class);
        assertNotNull(response.getBody());
        assertEquals("test 10", response.getBody().getNombreProveedor());
        assertEquals(-10, (int) (double) response.getBody().getNitProveedor());
        assertEquals("test 10", response.getBody().getTelefonoProveedor());

        request.delete(URI + "/-10");

        try {
            request.getForEntity(URI + "/-10", Map.class);
            fail("Expected Exception: org.springframework.web.client.HttpClientErrorException$NotFound");
        } catch (HttpClientErrorException.NotFound e) {
            var response_ = gson.fromJson(e.getResponseBodyAsString(), Map.class);
            assertNull(response_);
        }
    }


    @Test(expected = HttpClientErrorException.NotFound.class)
    public void testNotFoundDelete() {
        RestTemplate request = new RestTemplate();
        request.delete(URI + "/-11");
    }

    @Test(expected = HttpClientErrorException.MethodNotAllowed.class)
    public void testMethodNotAllowed() {
        RestTemplate request = new RestTemplate();
        request.delete(URI);
    }
}
