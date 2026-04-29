package com.praio.backend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "recomendacoes")
public class Recomendacao {

    @Id
    private String id;

    private String usuarioId;

    private List<PraiaRecomendada> praiasRecomendadas;

    private Date dataGeracao;
}