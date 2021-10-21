package principal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import principal.models.VentasModel;
import principal.repositories.ClienteRepository;
import principal.repositories.UsuarioRepository;
import principal.repositories.VentasRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class VentasService {

    @Autowired
    VentasRepository ventasRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ClienteRepository clienteRepository;

    public ArrayList<VentasModel> getVentas() {
        return (ArrayList<VentasModel>) ventasRepository.findAll();
    }

    public ArrayList<VentasModel> getVentas(Long cedulaCliente) {
        return (ArrayList<VentasModel>) ventasRepository.findByCedulaCliente(cedulaCliente);
    }

    public Map<String, Object> createVenta(VentasModel ventasModel) {
        var nullFieldErrorResponse = checkNullFields(ventasModel);
        if (nullFieldErrorResponse != null) {
            return nullFieldErrorResponse;
        }
        var uniqueErrorResponse = checkUniqueFields(ventasModel);
        if (uniqueErrorResponse != null) {
            return uniqueErrorResponse;
        }
        var foreignKeyNotFoundError = chechForeignKeys(ventasModel);
        if (foreignKeyNotFoundError != null) {
            return foreignKeyNotFoundError;
        }

        ventasRepository.save(ventasModel);

        Map<String, Object> response = new HashMap<>();
        response.put("status", 201);
        response.put("type", "ok");

        return response;
    }


    /**
     * @param ventasModel Objeto que la funcion evaluara en busca de campos nulos
     * @return Si no se encuentran campos nulos la funcion retornara null, de lo contrario
     * retornara el prototipo de una respuesta JSON en forma de objeto {@code Map<String,Object>}
     */
    private Map<String, Object> checkNullFields(VentasModel ventasModel) {
        String nullField = "";


        if (ventasModel.getCedulaCliente() == null) {
            nullField = "cedulaCliente";
        }
        if (ventasModel.getTotalVenta() == null) {
            nullField = "totalVenta";
        }
        if (ventasModel.getIvaventa() == null) {
            nullField = "ivaventa";
        }
        if (ventasModel.getValorVenta() == null) {
            nullField = "valorVenta";
        }
        if (ventasModel.getCedulaUsuario() == null) {
            nullField = "cedulaUsuario";
        }
        if (ventasModel.getCodigoVenta() == null) {
            nullField = "codigoVenta";
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
     * @param ventasModel Objeto que la funcion evaluara en busca de campos repetidos en la BBDD
     * @return Si no se encuentran campos duplicados la funcion retornara null, de lo contrario
     * retornara el prototipo de una respuesta JSON en forma de objeto {@code Map<String,Object>}
     */
    private Map<String, Object> checkUniqueFields(VentasModel ventasModel) {
        String conflictParam = "";

        if (ventasModel.getCodigoVenta() == null) return null;

        if (ventasRepository.existsById(ventasModel.getCodigoVenta())) {
            conflictParam = "codigoVenta";
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

    private Map<String, Object> chechForeignKeys(VentasModel ventasModel) {
        String foreignKey = "";

        if (!clienteRepository.existsById(ventasModel.getCedulaCliente())) {
            foreignKey = "cedulaCliente";
        }
        if (!usuarioRepository.existsById(ventasModel.getCedulaUsuario())) {
            foreignKey = "cedulaUsuario";
        }

        if (!foreignKey.equals("")) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", 404);
            response.put("type", "not found");
            response.put("message", "'" + foreignKey + "' not found");

            return response;
        }

        return null;
    }
}
