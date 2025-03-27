package com.proyecto_prod.proyecto3.Model.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto_prod.proyecto3.Model.Entities.Producto;

@Service  // ayuda a organizar la lógica de la aplicación separándola en controladores y capas de acceso de datos
public class ProductoDaoImp {

    @Autowired       
    private IProductoDao productoDao;

    public List<Producto> findAll() {
        return (List<Producto>) productoDao.findAll();
    }

    public Producto findOne(Long id) {
        Optional<Producto> opt = productoDao.findById(id);
        return opt.orElse(null);
    }
    
    public Producto save(Producto producto) {
        
        Optional<Producto> existente = productoDao.findByNombre(producto.getNombre());
        if (existente.isPresent() && (producto.getId() == null || !existente.get().getId().equals(producto.getId()))) { // isPresent() básicamente solo ejecuta la acción del Optional si no está vacío y se presenta algún dato
            throw new IllegalArgumentException("Ya existe un producto con el nombre: " + producto.getNombre());
        }   
        return productoDao.save(producto);
    }

    public void delete(Long id) {
        productoDao.deleteById(id);
    }
}
