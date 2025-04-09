package com.proyecto_prod.proyecto3.Model.Dao;

import java.util.List;
import com.proyecto_prod.proyecto3.Model.Entities.Encabezado;

public interface IEncabezadoDao {
    public List<Encabezado> findAll();
    public Encabezado findOne(Long id);
    public void save(Encabezado encabezado);
    public void delete(Long id);
    public List<Encabezado> findByClienteId(Long clienteId);
}