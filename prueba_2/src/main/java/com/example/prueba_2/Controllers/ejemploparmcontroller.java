package com.example.prueba_2.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/params")
public class ejemploparmcontroller {

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("titulo", "Recibir parametros de la ruta (@PathVariable)");
        return "parametros/index";
    }

    @GetMapping("/string")
    public String param(Model model, @RequestParam String texto){
        model.addAttribute("titulo", "Recibir parametros del request (@RequestParam)");
        model.addAttribute("resultado", "El texto enviado es: " + texto);
        return "parametros/ver";
    }
    @GetMapping("/mix-params")
    public String variables(@RequestParam (name="saludo",required = true, defaultValue = "dddd") String texto,
                            @RequestParam Integer num, Model model){    //(name="saludo",required = false, defaultValue = "dddd") otra forma de pedir los datos por url.
        model.addAttribute("titulo", "Recibir parametros del request HTTP GET - URL");
        model.addAttribute("resultado", "El texto enviado es: " + texto+ " y el numero es: "+ num); //para poderlo ver en la url tiene que ser el texto y una & para poder tomar el numero
        return "parametros/ver";
    }

}
