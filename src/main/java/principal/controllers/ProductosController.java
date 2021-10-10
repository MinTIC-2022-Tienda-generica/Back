package principal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import principal.models.ProductosModel;
import principal.services.ProductosService;

import java.util.ArrayList;
import java.util.Map;


@RestController
@RequestMapping("productos")
@CrossOrigin(origins = "*")
public class ProductosController {

    @Autowired
    ProductosService productosService;

    @GetMapping
    public ResponseEntity<ArrayList<ProductosModel>> getProductos() {
        return ResponseEntity.status(200).body(productosService.getProductos());
    }

    @GetMapping("{codigo}")
    public ResponseEntity<ProductosModel> getProducto(@PathVariable long codigo) {
        var res = productosService.getProducto(codigo);
        if (res == null) {
            return ResponseEntity.status(404).body(null);
        } else {
            return ResponseEntity.status(200).body(res);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createProducto(@RequestBody ProductosModel productosModel) {
        var res = productosService.createProducto(productosModel);

        return ResponseEntity.status((int) res.getOrDefault("status", 500)).body(res);
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> updateProducto(@RequestBody ProductosModel productosModel) {
        var res = productosService.updateProducto(productosModel);

        return ResponseEntity.status((int) res.getOrDefault("status", 500)).body(res);
    }

    @DeleteMapping("{codigo}")
    public ResponseEntity<Map<String, Object>> deleteProducto(@PathVariable long codigo) {
        var res = productosService.deleteProducto(codigo);

        return ResponseEntity.status((int) res.getOrDefault("status", 500)).body(res);
    }
}
