package com.devsu.apicuenta.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMovimiento;
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column
    private BigDecimal valor;
    @Column
    private BigDecimal saldo;
    @Column
    private String descripcion;
    @Column
    private ETipoMovimiento tipoMovimiento;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cuenta_id", nullable=false)
    private Cuenta cuenta;
}
