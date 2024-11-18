package com.owl.spring.boot_security.demo.Controller;

import com.owl.spring.boot_security.demo.Models.User;
import com.owl.spring.boot_security.demo.Service.RegistrationService;
import com.owl.spring.boot_security.demo.Service.RoleService;
import com.owl.spring.boot_security.demo.Service.UserService;
import com.owl.spring.boot_security.demo.util.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RegistrationService registrationService;
    private final RoleService roleService;
    private final UserValidator userValidator;

    public AuthController(UserService usersService, UserValidator userValidator, RoleService roleService,
                          RegistrationService registrationService) {
        this.registrationService = registrationService;
        this.userValidator = userValidator;
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public String loginPage(){
        return "/auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user){
        return "/auth/registration";
    }

    @PostMapping("/registration")
    public String registrationPerform(@ModelAttribute("user") @Valid User user,
                                      BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/auth/registration";
        } else {
            user.setRoles(roleService.findByName("ROLE_USER"));
            registrationService.registration(user);
            return "redirect:/auth/success_registration";
        }
    }

    @GetMapping("/success_registration")
    public String successRegistrationPage(){
        return "/auth/success_registration";
    }
}
