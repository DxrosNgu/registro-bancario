package com.ec.pichincha.registro.bancario.util.mapper;

import com.ec.pichincha.registro.bancario.model.dto.cuenta.CuentaDto;
import com.ec.pichincha.registro.bancario.model.dto.movimiento.MovimientoDto;
import com.ec.pichincha.registro.bancario.model.dto.movimiento.request.MovimientoRequest;
import com.ec.pichincha.registro.bancario.model.entity.Cuenta;
import com.ec.pichincha.registro.bancario.model.entity.Movimiento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface MovimientoMapper {

    MovimientoDto mapMovimientoDto(Movimiento movimiento);

    List<MovimientoDto> mapMovimientoDtoList(List<Movimiento> movimientos);

    @Mapping(target = "cuenta.cuentaId", source = "cuentaId")
    Movimiento mapMovimiento(MovimientoRequest movimientoRequest);

    default Movimiento mapActualizarMovimiento(String movimientoId, MovimientoDto movimientoDto, MovimientoRequest movimientoRequest) {
        if ( movimientoRequest == null ) {
            return null;
        }

        Movimiento movimiento = new Movimiento();

        movimiento.setMovimientoId( movimientoId);
        movimiento.setFecha( movimientoRequest.getFecha() != null ? movimientoRequest.getFecha() : movimientoDto.getFecha());
        movimiento.setTipoMovimiento( movimientoRequest.getTipoMovimiento() != null ? movimientoRequest.getTipoMovimiento() : movimientoDto.getTipoMovimiento() );
        movimiento.setValor( movimientoRequest.getValor() != null ? movimientoRequest.getValor() : movimientoDto.getValor());
        movimiento.setSaldo( movimientoRequest.getSaldo() != null ? movimientoRequest.getSaldo() : movimientoDto.getSaldo() );

        CuentaDto cuentaDto = movimientoDto.getCuenta();
        Cuenta cuenta = new Cuenta();
        cuenta.setCuentaId(cuentaDto.getCuentaId());
        cuenta.setClienteId(cuentaDto.getClienteId());
        cuenta.setSaldoInicial(cuentaDto.getSaldoInicial());
        cuenta.setNumeroCuenta(cuentaDto.getNumeroCuenta());
        cuenta.setTipoCuenta(cuentaDto.getTipoCuenta());
        cuenta.setEstado(cuentaDto.getEstado());
        movimiento.setCuenta( cuenta );

        return movimiento;
    }

}
