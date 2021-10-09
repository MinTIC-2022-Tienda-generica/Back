package principal;

import principal.models.ClienteModel;
import principal.models.UsuarioModel;

public class TestUtil {

    public static ClienteModel buildTestCliente() {
        ClienteModel res = new ClienteModel();
        res.setNombreCliente("test");
        res.setCedulaCliente(-1);
        res.setTelefonoCliente("-1");
        res.setDireccionCliente("Crr Test#-1");
        res.setEmailCliente("test@test.com");

        return res;
    }

    public static UsuarioModel buildTestUsuario() {
        UsuarioModel res = new UsuarioModel();
        res.setUsuario("test");
        res.setEmailUsuario("test@test.com");
        res.setCedulaUsuario(-1L);
        res.setNombreUsuario("test test");
        res.setPassword("password test");

        return res;
    }
}
