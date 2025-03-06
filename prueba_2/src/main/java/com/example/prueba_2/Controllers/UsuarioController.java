package com.example.prueba_2.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioController {

    
    @GetMapping({"/usuario"})

    public String usuario(Model model) {
        model.addAttribute("name", "Pipe");
        model.addAttribute("last_name", "Henao");
        model.addAttribute("email", "Pipe@hotmail.com");

        return "usuario";
    }
    
    
}
