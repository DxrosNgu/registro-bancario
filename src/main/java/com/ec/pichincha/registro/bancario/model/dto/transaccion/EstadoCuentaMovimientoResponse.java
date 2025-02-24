package com.ec.pichincha.registro.bancario.model.dto.transaccion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstadoCuentaMovimientoResponse {

    private String fecha;
    private String tipoMovimiento;
    private Double valor;
    private Double saldo;
}
