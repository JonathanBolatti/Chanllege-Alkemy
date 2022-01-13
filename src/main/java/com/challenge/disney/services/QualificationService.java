package com.challenge.disney.services;

import com.challenge.disney.entities.Qualification;
import com.challenge.disney.exception.ErrorService;
import com.challenge.disney.repositories.QualificationRepository;
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
public class QualificationService {

	@Autowired
	private QualificationRepository qualificationRepo;

	@Autowired
	private MovieOrSerieService movieOrSerieService;

	public Qualification save(Qualification qualification) throws ErrorService {
		if (qualification.getScore().isEmpty() || qualification.getScore() == null) {
			throw new ErrorService("La calificacion no puede estar vacia");
		}
		return qualificationRepo.save(qualification);
	}

	public List<Qualification> listAll() {
		List<Qualification> list = qualificationRepo.findAll();
		Collections.sort(list);
		return list;
	}

	public List<Qualification> listAll(String q) {
		return qualificationRepo.findAll("%" + q + "%");
	}

	public Optional<Qualification> findById(String id) {
		return qualificationRepo.findById(id);
	}

	public Qualification findById(Qualification qualification) {
		Optional<Qualification> optional = qualificationRepo.findById(qualification.getId());
		if (optional.isPresent()) {
			qualification = optional.get();
		}
		return qualification;
	}

	@Transactional
	public void delete(Qualification qualification) {
		qualificationRepo.delete(qualification);
	}

	@Transactional
	public void deleteById(String id) {
		Optional<Qualification> optional = qualificationRepo.findById(id);
		if (optional.isPresent()) {
			Qualification qualification = optional.get();
			qualificationRepo.delete(qualification);
		}

	}

}
