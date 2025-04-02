package com.proyecto_prod.proyecto3.Model.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Pattern(regexp = "^[A-Za-z\\s]+$", message = "El nombre solo puede contener letras y espacios")
    private String nombre;

    @Positive(message = "El precio debe ser un número positivo")
    private double precio;

    private Long id_vendedor;

    private Integer  cantidad;

    public Producto() {
    } 
   
    
    public Producto(Long id, String nombre, double precio, Long id_vendedor, Integer cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.id_vendedor = id_vendedor;
        this.cantidad = cantidad;
    }

    //getters y setters
    public Integer getCantidad() {
        return cantidad;
    }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }


    public Long getId_vendedor() {
        return id_vendedor;
    }

    public void setId_vendedor(Long id_vendedor) {
        this.id_vendedor = id_vendedor;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}

