package com.ec.pichincha.registro.bancario.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "CUENTA")
public class Cuenta {

    @Id
    @Column(name = "cuenta_id")
    private String cuentaId;
    private String clienteId;

    @Column(nullable = false, unique = true)
    private String numeroCuenta;

    private String tipoCuenta;
    private Double saldoInicial;
    private Boolean estado;

}
