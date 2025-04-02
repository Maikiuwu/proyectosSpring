package com.proyecto_prod.proyecto3.Model.Dao;

import com.proyecto_prod.proyecto3.Model.Entities.Producto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ProductoDaoImp implements IProductoDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        return entityManager.createQuery("from Producto", Producto.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findOne(Long id) {
        return entityManager.find(Producto.class, id);
    }

    @Override
    @Transactional
    public void save(Producto producto) {
        if (producto.getId() != null && producto.getId() > 0) {
            entityManager.merge(producto);
        } else {
            entityManager.persist(producto);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        entityManager.remove(findOne(id));
    }

    @Transactional(readOnly = true)
    public List<Producto> findByVendorId(Long vendorId) {
        return entityManager.createQuery(
                "SELECT p FROM Producto p WHERE p.id_vendedor = :vendorId",
                Producto.class)
                .setParameter("vendorId", vendorId)
                .getResultList();
    }
}