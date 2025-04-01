package com.proyecto_prod.proyecto3.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.proyecto_prod.proyecto3.Model.Entities.Producto;
import com.proyecto_prod.proyecto3.Model.Entities.Cliente;
import jakarta.validation.Valid;
import com.proyecto_prod.proyecto3.Model.Dao.ProductoDaoImp;
import com.proyecto_prod.proyecto3.Model.Dao.ClienteDaoImp;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/producto")
public class ProductoControlador {
    @Autowired
    private ProductoDaoImp productoDaoImp;
    
    @Autowired
    private ClienteDaoImp clienteDaoImp;

    @GetMapping("/listar/{id}")
    public String listar(@PathVariable Long id, Model model) {
        // Get the client/user information
        Cliente cliente = clienteDaoImp.findOne(id);
        if (cliente == null) {
            // Handle error - client not found
            return "redirect:/error";
        }
        
        // Get client names for display
        Map<Long, String> clientNames = new HashMap<>();
        List<Cliente> clientes = clienteDaoImp.findAll();
        for (Cliente c : clientes) {
            clientNames.put(c.getId(), c.getNombre());
        }
        model.addAttribute("clientNames", clientNames);
        
        // Check the role and get appropriate products
        if ("Proveedor".equals(cliente.getRol())) {
            // If provider, only show their products
            model.addAttribute("productos", productoDaoImp.findByVendorId(id));
            model.addAttribute("isProveedor", true);
        } else {
            // If client, show all products
            model.addAttribute("productos", productoDaoImp.findAll());
            model.addAttribute("isProveedor", false);
        }
        
        model.addAttribute("clienteId", id);
        model.addAttribute("rol", cliente.getRol());
        
        return "listar";
    }

    @GetMapping("/form/{id}")
    public String crear(@PathVariable Long id, Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("clienteId", id);
        return "form";
    }

    @PostMapping("/form")
    public String guardar(@Valid Producto producto, BindingResult result, 
                          @RequestParam("clienteId") Long clienteId, 
                          Model model, RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Producto");
            model.addAttribute("clienteId", clienteId);
            return "form";
        }
       
        try {
            // Set the client ID to the product before saving
            producto.setId_vendedor(clienteId);
            
            productoDaoImp.save(producto);
            flash.addFlashAttribute("success", "Producto agregado correctamente!");
            return "redirect:/producto/listar/" + clienteId;
        } catch (IllegalArgumentException e) {
            flash.addFlashAttribute("error", e.getMessage());
            return "redirect:/producto/form/" + clienteId;
        }
    }
       
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes flash, 
                        @RequestParam(required = false) Long clienteId) {
        Producto producto = productoDaoImp.findOne(id);
     
        if (producto == null) {
            flash.addFlashAttribute("error", "El producto no existe");
            return "redirect:/producto/listar/" + clienteId;
        }
        model.addAttribute("producto", producto);
        model.addAttribute("clienteId", clienteId);
        return "form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, 
                          @RequestParam Long clienteId, 
                          RedirectAttributes flash) {
        productoDaoImp.delete(id);
        flash.addFlashAttribute("success", "Producto eliminado con éxito");
        return "redirect:/producto/listar/" + clienteId;
    }
}