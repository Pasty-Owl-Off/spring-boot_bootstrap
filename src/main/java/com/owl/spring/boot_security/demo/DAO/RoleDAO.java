package com.owl.spring.boot_security.demo.DAO;

import com.owl.spring.boot_security.demo.Models.Role;

import java.util.List;
import java.util.Set;

public interface RoleDAO {
    Role findById(int id);
    void remove(int id);
    void add(Role role);
    Set<Role> findByName(String name);
    List<Role> listRole();
}
