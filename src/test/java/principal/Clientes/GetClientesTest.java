package principal.Clientes;

import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import principal.models.ClienteModel;

import static org.junit.Assert.*;


public class GetClientesTest extends TestClientesSuite {

    
    @Test
    public void testGetFoundClient() {
        RestTemplate request = new RestTemplate();
        var response = request.getForEntity(URI + "/-1", ClienteModel.class);

        assertNotNull(response.getBody());
        assertEquals(200, response.getStatusCode().value());

        assertEquals(modelTest, response.getBody());
    }

    @Test(expected = HttpClientErrorException.NotFound.class)
    public void testGetNotFoundClient() {
        RestTemplate request = new RestTemplate();
        request.getForEntity(URI + "/-2", ClienteModel.class);
    }

    @Test
    public void testGetAllClients() {
        RestTemplate request = new RestTemplate();
        var response = request.getForEntity(URI, ClienteModel[].class);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());

        assertTrue(response.getBody().length > 0);
        assertEquals(response.getBody()[0], modelTest);
    }

    @Test(expected = HttpClientErrorException.NotFound.class)
    public void testRouteNotFound() {
        RestTemplate request = new RestTemplate();
        request.getForEntity(URI + "/-1/1", Object.class);
    }
}
