package com.devsu.apicuenta.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CuentaResponseDTO {
    private Long idCuenta;
    private Integer numCuenta;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private String estado;
}
