package com.owl.spring.boot_security.demo.util;

import com.owl.spring.boot_security.demo.DAO.UserDAO;
import com.owl.spring.boot_security.demo.Models.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private final UserDAO userDAO;

    public UserValidator(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (!userDAO.findByUsername(user.getUsername()).isEmpty() &&
                userDAO.findByUsername(user.getUsername()).get(0).getId() != user.getId()) {
            errors.rejectValue("username", "", "This username is already used");
        }

        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            errors.rejectValue("password", "", "Password and confirm password is not equal");
        }

        if (!userDAO.findByEmail(user.getEmail()).isEmpty() &&
                userDAO.findByEmail(user.getEmail()).get(0).getId() != user.getId()) {
            errors.rejectValue("email", "", "This email is already used");
        }
    }
}
