package com.ec.pichincha.registro.bancario.service.impl;

import com.ec.pichincha.registro.bancario.model.client.ClienteResponse;
import com.ec.pichincha.registro.bancario.model.dto.cuenta.CuentaDto;
import com.ec.pichincha.registro.bancario.model.dto.cuenta.request.CuentaRequest;
import com.ec.pichincha.registro.bancario.model.dto.movimiento.MovimientoDto;
import com.ec.pichincha.registro.bancario.model.dto.movimiento.request.MovimientoRequest;
import com.ec.pichincha.registro.bancario.model.dto.transaccion.*;
import com.ec.pichincha.registro.bancario.service.CuentaService;
import com.ec.pichincha.registro.bancario.service.MovimientoService;
import com.ec.pichincha.registro.bancario.util.client.ClienteClient;
import com.ec.pichincha.registro.bancario.util.constant.ConstantsUtil;
import com.ec.pichincha.registro.bancario.util.exception.CuentaException;
import com.ec.pichincha.registro.bancario.util.exception.TransaccionException;
import com.ec.pichincha.registro.bancario.util.exception.UsuarioException;
import com.ec.pichincha.registro.bancario.util.mapper.TransaccionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

@ActiveProfiles("unittest")
@ExtendWith(MockitoExtension.class)
class TransaccionServiceImplTest {

    @Mock
    private MovimientoService movimientoService;

    @Mock
    private CuentaService cuentaService;

    @Mock
    private ClienteClient clienteClient;

    @Mock
    private TransaccionMapper transaccionMapper;

    @InjectMocks
    private TransaccionServiceImpl transaccionService;

    private TransaccionRequest transaccionRequest;
    private CuentaDto cuentaDto;
    private MovimientoRequest movimientoRequest;
    private TransaccionResponse transaccionResponse;
    private ClienteResponse clienteResponse;
    private MovimientoDto movimientoDto;
    private MovimientoClienteResponse movimientoClienteResponse;

    @BeforeEach
    void setUp() {
        transaccionRequest = new TransaccionRequest();
        transaccionRequest.setNumeroCuenta("12345");
        transaccionRequest.setTipoMovimiento("Deposito");
        transaccionRequest.setValor(1500.00);

        cuentaDto = new CuentaDto();
        cuentaDto.setCuentaId("12345");
        cuentaDto.setNumeroCuenta("12345");
        cuentaDto.setSaldoInicial(10000.00);
        cuentaDto.setEstado(true);

        movimientoRequest = new MovimientoRequest();
        movimientoRequest.setCuentaId("12345");
        movimientoRequest.setTipoMovimiento("Deposito");
        movimientoRequest.setValor(1500.00);
        movimientoRequest.setSaldo(11500.00);
        movimientoRequest.setFecha(LocalDateTime.now());

        transaccionResponse = new TransaccionResponse();

        LocalDate localDate = LocalDate.of(2024, 1, 5);
        clienteResponse = new ClienteResponse();
        clienteResponse.setNombre("John Doe");

        CuentaDto cuenta = new CuentaDto();
        cuenta.setCuentaId("12345");
        movimientoDto = new MovimientoDto();
        movimientoDto.setTipoMovimiento("Retiro");
        movimientoDto.setValor(500.0);
        movimientoDto.setSaldo(9500.0);
        movimientoDto.setCuenta(cuenta);
        movimientoDto.setFecha(localDate.atStartOfDay());

        movimientoClienteResponse = new MovimientoClienteResponse(
                localDate,
                "John Doe",
                "12345",
                "tipoCuenta",
                10000.0, // saldo inicial
                true,
                -500.0,  // movimiento
                9500.0   // saldo final
        );
    }

