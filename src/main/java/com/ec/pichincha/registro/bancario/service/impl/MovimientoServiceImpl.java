package com.ec.pichincha.registro.bancario.service.impl;

import com.ec.pichincha.registro.bancario.model.dto.cuenta.CuentaDto;
import com.ec.pichincha.registro.bancario.model.dto.movimiento.MovimientoDto;
import com.ec.pichincha.registro.bancario.model.dto.movimiento.request.MovimientoRequest;
import com.ec.pichincha.registro.bancario.model.dto.movimiento.response.MovimientoEliminadoResponse;
import com.ec.pichincha.registro.bancario.model.entity.Movimiento;
import com.ec.pichincha.registro.bancario.repository.MovimientoRepository;
import com.ec.pichincha.registro.bancario.service.MovimientoService;
import com.ec.pichincha.registro.bancario.util.constant.PrimaryKeyGenerator;

import com.ec.pichincha.registro.bancario.util.exception.MovimientoException;
import com.ec.pichincha.registro.bancario.util.mapper.MovimientoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimientoServiceImpl implements MovimientoService {

    private final MovimientoMapper movimientoMapper;
    private final MovimientoRepository movimientoRepository;

    @Override
    public MovimientoDto obtenerMovimiento(String movimientoId) throws MovimientoException {
        var movimiento = movimientoRepository.findById(movimientoId)
                .orElseThrow(() -> new MovimientoException("Movimiento no encontrado"));
        return movimientoMapper.mapMovimientoDto(movimiento);
    }

    @Override
    public List<MovimientoDto> obtenerMovimientos(String cuentaId, Boolean estado, LocalDateTime initFechaHora, LocalDateTime finFechaHora, int pagina) {
        Sort sortByFecha = Sort.by(Sort.Order.desc("fecha"));
        PageRequest pageRequest = PageRequest.of(pagina, 5).withSort(sortByFecha);

        var movimientos = movimientoRepository.findMovimientoByCuenta_CuentaIdAndCuenta_EstadoAndFechaBetween(cuentaId, estado, initFechaHora, finFechaHora, pageRequest);
        return movimientoMapper.mapMovimientoDtoList(movimientos.getContent());
    }

    @Override
    public List<MovimientoDto> obtenerMovimientosByCliente(String clienteId, Boolean estado, LocalDateTime initFechaHora, LocalDateTime finFechaHora, int pagina) {
        Sort sortByTipoCuentaAndFecha = Sort.by(Sort.Order.asc("cuenta.tipoCuenta"), Sort.Order.desc("fecha"));
        PageRequest pageRequest = PageRequest.of(pagina, 5).withSort(sortByTipoCuentaAndFecha);


        var movimientos = movimientoRepository.findMovimientoByCuenta_ClienteIdAndCuenta_EstadoAndFechaBetween(clienteId, estado, initFechaHora, finFechaHora, pageRequest);
        return movimientoMapper.mapMovimientoDtoList(movimientos.getContent());
    }

    @Override
    public MovimientoDto crearMovimiento(MovimientoRequest movimientoRequest) {
        var movimiento = movimientoMapper.mapMovimiento(movimientoRequest);

        movimiento.setMovimientoId(PrimaryKeyGenerator.generatePrimaryKey());
        Movimiento movimientoCreado = movimientoRepository.save(movimiento);

        return movimientoMapper.mapMovimientoDto(movimientoCreado);
    }

    @Override
    public MovimientoDto actualizarMovimiento(String movimientoId, MovimientoRequest movimientoRequest) throws MovimientoException {
        if (movimientoId == null) throw new MovimientoException("Movimiento no debe ser nulo");
        MovimientoDto movimientoExistente = obtenerMovimiento(movimientoId);

        var movimiento = movimientoMapper.mapActualizarMovimiento(movimientoId, movimientoExistente, movimientoRequest);
        Movimiento movimientoActualizado = movimientoRepository.save(movimiento);

        return movimientoMapper.mapMovimientoDto(movimientoActualizado);
    }

    @Override
    public MovimientoEliminadoResponse deleteMovimiento(String movimientoId) {
        movimientoRepository.deleteById(movimientoId);
        return new MovimientoEliminadoResponse(movimientoId);
    }
}
