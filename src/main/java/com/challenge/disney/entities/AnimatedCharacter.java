package com.challenge.disney.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Jonathan
 */
@Data
@Entity
@Table(name = "Personaje")
@Inheritance(strategy = InheritanceType.JOINED)
public class AnimatedCharacter implements Serializable, Comparable{

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	@OneToOne
	private Photo photo;

	private String name;
	private String age;
	private Double weight;
	private String lore;
	
	@ManyToOne
	private MovieOrSerie movieOrSerie;
	
	@Override
    public int compareTo(Object t) {
        AnimatedCharacter animatedCharacter = (AnimatedCharacter) t;
        return this.name.compareTo(animatedCharacter.getName());
    }
	
}
