package principal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import principal.models.UsuarioModel;
import principal.repositories.UsuarioRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    UsuarioRepository usuarioRepository;

    private static final HashMap<String, UsuarioModel> sessions = new HashMap<>();

    public Map<String, Object> createSession(Map<String, Object> body) {
        Map<String, Object> response = new HashMap<>();

        var username = body.getOrDefault("username", null);
        var password = body.getOrDefault("password", null);

        if (username == null || password == null) {
            response.put("status", 400);
            response.put("type", "bad request");
            response.put("message", username == null ? "username" : "password" + "' field");
            return response;
        }

        var usuario = usuarioRepository.findByUsuario((String) username);
        if (usuario != null) {
            var check = BCrypt.checkpw((String) password, usuario.getPassword());
            if (check) {
                response.put("status", 201);
                response.put("type", "created");
                response.put("SSID", serializarSession((String) username, (String) password, usuario));
            } else {
                response.put("status", 401);
                response.put("type", "incorrect password");
            }
        } else {
            response.put("status", 404);
            response.put("type", "not found");
            response.put("message", "user not found");
        }
        return response;
    }

    public String serializarSession(String username, String password, UsuarioModel usuarioModel) {
        var hash = BCrypt.hashpw(username + password + Math.random() * 1000, BCrypt.gensalt(4));
        sessions.put(hash, usuarioModel);
        return hash;
    }

    public UsuarioModel deserializarSession(String sessionId) {
        return sessions.getOrDefault(sessionId, null);
    }
}
