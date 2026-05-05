package com.praio.backend.service;

import com.praio.backend.dto.AuthResponse;
import com.praio.backend.dto.CadastroRequest;
import com.praio.backend.dto.LoginRequest;
import com.praio.backend.model.TipoUsuario;
import com.praio.backend.model.Usuario;
import com.praio.backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public AuthResponse login(LoginRequest request) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(request.getEmail());

        if (usuarioOpt.isEmpty() || !usuarioOpt.get().getSenha().equals(request.getSenha())) {
            return new AuthResponse(false, "Email ou senha inválidos", null, null, null);
        }

        Usuario usuario = usuarioOpt.get();
        return new AuthResponse(true, "Login realizado com sucesso",
                usuario.getId(), usuario.getNome(), usuario.getEmail());
    }

    public AuthResponse cadastro(CadastroRequest request) {
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            return new AuthResponse(false, "Este email já está cadastrado", null, null, null);
        }

        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setSenha(request.getSenha());
        usuario.setDataCriacao(new Date());
        usuario.setTipoUsuario(TipoUsuario.COMUM);

        Usuario saved = usuarioRepository.save(usuario);
        return new AuthResponse(true, "Cadastro realizado com sucesso",
                saved.getId(), saved.getNome(), saved.getEmail());
    }
}
