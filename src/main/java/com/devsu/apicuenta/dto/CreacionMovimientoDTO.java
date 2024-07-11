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

public class CreacionMovimientoDTO {
    @NotNull
    private Integer numCuenta;
    @NotNull
    private BigDecimal saldo;
    @NotNull
    private BigDecimal valor;
    @NotNull
    private String descripcion;
}
