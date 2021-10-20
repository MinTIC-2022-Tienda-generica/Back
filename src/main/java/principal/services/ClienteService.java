package principal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import principal.models.ClienteModel;
import principal.repositories.ClienteRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    public ArrayList<ClienteModel> getClientes() {
        return (ArrayList<ClienteModel>) clienteRepository.findAll();
    }

    public ClienteModel getCliente(long cedula) {
        return clienteRepository.findById(cedula).orElse(null);
    }

    public Map<String, Object> createCliente(ClienteModel clienteModel) {
        var nullFieldErrorResponse = checkNullFields(clienteModel);
        if (nullFieldErrorResponse != null) {
            return nullFieldErrorResponse;
        }
        var uniqueErrorResponse = checkUniqueFields(clienteModel);
        if (uniqueErrorResponse != null) {
            return uniqueErrorResponse;
        }

        clienteRepository.save(clienteModel);

        Map<String, Object> response = new HashMap<>();
        response.put("status", 201);
        response.put("type", "ok");

        return response;
    }

    public Map<String, Object> updateCliente(ClienteModel clienteModel) {
        Map<String, Object> response = new HashMap<>();

        var nullFieldErrorResponse = checkNullFields(clienteModel);
        if (nullFieldErrorResponse != null && nullFieldErrorResponse.get("nullField").equals("cedulaCliente")) {
            return nullFieldErrorResponse;
        }

        var currentData = clienteRepository.findById(clienteModel.getCedulaCliente()).orElse(null);

        if (currentData != null) {
            var uniqueErrorResponse = checkUniqueFields(clienteModel);
            if (uniqueErrorResponse != null && !uniqueErrorResponse.get("conflictParam").equals("cedulaCliente")) {
                return uniqueErrorResponse;
            }

            currentData.copyNotNullValues(clienteModel);
            clienteRepository.save(currentData);

            response.put("status", 200);
            response.put("type", "ok");
        } else {
            response.put("status", 404);
            response.put("type", "not found");
        }
        return response;
    }

    public Map<String, Object> deleteCliente(long cedula) {
        Map<String, Object> response = new HashMap<>();
        if (clienteRepository.existsById(cedula)) {
            clienteRepository.deleteById(cedula);
            response.put("status", 200);
            response.put("type", "ok");
        } else {
            response.put("status", 404);
            response.put("type", "not found");
        }

        return response;
    }

    /**
     * @param clienteModel Objeto que la funcion evaluara en busca de campos nulos
     * @return Si no se encuentran campos nulos la funcion retornara null, de lo contrario
     * retornara el prototipo de una respuesta JSON en forma de objeto {@code Map<String,Object>}
     */
    private Map<String, Object> checkNullFields(ClienteModel clienteModel) {
        String nullField = "";


        if (clienteModel.getNombreCliente() == null) {
            nullField = "nombreCliente";
        }
        if (clienteModel.getEmailCliente() == null) {
            nullField = "emailCliente";
        }
        if (clienteModel.getTelefonoCliente() == null) {
            nullField = "telefonoCliente";
        }
        if (clienteModel.getDireccionCliente() == null) {
            nullField = "direccionCliente";
        }
        if (clienteModel.getCedulaCliente() == null) {
            nullField = "cedulaCliente";
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

    /**
     * @param clienteModel Objeto que la funcion evaluara en busca de campos repetidos en la BBDD
     * @return Si no se encuentran campos duplicados la funcion retornara null, de lo contrario
     * retornara el prototipo de una respuesta JSON en forma de objeto {@code Map<String,Object>}
     */
    private Map<String, Object> checkUniqueFields(ClienteModel clienteModel) {
        String conflictParam = "";

        if (clienteRepository.existsById(clienteModel.getCedulaCliente())) {
            conflictParam = "cedulaCliente";
        }
        if (clienteRepository.existsByEmailCliente(clienteModel.getEmailCliente())) {
            conflictParam = "emailCliente";
        }
        if (clienteRepository.existsByTelefonoCliente(clienteModel.getTelefonoCliente())) {
            conflictParam = "telefonoCliente";
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
