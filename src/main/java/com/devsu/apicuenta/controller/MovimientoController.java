package com.devsu.apicuenta.controller;

import com.devsu.apicuenta.dto.*;
import com.devsu.apicuenta.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
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
    public ResponseEntity<ConsultaMovimientosResponseDTO> getMovimientos(@RequestParam("numCuenta")Integer numCuenta,
                                                                          @RequestParam("fechaDesde") String fechaDesde,
                                                                          @RequestParam("fechaHasta") String fechaHasta, Model model) throws ParseException {

        Date dt=new SimpleDateFormat("yyyy-MM-dd").parse(fechaDesde);
        Date dt2=new SimpleDateFormat("yyyy-MM-dd").parse(fechaHasta);

        ConsultaMovimientosResponseDTO resultado= movimientoService.getMovimientos(numCuenta,dt, dt2,model).get();
            return ResponseEntity.ok(resultado);

    }
}
