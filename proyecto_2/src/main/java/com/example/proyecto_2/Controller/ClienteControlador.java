package com.example.proyecto_2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import com.example.proyecto_2.Model.Dao.IClienteDao;

@Controller //pq la clase es un controlador xd
public class ClienteControlador {

    @Autowired  // inyeccion de dependencias
    private IClienteDao clienteDao;

    @GetMapping("/listar")
    public String listar(Model model){

        model.addAttribute("titulo", "Listado de Clientes");
        model.addAttribute("Clientes", clienteDao.findAll());
        return "listar";
    }
    
}
