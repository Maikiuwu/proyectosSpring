package com.proyecto_prod.proyecto3.Model.Entities; 

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Detalles implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id_detalles;
    Long Id_encabezado;
    float descuento;
    Long id_producto;
    int cantidad;
    float subtotal;

    public Detalles() {
    }

    public Detalles(Long id_detalles, Long id_encabezado, float descuento, Long id_producto, int cantidad,
            float subtotal) {
        Id_detalles = id_detalles;
        Id_encabezado = id_encabezado;
        this.descuento = descuento;
        this.id_producto = id_producto;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getId_detalles() {
        return Id_detalles;
    }

    public void setId_detalles(Long id_detalles) {
        Id_detalles = id_detalles;
    }

    public Long getId_encabezado() {
        return Id_encabezado;
    }

    public void setId_encabezado(Long id_encabezado) {
        Id_encabezado = id_encabezado;
    }

    public float getDescuento() {
        return descuento;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public Long getId_producto() {
        return id_producto;
    }

    public void setId_producto(Long id_producto) {
        this.id_producto = id_producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

}
