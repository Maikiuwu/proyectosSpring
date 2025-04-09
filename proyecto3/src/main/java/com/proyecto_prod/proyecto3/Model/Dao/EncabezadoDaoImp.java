package com.proyecto_prod.proyecto3.Model.Dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto_prod.proyecto3.Model.Entities.Encabezado;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class EncabezadoDaoImp implements IEncabezadoDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<Encabezado> findAll() {
        return em.createQuery("from Encabezado", Encabezado.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Encabezado findOne(Long id) {
        return em.find(Encabezado.class, id);
    }

    @Override
    @Transactional
    public void save(Encabezado encabezado) {
        if (encabezado.getId() != null && encabezado.getId() > 0) {
            em.merge(encabezado);
        } else {
            em.persist(encabezado);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Encabezado encabezado = findOne(id);
        if (encabezado != null) {
            em.remove(encabezado);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Encabezado> findByClienteId(Long clienteId) {
        return em.createQuery("from Encabezado e where e.cliente.id = :clienteId", Encabezado.class)
                .setParameter("clienteId", clienteId)
                .getResultList();
    }
}