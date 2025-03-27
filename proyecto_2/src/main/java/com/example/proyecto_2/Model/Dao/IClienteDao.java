package com.example.proyecto_2.Model.Dao;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.proyecto_2.Model.Entities.Cliente;

@Repository
public interface IClienteDao extends CrudRepository<Cliente, Long> {
    
    Optional<Cliente> findByNombre(String nombre);  

}
