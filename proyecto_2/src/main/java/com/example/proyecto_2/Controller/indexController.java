package com.example.proyecto_2.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class indexController { 

    @GetMapping({"/"})
    public String index(Model model) {
        model.addAttribute("title", "bievenido a la pagina de inicio de proyectos de Spring con Usma :P");

        return "index";
    }

}

