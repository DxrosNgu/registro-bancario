package com.ec.pichincha.registro.bancario.model.dto.movimiento.request;

import com.ec.pichincha.registro.bancario.model.dto.cuenta.request.CuentaRequest;
import com.ec.pichincha.registro.bancario.model.entity.Cuenta;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MovimientoRequest {
    private LocalDateTime fecha;

    private String tipoMovimiento;
    private Double valor;
    private Double saldo;

//    private CuentaRequest cuenta;
    private String cuentaId;
}
