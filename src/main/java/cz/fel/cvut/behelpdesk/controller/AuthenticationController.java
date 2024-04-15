package cz.fel.cvut.behelpdesk.controller;

import cz.fel.cvut.behelpdesk.dto.DetailEmployeeDto;
import cz.fel.cvut.behelpdesk.dto.LoginRequest;
import cz.fel.cvut.behelpdesk.security.JwtUtil;
import cz.fel.cvut.behelpdesk.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final EmployeeService employeeService;

    public AuthenticationController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, EmployeeService employeeService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.employeeService = employeeService;
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
            // Generate JWT token
            final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            final DetailEmployeeDto detailEmployeeDto = employeeService.getEmployeeDetailByUsername(userDetails.getUsername());
            final String jwt = jwtUtil.generateToken(detailEmployeeDto);

            // Create a response object that includes the username and JWT token
            Map<String, String> response = new HashMap<>();
            response.put("token", jwt);

            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
    }
}