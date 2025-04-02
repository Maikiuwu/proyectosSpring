package com.proyecto_prod.proyecto3.Model.Entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "encabezados")
public class Encabezado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con el cliente que realizó la compra
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    // Fecha de emisión de la factura
    private LocalDate fecha;

    // Total de la factura (se puede calcular a partir de los detalles)
    private Double total;

    // Relación uno a muchos con los detalles de la factura
    @OneToMany(mappedBy = "encabezado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Detalles> detalles = new ArrayList<>();

    // Constructor vacío
    public Encabezado() {
        this.fecha = LocalDate.now();
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<Detalles> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<Detalles> detalles) {
        this.detalles = detalles;
    }
    
    // Método para agregar un detalle y actualizar el total
    public void addDetalle(Detalles detalle) {
        detalle.setEncabezado(this);
        this.detalles.add(detalle);
        if(this.total == null){
            this.total = 0.0;
        }
        this.total += detalle.getSubtotal();
    }
    
    // Método para remover un detalle
    public void removeDetalle(Detalles detalle) {
        detalle.setEncabezado(null);
        this.detalles.remove(detalle);
        if(this.total == null){
            this.total = 0.0;
        }
        this.total -= detalle.getSubtotal();
    }
}
