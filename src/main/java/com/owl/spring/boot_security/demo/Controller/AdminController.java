package com.owl.spring.boot_security.demo.Controller;

import com.owl.spring.boot_security.demo.Models.User;
import com.owl.spring.boot_security.demo.Service.RegistrationService;
import com.owl.spring.boot_security.demo.Service.UserService;
import com.owl.spring.boot_security.demo.util.UserValidator;
import org.springframework.boot.Banner;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final UserValidator userValidator;
    private final RegistrationService registrationService;


    public AdminController(UserService usersService, UserValidator userValidator,
                           RegistrationService registrationService) {
        this.userService = usersService;
        this.userValidator = userValidator;
        this.registrationService = registrationService;
    }

    @GetMapping(value = "/")
    public String printUsersTable(@AuthenticationPrincipal User authUser, Model model) {
        User newUser = new User();
        setupModelAttributes(model, authUser, newUser, newUser);
        return "/admin/index";
    }

//    @GetMapping(value = "/new")
//    public String newUser(@ModelAttribute("user") User user, Model model) {
//        return "/admin/new";
//    }

    @PostMapping(value = "/new")
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/admin/new";
        }
        registrationService.registration(user);
        return "redirect:/admin/";
    }

    @GetMapping(value = "/edit")
    public String updateUser(@RequestParam("id") long id,
                             @AuthenticationPrincipal User authUser,
                             Model model) {
        User newUser = new User();
        User userEdit = userService.findById(id);
        userEdit.setPasswordConfirm(userEdit.getPassword());
        setupModelAttributes(model, authUser, newUser, userEdit);
        return "admin/index";
    }

    @PostMapping(value = "/update")
    public String editUser(@ModelAttribute("userEdit") @Valid User user, BindingResult bindingResult,
                           @AuthenticationPrincipal User authUser, Model model
    ) {
        user.setPasswordConfirm(user.getPassword());
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            User newUser = new User();
            setupModelAttributes(model, authUser, newUser, user);
            return "/admin/index";
        }
        registrationService.update(user);
        return "redirect:/admin/";
    }

    @GetMapping(value = "/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.remove(id);
        return "redirect:/admin/";
    }

    @GetMapping(value = "/user_information")
    public String profile(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "/admin/user_information";
    }

    private void setupModelAttributes(Model model, User authUser, User newUser, User userEdit) {
        List<User> userList = userService.list();
        model.addAttribute("users", userList);
        model.addAttribute("authUser", authUser);
        model.addAttribute("newUser", newUser);
        model.addAttribute("userEdit", userEdit);
    }
}
