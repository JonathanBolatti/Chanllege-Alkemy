package com.challenge.disney.entities;


import java.io.Serializable;
import javax.persistence.Entity;
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
@Table(name = "genero")
@Inheritance(strategy = InheritanceType.JOINED)
public class Gender implements Serializable, Comparable{

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String name;
//	@OneToOne
//	private Photo image;

@Override
    public int compareTo(Object t) {
        Gender gender = (Gender) t;
        return this.name.compareTo(gender.getName());
    }
	
}
