package com.example.proyecto_2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.proyecto_2.Model.Dao.IProductoDao;

@Controller
public class ProductoControlador {

    @Autowired
    private IProductoDao ProductoDao;

    @GetMapping("/Producto")
    public String listar(Model model){

        model.addAttribute("titulo", "Listado de Productos");
        model.addAttribute("productos", ProductoDao.findAll());
        return "Producto";
    }
    
}
