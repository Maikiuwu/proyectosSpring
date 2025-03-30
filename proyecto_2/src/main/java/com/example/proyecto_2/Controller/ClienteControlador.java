package com.example.proyecto_2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.proyecto_2.Model.Dao.ClienteDaoImp;
import com.example.proyecto_2.Model.Entities.Cliente;

@Controller
@RequestMapping("/cliente")

public class ClienteControlador {

    @Autowired
    private ClienteDaoImp clienteDaoImp;

    @GetMapping("/clientes")
    public String listar(Model model) {
        model.addAttribute("clientes", clienteDaoImp.findAll());
        return "clientes";
    }

    @GetMapping("/form_cliente")
    public String crear(Model model) {
        model.addAttribute("clientes", new Cliente());
        return "form_cliente";
    }

    @PostMapping("/form_cliente")
    public String guardar(Cliente cliente, RedirectAttributes flash) {
        try {
            clienteDaoImp.save(cliente);
            flash.addFlashAttribute("success", "Cliente guardado con éxito!");
        } catch (IllegalArgumentException e) {
            // Si llega aquí, significa que hay un nombre duplicado
            flash.addFlashAttribute("error", e.getMessage());
            return "redirect:/cliente/form_cliente";
        }
        return "redirect:/cliente/clientes";
    }

    @GetMapping("/form_cliente/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes flash) {
        Cliente cliente = clienteDaoImp.findOne(id);
        if (cliente == null) {
            flash.addFlashAttribute("error", "El cliente no existe");
            return "redirect:/cliente/clientes";
        }
        model.addAttribute("clientes", cliente);
        return "form_cliente";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        clienteDaoImp.delete(id);
        flash.addFlashAttribute("success", "Producto eliminado con éxito");
        return "redirect:/cliente/clientes";
    }

}