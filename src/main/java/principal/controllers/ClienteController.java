package principal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import principal.models.ClienteModel;
import principal.services.ClienteService;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @GetMapping
    public ResponseEntity<ArrayList<ClienteModel>> getClientes() {
        return ResponseEntity.status(200).body(clienteService.getClientes());
    }

    @GetMapping("{cedula}")
    public ResponseEntity<ClienteModel> getCliente(@PathVariable long cedula) {
        var res = clienteService.getCliente(cedula);
        if (res == null) {
            return ResponseEntity.status(404).body(null);
        } else {
            return ResponseEntity.status(200).body(res);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createCliente(@RequestBody ClienteModel clienteModel) {
        var res = clienteService.createCliente(clienteModel);

        return ResponseEntity.status((int) res.getOrDefault("status", 500)).body(res);
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> updateCliente(@RequestBody ClienteModel clienteModel) {
        var response = clienteService.updateCliente(clienteModel);

        return ResponseEntity.status((int) response.getOrDefault("status", 500)).body(response);
    }

    @DeleteMapping("{cedula}")
    public ResponseEntity<Map<String, Object>> deleteCliente(@PathVariable long cedula) {
        var response = clienteService.deleteCliente(cedula);

        return ResponseEntity.status((int) response.getOrDefault("status", 500)).body(response);
    }
}
