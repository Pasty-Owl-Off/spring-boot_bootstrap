package com.owl.spring.boot_security.demo.Controller;

import com.owl.spring.boot_security.demo.Models.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UsersController {

	@GetMapping(value = "/user_information")
	public String profile(@AuthenticationPrincipal User user, Model model) {
		model.addAttribute("user", user);
		return "/user/user_information";
	}
}
