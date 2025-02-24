package com.ec.pichincha.registro.bancario.model.dto.movimiento.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MovimientoEliminadoResponse {
    private final String respuesta = "Movimiento eliminado exitosamente.";
    private String movimientoId;
}
