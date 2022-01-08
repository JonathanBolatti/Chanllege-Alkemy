package com.challenge.disney.repositories;

import com.challenge.disney.entities.User;
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
public interface UserRepository extends JpaRepository<User, String> {

	@Query("select p from User p where p.user LIKE :query or "
			+ "p.email LIKE :query")
	List<User> findAllByQ(@Param("query") String query);

	@Query("select u from User u where u.email = :email")
	User findByEmail(@Param("email") String email);

	@Query("select u from User u where u.id = :id")
	User searchForID(@Param("id") String id);
}

