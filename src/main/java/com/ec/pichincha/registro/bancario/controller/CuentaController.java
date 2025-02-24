package com.ec.pichincha.registro.bancario.controller;

import com.ec.pichincha.registro.bancario.model.dto.cuenta.CuentaDto;
import com.ec.pichincha.registro.bancario.model.dto.cuenta.request.CuentaRequest;
import com.ec.pichincha.registro.bancario.service.CuentaService;
import com.ec.pichincha.registro.bancario.util.exception.CuentaException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/registro-bancario/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService cuentaService;

    @GetMapping(path = "/{cuentaId}", produces="application/json")
    public @ResponseBody ResponseEntity<CuentaDto> obtenerCuenta(@PathVariable String cuentaId) throws CuentaException {
        return  ResponseEntity.ok(cuentaService.obtenerCuenta(cuentaId));
    }

    @GetMapping(path = "/buscar", produces="application/json")
    public @ResponseBody ResponseEntity<CuentaDto> obtenerCuentaByNumero(@RequestParam String numeroCuenta) throws CuentaException {
        return  ResponseEntity.ok(cuentaService.obtenerCuentaByNumero(numeroCuenta));
    }

    @PostMapping(consumes="application/json")
    public @ResponseBody ResponseEntity<CuentaDto> crearCuenta(@RequestBody CuentaRequest cuenta){
        return  ResponseEntity.ok(cuentaService.crearCuenta(cuenta));
    }

    @PutMapping(path = "/{cuentaId}", consumes="application/json")
    public @ResponseBody ResponseEntity<CuentaDto> actualizarCuenta(@PathVariable String cuentaId, @RequestBody CuentaRequest cuenta) throws CuentaException {
        return  ResponseEntity.ok(cuentaService.actualizarCuenta(cuentaId, cuenta));
    }

    @DeleteMapping(path = "/{cuentaId}", produces="application/json")
    public @ResponseBody ResponseEntity<CuentaDto> deleteCuenta(@PathVariable String cuentaId) throws CuentaException {
        return  ResponseEntity.ok(cuentaService.deleteCuenta(cuentaId));
    }
}
