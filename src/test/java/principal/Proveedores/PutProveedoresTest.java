package principal.Proveedores;

import com.google.gson.Gson;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import principal.models.ProveedorModel;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class PutProveedoresTest extends TestProveedoresSuite {

    @Test
    public void testBasicUpdate() {
        Gson gson = new Gson();

        Map<String, Object> body = new HashMap<>();
        body.put("nitProveedor", "-1");
        body.put("ciudadProveedor", "test update");
        body.put("direccion_proveedor", "test update direccion");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate request = new RestTemplate();
        HttpEntity<String> httpEntity = new HttpEntity<>(gson.toJson(body), headers);
        var response = request.exchange(URI, HttpMethod.PUT, httpEntity, Map.class);

        assertNotNull(response.getBody());
        assertEquals(200, response.getBody().get("status"));
        assertEquals("ok", response.getBody().get("type"));

        var currentData = request.getForEntity(URI + "/-1", ProveedorModel.class);

        assertNotNull(currentData.getBody());
        assertEquals("test update", currentData.getBody().getCiudadProveedor());
        assertEquals(proveedortest.getDireccionProveedor(), currentData.getBody().getDireccionProveedor());
    }

    @Test
    public void testNotIdUpdate() {
        Gson gson = new Gson();

        Map<String, Object> body = new HashMap<>();
        body.put("ciudadProveedor", "test update");
        body.put("direccionProveedor", "test update direccion");
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
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
            assertEquals("missing or null 'nitProveedor' argument", response.get("message"));
            assertEquals("nitProveedor", response.get("nullField"));
        }
    }

    @Test
    public void testDuplicateFieldsUpdate() {
        Gson gson = new Gson();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> body = new HttpEntity<>(gson.toJson(proveedortest), headers);
        RestTemplate request = new RestTemplate();
        try {
            var response = request.exchange(URI, HttpMethod.PUT, body, Map.class);
            fail("Expected Exception: org.springframework.web.client.HttpClientErrorException$Conflict");
        } catch (HttpClientErrorException.Conflict e) {
            var response = gson.fromJson(e.getResponseBodyAsString(), Map.class);
            assertEquals(409, (int) (double) response.get("status"));
            assertEquals("already exist", response.get("type"));
            ///assertEquals("telefonoProveedor", response.get("conflictParam"));
        }
    }

    @Test
    public void testNotFoundUpdate() {
        Gson gson = new Gson();

        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> bodyData = new HashMap<>();
        bodyData.put("nitProveedor", "-3");
        bodyData.put("ciudadProveedor", "test");
        bodyData.put("direccionProveedor", "Cra Test#Test-test");
        bodyData.put("subNombreProveedor", "SubTestTest");

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