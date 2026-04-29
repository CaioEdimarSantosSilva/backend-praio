package com.praio.backend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "usuarios")
public class Usuario {

    @Id
    private String id;

    private String nome;

    @Indexed(unique = true)
    private String email;

    private String senha;

    private String fotoPerfil;

    private Date dataCriacao;

    private PreferenciasUsuario preferencias;

    private List<HistoricoBuscaUsuario> historicoBuscas;

    private List<String> favoritos; // IDs das praias

    private TipoUsuario tipoUsuario;
}