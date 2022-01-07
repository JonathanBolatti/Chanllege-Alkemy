package com.challenge.disney.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Jonathan
 */

	@Controller
	@RequestMapping("/auth")
	public class LoginController {

		@GetMapping("/login")
		public String login(Model model, @RequestParam(required = false) String error, @RequestParam(required = false) String email,
				@RequestParam(required = false) String logout) {
			if (error != null) {
				model.addAttribute("error", "El usuario o la contraseña son incorrectos, favor verifique!");
			}
			if (email != null) {
				model.addAttribute("email", email);
			}
			return "login";
		}

	}
