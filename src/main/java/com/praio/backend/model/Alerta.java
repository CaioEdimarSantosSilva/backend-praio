package com.praio.backend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "alertas")
public class Alerta {

    @Id
    private String id;

    private String praiaId;

    private TipoAlerta tipo;

    private String descricao;

    private Date dataInicio;

    private Date dataFim;
}