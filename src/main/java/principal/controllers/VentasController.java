package principal.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import principal.models.DetalleVentasModel;
import principal.models.VentasModel;
import principal.services.DetalleVentasService;
import principal.services.VentasService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("ventas")
@CrossOrigin(origins = "*")
public class VentasController {

    @Autowired
    VentasService ventasService;
    @Autowired
    DetalleVentasService detalleVentasService;

    @GetMapping
    public ResponseEntity<ArrayList<VentasModel>> getVentas() {
        return ResponseEntity.status(200).body(ventasService.getVentas());
    }

    @GetMapping("/{cedulaCliente}")
    public ResponseEntity<ArrayList<VentasModel>> getVentasByCedulaCliente(@PathVariable Long cedulaCliente) {
        return ResponseEntity.status(200).body(ventasService.getVentas(cedulaCliente));
    }

    @GetMapping("/detalles")
    public ResponseEntity<ArrayList<DetalleVentasModel>> getDetalleVentasByCedulaCliente() {
        return ResponseEntity.status(200).body(detalleVentasService.getDetallesVentas());
    }

    @GetMapping("/detalles/{codigoVenta}")
    public ResponseEntity<ArrayList<DetalleVentasModel>> getDetalleVentasByCedulaCliente(@PathVariable Long codigoVenta) {
        return ResponseEntity.status(200).body(detalleVentasService.getDetallesVentas(codigoVenta));
    }


    @PostMapping
    public ResponseEntity<Map<String, Object>> createVenta(@RequestBody Map<String, Object> ventasModel) {
        Gson gson = new Gson();

        var venta = gson.fromJson(gson.toJson(ventasModel.get("venta")), VentasModel.class);
        var detalles = gson.fromJson(gson.toJson(ventasModel.get("detalles")), DetalleVentasModel[].class);

        var responseVenta = ventasService.createVenta(venta);
        if ((int) responseVenta.get("status") != 201) {
            return ResponseEntity.status((int) responseVenta.getOrDefault("status", 500)).body(responseVenta);
        }
        var responseDetalles = detalleVentasService.createDetallesVenta(detalles);

        Map<String, Object> response = new HashMap<>();
        response.put("status", (Integer) responseVenta.get("status") == 201 && (Integer) responseDetalles.get("status") == 201 ? 201 : 418);
        response.put("ventasResult", responseVenta);
        response.put("detallesResult", responseDetalles);

        return ResponseEntity.status((int) response.getOrDefault("status", 500)).body(response);
    }
}
