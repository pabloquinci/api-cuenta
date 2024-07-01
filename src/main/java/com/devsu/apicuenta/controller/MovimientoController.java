package com.devsu.apicuenta.controller;

import com.devsu.apicuenta.dto.*;
import com.devsu.apicuenta.model.Movimiento;
import com.devsu.apicuenta.service.CuentaService;
import com.devsu.apicuenta.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/movimiento")
public class MovimientoController {

    @Autowired
    MovimientoService movimientoService;


    @PostMapping("/crear")
    public ResponseEntity<CreacionMovimientoResponseDTO> crearCuenta(@RequestBody CreacionMovimientoDTO creacionMovimientoDTO) {
        Optional<CreacionMovimientoResponseDTO> response= this.movimientoService.crear(creacionMovimientoDTO);

        if(response.isPresent()){
            return ResponseEntity.ok(response.get());
        }

        return ResponseEntity.notFound().build();

    }

    @GetMapping("/getMovimientos")
    private ResponseEntity<List<Movimiento>> getMovimientos(@RequestParam("idCliente")Long idCliente,
                                                              @RequestParam("fechaDesde") String fechaDesde,
                                                              @RequestParam("fechaHasta") String fechaHasta) throws ParseException {

        Date dt=new SimpleDateFormat("yyyy-MM-dd").parse(fechaDesde);
        Date dt2=new SimpleDateFormat("yyyy-MM-dd").parse(fechaHasta);

        List<Movimiento> resultado= movimientoService.getMovimientos(idCliente,dt, dt2);
            return ResponseEntity.ok(resultado);
    }
/*
    @PutMapping("/editar")
    private ResponseEntity editar(@RequestBody ActualizarEditarRequestDTO request) {

        Optional<ResultadoResponseDTO> response=movimientoService.editar(request);

        if (response.isPresent()){
            return ResponseEntity.ok(response.get());
        }
        return ResponseEntity.internalServerError().body("Error");

    }
    @PatchMapping("/actualizar/{numCuenta}")
    private ResponseEntity<ResultadoResponseDTO>actualizar(@RequestBody Map<String, String> request, @PathVariable("numCuenta")  Integer numCuenta){

        Optional<ResultadoResponseDTO> resultado=cuentaService.actualizar(request, numCuenta);
        if(resultado.isPresent()){
            return ResponseEntity.ok(resultado.get());
        }
        return ResponseEntity.internalServerError().body(null);
    }


    @DeleteMapping("/borrar/{numCuenta}")
    private ResponseEntity<ResultadoResponseDTO> borrar(@PathVariable("numCuenta") Integer numCuenta){
        return ResponseEntity.ok(cuentaService.borrar(numCuenta).get());
    }
*/
}
