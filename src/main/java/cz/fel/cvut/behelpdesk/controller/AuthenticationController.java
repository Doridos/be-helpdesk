package cz.fel.cvut.behelpdesk.controller;

import cz.fel.cvut.behelpdesk.dto.LoginRequest;
import cz.fel.cvut.behelpdesk.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<Map<String, String>> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest, HttpServletRequest request) {
        return authenticationService.issueAuthenticationToken(authenticationRequest);
    }
}