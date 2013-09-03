package com.campusconnect.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "incident_recording")
public class IncidentRecording implements java.io.Serializable {

	private Integer id;
	private IncidentMsg incidentMsg;
	private String recording;

	@Id	// This annotation means column is PRIMARY KEY
	@Column(name = "id")	// Note Spring only needs this annotation if the member name isn't the same as column in database
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne
    @JoinColumn(name="incident_id")
	public IncidentMsg getIncidentMsg() {
		return this.incidentMsg;
	}

	public void setIncidentMsg(IncidentMsg incidentMsg) {
		this.incidentMsg = incidentMsg;
	}
	
	@Column(name = "recording")
	public String getRecording() {
		return this.recording;
	}

	public void setRecording(String recording) {
		this.recording = recording;
	}
	
	@Override
	public String toString(){
		return id +", " + recording;
	}
}


