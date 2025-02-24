package com.ec.pichincha.registro.bancario.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ec.pichincha.registro.bancario.model.dto.cuenta.CuentaDto;
import com.ec.pichincha.registro.bancario.model.dto.movimiento.MovimientoDto;
import com.ec.pichincha.registro.bancario.model.dto.movimiento.request.MovimientoRequest;
import com.ec.pichincha.registro.bancario.model.dto.movimiento.response.MovimientoEliminadoResponse;
import com.ec.pichincha.registro.bancario.model.entity.Movimiento;
import com.ec.pichincha.registro.bancario.repository.MovimientoRepository;
import com.ec.pichincha.registro.bancario.util.exception.MovimientoException;
import com.ec.pichincha.registro.bancario.util.mapper.MovimientoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ActiveProfiles("unittest")
@ExtendWith(MockitoExtension.class)
class MovimientoServiceImplTest {

    @Mock
    private MovimientoRepository movimientoRepository;

    @Mock
    private MovimientoMapper movimientoMapper;

    @InjectMocks
    private MovimientoServiceImpl movimientoService;

    private Movimiento movimiento;
    private MovimientoDto movimientoDto;
    private MovimientoRequest movimientoRequest;

    @BeforeEach
    void setUp() {
        movimiento = new Movimiento();
        movimiento.setMovimientoId("123");
        movimiento.setFecha(LocalDateTime.now());

        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setCuentaId("dsffdas");

        movimientoDto = new MovimientoDto("123",  LocalDateTime.now(), "Deposito", 1000.0, 5000.0 , cuentaDto);

        movimientoRequest = new MovimientoRequest();
        movimientoRequest.setCuentaId("Cuenta001");
        movimientoRequest.setTipoMovimiento("Deposito");
        movimientoRequest.setValor(1000.0);
    }

    @Test
    void givenObtenerMovimiento_whenfindById_thenReturnsMovimientoDto() throws MovimientoException {
        when(movimientoRepository.findById("123")).thenReturn(Optional.of(movimiento));
        when(movimientoMapper.mapMovimientoDto(movimiento)).thenReturn(movimientoDto);

        MovimientoDto result = movimientoService.obtenerMovimiento("123");

        assertNotNull(result);
        assertEquals("123", result.getMovimientoId());
        verify(movimientoRepository, times(1)).findById("123");
        verify(movimientoMapper, times(1)).mapMovimientoDto(movimiento);
    }

    @Test
    void givenObtenerMovimiento_whenFindById_thenReturnsNotFound() {
        when(movimientoRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(MovimientoException.class, () -> movimientoService.obtenerMovimiento("999"));
        verify(movimientoRepository, times(1)).findById("999");
    }

    @Test
    void givenObtenerMovimiento_whenFindMovimientoByCuenta_CuentaIdAndCuenta_EstadoAndFechaBetween_thenReturnsMovimientoDtoList() {
        Page<Movimiento> page = new PageImpl<>(Collections.singletonList(movimiento));
        when(movimientoRepository.findMovimientoByCuenta_CuentaIdAndCuenta_EstadoAndFechaBetween(
                anyString(), anyBoolean(), any(), any(), any())).thenReturn(page);
        when(movimientoMapper.mapMovimientoDtoList(anyList())).thenReturn(List.of(movimientoDto));

        List<MovimientoDto> result = movimientoService.obtenerMovimientos("Cuenta001", true, LocalDateTime.now().minusDays(1), LocalDateTime.now(), 0);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(movimientoRepository, times(1)).findMovimientoByCuenta_CuentaIdAndCuenta_EstadoAndFechaBetween(any(), any(), any(), any(), any());
    }

    @Test
    void givenCrearMovimiento_saveMovimiento_thenReturnsMovimientoDto() {
        when(movimientoMapper.mapMovimiento(movimientoRequest)).thenReturn(movimiento);
        when(movimientoRepository.save(movimiento)).thenReturn(movimiento);
        when(movimientoMapper.mapMovimientoDto(movimiento)).thenReturn(movimientoDto);

        MovimientoDto result = movimientoService.crearMovimiento(movimientoRequest);

        assertNotNull(result);
        assertEquals("123", result.getMovimientoId());
        verify(movimientoRepository, times(1)).save(movimiento);
    }

    @Test
    void givenActualizarMovimiento_whenSaveMovimiento_thenReturnsMovimientoDto() throws MovimientoException {
        when(movimientoRepository.findById("123")).thenReturn(Optional.of(movimiento));
        when(movimientoMapper.mapActualizarMovimiento(eq("123"), any(), any())).thenReturn(movimiento);
        when(movimientoRepository.save(movimiento)).thenReturn(movimiento);
        when(movimientoMapper.mapMovimientoDto(movimiento)).thenReturn(movimientoDto);

        MovimientoDto result = movimientoService.actualizarMovimiento("123", movimientoRequest);

        assertNotNull(result);
        assertEquals("123", result.getMovimientoId());
        verify(movimientoRepository, times(1)).save(movimiento);
    }

    @Test
    void givenActualizarMovimiento_whenMovimientoIdIsNull_thenReturnsNullId() {
        assertThrows(MovimientoException.class, () -> movimientoService.actualizarMovimiento(null, movimientoRequest));
    }

    @Test
    void givenDeleteMovimiento_whendeleteById_thenReturnsMovimientoEliminadoResponse() {
        doNothing().when(movimientoRepository).deleteById("123");

        MovimientoEliminadoResponse response = movimientoService.deleteMovimiento("123");

        assertNotNull(response);
        assertEquals("123", response.getMovimientoId());
        verify(movimientoRepository, times(1)).deleteById("123");
    }
}
