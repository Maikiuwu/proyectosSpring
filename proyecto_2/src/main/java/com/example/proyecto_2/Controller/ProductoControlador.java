package com.example.proyecto_2.Controller;

import java.sql.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.proyecto_2.Model.Crud.ProductoService;
import com.example.proyecto_2.Model.Dao.IProductoDao;
import com.example.proyecto_2.Model.Entities.Producto;

//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;


@Controller
public class ProductoControlador {

    @Autowired
    private IProductoDao ProductoDao;

    @GetMapping("/Producto")
    public String Productos(Model model){

        model.addAttribute("titulo", "Listado de Productos");
        model.addAttribute("productos", ProductoDao.findAll());
        return "Producto";
    }

    
    @GetMapping("/crear/{id}/{nombre}/{cantidad}/{precio_venta}/{fecha}")
    public String CRUD(@PathVariable Long id, @PathVariable String nombre, @PathVariable String cantidad, @PathVariable String precio_venta, @PathVariable Date fecha) {

        Producto Datos = new Producto(id, nombre, cantidad, precio_venta, fecha);

        ProductoService servicio = new ProductoService();
        servicio.Crear(Datos);

        return "ProductoCRUD";
    }
    
    
    
}
