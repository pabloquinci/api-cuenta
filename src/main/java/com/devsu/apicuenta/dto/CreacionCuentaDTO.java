package com.devsu.apicuenta.dto;

import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private Integer numCuenta;
    @NotNull
    private String tipoCuenta;
    @NotNull
    private BigDecimal saldoInicial;
    @NotNull
    private String estado;
    @NotNull
    private Integer dniCliente;
}
