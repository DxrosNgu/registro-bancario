package com.ec.pichincha.registro.bancario.service;

import com.ec.pichincha.registro.bancario.model.dto.cuenta.CuentaDto;
import com.ec.pichincha.registro.bancario.model.dto.cuenta.request.CuentaRequest;
import com.ec.pichincha.registro.bancario.util.exception.CuentaException;

import java.util.List;

public interface CuentaService {

    CuentaDto obtenerCuenta(String cuentaId) throws CuentaException;

    CuentaDto obtenerCuentaByNumero(String numeroCuenta) throws CuentaException;

    List<CuentaDto> obtenerCuentaByClienteIdAndEstado(String cuentaId, Boolean estado) throws CuentaException;

    CuentaDto obtenerCuentaByIdAndEstado(String cuentaId, Boolean estado) throws CuentaException;

    CuentaDto crearCuenta(CuentaRequest cuentaRequest);

    CuentaDto actualizarCuenta(String cuentaId, CuentaRequest cuentaRequest) throws CuentaException;

    CuentaDto deleteCuenta(String cuentaId) throws CuentaException;
}
