package com.challenge.disney.services;

import com.challenge.disney.entities.AnimatedCharacter;
import com.challenge.disney.entities.MovieOrSerie;
import com.challenge.disney.entities.Photo;
import com.challenge.disney.repositories.AnimatedCharacterRepository;
import com.challenge.disney.exception.ErrorService;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Jonathan
 */
@Service
public class AnimatedCharacterService {

	@Autowired
	private AnimatedCharacterRepository animatedCharacterRepo;

	@Autowired
	private MovieOrSerieService movieOrSerieService;

	@Autowired
	public PhotoService photoService;

	@Transactional
	public AnimatedCharacter save(AnimatedCharacter character, MultipartFile image) throws ErrorService, IOException  {

		if (character.getName().isEmpty() || character.getName() == null) {
			throw new ErrorService("Ingrese el nombre del Personaje");
		}

		if (character.getAge() == null || character.getAge().isEmpty()) {
			throw new ErrorService("Debe ingresar la edad del Personaje");
		}

		if (character.getWeight() == null) {
			throw new ErrorService("Debe ingresar el peso del Personaje");
		}
		if (character.getLore().isEmpty() || character.getLore() == null) {
			throw new ErrorService("Debe ingresar un reseña del Persona");
		}
		if (character.getMovieOrSerie() == null ) {
			throw new ErrorService("La pelicula no puede ser nula");
			
		}else {
			character.setMovieOrSerie(movieOrSerieService.findByIdTwo(character.getMovieOrSerie()));
		}
		
		Photo img = photoService.savePhoto(image);
		character.setPhoto(img);
		return animatedCharacterRepo.save(character);
	}

	public List<AnimatedCharacter> listAll() {
		List<AnimatedCharacter> list = animatedCharacterRepo.findAll();
		return list;
	}

	public List<AnimatedCharacter> findByName(String name) {
		List<AnimatedCharacter> list = animatedCharacterRepo.findByName("%" + name + "%");
		return list;
	}

	public List<AnimatedCharacter> listAllFilm(String film) {
		return animatedCharacterRepo.listAllFilm(film);
	}

	public Optional<AnimatedCharacter> findById(String id) {
		return animatedCharacterRepo.findById(id);
	}

	public AnimatedCharacter searchForId(String id) {
		return animatedCharacterRepo.searchForId(id);
	}

	public AnimatedCharacter findById2(AnimatedCharacter character) {
		Optional<AnimatedCharacter> optional1 = animatedCharacterRepo.findById(character.getId());
		if (optional1.isPresent()) {
			character = optional1.get();
		}
		return character;
	}

	@Transactional
	public void delete(AnimatedCharacter character) {
		animatedCharacterRepo.delete(character);
	}

	@Transactional
	public void deleteFieldPelicula(MovieOrSerie film) {
		List<AnimatedCharacter> character = animatedCharacterRepo.listAllFilm(film.getTitle());
		for (AnimatedCharacter character1 : character) {
			character1.setMovieOrSerie(null);
		}
		animatedCharacterRepo.saveAll(character);
	}

	@Transactional
	public void deleteById(String id) {
		Optional<AnimatedCharacter> optional = animatedCharacterRepo.findById(id);
		if (optional.isPresent()) {
			animatedCharacterRepo.delete(optional.get());
		}
	}

}
