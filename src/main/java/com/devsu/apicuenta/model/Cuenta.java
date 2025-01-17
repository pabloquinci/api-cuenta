package com.devsu.apicuenta.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCuenta;
    @Column
    private Integer numCuenta;
    @Column
    private String tipoCuenta;
    @Column
    private BigDecimal saldoInicial;
    @Column
    private BigDecimal saldoDisponible;
    @Column
    private String estado;
    @Column
    private Integer dniCliente;

    @OneToMany(mappedBy = "cuenta", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Movimiento> movimientos=new ArrayList<>();

}
