package principal.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import principal.models.VentasModel;
import principal.services.VentasService;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("ventas")
@CrossOrigin(origins = "*")
public class VentasController {

    @Autowired
    VentasService ventasService;

    @GetMapping
    public ResponseEntity<ArrayList<VentasModel>> getVentas() {
        return ResponseEntity.status(200).body(ventasService.getProductos());
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createVenta(@RequestBody Map<String, Object> ventasModel) {
        Gson gson = new Gson();

        var venta = gson.fromJson(gson.toJson(ventasModel.get("venta")), VentasModel.class);
//        var detalles = gson.fromJson(gson.toJson(ventasModel.get("detalles")), ArrayList<Map>.class);
        var response = ventasService.createVenta(venta);

        return ResponseEntity.status((int) response.getOrDefault("status", 500)).body(response);
    }
}
