package com.devsu.apicuenta.repository;

import com.devsu.apicuenta.model.Movimiento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimientoRepository extends CrudRepository<Movimiento, Long> {
}
