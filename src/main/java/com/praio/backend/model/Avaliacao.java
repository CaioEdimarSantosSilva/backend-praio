package com.praio.backend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "avaliacoes")
public class Avaliacao {

    @Id
    private String id;

    private String usuarioId;

    private String praiaId;

    private int nota; // 1 a 5

    private String comentario;

    private Date data;
}