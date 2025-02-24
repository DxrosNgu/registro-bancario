package com.ec.pichincha.registro.bancario.repository;

import com.ec.pichincha.registro.bancario.model.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, String> {

    Optional<Cuenta> findCuentaByNumeroCuenta(String numeroCuenta );

    Optional<Cuenta> findCuentaByCuentaIdAndEstado(String cuentaId, Boolean estado);

    List<Cuenta> findCuentaByClienteIdAndEstado(String clienteId, Boolean estado);
}
