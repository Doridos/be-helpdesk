package cz.fel.cvut.behelpdesk.controller;

import cz.fel.cvut.behelpdesk.dto.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    public AuthenticationController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

@PostMapping
public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest, HttpServletRequest request) {
    Authentication authentication = null;
    try {
        authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
        );
    } catch (AuthenticationException e) {
        // Handle authentication failure
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
    }

    // Only create a session if the authentication was successful
    if (authentication != null && authentication.isAuthenticated()) {
        // Get the session ID
        String sessionId = request.getSession(true).getId();

        // Create a response object that includes the username and session ID
        Map<String, String> response = new HashMap<>();
        response.put("username", authenticationRequest.getUsername());
        response.put("sessionId", sessionId);

        return ResponseEntity.ok(response);
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
}
}