package com.owl.spring.boot_security.demo;

import com.owl.spring.boot_security.demo.Models.Role;
import com.owl.spring.boot_security.demo.Models.User;
import com.owl.spring.boot_security.demo.Service.RegistrationService;
import com.owl.spring.boot_security.demo.Service.RoleServiceImpl;
import com.owl.spring.boot_security.demo.Service.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.HashSet;

@SpringBootApplication
public class SpringBootSecurityDemoApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringBootSecurityDemoApplication.class, args);

//		Код для теста
		RegistrationService registrationService = context.getBean(RegistrationService.class);
		UserServiceImpl userService = context.getBean(UserServiceImpl.class);
		RoleServiceImpl roleService = context.getBean(RoleServiceImpl.class);
		Role adminRole = new Role(1, "ROLE_ADMIN");
		Role userRole = new Role(2, "ROLE_USER");
		User admin1 = new User("Name1", "Surname1", (byte) 11,
				"email1@gmail.com", "Admin1", "Admin1", new HashSet<Role>(Arrays.asList(adminRole, userRole)));
		User user1 = new User("Name2", "Surname2", (byte) 22,
				"email2@gmail.com", "User1", "User1", new HashSet<Role>(Arrays.asList(userRole)));

		if (roleService.findById(adminRole.getId()) == null) {
			roleService.add(adminRole);
		}

		if (roleService.findById(userRole.getId()) == null) {
			roleService.add(userRole);
		}

		if (userService.findByUsername(admin1.getUsername()).isEmpty()) {
			registrationService.registration(admin1);
		}

		if (userService.findByUsername(user1.getUsername()).isEmpty()) {
			registrationService.registration(user1);
		}
	}
}
