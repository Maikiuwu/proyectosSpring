package com.proyecto_prod.proyecto3.Model.Dao;

import org.springframework.data.repository.CrudRepository;
import com.proyecto_prod.proyecto3.Model.Entities.Encabezado;

public interface EncabezadoDaoImp extends CrudRepository<Encabezado, Long> {
    // Métodos adicionales de consulta si se requieren
}
