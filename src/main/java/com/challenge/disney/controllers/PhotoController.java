package com.challenge.disney.controllers;

import com.challenge.disney.entities.AnimatedCharacter;
import com.challenge.disney.entities.MovieOrSerie;
import com.challenge.disney.exception.ErrorService;
import com.challenge.disney.services.AnimatedCharacterService;
import com.challenge.disney.services.MovieOrSerieService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Jonathan
 */
@Controller
@RequestMapping("/photo")
public class PhotoController {

	@Autowired
	private MovieOrSerieService movieOrSerieService;

	@Autowired
	private AnimatedCharacterService animatedCharacterService;
	
	
	///////////////////////////////////////////////////////////////
	//  CONTROLADORES PARA MOSTRAR LAS FOTOS DE MANERA DINAMICA	//										//
	//////////////////////////////////////////////////////////////

	@GetMapping("/films")
	public ResponseEntity<byte[]> filmImage(@RequestParam String id) throws ErrorService {
		try {
			MovieOrSerie movieOrSerie = movieOrSerieService.searchForId(id);
			byte[] photo  = movieOrSerie.getPhoto().getContainer();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_JPEG);
			return new ResponseEntity<>(photo, headers, HttpStatus.OK);
		} catch (Exception e) {
			Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/characters")
	public ResponseEntity<byte[]> characterImage(@RequestParam String id) throws ErrorService {
		try {
			AnimatedCharacter character = animatedCharacterService.searchForId(id);
			byte[] foto = character.getPhoto().getContainer();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_JPEG);
			return new ResponseEntity<>(foto, headers, HttpStatus.OK);
		} catch (Exception e) {
			Logger.getLogger(PhotoController.class.getName()).log(Level.SEVERE, null, e);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
