package com.example.proyecto_2.Model.Dao;

import java.util.List;

import com.example.proyecto_2.Model.Entities.Cliente;

public interface IClienteDao {
    
    public List<Cliente> findAll();

}
