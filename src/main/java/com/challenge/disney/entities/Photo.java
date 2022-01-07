package com.challenge.disney.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Jonathan
 */
@Data
@Entity
@Table(name = "foto")
public class Photo implements Serializable, Comparable{

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;
	private String name; 
	private String mime; 
	
	@Lob @Basic(fetch = FetchType.LAZY)
	private byte[] container; 
	
	@Override
    public int compareTo(Object t) {
        Photo photo = (Photo) t;
        return this.name.compareTo(photo.getName());
    }
	
}
