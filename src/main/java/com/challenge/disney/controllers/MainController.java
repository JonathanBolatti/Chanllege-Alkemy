package com.challenge.disney.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Jonathan
 */
@Controller
@RequestMapping
public class MainController {

	@GetMapping("/")
	public String inicio() {
		return "index.html";
	}
	
	//Permitir acceso al panel de administracion solo al administrador
	//@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/panel-admin")
	public String login() {
		return "panel-admin.html";
	}

}
