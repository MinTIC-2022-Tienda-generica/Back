package principal.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import principal.models.ProductoModel;
import principal.repositories.ProductoRepository;
import principal.repositories.ProveedorRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProductoService {

    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    ProveedorRepository proveedorRepository;

    public ArrayList<ProductoModel> getProductos() {
        return (ArrayList<ProductoModel>) productoRepository.findAll();
    }

    public ProductoModel getProducto(long codigo) {
        return productoRepository.findById(codigo).orElse(null);
    }

    public Map<String, Object> createProducto(ProductoModel[] productosModel) {
        if (productosModel.length == 0) {
            Map<String, Object> zeroLengthError = new HashMap<>();
            zeroLengthError.put("status", 400);
            zeroLengthError.put("type", "bad request");
            zeroLengthError.put("message", "provided zero length array");

            return zeroLengthError;
        }

        for (ProductoModel productoModel : productosModel) {
            var nullFieldErrorResponse = checkNullFields(productoModel);
            if (nullFieldErrorResponse != null) {
                return nullFieldErrorResponse;
            }
            var uniqueErrorResponse = checkUniqueFields(productoModel);
            if (uniqueErrorResponse != null) {
                return uniqueErrorResponse;
            }
            var notFoundProoveedorResponse = !proveedorRepository.existsById(productoModel.getNitProveedor());
            if (notFoundProoveedorResponse) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", 404);
                response.put("type", "not found");
                response.put("message", "nit proveedor not found");

                return response;
            }

            productoRepository.save(productoModel);


        }

        Map<String, Object> response = new HashMap<>();
        response.put("status", 201);
        response.put("type", "ok");

        return response;
    }

    public Map<String, Object> updateProducto(ProductoModel productoModel) {
        Map<String, Object> response = new HashMap<>();

        var nullFieldErrorResponse = checkNullFields(productoModel);
        if (nullFieldErrorResponse != null && nullFieldErrorResponse.get("nullField").equals("codigoProducto")) {
            return nullFieldErrorResponse;
        }

        var currentData = productoRepository.findById(productoModel.getCodigoProducto()).orElse(null);

        if (currentData != null) {
            var uniqueErrorResponse = checkUniqueFields(productoModel);
            if (uniqueErrorResponse != null && !uniqueErrorResponse.get("conflictParam").equals("codigoProducto")) {
                return uniqueErrorResponse;
            }

            currentData.copyNotNullValues(productoModel);
            productoRepository.save(currentData);

            response.put("status", 200);
            response.put("type", "ok");
        } else {
            response.put("status", 404);
            response.put("type", "not found");
        }
        return response;
    }


    public Map<String, Object> deleteProducto(long codigo) {
        Map<String, Object> response = new HashMap<>();
        if (productoRepository.existsById(codigo)) {
            productoRepository.deleteById(codigo);
            response.put("status", 200);
            response.put("type", "ok");
        } else {
            response.put("status", 404);
            response.put("type", "not found");
        }

        return response;
    }

    private Map<String, Object> checkNullFields(ProductoModel productoModel) {
        String nullField = "";


        if (productoModel.getPrecioCompra() == null) {
            nullField = "precioCompra";
        }
        if (productoModel.getPrecioVenta() == null) {
            nullField = "precioVenta";
        }
        if (productoModel.getIvaCompra() == null) {
            nullField = "ivaCompra";
        }
        if (productoModel.getNombreProducto() == null) {
            nullField = "nombreProducto";
        }
        if (productoModel.getNitProveedor() == null) {
            nullField = "nitProveedor";
        }
        if (productoModel.getCodigoProducto() == null) {
            nullField = "codigoProducto";
        }

        if (!nullField.equals("")) {
            Map<String, Object> result = new HashMap<>();
            result.put("status", 400);
            result.put("type", "bad request");
            result.put("nullField", nullField);
            result.put("message", "missing or null '" + nullField + "' argument");

            return result;
        }

        return null;
    }


    private Map<String, Object> checkUniqueFields(ProductoModel productoModel) {
        String conflictParam = "";

        if (productoRepository.existsById(productoModel.getCodigoProducto())) {
            conflictParam = "codigoProducto";
        }
        if (productoRepository.existsByNombreProducto(productoModel.getNombreProducto())) {
            conflictParam = "nombreProducto";
        }

        if (!conflictParam.equals("")) {
            Map<String, Object> result = new HashMap<>();
            result.put("status", 409);
            result.put("type", "already exist");
            result.put("conflictParam", conflictParam);
            return result;
        }

        return null;
    }
}
