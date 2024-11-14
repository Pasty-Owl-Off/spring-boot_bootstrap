package com.owl.spring.boot_security.demo.DAO;

import com.owl.spring.boot_security.demo.Models.User;

import java.util.List;

public interface UserDAO {
    void add(User user);
    List<User> list();
    void remove(long id);
    User findById(long id);
    List<User> findByUsername(String username);
    void update(User userNew);
    List<User> findByEmail(String email);
}
