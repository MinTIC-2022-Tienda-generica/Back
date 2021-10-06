package principal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import principal.models.UsuarioModel;
import principal.services.UsuarioService;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    @GetMapping()
    public ResponseEntity<ArrayList<UsuarioModel>> getUsuarios() {
        var usuarios = usuarioService.obtenerUsuarios();
        return ResponseEntity.status(200).body(usuarios);
    }

    @GetMapping("{cedula}")
    public ResponseEntity<UsuarioModel> getUsuario(@PathVariable long cedula) {
        var usuario = usuarioService.obtenerUsuario(cedula);
        if (usuario == null) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.status(200).body(usuario);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> crearUsuario(@RequestBody UsuarioModel usuario) {
        var response = usuarioService.crearUsuario(usuario);
        return ResponseEntity.status((Integer) response.getOrDefault("status", 500)).body(response);
    }

    @PutMapping
    public ResponseEntity<Map<String, Object>> actualizarUsuario(@RequestBody UsuarioModel usuario) {
        var response = usuarioService.actualizarUsuario(usuario);
        return ResponseEntity.status((Integer) response.getOrDefault("status", 500)).body(response);
    }

    @DeleteMapping("{cedula}")
    public ResponseEntity<Map<String, Object>> eliminarUsuario(@PathVariable long cedula) {
        var res = usuarioService.eliminarUsuario(cedula);

        return ResponseEntity.status((int) res.getOrDefault("status", 500)).body(res);
    }
}
