package com.ec.pichincha.registro.bancario.controller;

import com.ec.pichincha.registro.bancario.model.dto.transaccion.EstadoCuentaClienteResponse;
import com.ec.pichincha.registro.bancario.model.dto.transaccion.EstadoCuentaResponse;
import com.ec.pichincha.registro.bancario.model.dto.transaccion.TransaccionRequest;
import com.ec.pichincha.registro.bancario.model.dto.transaccion.TransaccionResponse;
import com.ec.pichincha.registro.bancario.service.TransaccionService;
import com.ec.pichincha.registro.bancario.util.exception.CuentaException;
import com.ec.pichincha.registro.bancario.util.exception.UsuarioException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/registro-bancario")
@RequiredArgsConstructor
public class TransaccionController {

    private final TransaccionService transaccionService;

    @PostMapping(path = "/transaccion")
    public @ResponseBody ResponseEntity<TransaccionResponse>
    registrarTransaccion(@RequestBody TransaccionRequest transaccionRequest) throws CuentaException {
        return ResponseEntity.ok(transaccionService.crearTrasaccion(transaccionRequest));
    }

    @GetMapping(path = "/cuenta/{cuentaId}/reportes")
    public @ResponseBody ResponseEntity<EstadoCuentaResponse>
    generarEstadoCuentaPorId(@PathVariable String cuentaId,
                             @RequestParam String initFecha,
                             @RequestParam String finFecha,
                             @RequestParam(defaultValue = "0") int pagina) throws CuentaException {
        return ResponseEntity.ok(transaccionService.obtenerEstadoCuentaByCuenta(cuentaId, initFecha, finFecha, pagina));
    }

    @GetMapping(path = "/cliente/{clienteId}/reportes")
    public @ResponseBody ResponseEntity<EstadoCuentaClienteResponse>
    generarEstadoCuentaPorCliente(@PathVariable String clienteId,
                                  @RequestParam String initFecha,
                                  @RequestParam String finFecha,
                                  @RequestParam(defaultValue = "0") int pagina) throws UsuarioException {
        return ResponseEntity.ok(transaccionService.obtenerEstadoCuentaByCliente(clienteId, initFecha, finFecha, pagina));
    }
}
