package com.devsu.apicuenta.dto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@ConfigurationProperties(prefix = "myapp")
public class ConsultaMovimientosResponseDTO {
    List<MovimientoDTO> movimientos=new ArrayList<>();

    @Autowired
    public ConsultaMovimientosResponseDTO( List<MovimientoDTO> movimientos){
        this.movimientos=movimientos;
    }
    public ConsultaMovimientosResponseDTO(){}

    public List<MovimientoDTO> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<MovimientoDTO> movimientos) {
        this.movimientos = movimientos;
    }
}
