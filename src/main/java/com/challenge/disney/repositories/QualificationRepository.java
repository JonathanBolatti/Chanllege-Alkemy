package com.challenge.disney.repositories;

import com.challenge.disney.entities.Qualification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jonathan
 */
@Repository
public interface QualificationRepository extends JpaRepository<Qualification, String>{
	
	  @Query("select X from Qualification X where X.id LIKE :q or X.score LIKE :q")
    List<Qualification> findAll(@Param("q") String q);

}

