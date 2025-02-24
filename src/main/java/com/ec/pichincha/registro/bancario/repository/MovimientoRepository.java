package com.ec.pichincha.registro.bancario.repository;

import com.ec.pichincha.registro.bancario.model.entity.Movimiento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, String> {

    @Query
    Page<Movimiento> findMovimientoByCuenta_CuentaIdAndCuenta_EstadoAndFechaBetween(String cuentaId, Boolean estado, LocalDateTime startFecha, LocalDateTime endFecha, Pageable pageable);

    Page<Movimiento> findMovimientoByCuenta_ClienteIdAndCuenta_EstadoAndFechaBetween(String cuentaId, Boolean estado, LocalDateTime startFecha, LocalDateTime endFecha, Pageable pageable);

}
