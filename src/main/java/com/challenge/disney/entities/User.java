package com.challenge.disney.entities;

import com.challenge.disney.enums.Role;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Jonathan
 */
@Data
@Entity
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable, Comparable {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	
	private String user;
	private String email;
	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

	@Override
	public int compareTo(Object t) {
		User user = (User) t;
		return this.user.compareToIgnoreCase(user.getUser()); 

	}

}
