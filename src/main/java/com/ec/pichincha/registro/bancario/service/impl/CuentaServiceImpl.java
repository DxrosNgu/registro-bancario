package com.ec.pichincha.registro.bancario.service.impl;

import com.ec.pichincha.registro.bancario.model.dto.cuenta.CuentaDto;
import com.ec.pichincha.registro.bancario.model.dto.cuenta.request.CuentaRequest;
import com.ec.pichincha.registro.bancario.model.entity.Cuenta;
import com.ec.pichincha.registro.bancario.repository.CuentaRepository;
import com.ec.pichincha.registro.bancario.service.CuentaService;
import com.ec.pichincha.registro.bancario.util.exception.CuentaException;
import com.ec.pichincha.registro.bancario.util.constant.PrimaryKeyGenerator;
import com.ec.pichincha.registro.bancario.util.mapper.CuentaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository cuentaRepository;
    private final CuentaMapper cuentaMapper;

    @Override
    public CuentaDto obtenerCuenta(String cuentaId) throws CuentaException {
        Cuenta cuenta = cuentaRepository.findById(cuentaId)
                .orElseThrow(() -> new CuentaException("Cuenta no encontada"));
        return cuentaMapper.mapCuentaDto(cuenta);
    }

    @Override
    public CuentaDto obtenerCuentaByNumero(String numeroCuenta) throws CuentaException {
        Cuenta cuenta = cuentaRepository.findCuentaByNumeroCuenta(numeroCuenta)
                .orElseThrow(() -> new CuentaException("Cuenta no encontada"));
        return cuentaMapper.mapCuentaDto(cuenta);
    }

    @Override
    public CuentaDto obtenerCuentaByIdAndEstado(String cuentaId, Boolean estado) throws CuentaException {
        Cuenta cuenta = cuentaRepository.findCuentaByCuentaIdAndEstado(cuentaId, estado)
                .orElseThrow(() -> new CuentaException("Cuenta no encontrada o no valida"));
        return cuentaMapper.mapCuentaDto(cuenta);
    }

    @Override
    public List<CuentaDto> obtenerCuentaByClienteIdAndEstado(String clienteId, Boolean estado) throws CuentaException {
        List<Cuenta> cuentaList = cuentaRepository.findCuentaByClienteIdAndEstado(clienteId, estado);

        if(cuentaList.isEmpty()) {
            throw new CuentaException("No se encontro ninguna cuenta para el usuario");
        }

        return cuentaMapper.mapCuentaDtoList(cuentaList);
    }

    @Override
    public CuentaDto crearCuenta(CuentaRequest cuentaRequest) {
        var cuenta = cuentaMapper.mapCuenta(cuentaRequest);
        cuenta.setCuentaId(PrimaryKeyGenerator.generatePrimaryKey());

        Cuenta cuentaCreada = cuentaRepository.save(cuenta);
        return cuentaMapper.mapCuentaDto(cuentaCreada);
    }

    @Override
    public CuentaDto actualizarCuenta(String cuentaId, CuentaRequest cuentaRequest) throws CuentaException {
        if (cuentaId == null) throw new CuentaException("Cuenta no debe ser nulo");
        var CuentaDto = obtenerCuenta(cuentaId);

        var cuenta = cuentaMapper.mapActualizarCuenta(cuentaId, CuentaDto, cuentaRequest);

        Cuenta cuentaCreada = cuentaRepository.save(cuenta);
        return cuentaMapper.mapCuentaDto(cuentaCreada);
    }

    @Override
    public CuentaDto deleteCuenta(String cuentaId) throws CuentaException {
        var cuentaDto = obtenerCuenta(cuentaId);
        cuentaDto.setEstado(false);

        var cuenta = cuentaMapper.mapCuenta(cuentaDto);
        cuentaRepository.save(cuenta);
        return cuentaDto;
    }
}
