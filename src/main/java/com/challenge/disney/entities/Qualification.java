package com.challenge.disney.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
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
@Table(name = "Calificacion")
public class Qualification implements Serializable, Comparable {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String score;

	
	private MovieOrSerie movieOrSerie;

	@Override
	public int compareTo(Object t) {
		Qualification qualification = (Qualification) t;
		return this.score.compareTo(qualification.getScore());
	}

}
