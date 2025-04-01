package com.proyecto_prod.proyecto3.Model.Dao;

import com.proyecto_prod.proyecto3.Model.Entities.Cliente;
import java.util.List;

public interface IClienteDao {
    // CRUD operations for Cliente entity
    List<Cliente> findAll();
    void save(Cliente cliente);
    Cliente findOne(Long id);
    void delete(Long id);
    Cliente findByEmail(String email);
}
