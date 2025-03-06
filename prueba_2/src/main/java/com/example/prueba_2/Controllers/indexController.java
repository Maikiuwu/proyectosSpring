package com.example.prueba_2.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.prueba_2.entities.Usuario;

import java.util.ArrayList;
import java.util.List;


@Controller
public class indexController { 

    @GetMapping({"/"})
    public String index(Model model) {
        model.addAttribute("title", "hello Springboot");

        return "index";
    }
    @GetMapping({"/listar"})
    public String listar(Model model){

        List <Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario("Andres", "Henao", "wuuuuuuu@gmail.com"));
        usuarios.add(new Usuario("sebas", "Forero", "waos@gmail,com"));
        usuarios.add(new Usuario("maikol", "Gay", "popo@gmail.com"));

        model.addAttribute("Usuarios", usuarios);
        model.addAttribute("titulo", "Listado de productos");
        
        return "listar";
    }

}

