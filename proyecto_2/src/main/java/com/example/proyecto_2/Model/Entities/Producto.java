package com.example.proyecto_2.Model.Entities;

import java.io.Serializable;
import java.sql.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
//import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Producto implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String cantidad;
    private String precio_venta;

    @Column(name = "Fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;

    public Producto(){

    }

    public Producto(Long ID, String nombre, String cantidad, String precio_venta, Date fecha){

        this.nombre = nombre;
        this.cantidad = cantidad;
        this.id = ID;
        this.precio_venta = precio_venta;
        this.fecha = fecha;       

    }

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

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPrecio_venta() {
        return precio_venta;
    }

    public void setPrecio_venta(String precio_venta) {
        this.precio_venta = precio_venta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public static Long getSerialVersionUID(){
        return SerialVersionUID;
    }   

    private static final Long SerialVersionUID = 1L;    

    
}


