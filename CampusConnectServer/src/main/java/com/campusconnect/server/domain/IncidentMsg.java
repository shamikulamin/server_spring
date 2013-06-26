package com.campusconnect.server.domain;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.campusconnect.server.domain.IncidentPicture;

@SuppressWarnings("serial")
@Entity
@Table(name = "incident_msg")
@NamedQueries({
	@NamedQuery(name="IncidentMsg.getById", query="select distinct im from IncidentMsg im where im.id = :id")})
public class IncidentMsg  implements java.io.Serializable {
	
	private Integer incidentId;
	private String msgTitle;
	private String msgDescription;
	private Date reportingTime;
	private String latlong;
	private Set<IncidentPicture> incidentPictures = new HashSet<IncidentPicture>(0);
	//private int noOfImages;
	
	public IncidentMsg() {}

	public IncidentMsg(String msg_title, String msg_description, Timestamp timestamp, String latLong2, Set<IncidentPicture> iPics) {
		this.msgTitle = msgTitle;
		this.msgDescription = msgDescription;
		this.reportingTime = reportingTime;
		this.latlong = latlong;
		this.incidentPictures = incidentPictures;
	}

	@Id	// This annotation means column is PRIMARY KEY
	@Column(name = "incident_id")	// Note Spring only needs this annotation if the member name isn't the same as column in database
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getIncidentId() {
		return this.incidentId;
	}
	
	public void setIncidentId(Integer incidentId) {
		this.incidentId = incidentId;
	}
	
	@Column(name = "msg_title")
	public String getMsgTitle() {
		return this.msgTitle;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}
	
	@Column(name = "msg_description")
	public String getMsgDescription() {
		return this.msgDescription;
	}

	public void setMsgDescription(String msgDescription) {
		this.msgDescription = msgDescription;
	}
	
	@Column(name = "reporting_time")
	public Date getReportingTime() {
		return this.reportingTime;
	}

	public void setReportingTime(Date reportingTime) {
		this.reportingTime = reportingTime;
	}
	
	@Column(name = "latlong")
	public String getLatlong() {
		return this.latlong;
	}

	public void setLatlong(String latlong) {
		this.latlong = latlong;
	}
	
	@OneToMany(mappedBy = "incidentMsg", fetch=FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true)
	public Set<IncidentPicture> getIncidentPictures() {
		return this.incidentPictures;
	}

	public void setIncidentPictures(Set<IncidentPicture> incidentPictures) {
		this.incidentPictures = incidentPictures;
	}
	
	/*
	public void setNoOfImages(int numImages){
		this.noOfImages = numImages;
	}
	public int getNoOfImages(){
		return this.noOfImages;
	}*/
	
	@Override
	public String toString(){
		return msgTitle + ", " + msgDescription;
	}
}


