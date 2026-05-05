package com.praio.backend.controller;

import com.praio.backend.dto.AuthResponse;
import com.praio.backend.dto.CadastroRequest;
import com.praio.backend.dto.LoginRequest;
import com.praio.backend.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = usuarioService.login(request);
        return response.isSuccess()
                ? ResponseEntity.ok(response)
                : ResponseEntity.status(401).body(response);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<AuthResponse> cadastro(@RequestBody CadastroRequest request) {
        AuthResponse response = usuarioService.cadastro(request);
        return response.isSuccess()
                ? ResponseEntity.status(201).body(response)
                : ResponseEntity.status(409).body(response);
    }
}
