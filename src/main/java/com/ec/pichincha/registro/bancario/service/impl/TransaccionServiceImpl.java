package com.ec.pichincha.registro.bancario.service.impl;

import com.ec.pichincha.registro.bancario.model.client.ClienteResponse;
import com.ec.pichincha.registro.bancario.model.dto.cuenta.request.CuentaRequest;
import com.ec.pichincha.registro.bancario.model.dto.movimiento.MovimientoDto;
import com.ec.pichincha.registro.bancario.model.dto.transaccion.*;
import com.ec.pichincha.registro.bancario.model.dto.cuenta.CuentaDto;
import com.ec.pichincha.registro.bancario.model.dto.movimiento.request.MovimientoRequest;
import com.ec.pichincha.registro.bancario.service.CuentaService;
import com.ec.pichincha.registro.bancario.service.MovimientoService;
import com.ec.pichincha.registro.bancario.service.TransaccionService;
import com.ec.pichincha.registro.bancario.util.client.ClienteClient;
import com.ec.pichincha.registro.bancario.util.constant.TransaccionUtil;
import com.ec.pichincha.registro.bancario.util.exception.CuentaException;
import com.ec.pichincha.registro.bancario.util.exception.TransaccionException;
import com.ec.pichincha.registro.bancario.util.exception.UsuarioException;
import com.ec.pichincha.registro.bancario.util.mapper.TransaccionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.ec.pichincha.registro.bancario.util.constant.ConstantsUtil.ZERO;
import static com.ec.pichincha.registro.bancario.util.constant.ConstantsUtil.CUENTA_VALIDA;

@Service
@RequiredArgsConstructor
public class TransaccionServiceImpl implements TransaccionService {

    private final MovimientoService transaccionService;
    private final CuentaService cuentaService;
    private final ClienteClient client;
    private final TransaccionMapper transaccionMapper;

    @Override
    public TransaccionResponse crearTrasaccion(TransaccionRequest transaccionRequest) throws CuentaException {
        String numeroCuenta = transaccionRequest.getNumeroCuenta();
        CuentaRequest cuentaRequest = new CuentaRequest();
        MovimientoRequest movimientoRequest = new MovimientoRequest();
        CuentaDto cuentaDto = cuentaService.obtenerCuentaByNumero(numeroCuenta);

        if (!cuentaDto.getEstado()) {
            throw new TransaccionException("La cuenta esta desahibilitada");
        }

        Double movimiento = TransaccionUtil.getMovimiento(transaccionRequest.getTipoMovimiento(), transaccionRequest.getValor());
        Double saldoDisponible = TransaccionUtil.getSaldoDisponible(cuentaDto.getSaldoInicial(), movimiento);

        if (ZERO.compareTo(saldoDisponible) > 0) {
            throw new TransaccionException("Saldo no disponible");
        }

        movimientoRequest.setTipoMovimiento(transaccionRequest.getTipoMovimiento());
        movimientoRequest.setValor(transaccionRequest.getValor());
        movimientoRequest.setSaldo(saldoDisponible);
        movimientoRequest.setCuentaId(cuentaDto.getCuentaId());
        movimientoRequest.setFecha(LocalDateTime.now());
        var movimientoCreado = transaccionService.crearMovimiento(movimientoRequest);

        cuentaRequest.setSaldoInicial(saldoDisponible);
        var cuentaActualizada = cuentaService.actualizarCuenta(cuentaDto.getCuentaId(), cuentaRequest);

        return transaccionMapper.mapTransaccionResponse(movimientoCreado.getMovimientoId(), cuentaDto.getSaldoInicial(), movimientoCreado.getValor(), movimientoCreado.getTipoMovimiento(), cuentaActualizada.getSaldoInicial());
    }

    @Override
    public EstadoCuentaResponse obtenerEstadoCuentaByCuenta(String cuentaId, String initFecha, String finFecha, int pagina) throws CuentaException {
        LocalDateTime initFechaHora = TransaccionUtil.getFechaInicial(initFecha);
        LocalDateTime finFechaHora = TransaccionUtil.getFechaFin(finFecha);

        CuentaDto cuentaDto = cuentaService.obtenerCuentaByIdAndEstado(cuentaId, CUENTA_VALIDA);

        List<MovimientoDto> movimientoDtoList = transaccionService.obtenerMovimientos(cuentaId, CUENTA_VALIDA, initFechaHora, finFechaHora, pagina);
        return transaccionMapper.mapEstadoCuentaResponse(cuentaDto, movimientoDtoList);
    }

    @Override
    public EstadoCuentaClienteResponse obtenerEstadoCuentaByCliente(String clienteId, String initFecha, String finFecha, int pagina) throws UsuarioException {
        LocalDateTime initFechaHora = TransaccionUtil.getFechaInicial(initFecha);
        LocalDateTime finFechaHora = TransaccionUtil.getFechaFin(finFecha);

        ClienteResponse clienteResponse = client.getClienteById(clienteId);

        List<MovimientoClienteResponse> movimientos = transaccionService.obtenerMovimientosByCliente(clienteId, CUENTA_VALIDA, initFechaHora, finFechaHora, pagina)
                .stream()
                .map(movimientoDto -> {
                    Double movimiento = TransaccionUtil.getMovimiento(movimientoDto.getTipoMovimiento(), movimientoDto.getValor());
                    Double saldoInicial = TransaccionUtil.getSaldoInicial(movimientoDto.getSaldo(), movimiento);
                    return transaccionMapper.mapMovimientoClienteResponse(
                            clienteResponse.getNombre(),
                            movimientoDto.getCuenta(),
                            movimientoDto.getFecha(),
                            saldoInicial,
                            movimiento,
                            movimientoDto.getSaldo()
                    );
                })
                .collect(Collectors.toList());

        return new EstadoCuentaClienteResponse(movimientos);
    }
}
