package principal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import principal.models.DetalleVentasModel;
import principal.repositories.DetalleVentasRepository;
import principal.repositories.ProductoRepository;
import principal.repositories.VentasRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class DetalleVentasService {

    @Autowired
    DetalleVentasRepository detalleVentasRepository;
    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    VentasRepository ventasRepository;

    public ArrayList<DetalleVentasModel> getDetallesVentas() {
        return (ArrayList<DetalleVentasModel>) detalleVentasRepository.findAll();
    }

    public ArrayList<DetalleVentasModel> getDetallesVentas(Long codigoVenta) {
        return (ArrayList<DetalleVentasModel>) detalleVentasRepository.findAllByCodigoVenta(codigoVenta);
    }

    public Map<String, Object> createDetallesVenta(DetalleVentasModel[] detallesVentasModel) {

        if (detallesVentasModel.length == 0) {
            Map<String, Object> zeroLengthError = new HashMap<>();
            zeroLengthError.put("status", 400);
            zeroLengthError.put("type", "bad request");
            zeroLengthError.put("message", "provided zero length array");

            return zeroLengthError;
        }

        for (DetalleVentasModel detalleVentasModel : detallesVentasModel) {
            var nullFieldErrorResponse = checkNullFields(detalleVentasModel);
            if (nullFieldErrorResponse != null) {
                return nullFieldErrorResponse;
            }
            var uniqueErrorResponse = checkUniqueFields(detalleVentasModel);
            if (uniqueErrorResponse != null) {
                return uniqueErrorResponse;
            }
            var notFoundForeignKeyErrorResponse = chechForeignKeys(detalleVentasModel);
            if (notFoundForeignKeyErrorResponse != null) {
                return notFoundForeignKeyErrorResponse;
            }

            detalleVentasRepository.save(detalleVentasModel);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("status", 201);
        response.put("type", "ok");

        return response;
    }

    /**
     * @param detalleVentasModel Objeto que la funcion evaluara en busca de campos nulos
     * @return Si no se encuentran campos nulos la funcion retornara null, de lo contrario
     * retornara el prototipo de una respuesta JSON en forma de objeto {@code Map<String,Object>}
     */
    private Map<String, Object> checkNullFields(DetalleVentasModel detalleVentasModel) {
        String nullField = "";


        if (detalleVentasModel.getValorVenta() == null) {
            nullField = "valorVenta";
        }
        if (detalleVentasModel.getValorTotal() == null) {
            nullField = "valorTotal";
        }
        if (detalleVentasModel.getValorIva() == null) {
            nullField = "valorIva";
        }
        if (detalleVentasModel.getCantidadProducto() == null) {
            nullField = "cantidadProducto";
        }
        if (detalleVentasModel.getCodigoProducto() == null) {
            nullField = "codigoProducto";
        }
        if (detalleVentasModel.getCodigoVenta() == null) {
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
     * @param detalleVentasModel Objeto que la funcion evaluara en busca de campos repetidos en la BBDD
     * @return Si no se encuentran campos duplicados la funcion retornara null, de lo contrario
     * retornara el prototipo de una respuesta JSON en forma de objeto {@code Map<String,Object>}
     */
    private Map<String, Object> checkUniqueFields(DetalleVentasModel detalleVentasModel) {
        String conflictParam = "";

        if (detalleVentasModel.getCodigoDetalleVenta() == null) {
            return null;
        }
        if (detalleVentasRepository.existsById(detalleVentasModel.getCodigoDetalleVenta())) {
            conflictParam = "codigoDetalleVenta";
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

    private Map<String, Object> chechForeignKeys(DetalleVentasModel detalleVentasModel) {
        String foreignKey = "";

        if (!ventasRepository.existsById(detalleVentasModel.getCodigoVenta())) {
            foreignKey = "codigoVenta";
        }
        if (!productoRepository.existsById(detalleVentasModel.getCodigoProducto())) {
            foreignKey = "codigoProducto";
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
