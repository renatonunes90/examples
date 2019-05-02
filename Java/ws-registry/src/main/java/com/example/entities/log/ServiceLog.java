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
 * Entity who represents table SERVICE_LOG in database.
 * 
 * @author Renato M B Nunes
 */
@Getter
@Setter
@Entity
@Table(name = "SERVICE_LOG", schema = "REGISTRY")
public class ServiceLog extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SERVICE_LOG")
	@SequenceGenerator(name = "SEQ_SERVICE_LOG", sequenceName = "SEQ_SERVICE_LOG",
		schema = "REGISTRY", allocationSize = 1)
	@Column(name = "SERVICE_LOG_ID", updatable = false, nullable = false)
	private Long serviceLogId;

	@Column(name = "CURRENT_DATE")
	private Date date;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "SOURCE")
	private String source;

	@Column(name = "METHOD")
	private String method;

	@Column(name = "PATH")
	private String path;

	@Column(name = "PARAMS")
	private String queryParams;

	@Column(name = "PAYLOAD")
	private String payload;

	@Column(name = "RESPONSE")
	private String response;

	@Column(name = "RETURN_STATUS")
	private Long returnStatus;

	@Column(name = "SUCCESS")
	private String success;

	@Override
	public int hashCode() {
		return createHashCode(serviceLogId, date, username, source, method, path, queryParams,
			success);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof ServiceLog)) {
			return false;
		}
		ServiceLog other = (ServiceLog) obj;
		boolean result = compareProp(serviceLogId, other.serviceLogId);
		result &= compareProp(date, other.date);
		result &= compareProp(username, other.username);
		result &= compareProp(source, other.source);
		result &= compareProp(method, other.method);
		result &= compareProp(path, other.path);
		result &= compareProp(queryParams, other.queryParams);
		result &= compareProp(success, other.success);
		result &= compareProp(payload, other.payload);
		result &= compareProp(response, other.response);
		return result;
	}

	@Override
	public String toString() {
		return stringify(this);
	}
}
