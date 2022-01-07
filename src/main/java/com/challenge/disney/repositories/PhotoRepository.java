
package com.challenge.disney.repositories;

import com.challenge.disney.entities.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Jonathan
 */
public interface PhotoRepository extends JpaRepository<Photo, String>{
	
	
}
