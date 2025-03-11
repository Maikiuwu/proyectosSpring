package com.example.proyecto_2.Model.Crud;

import com.example.proyecto_2.Model.Entities.Producto;
import java.util.Optional;
import java.sql.Date;

//falto
public interface ProductoCRUD{

    Producto producto = new Producto();

    Producto Crear(Producto producto);
    Optional<Producto> Consulta(Long id);
    Boolean Eliminar(Long id);
    Producto Actualizar(Long id, String nombre, String cantidad, String precio_venta, Date fecha);
    
}
