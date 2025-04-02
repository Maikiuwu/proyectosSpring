package com.proyecto_prod.proyecto3.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.proyecto_prod.proyecto3.Model.Entities.Producto;
import com.proyecto_prod.proyecto3.Model.Entities.Cliente;
import com.proyecto_prod.proyecto3.Model.Entities.Detalles;
import com.proyecto_prod.proyecto3.Model.Entities.Encabezado;

import jakarta.validation.Valid;
import com.proyecto_prod.proyecto3.Model.Dao.ProductoDaoImp;
import com.proyecto_prod.proyecto3.Model.Dao.ClienteDaoImp;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
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
    
    // Estructura en memoria para almacenar carritos por cliente (no es thread-safe)
    private Map<Long, List<Producto>> carritoMap = new HashMap<>();

    @GetMapping("/listar/{id}")
    public String listar(@PathVariable Long id, Model model) {
        // Obtener información del cliente/usuario
        Cliente cliente = clienteDaoImp.findOne(id);
        if (cliente == null) {
            // Manejo del error - cliente no encontrado
            return "redirect:/error";
        }
        
        // Obtener nombres de clientes para mostrar
        Map<Long, String> clientNames = new HashMap<>();
        List<Cliente> clientes = clienteDaoImp.findAll();
        for (Cliente c : clientes) {
            clientNames.put(c.getId(), c.getNombre());
        }
        model.addAttribute("clientNames", clientNames);
        
        // Dependiendo del rol se muestran los productos correspondientes
        if ("Proveedor".equals(cliente.getRol())) {
            model.addAttribute("productos", productoDaoImp.findByVendorId(id));
            model.addAttribute("isProveedor", true);
        } else {
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
            // Asignar el id del cliente (vendedor) al producto antes de guardarlo
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
    
    // Endpoint para ver el carrito
    @GetMapping("/carrito")
    public String verCarrito(@RequestParam Long clienteId, Model model) {
        List<Producto> carrito = carritoMap.get(clienteId);
        if (carrito == null) {
            carrito = new ArrayList<>();
            carritoMap.put(clienteId, carrito);
        }
        model.addAttribute("carrito", carrito);
        model.addAttribute("clienteId", clienteId);
        return "Carrito";
    }

    // Método que agrega el producto al carrito (almacenado en el Map) y redirige a la vista Carrito
    @GetMapping("/agregar/{id}")
    public String agregar(@PathVariable Long id, @RequestParam Long clienteId, Model model, 
                          RedirectAttributes flash) {
        Producto producto = productoDaoImp.findOne(id);
        if (producto == null) {
            flash.addFlashAttribute("error", "Producto no encontrado");
            return "redirect:/producto/listar/" + clienteId;
        }
        
        List<Producto> carrito = carritoMap.get(clienteId);
        if (carrito == null) {
            carrito = new ArrayList<>();
        }
        carrito.add(producto);
        carritoMap.put(clienteId, carrito);
        flash.addFlashAttribute("success", "Producto agregado al carrito");
        return "redirect:/producto/carrito?clienteId=" + clienteId;
    }
    
    // Endpoint para finalizar la compra y mostrar la factura
// Endpoint para finalizar la compra y generar la factura
@GetMapping("/factura")
public String factura(@RequestParam Long clienteId, Model model, RedirectAttributes flash) {
    Cliente cliente = clienteDaoImp.findOne(clienteId);
    if (cliente == null) {
        flash.addFlashAttribute("error", "Cliente no encontrado");
        return "redirect:/producto/listar/" + clienteId;
    }
    
    List<Producto> carrito = carritoMap.get(clienteId);
    if (carrito == null || carrito.isEmpty()) {
        flash.addFlashAttribute("error", "No hay productos en el carrito");
        return "redirect:/producto/carrito?clienteId=" + clienteId;
    }
    
    // Crear el encabezado de la factura
    Encabezado encabezado = new Encabezado();
    encabezado.setCliente(cliente);
    
    // Para cada producto en el carrito, crea un detalle (aquí se asume cantidad = 1)
    for (Producto producto : carrito) {
        Detalles detalle = new Detalles();
        detalle.setProducto(producto);
        detalle.setCantidad(1); // O la cantidad deseada
        detalle.setPrecioUnitario(producto.getPrecio());
        // El subtotal se calcula automáticamente
        encabezado.addDetalle(detalle);
    }
    
    // Aquí podrías guardar el encabezado (y en cascada, los detalles) en la BD
    // encabezadoDaoImp.save(encabezado);
    
    // Se limpia el carrito
    carritoMap.remove(clienteId);
    
    // Se envía a la vista de factura
    model.addAttribute("cliente", cliente);
    model.addAttribute("encabezado", encabezado);
    return "Factura";
}

    
}
