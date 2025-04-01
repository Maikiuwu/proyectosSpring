package com.proyecto_prod.proyecto3.Model.Dao;

import com.proyecto_prod.proyecto3.Model.Entities.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ClienteDaoImp implements IClienteDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return em.createQuery("from Cliente", Cliente.class).getResultList();
    }

    @Override
    @Transactional
    public void save(Cliente cliente) {
        if (cliente.getId() != null && cliente.getId() > 0) {
            em.merge(cliente);
        } else {
            em.persist(cliente);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente findOne(Long id) {
        return em.find(Cliente.class, id);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Cliente cliente = findOne(id);
        if (cliente != null) {
            em.remove(cliente);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente findByEmail(String email) {
        try {
            return em.createQuery("from Cliente c where c.email = :email", Cliente.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}