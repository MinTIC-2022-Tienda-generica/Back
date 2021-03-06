package principal.Clientes;

import com.google.gson.Gson;
import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import principal.models.ClienteModel;

import java.util.Map;

import static org.junit.Assert.*;

public class DeleteClientesTest extends TestClientesSuite {

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


        ClienteModel __modelTest = new ClienteModel();
        __modelTest.setCedulaCliente(-10L);
        __modelTest.setDireccionCliente("test 10");
        __modelTest.setNombreCliente("test 10");
        __modelTest.setEmailCliente("test 10");
        __modelTest.setTelefonoCliente("test 10");


        var client = request.postForEntity(URI, __modelTest, Map.class);
        assertNotNull(client.getBody());
        assertEquals(201, client.getBody().get("status"));

        var response = request.getForEntity(URI + "/-10", ClienteModel.class);
        assertNotNull(response.getBody());
        assertEquals("test 10", response.getBody().getNombreCliente());
        assertEquals(-10, (int) (double) response.getBody().getCedulaCliente());
        assertEquals("test 10", response.getBody().getEmailCliente());

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
