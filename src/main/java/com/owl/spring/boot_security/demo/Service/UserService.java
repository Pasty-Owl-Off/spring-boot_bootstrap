package com.owl.spring.boot_security.demo.Service;

import com.owl.spring.boot_security.demo.Models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    void add(User user);
    List<User> list();
    void remove(long id);
    User findById(long id);
    List<User> findByUsername(String username);
}
