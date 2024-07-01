package com.devsu.apicuenta.dto;

import com.devsu.apicuenta.model.Cuenta;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class MovimientoDTO {
    private Date fecha;
    private BigDecimal valorMovimiento;
    private String cliente;
    private Integer numCuenta;
    private String tipoCuenta;
    private BigDecimal saldoInicial;
    private Boolean estado;
    private BigDecimal getSaldoDisponible;
    private Cuenta cuenta;
}
