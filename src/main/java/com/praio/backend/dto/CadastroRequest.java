package com.praio.backend.dto;

import lombok.Data;

@Data
public class CadastroRequest {
    private String nome;
    private String email;
    private String senha;
}
