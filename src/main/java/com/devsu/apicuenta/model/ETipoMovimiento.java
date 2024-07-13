package com.devsu.apicuenta.model;

public enum ETipoMovimiento {
    DEPOSITO("Deposito",1), EXTRACCION("Extraccion",2);

    private String descripcion;
    private int numOperacion;

    private ETipoMovimiento (String descripcion, int numOperacion){
        this.descripcion = descripcion;
        this.numOperacion = numOperacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getNumOperacion() {
        return numOperacion;
    }	}
