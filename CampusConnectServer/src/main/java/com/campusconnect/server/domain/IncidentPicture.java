package com.campusconnect.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "incident_picture")
public class IncidentPicture implements java.io.Serializable {

	private Integer id;
	private IncidentMsg incidentMsg;
	private String picture;

	@Id	// This annotation means column is PRIMARY KEY
	@Column(name = "incident_id")	// Note Spring only needs this annotation if the member name isn't the same as column in database
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
    @JoinColumn(name="id")
	public IncidentMsg getIncidentMsg() {
		return this.incidentMsg;
	}

	public void setIncidentMsg(IncidentMsg incidentMsg) {
		this.incidentMsg = incidentMsg;
	}
	
	@Column(name = "picture")
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	@Override
	public String toString(){
		return id +", " + picture;
	}
}


