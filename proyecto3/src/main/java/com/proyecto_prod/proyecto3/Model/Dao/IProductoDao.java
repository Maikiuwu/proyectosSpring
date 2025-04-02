package com.proyecto_prod.proyecto3.Model.Dao;

import com.proyecto_prod.proyecto3.Model.Entities.Producto;
import java.util.List;


public interface IProductoDao {
    public List<Producto> findAll();
    public Producto findOne(Long id);
    public void save(Producto producto);
    public void delete(Long id);
    public List<Producto> findByVendorId(Long vendorId);
}