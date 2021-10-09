package principal.Clientes;

import com.google.gson.Gson;
import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import principal.TestUtil;
import principal.models.ClienteModel;

import java.util.Map;

import static org.junit.Assert.*;

public class DeleteClientesTest {

    private static final ClienteModel modelTest = TestUtil.buildTestCliente();
    private static final String URI = "http://localhost:8000/clientes";

    @Test
    public void testBasicDelete() {
        Gson gson = new Gson();
        RestTemplate request = new RestTemplate();
        try {
            var response = request.getForEntity(URI + "/-1", Map.class);
            fail("Expected Exception: org.springframework.web.client.HttpClientErrorException$NotFound");
        } catch (HttpClientErrorException.NotFound e) {
            var response = gson.fromJson(e.getResponseBodyAsString(), Map.class);
            assertNull(response);
        }

        var user = request.postForEntity(URI, modelTest, Map.class);
        assertNotNull(user.getBody());
        assertEquals(201, user.getBody().get("status"));

        var response = request.getForEntity(URI + "/-1", ClienteModel.class);
        assertNotNull(response.getBody());
        assertEquals("test", response.getBody().getNombreCliente());
        assertEquals(-1, (int) (double) response.getBody().getCedulaCliente());
        assertEquals("test@test.com", response.getBody().getEmailCliente());

        request.delete(URI + "/-1");

        try {
            var response_ = request.getForEntity(URI + "/-1", Map.class);
            fail("Expected Exception: org.springframework.web.client.HttpClientErrorException$NotFound");
        } catch (HttpClientErrorException.NotFound e) {
            var response_ = gson.fromJson(e.getResponseBodyAsString(), Map.class);
            assertNull(response_);
        }
    }

    @Test(expected = HttpClientErrorException.NotFound.class)
    public void testNotFoundDelete() {
        RestTemplate request = new RestTemplate();
        request.delete(URI + "/-1");
    }

    @Test(expected = HttpClientErrorException.MethodNotAllowed.class)
    public void testMethodNotAllowed() {
        RestTemplate request = new RestTemplate();
        request.delete(URI);
    }
}