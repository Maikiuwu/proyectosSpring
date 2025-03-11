package com.proyecto_prod.proyecto3.Model.Dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.proyecto_prod.proyecto3.Model.Entities.Producto;
import java.util.Optional;

@Repository //contiene  los metodos internos de un CRUD
public interface IProductoDao extends CrudRepository<Producto, Long> {
    // Puedes añadir consultas personalizadas si lo requieres

    Optional<Producto> findByNombre(String nombre);     //find by nombre busca productos por nombre en la base de datos
    //optional<objeto> se usa para evitar errores nullpointerexception y manejar valores opcionales de forma segura

}
