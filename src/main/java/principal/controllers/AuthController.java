package principal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import principal.models.UsuarioModel;
import principal.services.AuthService;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
public class AuthController {

    @Autowired
    AuthService authService;

    @GetMapping
    public ResponseEntity<UsuarioModel> getSessionData(@CookieValue(name = "$3$$10N-ID", defaultValue = "-1") String cookie) {
        if (cookie.equals("-1"))
            return ResponseEntity.status(401).body(null);
        var response = authService.deserializarSession(cookie);
        if (response == null)
            return ResponseEntity.status(401).body(null);
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createSession(HttpServletResponse httpServletResponse,
                                                             @RequestBody Map<String, Object> body,
                                                             @CookieValue(name = "$3$$10N-ID", defaultValue = "-1") String sessionId) {
        if (!sessionId.equals("-1") && authService.deserializarSession(sessionId) != null)
            return ResponseEntity.status(200).body(null);

        var response = authService.createSession(body);
        if ((int) response.get("status") == 201) {
            ResponseCookie resCookie = ResponseCookie.from("$3$$10N-ID", (String) response.get("SSID"))
                    .httpOnly(true)
                    .sameSite("lax")
                    .path("/")
                    .build();
            httpServletResponse.addHeader("Set-Cookie", resCookie.toString());
        }
        return ResponseEntity.status((int) response.getOrDefault("status", 500)).body(null);
    }
}
