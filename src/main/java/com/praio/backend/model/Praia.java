package com.praio.backend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "praias")
public class Praia {

    @Id
    private String id;

    private String nome;

    private String descricao;

    private LocalizacaoPraia localizacao;

    private List<String> imagens;

    private EstruturaPraia estrutura;

    private double mediaAvaliacoes;

    private int totalAvaliacoes;

    private List<String> tags;
}