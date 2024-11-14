package com.owl.spring.boot_security.demo.Service;

import com.owl.spring.boot_security.demo.Models.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    void add(Role role);
    void remove(int id);
    Role findById(int id);
    Set<Role> findByName(String name);
    List<Role> listRole();

}
