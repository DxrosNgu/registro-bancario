package com.ec.pichincha.registro.bancario.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ec.pichincha.registro.bancario.model.dto.cuenta.CuentaDto;
import com.ec.pichincha.registro.bancario.model.dto.cuenta.request.CuentaRequest;
import com.ec.pichincha.registro.bancario.model.entity.Cuenta;
import com.ec.pichincha.registro.bancario.repository.CuentaRepository;
import com.ec.pichincha.registro.bancario.util.exception.CuentaException;
import com.ec.pichincha.registro.bancario.util.mapper.CuentaMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@ActiveProfiles("unitTest")
@ExtendWith(MockitoExtension.class)
class CuentaServiceImplTest {

    @Mock
    private CuentaRepository cuentaRepository;

    @Mock
    private CuentaMapper cuentaMapper;

    @InjectMocks
    private CuentaServiceImpl cuentaService;

    private Cuenta cuenta;
    private CuentaDto cuentaDto;
    private CuentaRequest cuentaRequest;

    @BeforeEach
    void setUp() {
        cuenta = new Cuenta();
        cuenta.setCuentaId("123");
        cuenta.setNumeroCuenta("456");
        cuenta.setEstado(true);

        cuentaDto = new CuentaDto();
        cuentaDto.setCuentaId("123");
        cuentaDto.setNumeroCuenta("456");
        cuentaDto.setEstado(true);

        cuentaRequest = new CuentaRequest();
        cuentaRequest.setNumeroCuenta("456");
    }

    @Test
    void givenObtenerCuenta_whenFindById_thenReturnsCuentaDto() throws CuentaException {
        when(cuentaRepository.findById("123")).thenReturn(Optional.of(cuenta));
        when(cuentaMapper.mapCuentaDto(cuenta)).thenReturn(cuentaDto);

        CuentaDto result = cuentaService.obtenerCuenta("123");

        assertNotNull(result);
        assertEquals("123", result.getCuentaId());
    }

    @Test
    void givenObtenerCuenta_whenFindById_thenReturnsNotFound() {
        when(cuentaRepository.findById("123")).thenReturn(Optional.empty());
        assertThrows(CuentaException.class, () -> cuentaService.obtenerCuenta("123"));
    }

    @Test
    void  givenObtenerCuentaByNumero_whenFindCuentaByNumeroCuenta_thenReturnsCuentaDto() throws CuentaException {
        when(cuentaRepository.findCuentaByNumeroCuenta("456")).thenReturn(Optional.of(cuenta));
        when(cuentaMapper.mapCuentaDto(cuenta)).thenReturn(cuentaDto);

        CuentaDto result = cuentaService.obtenerCuentaByNumero("456");

        assertNotNull(result);
        assertEquals("456", result.getNumeroCuenta());
    }

    @Test
    void givenObtenerCuentaByNumero_whenFindCuentaByNumeroCuenta_thenReturnsNotFound() {
        when(cuentaRepository.findCuentaByNumeroCuenta("456")).thenReturn(Optional.empty());
        assertThrows(CuentaException.class, () -> cuentaService.obtenerCuentaByNumero("456"));
    }

    @Test
    void givenObtenerCuentaByNumero_whenSaveCuenta_thenReturnsCuentaDto() {
        when(cuentaMapper.mapCuenta(cuentaRequest)).thenReturn(cuenta);
        when(cuentaRepository.save(cuenta)).thenReturn(cuenta);
        when(cuentaMapper.mapCuentaDto(cuenta)).thenReturn(cuentaDto);

        CuentaDto result = cuentaService.crearCuenta(cuentaRequest);

        assertNotNull(result);
        assertEquals("456", result.getNumeroCuenta());
    }

    @Test
    void givenActualizarCuenta_whenSaveCuenta_thenReturnsCuentaDto() throws CuentaException {
        when(cuentaRepository.findById("123")).thenReturn(Optional.of(cuenta));
        when(cuentaMapper.mapCuentaDto(cuenta)).thenReturn(cuentaDto);
        when(cuentaMapper.mapActualizarCuenta("123", cuentaDto, cuentaRequest)).thenReturn(cuenta);
        when(cuentaRepository.save(cuenta)).thenReturn(cuenta);
        when(cuentaMapper.mapCuentaDto(cuenta)).thenReturn(cuentaDto);

        CuentaDto result = cuentaService.actualizarCuenta("123", cuentaRequest);

        assertNotNull(result);
        assertEquals("123", result.getCuentaId());
    }

    @Test
    void givenActualizarCuenta_whenfindById_thenReturnsNotFound() {
        when(cuentaRepository.findById("123")).thenReturn(Optional.empty());
        assertThrows(CuentaException.class, () -> cuentaService.actualizarCuenta("123", cuentaRequest));
    }

    @Test
    void givenDeleteCuenta_whenfindById_thenReturnsCuentaDto() throws CuentaException {
        when(cuentaRepository.findById("123")).thenReturn(Optional.of(cuenta));
        when(cuentaMapper.mapCuentaDto(cuenta)).thenReturn(cuentaDto);
        when(cuentaMapper.mapCuenta(any(CuentaDto.class))).thenReturn(cuenta);

        CuentaDto result = cuentaService.deleteCuenta("123");

        assertNotNull(result);
        assertFalse(result.getEstado());
    }

    @Test
    void givenDeleteCuenta_whenfindById_thenReturnsNotFound() {
        when(cuentaRepository.findById("123")).thenReturn(Optional.empty());
        assertThrows(CuentaException.class, () -> cuentaService.deleteCuenta("123"));
    }
}
