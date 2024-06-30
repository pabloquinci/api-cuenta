package com.devsu.apicuenta.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

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
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cuenta_id", nullable=false)
    private Cuenta cuenta;
}
