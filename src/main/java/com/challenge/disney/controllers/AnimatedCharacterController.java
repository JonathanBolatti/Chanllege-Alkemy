package com.challenge.disney.controllers;

import com.challenge.disney.entities.AnimatedCharacter;
import com.challenge.disney.exception.ErrorService;
import com.challenge.disney.services.AnimatedCharacterService;
import com.challenge.disney.services.MovieOrSerieService;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Jonathan
 */
@Controller
@RequestMapping("/animated")
public class AnimatedCharacterController {

	@Autowired
	private AnimatedCharacterService animatedCharacterService;

	@Autowired
	private MovieOrSerieService movieOrSerieService;

	@GetMapping("/characters")
	public String listCharacters(Model model) {
		model.addAttribute("characters", animatedCharacterService.listAll());
		return "characters.html";

	}


	/////DEVUELVE UNA LISTA DE PERSONAJES POR NOMBRE////
	//@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/list")
	public String allList(Model model, @RequestParam(required = false) String query) {
		if (query != null) {
			model.addAttribute("characters", animatedCharacterService.findByName(query));
		} else {
			model.addAttribute("characters", animatedCharacterService.listAll());
		}
		return "characters-list";
	}

	//@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/form")
	public String createCharacter(Model model, @RequestParam(required = false) String id) {
		if (id != null) {
			Optional<AnimatedCharacter> optional = animatedCharacterService.findById(id);
			if (optional.isPresent()) {
				model.addAttribute("personaje", optional.get());
			} else {
				return "redirect:/characters/list";
			}
		} else {
			model.addAttribute("personaje", new AnimatedCharacter());
		}
		model.addAttribute("peliculas", movieOrSerieService.listAll());
		return "character-form";
	}

	//@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/save")
	public String saveCharacter(Model model, RedirectAttributes redirectAttributes, @RequestParam(required = false) MultipartFile imagen, AnimatedCharacter animatedCharacter) throws IOException, ErrorService {
		try {
			animatedCharacterService.save(animatedCharacter, imagen);
			  redirectAttributes.addFlashAttribute("success", "Personaje guardado con exito");  
		} catch (ErrorService ex) {
			ex.printStackTrace();
			model.addAttribute("error", ex.getMessage());
			model.addAttribute("personaje", animatedCharacter);
			model.addAttribute("peliculas", movieOrSerieService.listAll());
			return "character-form";
		}
		return "redirect:/animated/form";
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/delete")
	public String delete(@RequestParam(required = true) String id) {
		animatedCharacterService.deleteById(id);
		return "redirect:/animated/list";
	}

}
