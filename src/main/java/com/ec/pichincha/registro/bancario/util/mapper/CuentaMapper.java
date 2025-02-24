package com.ec.pichincha.registro.bancario.util.mapper;

import com.ec.pichincha.registro.bancario.model.dto.cuenta.CuentaDto;
import com.ec.pichincha.registro.bancario.model.dto.cuenta.request.CuentaRequest;
import com.ec.pichincha.registro.bancario.model.entity.Cuenta;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface CuentaMapper {

    CuentaDto mapCuentaDto(Cuenta cuenta);

    List<CuentaDto> mapCuentaDtoList(List<Cuenta> cuenta);

    Cuenta mapCuenta(CuentaRequest cuentaRequest);

    default Cuenta mapActualizarCuenta(String cuentaId, CuentaDto cuentaDto, CuentaRequest cuentaRequest) {
        if ( cuentaRequest == null ) {
            return null;
        }

        Cuenta cuenta = new Cuenta();

        cuenta.setCuentaId(cuentaId);
        cuenta.setClienteId( cuentaRequest.getClienteId() != null ? cuentaRequest.getClienteId() : cuentaDto.getClienteId() );
        cuenta.setNumeroCuenta( cuentaRequest.getNumeroCuenta() != null ? cuentaRequest.getNumeroCuenta() : cuentaDto.getNumeroCuenta() );
        cuenta.setTipoCuenta( cuentaRequest.getTipoCuenta() != null ? cuentaRequest.getTipoCuenta() : cuentaDto.getTipoCuenta() );
        cuenta.setSaldoInicial( cuentaRequest.getSaldoInicial() != null ? cuentaRequest.getSaldoInicial() : cuentaDto.getSaldoInicial() );
        cuenta.setEstado(cuentaDto.getEstado());

        return cuenta;
    }

    Cuenta mapCuenta(CuentaDto cuentaDto);
}
