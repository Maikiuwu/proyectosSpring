package com.proyecto_prod.proyecto3.Model.Entities;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El nombre no puede estar vacío.")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúñÑ ]{3,50}$", 
             message = "El nombre debe tener entre 3 y 50 caracteres y solo puede contener letras y espacios.")
    @Column(unique = true)
    private String nombre;
    
    private int cantidad;
    private float precio_venta;
    
    @Column(name = "Fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    public Producto() {
    }

    public Producto(Long id, String nombre, int cantidad, float precio_venta, Date fecha) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio_venta = precio_venta;
        this.fecha = fecha;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(float precio_venta) {
        this.precio_venta = precio_venta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Producto [id=" + id + ", nombre=" + nombre + ", cantidad=" + cantidad + ", precio_venta=" 
                + precio_venta + ", fecha=" + fecha + "]";
    }
}
