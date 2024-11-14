package com.owl.spring.boot_security.demo.DAO;

import com.owl.spring.boot_security.demo.Models.Role;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDAOImpl implements RoleDAO{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(Role role) {
        entityManager.persist(role);
    }

    @Override
    public void remove(int id) {
        entityManager.remove(findById(id));
    }

    @Override
    public Role findById(int id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public Set<Role> findByName(String name) {
        return new HashSet<>(entityManager.createQuery("SELECT r FROM Role r WHERE r.name = :name", Role.class)
                .setParameter("name", name).getResultList());
    }

    @Override
    public List<Role> listRole() {
        return entityManager.createQuery("SELECT r FROM Role r", Role.class).getResultList();
    }
}
