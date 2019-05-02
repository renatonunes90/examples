package com.example.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.common.beans.BaseBean;
import com.example.entities.domain.MaritalStatus;

import lombok.Getter;
import lombok.Setter;

/**
 * Entity who represents table PERSON in database.
 * 
 * @author Renato M B Nunes
 */
@Getter
@Setter
@Entity
@Table(name = "PERSON", schema = "REGISTRY")
public class Person extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PERSON")
   @SequenceGenerator(name = "SEQ_PERSON", sequenceName = "SEQ_PERSON", schema = "REGISTRY",
      allocationSize = 1)
	@Column(name = "PERSON_ID")
	private Long personId;

	@Column(name = "GENDER")
	private String genderType;

	@Column(name = "BIRTH_DATE")
	private Date birthDate;

	@Column(name = "NAME")
	private String name;

	@Column(name = "CPF")
	private String cpf;

   @Column(name = "BIRTH_COUNTRY")
	private String birthCountry;

	@Autowired
	@OneToOne
	@JoinColumn(name = "MARITAL_STATUS_ID")
	private MaritalStatus maritalStatus;

	@Column(name = "BIRTH_CITY")
	private String birthCity;

	@Column(name = "FATHER_NAME")
	private String fatherName;

	@Column(name = "MOTHER_NAME")
	private String motherName;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "ACTIVE")
	private String active;

	@Override
	public int hashCode() {
		return createHashCode(personId, birthDate, name, cpf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof Person)) {
			return false;
		}
		Person other = (Person) obj;
		boolean result = compareProp(birthDate, other.birthDate);
		result &= compareProp(name, other.name);
		result &= compareProp(cpf, other.cpf);
		return result;
	}

	@Override
	public String toString() {
		return stringify(this);
	}
}
