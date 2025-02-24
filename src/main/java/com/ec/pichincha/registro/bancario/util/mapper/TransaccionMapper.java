package com.ec.pichincha.registro.bancario.util.mapper;

import com.ec.pichincha.registro.bancario.model.dto.cuenta.CuentaDto;
import com.ec.pichincha.registro.bancario.model.dto.movimiento.MovimientoDto;
import com.ec.pichincha.registro.bancario.model.dto.transaccion.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public interface TransaccionMapper {

    @Mapping(target = "transaccionId", source = "movimientoId")
    TransaccionResponse mapTransaccionResponse(String movimientoId, Double saldoInicial, Double movimiento, String tipoMovimiento, Double saldoDisponible);

    EstadoCuentaMovimientoResponse mapEstadoCuentaMovimientoResponse(MovimientoDto movimientoDto);

    List<EstadoCuentaMovimientoResponse> mapEstadoCuentaMovimientoResponseList(List<MovimientoDto> movimientoDtoList);

    EstadoCuentaResponse mapEstadoCuentaResponse(CuentaDto cuentaDto, List<MovimientoDto> movimientos);

    @Mapping(target = "saldoInicial", source = "saldoInicial")
    MovimientoClienteResponse mapMovimientoClienteResponse(String cliente, CuentaDto cuentaDto, LocalDateTime fecha, Double saldoInicial, Double movimiento, Double saldoDisponible);

}
