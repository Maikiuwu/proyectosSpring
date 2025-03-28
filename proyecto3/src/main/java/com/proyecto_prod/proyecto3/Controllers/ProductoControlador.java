package com.proyecto_prod.proyecto3.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.proyecto_prod.proyecto3.Model.Entities.Producto;

import jakarta.validation.Valid;

import com.proyecto_prod.proyecto3.Model.Dao.ProductoDaoImp;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/producto")

public class ProductoControlador {

    @Autowired
    private ProductoDaoImp productoDaoImp;

    @GetMapping("/listar")
    public String listar(Model model) {
        model.addAttribute("productos", productoDaoImp.findAll());
        return "listar";
    }

    @GetMapping("/form")
    public String crear(Model model) {
        model.addAttribute("producto", new Producto());
        return "form"; 
    }
/* 
    @PostMapping("/form")
    public String guardar(@Valid Producto producto, RedirectAttributes flash) {
        try {
            productoDaoImp.save(producto);
            flash.addFlashAttribute("success", "Producto guardado con éxito!"); 
        } catch (IllegalArgumentException e) {
            // Si llega aquí, significa que hay un nombre duplicado
            flash.addFlashAttribute("error", e.getMessage());
            return "redirect:/producto/form";
        }
        return "redirect:/producto/listar";
    }*/

@PostMapping("/form")
public String guardar(@Valid Producto producto, BindingResult result, RedirectAttributes flash) {
    if (result.hasErrors()) {
        return "form";  // Se retorna al formulario mostrando los errores
    }
    try {
        productoDaoImp.save(producto);
        flash.addFlashAttribute("success", "Producto guardado con éxito!");
    } catch (IllegalArgumentException e) {
        flash.addFlashAttribute("error", e.getMessage());
        return "redirect:/producto/form";
    }
    return "redirect:/producto/listar";
}


    

    @GetMapping("/form/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes flash) {
        Producto producto = productoDaoImp.findOne(id);
        if (producto == null) {
            flash.addFlashAttribute("error", "El producto no existe");
            return "redirect:/producto/listar";
        }
        model.addAttribute("producto", producto);
        return "form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        productoDaoImp.delete(id);
        flash.addFlashAttribute("success", "Producto eliminado con éxito"); //flash.addFlashAttribute, permite enviar mensajes entre controladores y vistas por http, model dura solo por una pantalla y en otra nueva pierde los datos
        return "redirect:/producto/listar";
    }
}
