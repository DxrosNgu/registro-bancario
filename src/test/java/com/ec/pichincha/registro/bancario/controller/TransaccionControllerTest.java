package com.ec.pichincha.registro.bancario.controller;

import com.ec.pichincha.registro.bancario.model.dto.transaccion.EstadoCuentaClienteResponse;
import com.ec.pichincha.registro.bancario.model.dto.transaccion.EstadoCuentaResponse;
import com.ec.pichincha.registro.bancario.model.dto.transaccion.TransaccionRequest;
import com.ec.pichincha.registro.bancario.model.dto.transaccion.TransaccionResponse;
import com.ec.pichincha.registro.bancario.service.TransaccionService;
import com.ec.pichincha.registro.bancario.util.exception.CuentaException;
import com.ec.pichincha.registro.bancario.util.exception.UsuarioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

@ActiveProfiles("unittest")
@ExtendWith(MockitoExtension.class)
class TransaccionControllerTest {

    @Mock
    private TransaccionService transaccionService;

    @InjectMocks
    private TransaccionController transaccionController;

    private TransaccionRequest transaccionRequest;
    private TransaccionResponse transaccionResponse;
    private EstadoCuentaResponse estadoCuentaResponse;
    private EstadoCuentaClienteResponse estadoCuentaClienteResponse;

    @BeforeEach
    void setUp() {
        transaccionRequest = new TransaccionRequest();
        transaccionResponse = new TransaccionResponse();
        estadoCuentaResponse = new EstadoCuentaResponse();
        estadoCuentaClienteResponse = new EstadoCuentaClienteResponse();
    }

    @Test
    void givenRegistrarTransaccion_whenCrearTrasaccion_thenReturns200() throws CuentaException {
        when(transaccionService.crearTrasaccion(any(TransaccionRequest.class))).thenReturn(transaccionResponse);

        ResponseEntity<TransaccionResponse> response = transaccionController.registrarTransaccion(transaccionRequest);

        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertEquals(transaccionResponse, response.getBody());

        verify(transaccionService, times(1)).crearTrasaccion(any(TransaccionRequest.class));
    }

    @Test
    void givenGenerarEstadoCuentaPorId_whenObtenerEstadoCuentaByCuenta_thenReturns200() throws CuentaException {
        when(transaccionService.obtenerEstadoCuentaByCuenta(anyString(), anyString(), anyString(), anyInt()))
                .thenReturn(estadoCuentaResponse);

        ResponseEntity<EstadoCuentaResponse> response = transaccionController.generarEstadoCuentaPorId(
                "12345", "01-01-2024", "10-01-2024", 0);

        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertEquals(estadoCuentaResponse, response.getBody());

        verify(transaccionService, times(1)).obtenerEstadoCuentaByCuenta(anyString(), anyString(), anyString(), anyInt());
    }
    @Test
    void givenGenerarEstadoCuentaPorCliente_whenObtenerEstadoCuentaByCliente_thenReturns200() throws UsuarioException {
        when(transaccionService.obtenerEstadoCuentaByCliente(anyString(), anyString(), anyString(), anyInt()))
                .thenReturn(estadoCuentaClienteResponse);

        ResponseEntity<EstadoCuentaClienteResponse> response = transaccionController.generarEstadoCuentaPorCliente(
                "54321", "01-01-2024", "10-01-2024", 0);

        assertNotNull(response);
        assertEquals(OK, response.getStatusCode());
        assertEquals(estadoCuentaClienteResponse, response.getBody());

        verify(transaccionService, times(1)).obtenerEstadoCuentaByCliente(anyString(), anyString(), anyString(), anyInt());
    }
}