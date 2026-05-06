package com.praio.backend.controller;

import com.praio.backend.dto.AuthResponse;
import com.praio.backend.dto.CadastroRequest;
import com.praio.backend.dto.LoginRequest;
import com.praio.backend.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "Endpoints de login e cadastro de usuários")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Autenticar usuário", description = "Realiza login com email e senha")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Email ou senha inválidos")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = usuarioService.login(request);
        return response.isSuccess()
                ? ResponseEntity.ok(response)
                : ResponseEntity.status(401).body(response);
    }

    @Operation(summary = "Cadastrar usuário", description = "Cria uma nova conta com nome, email e senha")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cadastro realizado com sucesso"),
        @ApiResponse(responseCode = "409", description = "Email já cadastrado")
    })
    @PostMapping("/cadastro")
    public ResponseEntity<AuthResponse> cadastro(@RequestBody CadastroRequest request) {
        AuthResponse response = usuarioService.cadastro(request);
        return response.isSuccess()
                ? ResponseEntity.status(201).body(response)
                : ResponseEntity.status(409).body(response);
    }
}
