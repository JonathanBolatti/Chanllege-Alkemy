package com.challenge.disney.repositories;

import com.challenge.disney.entities.AnimatedCharacter;
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
public interface AnimatedCharacterRepository extends JpaRepository<AnimatedCharacter, String> {

	//para buscar por nombre : /characters?name=nombre
	@Query("SELECT p FROM AnimatedCharacter p WHERE p.name LIKE :query")
	List<AnimatedCharacter> findByName(@Param("query") String query);

	//para buscar por edad : /characters?age=edad
	@Query("SELECT p FROM AnimatedCharacter p where p.age = :age")
	public List<AnimatedCharacter> findByAge(@Param("age") Integer age);

	//para buscar por movies : /characters?movies=idMovie
	@Query("SELECT p from AnimatedCharacter p where p.id = :film")
	List<AnimatedCharacter> listAllFilm(@Param("film") String film);

	@Query("select p from AnimatedCharacter p where p.id = :id")
	AnimatedCharacter searchForId(@Param("id") String id);

}
