package com.ec.pichincha.registro.bancario.service;

import com.ec.pichincha.registro.bancario.model.dto.transaccion.EstadoCuentaClienteResponse;
import com.ec.pichincha.registro.bancario.model.dto.transaccion.EstadoCuentaResponse;
import com.ec.pichincha.registro.bancario.model.dto.transaccion.TransaccionRequest;
import com.ec.pichincha.registro.bancario.model.dto.transaccion.TransaccionResponse;
import com.ec.pichincha.registro.bancario.util.exception.CuentaException;
import com.ec.pichincha.registro.bancario.util.exception.MovimientoException;
import com.ec.pichincha.registro.bancario.util.exception.UsuarioException;
import org.springframework.web.bind.annotation.RequestParam;

public interface TransaccionService {

    TransaccionResponse crearTrasaccion(TransaccionRequest transaccionRequest) throws CuentaException;

    EstadoCuentaResponse obtenerEstadoCuentaByCuenta(String cuentaId, String initFecha, String finFecha, int pagina) throws CuentaException;

    EstadoCuentaClienteResponse obtenerEstadoCuentaByCliente(String clienteId, String initFecha, String finFecha, int pagina) throws UsuarioException;
}
