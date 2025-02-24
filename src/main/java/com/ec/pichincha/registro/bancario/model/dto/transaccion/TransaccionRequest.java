package com.ec.pichincha.registro.bancario.model.dto.transaccion;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransaccionRequest {

    private String tipoMovimiento;
    private Double valor;
    private String numeroCuenta;
}
