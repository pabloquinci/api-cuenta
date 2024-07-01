package com.devsu.apicuenta.dto.rabbit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteEvent {
    private String status; // pending, progress, completed
    private String message;
    private ClienteDTO cliente;
}
