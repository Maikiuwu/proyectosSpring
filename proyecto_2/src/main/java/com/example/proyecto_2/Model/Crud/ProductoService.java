package com.example.proyecto_2.Model.Crud;

import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.proyecto_2.Model.Entities.Producto;

@Service
public class ProductoService implements ProductoCRUD{

    @Autowired
    private InterfaceCRUD Interface;

    @Override
    public Producto Crear(Producto producto){

        return this.Interface.save(producto);

    }

    @Override
    public Boolean Eliminar(Long id){

        this.Interface.deleteById(id);
        return true;

    }

    @Override
    public Optional<Producto> Consulta(Long id){

        return this.Interface.findById(id);

    }

    @Override
    public Producto Actualizar(Long id, String nombre, String cantidad, String precio_venta, Date fecha){

        Optional<Producto> encontrar = this.Interface.findById(id);
        if(encontrar.isPresent()){

            Producto tmp = encontrar.get();
            tmp.setNombre(nombre);
            tmp.setCantidad(cantidad);
            tmp.setPrecio_venta(precio_venta);
            tmp.setFecha(fecha);
            return Crear(tmp);
            
        }

        return null;

    }

}
