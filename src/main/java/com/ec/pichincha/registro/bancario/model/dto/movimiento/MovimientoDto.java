package com.ec.pichincha.registro.bancario.model.dto.movimiento;

import com.ec.pichincha.registro.bancario.model.dto.cuenta.CuentaDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovimientoDto {

    private String movimientoId;
    private LocalDateTime fecha;

    private String tipoMovimiento;
    private Double valor;
    private Double saldo;

    private CuentaDto cuenta;
}
