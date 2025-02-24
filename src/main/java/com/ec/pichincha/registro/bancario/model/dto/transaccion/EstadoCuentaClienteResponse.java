package com.ec.pichincha.registro.bancario.model.dto.transaccion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstadoCuentaClienteResponse {

    private List<MovimientoClienteResponse> movimientos;
}
