package com.ec.pichincha.registro.bancario.model.dto.transaccion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoClienteResponse {
    private LocalDate fecha;
    private String cliente;
    private String numeroCuenta;
    private String tipoCuenta;
    private Double saldoInicial;
    private Boolean estado;
    private Double movimiento;
    private Double saldoDisponible;
}
