package com.example.proyecto_2.Model.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

import java.sql.Date;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.GenerationType;

@Entity
public class Encabezado implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id_compra;
    Long id_cliente;
    float descuento_Total;
    float Total;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    Date fecha;

    public Encabezado() {
    }

    public Encabezado(Long id_compra, Long id_cliente, float descuento_Total, float total, Date fecha) {
        Id_compra = id_compra;
        this.id_cliente = id_cliente;
        this.descuento_Total = descuento_Total;
        Total = total;
        this.fecha = fecha;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getId_compra() {
        return Id_compra;
    }

    public void setId_compra(Long id_compra) {
        Id_compra = id_compra;
    }

    public Long getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Long id_cliente) {
        this.id_cliente = id_cliente;
    }

    public float getDescuento_Total() {
        return descuento_Total;
    }

    public void setDescuento_Total(float descuento_Total) {
        this.descuento_Total = descuento_Total;
    }

    public float getTotal() {
        return Total;
    }

    public void setTotal(float total) {
        Total = total;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}
