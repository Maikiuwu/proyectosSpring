package com.proyecto_prod.proyecto3.Model.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "detalles")
public class Detalles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con el producto (suponiendo que ya tienes la entidad Producto definida)
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    // Cantidad de productos adquiridos
    private Integer cantidad;

    // Precio unitario del producto en el momento de la compra
    private Double precioUnitario;

    // Subtotal = cantidad * precioUnitario
    private Double subtotal;

    // Relación con el encabezado de la factura
    @ManyToOne
    @JoinColumn(name = "encabezado_id")
    private Encabezado encabezado;

    // Constructor vacío
    public Detalles() {
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Encabezado getEncabezado() {
        return encabezado;
    }

    public void setEncabezado(Encabezado encabezado) {
        this.encabezado = encabezado;
    }

    // Método para calcular subtotal
    public void calcularSubtotal() {
        this.subtotal = this.cantidad * this.precioUnitario;
    }
}