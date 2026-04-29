package com.praio.backend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;

@Data
@Document(collection = "logs")
public class Log {

    @Id
    private String id;

    private String usuarioId;

    private AcaoLog acao;

    private Map<String, Object> detalhes;

    private Date data;
}