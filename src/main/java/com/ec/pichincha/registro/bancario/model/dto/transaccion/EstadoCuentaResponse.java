package com.ec.pichincha.registro.bancario.model.dto.transaccion;

import com.ec.pichincha.registro.bancario.model.dto.movimiento.MovimientoDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstadoCuentaResponse {

    private String numeroCuenta;
    private String tipoCuenta;
    private String saldoInicial;
    private List<EstadoCuentaMovimientoResponse> movimientos;
}
