package com.example.proyecto_2.Model.Dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.proyecto_2.Model.Entities.Producto;
import java.util.Optional;

 @Repository //contiene  los metodos internos de un CRUD
public interface IProductoDao extends CrudRepository<Producto, Long> {
    // Puedes añadir consultas personalizadas si lo requieres

    Optional<Producto> findByNombre(String nombre);     //find by nombre busca productos por nombre en la base de datos
    //optional<objeto> se usa para evitar errores nullpointerexception y manejar valores opcionales de forma segura
    
}
