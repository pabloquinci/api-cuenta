package com.devsu.apicuenta.repository;

import com.devsu.apicuenta.model.Cuenta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuentaRepository extends CrudRepository<Cuenta, Long> {
    List<Cuenta> findAll();
    Optional<Cuenta> findByNumCuenta(Integer numCuenta);
}