    @Test
    void givenCrearTrasaccion_whenActualizarCuentaAndMovimiento_thenReturnsTransaccionResponse() throws CuentaException {
        when(cuentaService.obtenerCuentaByNumero(transaccionRequest.getNumeroCuenta())).thenReturn(cuentaDto);
        when(movimientoService.crearMovimiento(any(MovimientoRequest.class))).thenReturn(movimientoDto);
        when(cuentaService.actualizarCuenta(eq(cuentaDto.getCuentaId()), any(CuentaRequest.class)))
                .thenReturn(new CuentaDto(null, cuentaDto.getClienteId(), cuentaDto.getNumeroCuenta(), cuentaDto.getTipoCuenta(), 6000.0, true));
        when(transaccionMapper.mapTransaccionResponse(any(), anyDouble(), anyDouble(), anyString(), anyDouble()))
                .thenReturn(transaccionResponse);

        TransaccionResponse response = transaccionService.crearTrasaccion(transaccionRequest);

        assertNotNull(response);
        verify(cuentaService, times(1)).obtenerCuentaByNumero(transaccionRequest.getNumeroCuenta());
        verify(movimientoService, times(1)).crearMovimiento(any(MovimientoRequest.class));
        verify(cuentaService, times(1)).actualizarCuenta(eq(cuentaDto.getCuentaId()), any(CuentaRequest.class));
        verify(transaccionMapper, times(1)).mapTransaccionResponse(any(), anyDouble(), anyDouble(), anyString(), anyDouble());
    }

    @Test
    void givenCrearTrasaccion_whenObtenerCuentaByNumero_thenReturnsErrorCuentaDesactivada() throws CuentaException {
        cuentaDto.setEstado(false);

        when(cuentaService.obtenerCuentaByNumero(anyString())).thenReturn(cuentaDto);

        TransaccionException exception = assertThrows(TransaccionException.class,
                () -> transaccionService.crearTrasaccion(transaccionRequest));

        assertEquals("La cuenta esta desahibilitada", exception.getMessage());
        verify(cuentaService, times(1)).obtenerCuentaByNumero(anyString());
        verify(movimientoService, never()).crearMovimiento(any());
    }

    @Test
    void givenCrearTrasaccion_whenObtenerCuentaByNumero_thenReturnsErrorCuentaInsuficiente() throws CuentaException {
        transaccionRequest.setTipoMovimiento("Retiro");
        transaccionRequest.setValor(20000.00); // More than initial balance

        when(cuentaService.obtenerCuentaByNumero(anyString())).thenReturn(cuentaDto);

        TransaccionException exception = assertThrows(TransaccionException.class,
                () -> transaccionService.crearTrasaccion(transaccionRequest));

        assertEquals("Saldo no disponible", exception.getMessage());
        verify(cuentaService, times(1)).obtenerCuentaByNumero(anyString());
        verify(movimientoService, never()).crearMovimiento(any());
    }

    @Test
    void givenOtenerEstadoCuentaByCuenta_whenObtenerMovimientos_thenReturnsEstadoCuentaResponse() throws CuentaException {
        when(cuentaService.obtenerCuentaByIdAndEstado(anyString(), anyBoolean())).thenReturn(cuentaDto);
        when(movimientoService.obtenerMovimientos(anyString(), anyBoolean(), any(), any(), anyInt()))
                .thenReturn(List.of(new MovimientoDto()));

        EstadoCuentaResponse expectedResponse = new EstadoCuentaResponse();
        when(transaccionMapper.mapEstadoCuentaResponse(any(), anyList())).thenReturn(expectedResponse);

        EstadoCuentaResponse response = transaccionService.obtenerEstadoCuentaByCuenta("12345", "2024-01-01", "2024-01-01", 0);

        assertNotNull(response);
        verify(cuentaService, times(1)).obtenerCuentaByIdAndEstado(anyString(), anyBoolean());
        verify(movimientoService, times(1)).obtenerMovimientos(anyString(), anyBoolean(), any(), any(), anyInt());
    }

