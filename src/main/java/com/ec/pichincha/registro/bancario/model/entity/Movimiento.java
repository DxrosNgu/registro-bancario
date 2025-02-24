package com.ec.pichincha.registro.bancario.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "MOVIMIENTO")
public class Movimiento {

    @Id
    @Column(name = "movimiento_id")
    private String movimientoId;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fecha;

    private String tipoMovimiento;
    private Double valor;
    private Double saldo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cuenta_id", nullable = false)
    private Cuenta cuenta;
}
