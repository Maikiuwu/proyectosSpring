package com.example.proyecto_2.Model.Dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.proyecto_2.Model.Entities.Cliente;

@Service
public class ClienteDaoImp{
    
    @Autowired       
    private IClienteDao clienteDao;

    public List<Cliente> findAll() {
        return (List<Cliente>) clienteDao.findAll();
    }

    public Cliente findOne(Long id) {
        Optional<Cliente> opt = clienteDao.findById(id);
        return opt.orElse(null);
    }
    
    public Cliente save(Cliente cliente) {
        
        Optional<Cliente> existente = clienteDao.findByNombre(cliente.getNombre());
        if (existente.isPresent() && (cliente.getId() == null || !existente.get().getId().equals(cliente.getId()))) { //ispresent() basicamente solo ejeccuta la accion del optional si no esta vacio y se presenta algun dato
            
            throw new IllegalArgumentException("Ya existe un producto con el nombre: " + cliente.getNombre());
        }   
        return clienteDao.save(cliente);
    }

    public void delete(Long id) {
        clienteDao.deleteById(id);
    }
    
}
