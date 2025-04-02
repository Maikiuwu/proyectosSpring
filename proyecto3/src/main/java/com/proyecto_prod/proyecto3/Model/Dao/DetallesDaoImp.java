package com.proyecto_prod.proyecto3.Model.Dao;

import org.springframework.data.repository.CrudRepository;
import com.proyecto_prod.proyecto3.Model.Entities.Detalles;

public interface DetallesDaoImp extends CrudRepository<Detalles, Long> {
    // Métodos adicionales de consulta si se requieren
}
