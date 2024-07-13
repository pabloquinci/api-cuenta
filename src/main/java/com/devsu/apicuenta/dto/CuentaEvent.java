package com.devsu.apicuenta.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaEvent {
    private String status; // pending, progress, completed
    private String message;
    private CuentaDTO cuenta;
}
