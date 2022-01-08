package com.challenge.disney.controllers;

import com.challenge.disney.entities.User;
import com.challenge.disney.exception.ErrorService;
import com.challenge.disney.services.UserService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Jonathan
 */
@Controller
@RequestMapping("/auth")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/register")
	public String userRegister(Model model, @RequestParam(required = false) String id) {

		System.out.println(id);
		if (id != null) {
			Optional<User> optional = userService.findById(id);

			if (optional.isPresent()) {
				User user = optional.get();

				model.addAttribute("user", user);
			} else {
				return "redirect:/user/list";
			}
		} else {
			model.addAttribute("user", new User());
		}

		return "register.html";

	}

		
	//listar usuarios Registrados
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/list")
	public String userList(Model model, @RequestParam(required = false) String query) {
		if (query != null) {
			model.addAttribute("users", userService.listAllByQ(query));
		} else {
			model.addAttribute("users", userService.listAll());
		}
		return "user-list.html";
	}
	
	
	//Metodo para registrar un usuario
	@PostMapping("/register")
public String registeredUser(Model model, @ModelAttribute User user, RedirectAttributes redirectAttributes) throws Exception, ErrorService {
		try {
			userService.save(user); 
		} catch (ErrorService errorService) {
			model.addAttribute("error", errorService.getMessage());
			model.addAttribute("user",user); 
			return "register.html";
		}

		/* url a la que redirecciono despues de registrar un usuario */
		return "redirect:/";

	}

	//Metodo para Eliminar un Usuario desde Rol Administrador 
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/delete")
	public String userDelete(@RequestParam(required = true) String id, Model model) {
		try {
			userService.deleteById(id);
			model.addAttribute("user", userService.listAll());
		} catch (ErrorService ex) {
			ex.getMessage();
		}
		return "redirect:/auth/list";
	}

}
