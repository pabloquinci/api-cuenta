package com.devsu.apicuenta.repository;

import com.devsu.apicuenta.model.Movimiento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovimientoRepository extends CrudRepository<Movimiento, Long> {
    @Query("SELECT u FROM Movimiento u WHERE u.fecha BETWEEN :fechaDesde AND :fechaHasta ")
    public List<Movimiento> findMov(@Param("fechaDesde") Date fechaDesde, @Param("fechaHasta") Date fechaHasta);
}
