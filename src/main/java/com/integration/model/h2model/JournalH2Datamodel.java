package com.integration.model.h2model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "journals")
public class JournalH2Datamodel {
	
	@Id
	@Column(name = "ID")
	private String id;
	
	@Column(name = "MESSAGE")
	private String message;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "CREATED_ON")
	private String createdOn;
	
	@Column(name = "LAST_MODIFIED")
	private String lastModified;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	
	
		
}