    @Test
    void givenObtenerEstadoCuentaByCliente_whenObtenerMovimientosByCliente_thenReturnsEstadoCuentaClienteResponse() throws UsuarioException {
        String clienteId = "54321";
        String initFecha = "2024-01-01";
        String finFecha = "2024-10-01";
        int pagina = 0;

        LocalDateTime initFechaHora = LocalDate.parse(initFecha, ConstantsUtil.DATE_TIME_FORMATTER).atStartOfDay();
        LocalDateTime finFechaHora = LocalDate.parse(finFecha, ConstantsUtil.DATE_TIME_FORMATTER).atTime(23, 59, 59);

        when(clienteClient.getClienteById(clienteId)).thenReturn(clienteResponse);
        when(movimientoService.obtenerMovimientosByCliente(clienteId, true, initFechaHora, finFechaHora, pagina))
                .thenReturn(List.of(movimientoDto));
        when(transaccionMapper.mapMovimientoClienteResponse(anyString(), any(), any(), anyDouble(), anyDouble(), anyDouble()))
                .thenReturn(movimientoClienteResponse);

        EstadoCuentaClienteResponse response = transaccionService.obtenerEstadoCuentaByCliente(clienteId, initFecha, finFecha, pagina);

        assertNotNull(response);
        assertEquals(1, response.getMovimientos().size());
        assertEquals("John Doe", response.getMovimientos().get(0).getCliente());
        assertEquals("12345", response.getMovimientos().get(0).getNumeroCuenta());
        assertEquals(10000.0, response.getMovimientos().get(0).getSaldoInicial());
        assertEquals(-500.0, response.getMovimientos().get(0).getMovimiento());
        assertEquals(9500.0, response.getMovimientos().get(0).getSaldoDisponible());

        verify(clienteClient, times(1)).getClienteById(clienteId);
        verify(movimientoService, times(1)).obtenerMovimientosByCliente(clienteId, true, initFechaHora, finFechaHora, pagina);
        verify(transaccionMapper, times(1)).mapMovimientoClienteResponse(anyString(), any(), any(), anyDouble(), anyDouble(), anyDouble());
    }

    @Test
    void givenObtenerEstadoCuentaByCliente_whenObtenerMovimientosByCliente_thenReturnsEmptyMovimientos() throws UsuarioException {
        String clienteId = "54321";
        String initFecha = "2024-01-01";
        String finFecha = "2024-10-01";
        int pagina = 0;

        LocalDateTime initFechaHora = LocalDate.parse(initFecha, ConstantsUtil.DATE_TIME_FORMATTER).atStartOfDay();
        LocalDateTime finFechaHora = LocalDate.parse(finFecha, ConstantsUtil.DATE_TIME_FORMATTER).atTime(23, 59, 59);

        when(clienteClient.getClienteById(clienteId)).thenReturn(clienteResponse);
        when(movimientoService.obtenerMovimientosByCliente(clienteId, true, initFechaHora, finFechaHora, pagina))
                .thenReturn(List.of());

        EstadoCuentaClienteResponse response = transaccionService.obtenerEstadoCuentaByCliente(clienteId, initFecha, finFecha, pagina);

        assertNotNull(response);
        assertTrue(response.getMovimientos().isEmpty());

        verify(clienteClient, times(1)).getClienteById(clienteId);
        verify(movimientoService, times(1)).obtenerMovimientosByCliente(clienteId, true, initFechaHora, finFechaHora, pagina);
        verify(transaccionMapper, never()).mapMovimientoClienteResponse(anyString(), any(), any(), anyDouble(), anyDouble(), anyDouble());
    }

    @Test
    void givenObtenerEstadoCuentaByCliente_whenGetClienteById_thenReturnsClienteNotFound() throws UsuarioException {
        String clienteId = "54321";
        String initFecha = "2024-01-01";
        String finFecha = "2024-10-01";
        int pagina = 0;

        when(clienteClient.getClienteById(clienteId)).thenThrow(new UsuarioException("Error buscando"));

        assertThrows(UsuarioException.class,
                () -> transaccionService.obtenerEstadoCuentaByCliente(clienteId, initFecha, finFecha, pagina));

        verify(clienteClient, times(1)).getClienteById(clienteId);
        verify(movimientoService, never()).obtenerMovimientosByCliente(anyString(), anyBoolean(), any(), any(), anyInt());
        verify(transaccionMapper, never()).mapMovimientoClienteResponse(anyString(), any(), any(), anyDouble(), anyDouble(), anyDouble());
    }
}