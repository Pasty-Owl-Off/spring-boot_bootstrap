package com.owl.spring.boot_security.demo.Service;

import com.owl.spring.boot_security.demo.DAO.RoleDAO;
import com.owl.spring.boot_security.demo.Models.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;


@Service
public class RoleServiceImpl implements RoleService{
    private final RoleDAO roleDAO;

    public RoleServiceImpl(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    @Transactional
    public void add(Role role) {
        roleDAO.add(role);
    }

    @Override
    @Transactional
    public void remove(int id) {
        roleDAO.remove(id);
    }

    @Override
    @Transactional
    public Role findById(int id) {
        return roleDAO.findById(id);
    }

    @Override
    @Transactional
    public Set<Role> findByName(String name) {
        return roleDAO.findByName(name);
    }

    @Override
    @Transactional
    public List<Role> listRole() {
        return roleDAO.listRole();
    }

}
