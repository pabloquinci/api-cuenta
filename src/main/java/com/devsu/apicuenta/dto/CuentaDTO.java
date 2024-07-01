package com.devsu.apicuenta.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuentaDTO {
    private Long idCuenta;
    private Integer numCuenta;
    private Integer dni;
}
