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
        setupModelAttributes(model, authUser, newUser, newUser, newUser);
        return "/admin/index";
    }

//    @GetMapping(value = "/new")
//    public String newUser(@ModelAttribute("user") User user, Model model) {
//        return "/admin/new";
//    }

    @PostMapping(value = "/new")
    public String createUser(@ModelAttribute("user") @Valid User newUser,
                             BindingResult bindingResult) {
        userValidator.validate(newUser, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/admin/new";
        }
        registrationService.registration(newUser);
        return "redirect:/admin/";
    }

    @GetMapping(value = "/edit")
    public String editModal(@RequestParam("id") long id,
                             @AuthenticationPrincipal User authUser,
                             Model model) {
        User newUser = new User();
        User userEdit = userService.findById(id);
        userEdit.setPasswordConfirm(userEdit.getPassword());
        setupModelAttributes(model, authUser, newUser, userEdit, newUser);
        return "admin/index";
    }

    @PostMapping(value = "/update")
    public String updateUser(@ModelAttribute("userEdit") @Valid User userEdit, BindingResult bindingResult,
                           @AuthenticationPrincipal User authUser, Model model
    ) {
        userEdit.setPasswordConfirm(userEdit.getPassword());
        userValidator.validate(userEdit, bindingResult);
        if (bindingResult.hasErrors()) {
            User newUser = new User();
            setupModelAttributes(model, authUser, newUser, userEdit, newUser);
            return "/admin/index";
        }
        registrationService.update(userEdit);
        return "redirect:/admin/";
    }

    @GetMapping(value = "/remove")
    public String removeModal(@RequestParam("id") long id,
                              @AuthenticationPrincipal User authUser, Model model) {
        User userRemove = userService.findById(id);
        User newUser = new User();
        setupModelAttributes(model, authUser, newUser, newUser, userRemove);
        return "/admin/index";
    }

    @GetMapping(value = "/delete")
    public String deleteUser(@RequestParam("id") long id) {
        userService.remove(id);
        return "redirect:/admin/";
    }

    @GetMapping(value = "/user_information")
    public String profile(@AuthenticationPrincipal User authUser, Model model) {
        model.addAttribute("authUser", authUser);
        return "/admin/user_information";
    }

    private void setupModelAttributes(Model model, User authUser, User newUser, User userEdit, User userRemove) {
        List<User> userList = userService.list();
        model.addAttribute("users", userList);
        model.addAttribute("authUser", authUser);
        model.addAttribute("newUser", newUser);
        model.addAttribute("userEdit", userEdit);
        model.addAttribute("userRemove", userRemove);
    }
}
