package com.ec.pichincha.registro.bancario.model.dto.cuenta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CuentaDto {

    private String cuentaId;
    private String clienteId;
    private String numeroCuenta;

    private String tipoCuenta;
    private Double saldoInicial;
    private Boolean estado;
}
