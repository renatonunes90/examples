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
 * Entity who represents table MARITAL_STATUS in database.
 * 
 * @author Renato M B Nunes
 */
@Getter
@Setter
@Entity
@Table(name = "MARITAL_STATUS", schema = "REGISTRY")
public class MaritalStatus extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MARITAL_STATUS")
	@SequenceGenerator(name = "SEQ_MARITAL_STATUS", sequenceName = "SEQ_MARITAL_STATUS",
		schema = "REGISTRY", allocationSize = 1)
	@Column(name = "MARITAL_STATUS_ID", updatable = false, nullable = false)
	private Long maritalStatusId;

	@Column(name = "MARITAL_STATUS_TEXT")
	private String maritalStatusText;

	@Override
	public int hashCode() {
		return createHashCode(maritalStatusId, maritalStatusText);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof MaritalStatus)) {
			return false;
		}
		MaritalStatus other = (MaritalStatus) obj;
		boolean result = compareProp(maritalStatusId, other.maritalStatusId);
		result &= compareProp(maritalStatusText, other.maritalStatusText);
		return result;
	}

	@Override
	public String toString() {
		return stringify(this);
	}
}
