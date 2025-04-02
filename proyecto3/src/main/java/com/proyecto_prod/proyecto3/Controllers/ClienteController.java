package com.proyecto_prod.proyecto3.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.proyecto_prod.proyecto3.Model.Entities.Cliente;
import com.proyecto_prod.proyecto3.Model.Dao.IClienteDao;

import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private IClienteDao clienteDao;

    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String email, @RequestParam String password , RedirectAttributes flash) {
        // Buscar cliente por email
        Cliente cliente = clienteDao.findByEmail(email);
        
        // Verificar si el cliente existe y la contraseña es correcta
        if (cliente != null && cliente.getPassword().equals(password)) {
            // Si la autenticación es exitosa, redirigir al home con el ID en la URL
            return "redirect:/cliente/home/" + cliente.getId();
        } else {
            // Si la autenticación falla, mostrar mensaje de error
            flash.addFlashAttribute("error", "El usuario no está registrado o las credenciales son incorrectas");
            return "redirect:/cliente/login";
        }
    }

    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "registro";
    }

    @PostMapping("/registro")
    public String procesarRegistro(@Valid Cliente cliente, BindingResult result, Model model, RedirectAttributes flash) {
        
        if (result.hasErrors()) {
            return "registro";
        }
        
        // Verificar si el email ya está registrado
        if (clienteDao.findByEmail(cliente.getEmail()) != null) {
            flash.addFlashAttribute("error", "El email ya está registrado");
            return "redirect:/cliente/registro";
        }
        
        try {
            clienteDao.save(cliente);
            flash.addFlashAttribute("success", "¡Registro exitoso! Por favor inicie sesión");
            return "redirect:/cliente/login";
        } catch (Exception e) {
            flash.addFlashAttribute("error", "Error al registrar: " + e.getMessage());
            return "redirect:/cliente/registro";
        }
    }
    @GetMapping("/home/{id}")
    public String home(@PathVariable Long id, Model model, RedirectAttributes flash) {
        Cliente cliente = clienteDao.findOne(id);
        if (cliente == null) {
            flash.addFlashAttribute("error", "El cliente no existe");
            return "redirect:/cliente/login";
        }
        model.addAttribute("cliente", cliente);
        return "Home"; // Cambiado de "home" a "Home"
    }
}