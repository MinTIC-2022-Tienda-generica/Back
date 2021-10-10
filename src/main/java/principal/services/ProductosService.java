package principal.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import principal.models.ProductosModel;
import principal.repositories.ProductosRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProductosService {

    @Autowired
    ProductosRepository productosRepository;

    public ArrayList<ProductosModel> getProductos() {
        return (ArrayList<ProductosModel>) productosRepository.findAll();
    }

    public ProductosModel getProducto(long codigo) {
        return productosRepository.findById(codigo).orElse(null);
    }

    public Map<String, Object> createProducto(ProductosModel productosModel) {
        var nullFieldErrorResponse = checkNullFields(productosModel);
        if (nullFieldErrorResponse != null) {
            return nullFieldErrorResponse;
        }
        var uniqueErrorResponse = checkUniqueFields(productosModel);
        if (uniqueErrorResponse != null) {
            return uniqueErrorResponse;
        }

        productosRepository.save(productosModel);

        Map<String, Object> response = new HashMap<>();
        response.put("status", 201);
        response.put("type", "ok");

        return response;
    }

    public Map<String, Object> updateProducto(ProductosModel productosModel) {
        Map<String, Object> response = new HashMap<>();

        var nullFieldErrorResponse = checkNullFields(productosModel);
        if (nullFieldErrorResponse != null && nullFieldErrorResponse.get("nullField").equals("codigoProducto")) {
            return nullFieldErrorResponse;
        }

        var currentData = productosRepository.findById(productosModel.getCodigoProducto()).orElse(null);

        if (currentData != null) {
            var uniqueErrorResponse = checkUniqueFields(productosModel);
            if (uniqueErrorResponse != null && !uniqueErrorResponse.get("conflictParam").equals("codigoProducto")) {
                return uniqueErrorResponse;
            }

            currentData.copyNotNullValues(productosModel);
            productosRepository.save(currentData);

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
        if (productosRepository.existsById(codigo)) {
            productosRepository.deleteById(codigo);
            response.put("status", 200);
            response.put("type", "ok");
        } else {
            response.put("status", 404);
            response.put("type", "not found");
        }

        return response;
    }

    private Map<String, Object> checkNullFields(ProductosModel productosModel) {
        String nullField = "";


        if (productosModel.getPrecioCompra() == null) {
            nullField = "precioCompra";
        }
        if (productosModel.getPrecioVenta() == null) {
            nullField = "precioVenta";
        }
        if (productosModel.getIvaCompra() == null) {
            nullField = "ivaCompra";
        }
        if (productosModel.getNombreProducto() == null) {
            nullField = "nombreProducto";
        }
        if (productosModel.getNitProveedor() == null) {
            nullField = "nitProveedor";
        }
        if (productosModel.getCodigoProducto() == null) {
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


    private Map<String, Object> checkUniqueFields(ProductosModel productosModel) {
        String conflictParam = "";

        if (productosRepository.existsById(productosModel.getCodigoProducto())) {
            conflictParam = "codigoProducto";
        }
        if (productosRepository.existsByNombreProducto(productosModel.getNombreProducto())) {
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
