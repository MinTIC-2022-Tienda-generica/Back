package principal;

import principal.models.ClienteModel;
import principal.models.ProductoModel;
import principal.models.ProveedorModel;
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

    public static ProveedorModel buildTestProveedor() {
        ProveedorModel res = new ProveedorModel();
        res.setCiudadProveedor("test");
        res.setDireccionProveedor("testCarreratest");
        res.setNitProveedor(-1L);
        res.setNombreProveedor("test test");
        res.setTelefonoProveedor("-1");

        return res;
    }

    public static ProductoModel[] buildTestProductos() {
        ProductoModel productoModel1 = new ProductoModel();
        productoModel1.setCodigoProducto(-1L);
        productoModel1.setNombreProducto("test 1");
        productoModel1.setNitProveedor(-1L);
        productoModel1.setIvaCompra(19);
        productoModel1.setPrecioCompra(100L);
        productoModel1.setPrecioVenta(1000L);

        ProductoModel productoModel2 = new ProductoModel();
        productoModel2.setCodigoProducto(-2L);
        productoModel2.setNombreProducto("test 2");
        productoModel2.setNitProveedor(-1L);
        productoModel2.setIvaCompra(19);
        productoModel2.setPrecioCompra(200L);
        productoModel2.setPrecioVenta(2000L);

        ProductoModel productoModel3 = new ProductoModel();
        productoModel3.setCodigoProducto(-3L);
        productoModel3.setNombreProducto("test 3");
        productoModel3.setNitProveedor(-2L);
        productoModel3.setIvaCompra(19);
        productoModel3.setPrecioCompra(300L);
        productoModel3.setPrecioVenta(3000L);

        ProductoModel productoModel4 = new ProductoModel();
        productoModel4.setCodigoProducto(-4L);
        productoModel4.setNombreProducto("test 4");
        productoModel4.setNitProveedor(-2L);
        productoModel4.setIvaCompra(19);
        productoModel4.setPrecioCompra(400L);
        productoModel4.setPrecioVenta(4000L);

        return new ProductoModel[]{productoModel1, productoModel2, productoModel3, productoModel4};
    }
}
