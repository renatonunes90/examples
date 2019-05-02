package com.example.entities.persondata;

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
import com.example.entities.domain.PhoneType;

import lombok.Getter;
import lombok.Setter;

/**
 * Entity who represents table PHONE in database.
 * 
 * @author Renato M B Nunes
 */
@Getter
@Setter
@Entity
@Table(name = "PHONE", schema = "REGISTRY")
public class Phone extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PHONE")
   @SequenceGenerator(name = "SEQ_PHONE", sequenceName = "SEQ_PHONE", schema = "REGISTRY",
      allocationSize = 1)
	@Column(name = "PHONE_ID")
	private Long phoneId;

	@Column(name = "PERSON_ID")
	private Long personId;

	@Autowired
	@OneToOne
	@JoinColumn(name = "PHONE_TYPE_ID")
	private PhoneType phoneType;

	@Column(name = "NUMBER")
	private String number;

	@Column(name = "DDD")
	private String ddd;

	@Column(name = "DDI")
	private String ddi;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Override
	public int hashCode() {
		return createHashCode(phoneId, personId, phoneType, ddd, ddi, number);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof Phone)) {
			return false;
		}
		Phone other = (Phone) obj;
		boolean result = phoneType.equals(other.phoneType);
		result &= compareProp(number, other.number);
		result &= compareProp(ddd, other.ddd);
		result &= compareProp(ddi, other.ddi);
		result &= compareProp(number, other.number);
		return result;
	}

	@Override
	public String toString() {
		return stringify(this);
	}
}
