package com.challenge.disney.controllers;

import com.challenge.disney.entities.MovieOrSerie;
import com.challenge.disney.exception.ErrorService;
import com.challenge.disney.services.GenderService;
import com.challenge.disney.services.MovieOrSerieService;
import com.challenge.disney.services.QualificationService;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/movie")
public class MovieOrSerieController {

	@Autowired
	private MovieOrSerieService movieOrSerieService;

	@Autowired
	private QualificationService qualificacionService;

	@Autowired
	private GenderService genderService;

	@GetMapping("/films")
	public String listFilm(Model model) {
		model.addAttribute("films", movieOrSerieService.listAll());
		return "films.html";

	}

	//@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/list")
	public String lista(Model model, @RequestParam(required = false) String query) {
		if (query != null) {
			model.addAttribute("peliculas", movieOrSerieService.listallByQ(query));
		} else {
			model.addAttribute("peliculas", movieOrSerieService.listAll());
		}
		return "movies-list";
	}

	@GetMapping("/list-all")
	public String listAll(Model model, @RequestParam(required = false) String query) {
		if (query != null) {
			model.addAttribute("peliculas", movieOrSerieService.listallByQ(query));
		} else {
			model.addAttribute("peliculas", movieOrSerieService.listAll());
		}
		return "movies-list-all";
	}

	//@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/form")
	public String crearPelicula(Model model, @RequestParam(required = false) String id) {
		if (id != null) {
			Optional<MovieOrSerie> optional = movieOrSerieService.findById(id);
			if (optional.isPresent()) {
				model.addAttribute("pelicula", optional.get());
			} else {
				return "redirect:/movies/list";
			}
		} else {
			model.addAttribute("pelicula", new MovieOrSerie());
		}
		model.addAttribute("calificaciones", qualificacionService.listAll());
		model.addAttribute("generos", genderService.listAll());
		return "film-form.html";
	}

	// @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/save")
	public String guardarPelicula(Model model, RedirectAttributes redirectAttributes, @RequestParam(required = false) MultipartFile imagen, MovieOrSerie film) throws IOException {
		try {
			movieOrSerieService.save(film, imagen);
			redirectAttributes.addFlashAttribute("success", "Pelicula guardada con exito");
		} catch (ErrorService ex) {
			ex.printStackTrace();
			model.addAttribute("error", ex.getMessage());
			model.addAttribute("pelicula", film);
			model.addAttribute("calificaciones", qualificacionService.listAll());
			model.addAttribute("generos", genderService.listAll());
			return "film-form";
		}
		return "redirect:/movie/form";
	}

	//@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/delete")
	public String delete(@RequestParam(required = true) String id) {
		movieOrSerieService.deleteById(id);
		return "redirect:/movies/list";
	}
}
