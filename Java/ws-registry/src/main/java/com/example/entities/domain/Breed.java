package com.example.entities.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.example.common.beans.BaseBean;

import lombok.Getter;
import lombok.Setter;

/**
 * Entity who represents table BREED in database.
 * 
 * @author Renato M B Nunes
 */
@Getter
@Setter
@Entity
@Table(name = "BREED", schema = "REGISTRY")
public class Breed extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BREED")
	@SequenceGenerator(name = "SEQ_BREED", sequenceName = "SEQ_BREED", schema = "REGISTRY",
		allocationSize = 1)
	@Column(name = "BREED_ID", updatable = false, nullable = false)
	private Long breedId;

	@Column(name = "BREED_TEXT")
	private String breedText;

	@Override
	public int hashCode() {
		return createHashCode(breedId, breedText);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof Breed)) {
			return false;
		}
		Breed other = (Breed) obj;
		boolean result = compareProp(breedId, other.breedId);
		result &= compareProp(breedText, other.breedText);
		return result;
	}

	@Override
	public String toString() {
		return stringify(this);
	}
}
