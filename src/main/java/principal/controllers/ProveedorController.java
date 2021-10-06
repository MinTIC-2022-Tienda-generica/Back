package principal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import principal.models.ProveedorModel;
import principal.services.ProveedorService;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("proveedores")
@CrossOrigin(origins = "*")
public class ProveedorController {
    @Autowired
    ProveedorService proveedorService;

    @GetMapping
    public ResponseEntity<ArrayList<ProveedorModel>> getProveedores() {
        return ResponseEntity.status(200).body(proveedorService.getProveedores());
    }

    @GetMapping("{nit}")
    public ResponseEntity<ProveedorModel> getProveedor(@PathVariable Long nit) {
        var proveedor = proveedorService.getProveedor(nit);
        if (proveedor != null) {
            return ResponseEntity.status(200).body(proveedor);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createProveedor(@RequestBody ProveedorModel proveedorModel) {
        var response = proveedorService.createProveedor(proveedorModel);

        return ResponseEntity.status((int) response.getOrDefault("status", 500)).body(response);
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> updateProveedor(@RequestBody ProveedorModel proveedorModel) {
        var response = proveedorService.updateProveedor(proveedorModel);
        return ResponseEntity.status((int) response.getOrDefault("status", 500)).body(response);
    }

    @DeleteMapping("{nit}")
    public ResponseEntity<Map<String, Object>> deleteProveedor(@PathVariable Long nit) {
        var res = proveedorService.deleteProveedor(nit);

        return ResponseEntity.status((int) res.getOrDefault("status", 500)).body(res);
    }
}
