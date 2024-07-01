package com.devsu.apicuenta.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data
@Builder

public class ConsultaMovimientosRequestDTO {
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaDesde;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHasta;
    private Long idCliente;



}
