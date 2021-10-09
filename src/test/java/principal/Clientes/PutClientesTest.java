package principal.Clientes;

import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import principal.TestUtil;
import principal.models.ClienteModel;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class PutClientesTest {
    private static final ClienteModel modelTest = TestUtil.buildTestCliente();
    private static final String URI = "http://localhost:8000/clientes";

    @Before
    public void createClient() {
        Map<String, Object> body = new HashMap<>();
        body.put("cedulaCliente", modelTest.getCedulaCliente());
        body.put("direccionCliente", modelTest.getDireccionCliente());
        body.put("emailCliente", modelTest.getEmailCliente());
        body.put("nombreCliente", modelTest.getNombreCliente());
        body.put("telefonoCliente", modelTest.getTelefonoCliente());

        RestTemplate request = new RestTemplate();
        request.postForEntity(URI, body, Map.class);
    }

    @After
    public void deleteClient() {

        RestTemplate request = new RestTemplate();
        request.delete(URI + "/-1");
    }

    @Test
    public void testBasicUpdate() {
        Gson gson = new Gson();

        Map<String, Object> body = new HashMap<>();
        body.put("cedulaCliente", "-1");
        body.put("nombreCliente", "test update");
        body.put("direccion_cliente", "test update direccion");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate request = new RestTemplate();
        HttpEntity<String> httpEntity = new HttpEntity<>(gson.toJson(body), headers);
        var response = request.exchange(URI, HttpMethod.PUT, httpEntity, Map.class);

        assertNotNull(response.getBody());
        assertEquals(200, response.getBody().get("status"));
        assertEquals("ok", response.getBody().get("type"));

        var currentData = request.getForEntity(URI + "/-1", ClienteModel.class);

        assertNotNull(currentData.getBody());
        assertEquals("test update", currentData.getBody().getNombreCliente());
        assertEquals(modelTest.getDireccionCliente(), currentData.getBody().getDireccionCliente());
    }

    @Test
    public void testNotIdUpdate() {
        Gson gson = new Gson();

        Map<String, Object> body = new HashMap<>();
        body.put("nombreCliente", "test update");
        body.put("direccion_cliente", "test update direccion");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate request = new RestTemplate();
        HttpEntity<String> httpEntity = new HttpEntity<>(gson.toJson(body), headers);
        try {
            var response = request.exchange(URI, HttpMethod.PUT, httpEntity, Map.class);
            fail("Expected Exception: org.springframework.web.client.HttpClientErrorException$BadRequest");
        } catch (HttpClientErrorException.BadRequest e) {
            var response = gson.fromJson(e.getResponseBodyAsString(), Map.class);
            assertNotNull(response);
            assertEquals(400, (int) (double) response.get("status"));
            assertEquals("bad request", response.get("type"));
            assertEquals("missing or null 'cedulaCliente' argument", response.get("message"));
            assertEquals("cedulaCliente", response.get("nullField"));
        }
    }

    @Test
    public void testDuplicateFieldsUpdate() {
        Gson gson = new Gson();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> body = new HttpEntity<>(gson.toJson(modelTest), headers);
        RestTemplate request = new RestTemplate();
        try {
            var response = request.exchange(URI, HttpMethod.PUT, body, Map.class);
            fail("Expected Exception: org.springframework.web.client.HttpClientErrorException$Conflict");
        } catch (HttpClientErrorException.Conflict e) {
            var response = gson.fromJson(e.getResponseBodyAsString(), Map.class);
            assertEquals(409, (int) (double) response.get("status"));
            assertEquals("already exist", response.get("type"));
            assertEquals("telefonoCliente", response.get("conflictParam"));
        }
    }

    @Test
    public void testNotFoundUpdate() {
        Gson gson = new Gson();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> bodyData = new HashMap<>();
        bodyData.put("cedulaCliente", "-3");
        bodyData.put("nombreCliente", "test");
        bodyData.put("email", "test@test.com");
        bodyData.put("edadCliente", "13");

        HttpEntity<String> body = new HttpEntity<>(gson.toJson(bodyData), headers);
        RestTemplate request = new RestTemplate();
        try {
            var response = request.exchange(URI, HttpMethod.PUT, body, Map.class);
            fail("Expected Exception: org.springframework.web.client.HttpClientErrorException$NotFound");
        } catch (HttpClientErrorException.NotFound e) {
            var response = gson.fromJson(e.getResponseBodyAsString(), Map.class);
            assertEquals(404, (int) (double) response.get("status"));
            assertEquals("not found", response.get("type"));
        }
    }

    @Test(expected = HttpClientErrorException.MethodNotAllowed.class)
    public void testMethodNotAllowed() {
        RestTemplate request = new RestTemplate();
        request.put(URI + "/1", null);
    }
}
