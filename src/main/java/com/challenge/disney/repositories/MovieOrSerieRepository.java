
package com.challenge.disney.repositories;

import com.challenge.disney.entities.MovieOrSerie;
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
public interface MovieOrSerieRepository extends JpaRepository<MovieOrSerie, String>{
    
	@Query("SELECT m FROM MovieOrSerie m where m.title like :title")
    List<MovieOrSerie> findAllByQ(@Param("title") String title);
	
	@Query("select m from MovieOrSerie m where m.id = :id")
    MovieOrSerie searchForId(@Param("id") String id);
	
	@Query("select m from MovieOrSerie m where m.qualification = :q")
    List<MovieOrSerie> findAllByQualification(@Param("q") String q);

    @Query("SELECT p from MovieOrSerie p where p.gender = :estilo")
    List<MovieOrSerie> findByGenero(@Param("estilo") String estilo);

    @Query("SELECT p from MovieOrSerie p order by :order")
    List<MovieOrSerie> orderBy(@Param("order") String order);


}
	

