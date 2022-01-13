package com.challenge.disney.services;

import com.challenge.disney.entities.Gender;
import com.challenge.disney.exception.ErrorService;
import com.challenge.disney.repositories.GenderRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jonathan
 */
@Service
public class GenderService {

	@Autowired
	private GenderRepository genderRepo;

	public Gender save(Gender gender) throws ErrorService {
		if (gender.getName().isEmpty() || gender.getName() == null) {
			throw new ErrorService("El genero no puede quedar vacio");
		}

		return genderRepo.save(gender);
	}

	public List<Gender> listAll() {
		List<Gender> list = genderRepo.findAll();
		Collections.sort(list);
		return list;
	}

	public List<Gender> findAllByQ(String query) {
		List<Gender> list = genderRepo.findAllByQ("%" + query + "%");
		return list;
	}
	


	public Optional<Gender> findById(String id) {
		return genderRepo.findById(id);
	}

	public Gender findById(Gender gender) {
		Optional<Gender> optional = genderRepo.findById(gender.getId());
		if (optional.isPresent()) {
			gender = optional.get();
		}
		return gender;
	}

	@Transactional
	public void delete(Gender gender) {
		genderRepo.delete(gender);
	}

	@Transactional
	public void deleteById(String id) {
		Optional<Gender> optional = genderRepo.findById(id);
		if (optional.isPresent()) {
			Gender gender = optional.get();
			genderRepo.delete(gender);
		}

	}

}
