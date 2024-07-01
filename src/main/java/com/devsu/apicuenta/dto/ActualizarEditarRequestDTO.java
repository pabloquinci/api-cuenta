package com.devsu.apicuenta.dto;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActualizarEditarRequestDTO {
    private Integer numCuenta;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private String estado;
}
