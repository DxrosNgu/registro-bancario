package com.ec.pichincha.registro.bancario.controller;

import com.ec.pichincha.registro.bancario.model.dto.movimiento.MovimientoDto;
import com.ec.pichincha.registro.bancario.model.dto.movimiento.request.MovimientoRequest;
import com.ec.pichincha.registro.bancario.model.dto.movimiento.response.MovimientoEliminadoResponse;
import com.ec.pichincha.registro.bancario.service.MovimientoService;
import com.ec.pichincha.registro.bancario.util.exception.MovimientoException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/registro-bancario/movimientos")
@RequiredArgsConstructor
public class MovimientoController {

    private final MovimientoService movimientoService;

    @GetMapping(path = "/{movimientoId}", produces="application/json")
    public @ResponseBody ResponseEntity<MovimientoDto> obtenerMovimiento(@PathVariable String movimientoId) throws MovimientoException {
        return ResponseEntity.ok(movimientoService.obtenerMovimiento(movimientoId));
    }

    @PostMapping(consumes="application/json")
    public @ResponseBody ResponseEntity<MovimientoDto> crearMovimiento(@RequestBody MovimientoRequest movimientoRequest){
        return ResponseEntity.ok(movimientoService.crearMovimiento(movimientoRequest));
    }

    @PutMapping(path = "/{movimientoId}", consumes="application/json")
    public @ResponseBody ResponseEntity<MovimientoDto> actualizarMovimiento(@PathVariable String movimientoId, @RequestBody MovimientoRequest movimientoRequest) throws MovimientoException {
        return ResponseEntity.ok(movimientoService.actualizarMovimiento(movimientoId, movimientoRequest));
    }

    @DeleteMapping(path = "/{movimientoId}", produces="application/json")
    public @ResponseBody ResponseEntity<MovimientoEliminadoResponse> deleteMovimiento(@PathVariable String movimientoId){
        return ResponseEntity.ok(movimientoService.deleteMovimiento(movimientoId));
    }
}
