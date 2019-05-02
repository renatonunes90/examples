package com.example.entities.domain;

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
 * Entity who represents table PHONE_TYPE in database.
 * 
 * @author Renato M B Nunes
 */
@Getter
@Setter
@Entity
@Table(name = "PHONE_TYPE", schema = "REGISTRY")
public class PhoneType extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PHONE_TYPE")
	@SequenceGenerator(name = "SEQ_PHONE_TYPE", sequenceName = "SEQ_PHONE_TYPE",
		schema = "REGISTRY", allocationSize = 1)
	@Column(name = "PHONE_TYPE_ID", updatable = false, nullable = false)
	private Long phoneTypeId;

	@Column(name = "PHONE_TYPE_TEXT")
	private String phoneTypeText;

	@Column(name = "UDPATED_DATE")
	private Date updatedDate;

	@Override
	public int hashCode() {
		return createHashCode(phoneTypeId, phoneTypeText);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof PhoneType)) {
			return false;
		}
		PhoneType other = (PhoneType) obj;
		boolean result = compareProp(phoneTypeId, other.phoneTypeId);
		result &= compareProp(phoneTypeText, other.phoneTypeText);
		return result;
	}

	@Override
	public String toString() {
		return stringify(this);
	}
}
