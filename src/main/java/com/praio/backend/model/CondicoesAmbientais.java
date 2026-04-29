package com.praio.backend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "condicoes_ambientais")
public class CondicoesAmbientais {

    @Id
    private String id;

    private String praiaId;

    private Date data;

    private QualidadeAgua qualidadeAgua;

    private double temperaturaAgua;

    private String clima;

    private String vento;

    private String ondas;

    private Lotacao lotacao;

    private int indiceUV;

    private TipoAlertaAmbiental alerta;
}