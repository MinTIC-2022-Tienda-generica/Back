package principal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import principal.models.ProductoModel;
import principal.services.ProductoService;

import java.util.ArrayList;
import java.util.Map;


@RestController
@RequestMapping("productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    ProductoService productoService;

    @GetMapping
    public ResponseEntity<ArrayList<ProductoModel>> getProductos() {
        return ResponseEntity.status(200).body(productoService.getProductos());
    }

    @GetMapping("{codigo}")
    public ResponseEntity<ProductoModel> getProducto(@PathVariable long codigo) {
        var res = productoService.getProducto(codigo);
        if (res == null) {
            return ResponseEntity.status(404).body(null);
        } else {
            return ResponseEntity.status(200).body(res);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createProducto(@RequestBody ProductoModel[] productosModel) {
        var res = productoService.createProducto(productosModel);

        return ResponseEntity.status((int) res.getOrDefault("status", 500)).body(res);
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> updateProducto(@RequestBody ProductoModel productoModel) {
        var res = productoService.updateProducto(productoModel);

        return ResponseEntity.status((int) res.getOrDefault("status", 500)).body(res);
    }

    @DeleteMapping("{codigo}")
    public ResponseEntity<Map<String, Object>> deleteProducto(@PathVariable long codigo) {
        var res = productoService.deleteProducto(codigo);

        return ResponseEntity.status((int) res.getOrDefault("status", 500)).body(res);
    }
}
