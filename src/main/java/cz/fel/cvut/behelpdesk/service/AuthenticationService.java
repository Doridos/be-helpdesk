package cz.fel.cvut.behelpdesk.service;

import cz.fel.cvut.behelpdesk.dto.DetailEmployeeDto;
import cz.fel.cvut.behelpdesk.dto.LoginRequest;
import cz.fel.cvut.behelpdesk.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final EmployeeService employeeService;


    public ResponseEntity<Map<String, String>> issueAuthenticationToken(LoginRequest authenticationRequest) {

        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (
                AuthenticationException e) {
            // Handle authentication failure
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Authentication failed");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
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

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Authentication failed");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}
