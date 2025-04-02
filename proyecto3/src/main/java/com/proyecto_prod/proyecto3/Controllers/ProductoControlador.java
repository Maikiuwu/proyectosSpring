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

import jakarta.servlet.http.HttpServletRequest;
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
    
    // Estructura en memoria para almacenar carritos por cliente
    private Map<Long, List<Detalles>> carritoMap = new HashMap<>();

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

    // Método auxiliar para verificar si existe un producto con el mismo nombre para un proveedor
    private Producto findProductoByNombreAndVendorId(String nombre, Long vendorId) {
        List<Producto> productos = productoDaoImp.findByVendorId(vendorId);
        for (Producto p : productos) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }

    @PostMapping("/form")
    public String guardar(@Valid Producto producto, BindingResult result, 
                          @RequestParam("clienteId") Long clienteId, 
                          Model model, RedirectAttributes flash,
                          HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Producto");
            model.addAttribute("clienteId", clienteId);
            return "form";
        }
        
        try {
            // Verificar si es una edición (tiene ID) o un nuevo producto
            if (producto.getId() == null) {
                // Es un nuevo producto, verificar si ya existe uno con el mismo nombre para este proveedor
                Producto existente = findProductoByNombreAndVendorId(producto.getNombre(), clienteId);
                if (existente != null) {
                    // Ya existe un producto con este nombre para este proveedor
                    // Preguntar si desea editar el existente
                    model.addAttribute("productoExistente", existente);
                    model.addAttribute("nuevoProducto", producto);
                    model.addAttribute("clienteId", clienteId);
                    return "confirmar-edicion";
                }
            }
            
            // Asignar el id del cliente (vendedor) al producto antes de guardarlo
            producto.setId_vendedor(clienteId);
            productoDaoImp.save(producto);
            flash.addFlashAttribute("success", producto.getId() == null ? 
                    "Producto agregado correctamente!" : "Producto actualizado correctamente!");
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
        
        // Verificar que el producto pertenezca al vendedor
        if (!producto.getId_vendedor().equals(clienteId)) {
            flash.addFlashAttribute("error", "No tiene permisos para editar este producto");
            return "redirect:/producto/listar/" + clienteId;
        }
        
        model.addAttribute("producto", producto);
        model.addAttribute("clienteId", clienteId);
        return "form";
    }

    // Método para confirmar la edición de un producto existente
    @PostMapping("/confirmar-edicion")
    public String confirmarEdicion(@RequestParam("productoId") Long productoId,
                                  @RequestParam("clienteId") Long clienteId,
                                  @RequestParam("nombre") String nombre,
                                  @RequestParam("precio") Double precio,
                                  @RequestParam("cantidad") Integer cantidad,
                                  RedirectAttributes flash) {
        Producto producto = productoDaoImp.findOne(productoId);
        if (producto == null) {
            flash.addFlashAttribute("error", "El producto no existe");
            return "redirect:/producto/listar/" + clienteId;
        }
        
        // Actualizar los datos del producto existente
        producto.setNombre(nombre);
        producto.setPrecio(precio);
        producto.setCantidad(cantidad);
        
        productoDaoImp.save(producto);
        flash.addFlashAttribute("success", "Producto actualizado correctamente!");
        return "redirect:/producto/listar/" + clienteId;
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, 
                           @RequestParam Long clienteId, 
                           RedirectAttributes flash) {
        Producto producto = productoDaoImp.findOne(id);
        if (producto == null) {
            flash.addFlashAttribute("error", "El producto no existe");
            return "redirect:/producto/listar/" + clienteId;
        }
        
        // Verificar que el producto pertenezca al vendedor
        if (!producto.getId_vendedor().equals(clienteId)) {
            flash.addFlashAttribute("error", "No tiene permisos para eliminar este producto");
            return "redirect:/producto/listar/" + clienteId;
        }
        
        productoDaoImp.delete(id);
        flash.addFlashAttribute("success", "Producto eliminado con éxito");
        return "redirect:/producto/listar/" + clienteId;
    }
    
    // Endpoint para ver el carrito
    @GetMapping("/carrito")
    public String verCarrito(@RequestParam Long clienteId, Model model) {
        // Verificar que el usuario no sea proveedor
        Cliente cliente = clienteDaoImp.findOne(clienteId);
        if (cliente == null || "Proveedor".equals(cliente.getRol())) {
            return "redirect:/producto/listar/" + clienteId;
        }
        
        List<Detalles> carrito = carritoMap.get(clienteId);
        if (carrito == null) {
            carrito = new ArrayList<>();
            carritoMap.put(clienteId, carrito);
        }
        
        // Calcular el total general
        double totalCompra = 0;
        for (Detalles detalle : carrito) {
            totalCompra += detalle.getSubtotal();
        }
        
        model.addAttribute("carrito", carrito);
        model.addAttribute("clienteId", clienteId);
        model.addAttribute("totalCompra", totalCompra);
        return "Carrito";
    }

    // Método para agregar producto al carrito con cantidad
    @PostMapping("/agregar/{id}")
    public String agregarConCantidad(@PathVariable Long id, 
                                 @RequestParam Long clienteId,
                                 @RequestParam("cantidad_ingresada") Integer cantidadIngresada,
                                 RedirectAttributes flash) {
        // Verificar que el usuario no sea proveedor
        Cliente cliente = clienteDaoImp.findOne(clienteId);
        if (cliente == null || "Proveedor".equals(cliente.getRol())) {
            return "redirect:/producto/listar/" + clienteId;
        }
        
        Producto producto = productoDaoImp.findOne(id);
        if (producto == null) {
            flash.addFlashAttribute("error", "Producto no encontrado");
            return "redirect:/producto/listar/" + clienteId;
        }
        
        // Verificar si hay suficiente stock
        if (producto.getCantidad() < cantidadIngresada) {
            flash.addFlashAttribute("error", "No hay suficiente stock disponible. Stock actual: " + producto.getCantidad());
            return "redirect:/producto/listar/" + clienteId;
        }
        
        // Obtener o crear el carrito
        List<Detalles> carrito = carritoMap.get(clienteId);
        if (carrito == null) {
            carrito = new ArrayList<>();
            carritoMap.put(clienteId, carrito);
        }
        
        // Buscar si el producto ya está en el carrito
        boolean productoEncontrado = false;
        for (Detalles detalle : carrito) {
            if (detalle.getProducto().getId().equals(id)) {
                // Si ya existe, actualizar la cantidad
                detalle.setCantidad(detalle.getCantidad() + cantidadIngresada);
                detalle.setSubtotal(detalle.getCantidad() * detalle.getPrecioUnitario());
                productoEncontrado = true;
                break;
            }
        }
        
        // Si no existe en el carrito, agregar nuevo detalle
        if (!productoEncontrado) {
            Detalles detalle = new Detalles();
            detalle.setProducto(producto);
            detalle.setCantidad(cantidadIngresada);
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setSubtotal(cantidadIngresada * producto.getPrecio());
            carrito.add(detalle);
        }
        
        // Actualizar el stock del producto
        producto.setCantidad(producto.getCantidad() - cantidadIngresada);
        productoDaoImp.save(producto);
        
        flash.addFlashAttribute("success", "Producto agregado al carrito");
        return "redirect:/producto/carrito?clienteId=" + clienteId;
    }
    
    // Endpoint para eliminar un producto del carrito
    @GetMapping("/eliminarDelCarrito/{index}")
    public String eliminarDelCarrito(@PathVariable int index,
                                   @RequestParam Long clienteId,
                                   RedirectAttributes flash) {
        // Verificar que el usuario no sea proveedor
        Cliente cliente = clienteDaoImp.findOne(clienteId);
        if (cliente == null || "Proveedor".equals(cliente.getRol())) {
            return "redirect:/producto/listar/" + clienteId;
        }
        
        List<Detalles> carrito = carritoMap.get(clienteId);
        if (carrito != null && index >= 0 && index < carrito.size()) {
            Detalles detalle = carrito.get(index);
            
            // Devolver cantidad al stock
            Producto producto = detalle.getProducto();
            producto.setCantidad(producto.getCantidad() + detalle.getCantidad());
            productoDaoImp.save(producto);
            
            // Eliminar del carrito
            carrito.remove(index);
            flash.addFlashAttribute("success", "Producto eliminado del carrito");
        } else {
            flash.addFlashAttribute("error", "Producto no encontrado en el carrito");
        }
        
        return "redirect:/producto/carrito?clienteId=" + clienteId;
    }
    
    // Endpoint para finalizar la compra y generar la factura
    @GetMapping("/factura")
    public String factura(@RequestParam Long clienteId, Model model, RedirectAttributes flash) {
        Cliente cliente = clienteDaoImp.findOne(clienteId);
        if (cliente == null) {
            flash.addFlashAttribute("error", "Cliente no encontrado");
            return "redirect:/producto/listar/" + clienteId;
        }
        
        // Verificar que el usuario no sea proveedor
        if ("Proveedor".equals(cliente.getRol())) {
            return "redirect:/producto/listar/" + clienteId;
        }
        
        List<Detalles> carrito = carritoMap.get(clienteId);
        if (carrito == null || carrito.isEmpty()) {
            flash.addFlashAttribute("error", "No hay productos en el carrito");
            return "redirect:/producto/carrito?clienteId=" + clienteId;
        }
        
        // Crear el encabezado de la factura
        Encabezado encabezado = new Encabezado();
        encabezado.setCliente(cliente);
        
        // Calcular el total
        double total = 0;
        for (Detalles detalle : carrito) {
            encabezado.addDetalle(detalle);
            total += detalle.getSubtotal();
        }
        encabezado.setTotal(total);
        
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