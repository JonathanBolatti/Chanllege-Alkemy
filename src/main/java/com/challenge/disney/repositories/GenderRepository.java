
package com.challenge.disney.repositories;

import com.challenge.disney.entities.Gender;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Jonathan
 */
public interface GenderRepository extends JpaRepository<Gender, String>{
	 @Query("select g from Gender g where g.name LIKE :q")
    List<Gender> findAll(@Param("q") String q);
	
}
