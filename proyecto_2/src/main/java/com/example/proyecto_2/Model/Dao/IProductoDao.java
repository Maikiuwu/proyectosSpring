package com.example.proyecto_2.Model.Dao;

import java.util.List;

import com.example.proyecto_2.Model.Entities.Producto;

public interface IProductoDao {

    public List<Producto> findAll();
    
}
