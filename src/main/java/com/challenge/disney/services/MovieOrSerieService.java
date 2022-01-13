package com.challenge.disney.services;

import com.challenge.disney.entities.Gender;
import com.challenge.disney.entities.MovieOrSerie;
import com.challenge.disney.entities.Photo;
import com.challenge.disney.entities.Qualification;
import com.challenge.disney.exception.ErrorService;
import com.challenge.disney.repositories.GenderRepository;
import com.challenge.disney.repositories.MovieOrSerieRepository;
import java.io.IOException;
import java.util.Collections;
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
public class MovieOrSerieService {

	@Autowired
	private MovieOrSerieRepository movieOrSerieRepo;
	
	@Autowired
	private GenderRepository genderRepository; 
	
	@Autowired
	public PhotoService photoservice;

	public MovieOrSerie save(MovieOrSerie film, MultipartFile image) throws ErrorService, IOException {
		if (film.getTitle().isEmpty() || film.getTitle() == null) {
			throw new ErrorService("Debe ingresar un Titulo");
		}
		if (film.getDateCreation() == null) {
			throw new ErrorService("Ingrese Fecha de creacion");
		}

		Photo pic = photoservice.savePhoto(image);
//		film.setPhoto(pic);
		return movieOrSerieRepo.save(film);

	}

	public List<MovieOrSerie> listAll() {
		List<MovieOrSerie> list = movieOrSerieRepo.findAll();
		Collections.sort(list);
		return list;
	}

	public List<MovieOrSerie> listallByQ(String query) {
		List<MovieOrSerie> list = movieOrSerieRepo.findAllByQ("%" + query + "%");
		return list;
	}
	
	public List<MovieOrSerie> findByGenero(String q) {
		List<MovieOrSerie> list = movieOrSerieRepo.findByGenero("%" + q + "%");
		return list; 
	}

	public Optional<MovieOrSerie> findById(String id) {
		return movieOrSerieRepo.findById(id);
	}

	public MovieOrSerie findByIdTwo(MovieOrSerie film) {
		Optional<MovieOrSerie> optional = movieOrSerieRepo.findById(film.getId());
		if (optional.isPresent()) {
			film = optional.get();
		}
		return film;
	}

	@Transactional
	public void delete(MovieOrSerie film) {
		movieOrSerieRepo.delete(film);
	}

	@Transactional
	public void deleteById(String id) {
		Optional<MovieOrSerie> optional = movieOrSerieRepo.findById(id);
		if (optional.isPresent()) {
			movieOrSerieRepo.delete(optional.get());
		}
	}

	public MovieOrSerie searchForId(String id) {
		return movieOrSerieRepo.searchForId(id);
	}

	@Transactional
	public void deleteFieldQualification(Qualification qualification) {
		List<MovieOrSerie> film = movieOrSerieRepo.findAllByQualification(qualification.getScore());
		for (MovieOrSerie filmOne : film) {
			filmOne.setQualification(null);
		}
		movieOrSerieRepo.saveAll(film);
	}

	@Transactional
	public void deleteFieldGender(Gender gender) {
		List<MovieOrSerie> film = movieOrSerieRepo.findAllByQualification(gender.getId());
		for (MovieOrSerie filmOne : film) {
			filmOne.setTitle(null);
		}
		movieOrSerieRepo.saveAll(film);
	}
}
