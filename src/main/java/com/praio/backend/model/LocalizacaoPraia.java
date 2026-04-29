package com.praio.backend.model;

import lombok.Data;

@Data
public class LocalizacaoPraia {
    private String cidade;
    private String estado;
    private double latitude;
    private double longitude;
}
