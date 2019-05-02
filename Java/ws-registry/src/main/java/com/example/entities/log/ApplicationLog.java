package com.example.entities.log;

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
 * Entity who represents table APPLICATION_LOG in database.
 * 
 * @author Renato M B Nunes
 */
@Getter
@Setter
@Entity
@Table(name = "APPLICATION_LOG", schema = "REGISTRY")
public class ApplicationLog extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_APPLICATION_LOG")
	@SequenceGenerator(name = "SEQ_APPLICATION_LOG", sequenceName = "SEQ_APPLICATION_LOG",
		schema = "REGISTRY", allocationSize = 1)
	@Column(name = "APPLICATION_LOG_ID", updatable = false, nullable = false)
	private Long serviceLogId;

	@Column(name = "CURRENT_DATE")
	private Date date;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "MESSAGE")
	private String message;

	@Override
	public int hashCode() {
		return createHashCode(serviceLogId, date, username, message);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof ApplicationLog)) {
			return false;
		}
		ApplicationLog other = (ApplicationLog) obj;
		boolean result = compareProp(serviceLogId, other.serviceLogId);
		result &= compareProp(date, other.date);
		result &= compareProp(username, other.username);
		result &= compareProp(message, other.message);
		return result;
	}

	@Override
	public String toString() {
		return stringify(this);
	}
}
