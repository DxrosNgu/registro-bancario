package com.ec.pichincha.registro.bancario.service;

import com.ec.pichincha.registro.bancario.model.dto.cuenta.CuentaDto;
import com.ec.pichincha.registro.bancario.model.dto.movimiento.MovimientoDto;
import com.ec.pichincha.registro.bancario.model.dto.movimiento.request.MovimientoRequest;
import com.ec.pichincha.registro.bancario.model.dto.movimiento.response.MovimientoEliminadoResponse;
import com.ec.pichincha.registro.bancario.util.exception.MovimientoException;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoService {

    MovimientoDto obtenerMovimiento(String movimientoId) throws MovimientoException;

    List<MovimientoDto> obtenerMovimientos(String cuentaId, Boolean estado, LocalDateTime startFecha, LocalDateTime endFecha, int pagina);

    List<MovimientoDto> obtenerMovimientosByCliente(String cuentaId, Boolean estado, LocalDateTime startFecha, LocalDateTime endFecha, int pagina);

    MovimientoDto crearMovimiento(MovimientoRequest movimientoRequest);

    MovimientoDto actualizarMovimiento(String movimientoId, MovimientoRequest movimientoRequest) throws MovimientoException;

    MovimientoEliminadoResponse deleteMovimiento(String movimientoId);
}
