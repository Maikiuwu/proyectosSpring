package com.example.proyecto_2.Model.Crud;

import org.springframework.data.repository.CrudRepository;

import com.example.proyecto_2.Model.Entities.Producto;


public interface InterfaceCRUD extends CrudRepository<Producto, Long>{


}
