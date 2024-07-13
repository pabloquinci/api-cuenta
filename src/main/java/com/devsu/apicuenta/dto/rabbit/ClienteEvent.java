package com.devsu.apicuenta.dto.rabbit;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteEvent {
    private String status; // pending, progress, completed
    private String message;
    private ClienteDTO cliente;
}
