package principal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import principal.models.ProveedorModel;
import principal.repositories.ProveedorRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class ProveedorService {
    @Autowired
    ProveedorRepository proveedorRepository;

    public ArrayList<ProveedorModel> getProveedores() {
        return (ArrayList<ProveedorModel>) proveedorRepository.findAll();
    }

    public ProveedorModel getProveedor(Long nitProveedor) {
        return proveedorRepository.findById(nitProveedor).orElse(null);
    }

    public Map<String, Object> createProveedor(ProveedorModel proveedorModel) {
        Map<String, Object> response = new HashMap<>();

        var nullFieldErrorResponse = checkNullFields(proveedorModel);
        if (nullFieldErrorResponse != null) {
            return nullFieldErrorResponse;
        }
        var uniqueErrorResponse = checkUniqueFields(proveedorModel);
        if (uniqueErrorResponse != null) {
            return uniqueErrorResponse;
        }

        proveedorRepository.save(proveedorModel);

        response.put("status", 201);
        response.put("type", "ok");

        return response;
    }

    public Map<String, Object> updateProveedor(ProveedorModel proveedorModel) {
        Map<String, Object> response = new HashMap<>();

        var nullFieldErrorResponse = checkNullFields(proveedorModel);
        if (nullFieldErrorResponse != null && nullFieldErrorResponse.get("nullField").equals("nitProveedor")) {
            return nullFieldErrorResponse;
        }

        var currentData = proveedorRepository.findById(proveedorModel.getNitProveedor()).orElse(null);
        if (currentData != null) {
            var uniqueErrorResponse = checkUniqueFields(proveedorModel);
            if (uniqueErrorResponse != null && !uniqueErrorResponse.getOrDefault("conflictParam", null).equals("nitProveedor")) {
                return uniqueErrorResponse;
            }

            currentData.copyNotNullValues(proveedorModel);
            proveedorRepository.save(currentData);

            response.put("status", 200);
            response.put("type", "ok");

        } else {
            response.put("status", 404);
            response.put("type", "not found");

        }
        return response;
    }

    public Map<String, Object> deleteProveedor(Long nitProveedor) {
        Map<String, Object> response = new HashMap<>();
        if (proveedorRepository.existsById(nitProveedor)) {
            proveedorRepository.deleteById(nitProveedor);
            response.put("status", 200);
            response.put("type", "ok");
        } else {
            response.put("status", 404);
            response.put("type", "not found");
        }

        return response;
    }

    private Map<String, Object> checkNullFields(ProveedorModel proveedorModel) {
        String nullField = "";

        if (proveedorModel.getCiudadProveedor() == null) {
            nullField = "ciudadProveedor";
        }
        if (proveedorModel.getDireccionProveedor() == null) {
            nullField = "direccionProveedor";
        }
        if (proveedorModel.getNombreProveedor() == null) {
            nullField = "nombreProveedor";
        }
        if (proveedorModel.getTelefonoProveedor() == null) {
            nullField = "telefonoProveedor";
        }
        if (proveedorModel.getNitProveedor() == null) {
            nullField = "nitProveedor";
        }

        if (!nullField.equals("")) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", 400);
            response.put("type", "bad request");
            response.put("nullField", nullField);
            response.put("message", "missing or null '" + nullField + "' argument");

            return response;
        }

        return null;
    }

    private Map<String, Object> checkUniqueFields(ProveedorModel proveedorModel) {
        String conflictParam = "";

        if (proveedorRepository.existsById(proveedorModel.getNitProveedor()))
            conflictParam = "nitProveedor";
        if (proveedorRepository.existsByTelefonoProveedor(proveedorModel.getTelefonoProveedor()))
            conflictParam = "telefonoProveedor";

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
