package com.devsu.apicuenta.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CreacionCuentaDTO {
    private Integer numCuenta;
    private String tipoCUenta;
    private BigDecimal saldoInicial;
    private String estado;
}
