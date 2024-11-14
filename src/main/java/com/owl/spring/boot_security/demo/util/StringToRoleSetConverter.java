package com.owl.spring.boot_security.demo.util;

import com.owl.spring.boot_security.demo.Models.Role;
import com.owl.spring.boot_security.demo.Service.RoleService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class StringToRoleSetConverter implements Converter<String, Role> {
    private final RoleService roleService;

    public StringToRoleSetConverter(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public Role convert(String name) {
        return roleService.findByName(name).iterator().next();
    }
}
