package com.owl.spring.boot_security.demo.Service;

import com.owl.spring.boot_security.demo.DAO.UserDAO;
import com.owl.spring.boot_security.demo.Models.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegistrationService {
    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void registration(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.add(user);
    }

    @Transactional
    public void update(User user) {
        User userOld = userDAO.findById(user.getId());
        if (userOld != null) {
            if (!userOld.getPassword().equals(user.getPassword())) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
        }
        else {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        userDAO.update(user);
    }
}
