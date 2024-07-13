package com.devsu.apicuenta.controller;

import com.devsu.apicuenta.dto.*;
import com.devsu.apicuenta.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/cuenta")
public class CuentaController {

    CuentaService cuentaService;

    @Autowired
    public CuentaController(CuentaService cuentaService) {
        this.cuentaService=cuentaService;
    }


    @PostMapping("/crear")
    public ResponseEntity<CreacionCuentaResponseDTO> crearCuenta(@RequestBody CreacionCuentaDTO creacionCuentaDTO) {
        Optional<CreacionCuentaResponseDTO> response= this.cuentaService.crear(creacionCuentaDTO);

        if(response.isPresent()){
            return ResponseEntity.ok(response.get());
        }

        return ResponseEntity.notFound().build();

    }

    @PutMapping("/editar")
    public ResponseEntity<ResultadoResponseDTO> editar(@RequestBody ActualizarEditarRequestDTO request) {

        Optional<ResultadoResponseDTO> response=cuentaService.editar(request);

        if (response.isPresent()){
            return ResponseEntity.ok(response.get());
        }
        return ResponseEntity.internalServerError().body(ResultadoResponseDTO.builder().build());

    }
    @PatchMapping("/actualizar/{numCuenta}")
    public ResponseEntity<ResultadoResponseDTO>actualizar(@RequestBody Map<String, String> request, @PathVariable("numCuenta")  Integer numCuenta){

        Optional<ResultadoResponseDTO> resultado=cuentaService.actualizar(request, numCuenta);
        if(resultado.isPresent()){
            return ResponseEntity.ok(resultado.get());
        }
        return ResponseEntity.internalServerError().body(null);
    }

    @GetMapping("/getCuentas")
    public ResponseEntity<CuentasResponseDTO> getCuentas() {
        Optional<CuentasResponseDTO> resultado= cuentaService.getCuentas();
        if(resultado.isPresent()){
            return ResponseEntity.ok(resultado.get());
        }
        return ResponseEntity.internalServerError().body(null);
    }

    @DeleteMapping("/borrar/{numCuenta}")
    public ResponseEntity<ResultadoResponseDTO> borrar(@PathVariable("numCuenta") Integer numCuenta){
        return ResponseEntity.ok(cuentaService.borrar(numCuenta).get());
    }
}
