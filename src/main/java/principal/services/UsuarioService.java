package principal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import principal.models.UsuarioModel;
import principal.repositories.UsuarioRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    public ArrayList<UsuarioModel> obtenerUsuarios() {
        return (ArrayList<UsuarioModel>) usuarioRepository.findAll();
    }

    public UsuarioModel obtenerUsuario(long cedula) {
        return usuarioRepository.findById(cedula).orElse(null);
    }

    public Map<String, Object> crearUsuario(UsuarioModel usuarioModel) {
        Map<String, Object> result = new HashMap<>();

        var nullFieldErrorResponse = checkNullFields(usuarioModel);
        if (nullFieldErrorResponse != null) {
            return nullFieldErrorResponse;
        }
        var uniqueErrorResponse = checkUniqueFields(usuarioModel);
        if (uniqueErrorResponse != null) {
            return uniqueErrorResponse;
        }

        usuarioModel.setPassword(BCrypt.hashpw(usuarioModel.getPassword(), BCrypt.gensalt(8)));
        usuarioRepository.save(usuarioModel);

        result.put("status", 201);
        result.put("type", "ok");

        return result;
    }

    public Map<String, Object> actualizarUsuario(UsuarioModel usuarioModel) {
        Map<String, Object> result = new HashMap<>();

        var nullFieldErrorResponse = checkNullFields(usuarioModel);
        if (nullFieldErrorResponse != null && nullFieldErrorResponse.get("nullField").equals("cedulaUsuario")) {
            return nullFieldErrorResponse;
        }

        var currentData = usuarioRepository.findById(usuarioModel.getCedulaUsuario()).orElse(null);

        if (currentData != null) {
            var uniqueErrorResponse = checkUniqueFields(usuarioModel);
            if (uniqueErrorResponse != null && !uniqueErrorResponse.get("conflictParam").equals("cedulaUsuario")) {
                return uniqueErrorResponse;
            }

            currentData.copyNotNullValues(usuarioModel);
            currentData.setPassword(BCrypt.hashpw(currentData.getPassword(), BCrypt.gensalt(8)));
            usuarioRepository.save(currentData);

            result.put("status", 200);
            result.put("type", "ok");
        } else {
            result.put("status", 404);
            result.put("type", "not found");
        }
        return result;
    }

    public Map<String, Object> eliminarUsuario(long cedula_usuario) {
        Map<String, Object> result = new HashMap<>();

        if (usuarioRepository.existsById(cedula_usuario)) {
            usuarioRepository.deleteById(cedula_usuario);

            result.put("status", 200);
            result.put("type", "ok");
        } else {
            result.put("status", 404);
            result.put("type", "not found");
        }

        return result;
    }

    /**
     * @param usuarioModel Objeto que la funcion evaluara en busca de campos nulos
     * @return Si no se encuentran campos nulos la funcion retornara null, de lo contrario
     * retornara el prototipo de una respuesta JSON en forma de objeto {@code Map<String,Object>}
     */
    private Map<String, Object> checkNullFields(UsuarioModel usuarioModel) {
        String nullField = "";

        if (usuarioModel.getNombreUsuario() == null) {
            nullField = "nombreUsuario";
        }
        if (usuarioModel.getEmailUsuario() == null) {
            nullField = "emailUsuario";
        }
        if (usuarioModel.getUsuario() == null) {
            nullField = "usuario";
        }
        if (usuarioModel.getPassword() == null) {
            nullField = "password";
        }
        if (usuarioModel.getCedulaUsuario() == null) {
            nullField = "cedulaUsuario";
        }

        if (!nullField.equals("")) {
            Map<String, Object> res = new HashMap<>();
            res.put("status", 400);
            res.put("type", "bad request");
            res.put("nullField", nullField);
            res.put("message", "missing or null '" + nullField + "' argument");

            return res;
        }

        return null;
    }

    /**
     * @param usuarioModel Objeto que la funcion evaluara en busca de campos repetidos en la BBDD
     * @return Si no se encuentran campos duplicados la funcion retornara null, de lo contrario
     * retornara el prototipo de una respuesta JSON en forma de objeto {@code Map<String,Object>}
     */
    private Map<String, Object> checkUniqueFields(UsuarioModel usuarioModel) {
        String conflictParam = "";

        if (usuarioRepository.existsById(usuarioModel.getCedulaUsuario())) {
            conflictParam = "cedulaUsuario";
        }
        if (usuarioRepository.existsByEmailUsuario(usuarioModel.getEmailUsuario())) {
            conflictParam = "emailUsuario";
        }
        if (usuarioRepository.existsByUsuario(usuarioModel.getUsuario())) {
            conflictParam = "usuario";
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
}
