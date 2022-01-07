package com.challenge.disney.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Jonathan
 */
@Data
@Entity
@Table(name = "pelicula")
@Inheritance(strategy = InheritanceType.JOINED)
public class MovieOrSerie implements Serializable, Comparable {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String title;

	@OneToOne
	private Photo photo;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date dateCreation;

	@ManyToOne
	@JoinColumn(name = "calificacion_id")
	Qualification qualification;

	@ManyToOne
	@JoinColumn(name = "genero_id")
	Gender gender;

	@Override
	public int compareTo(Object t) {
		MovieOrSerie movieOrSerie = (MovieOrSerie) t;
		return this.title.compareTo(movieOrSerie.getTitle());
	}

}
