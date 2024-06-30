package com.devsu.apicuenta.repository;

import com.devsu.apicuenta.model.Cuenta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaRepository extends CrudRepository<Cuenta, Long> {
}
