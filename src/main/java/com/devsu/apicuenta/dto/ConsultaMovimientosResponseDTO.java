package com.devsu.apicuenta.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@ConfigurationProperties(prefix = "myapp")
public class ConsultaMovimientosResponseDTO {
    List<MovimientoDTO> movimientos=new ArrayList<>();

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
