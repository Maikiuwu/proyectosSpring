package com.example.proyecto_2.Model.Dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import com.example.proyecto_2.Model.Entities.Cliente;

@Repository
public class ClienteDaoImp implements IClienteDao{

    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    @Override
    public List<Cliente> findAll(){
        return em.createQuery("from Cliente").getResultList();
    }
    
}
