package com.ec.pichincha.registro.bancario.model.dto.transaccion;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransaccionResponse {

    private String transaccionId;
    private Double saldoInicial;
    private Double movimiento;
    private String tipoMovimiento;
    private Double saldoDisponible;
}
