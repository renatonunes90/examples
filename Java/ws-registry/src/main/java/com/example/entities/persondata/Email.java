package com.example.entities.persondata;

import java.io.Serializable;
import java.util.Date;

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
 * Entity who represents table EMAIL in database.
 * 
 * @author Renato M B Nunes
 */
@Getter
@Setter
@Entity
@Table(name = "EMAIL", schema = "REGISTRY")
public class Email extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EMAIL")
   @SequenceGenerator(name = "SEQ_EMAIL", sequenceName = "SEQ_EMAIL", schema = "REGISTRY",
      allocationSize = 1)
	@Column(name = "EMAIL_ID")
	private Long emailId;

	@Column(name = "PERSON_ID")
	private Long personId;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Override
	public int hashCode() {
		return createHashCode(emailId, personId, email);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof Email)) {
			return false;
		}
		Email other = (Email) obj;
		boolean result = compareProp(personId, other.personId);
		result &= compareProp(email, other.email);
		return result;
	}

	@Override
	public String toString() {
		return stringify(this);
	}
}
