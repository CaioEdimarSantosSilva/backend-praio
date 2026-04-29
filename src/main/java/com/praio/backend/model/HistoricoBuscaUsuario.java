package com.praio.backend.model;

import lombok.Data;
import java.util.Date;
import java.util.Map;

@Data
public class HistoricoBuscaUsuario {
    private Date data;
    private Map<String, String> filtros;
}