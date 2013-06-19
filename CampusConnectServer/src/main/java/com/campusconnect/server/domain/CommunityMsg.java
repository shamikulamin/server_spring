package com.campusconnect.server.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Date;
//SELECT DISTINCT a FROM Author a INNER JOIN a.books b WHERE b.publisher.name = 'XYZ Press'
@SuppressWarnings("serial")
@Entity
@Table(name = "community_msg")
@NamedQueries({
	@NamedQuery(name="CommunityMsg.getById", query="select distinct cm from CommunityMsg cm where cm.id = :id")})
public class CommunityMsg  implements Serializable {
	private Integer commMsgId;
	private String msgTitle;
	private String msgDescription;
	private Date reportingTime;
	private String latlong;
	private String msgType;
	private Date expiryTime;

	@Id	// This annotation means column is PRIMARY KEY
	@Column(name = "comm_msg_id")	// Note Spring only needs this annotation if the member name isn't the same as column in database
	public Integer getCommMsgId() {
		return this.commMsgId;
	}

	public void setCommMsgId(Integer commMsgId) {
		this.commMsgId = commMsgId;
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
	
	@Temporal(TemporalType.DATE)// Tells hibernate to map java.util.Date to java.sql.Date
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
	
	@Column(name = "msg_type")
	public String getMsgType() {
		return this.msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	
	@Temporal(TemporalType.DATE)// Tells hibernate to map java.util.Date to java.sql.Date
	@Column(name = "expiry_time")
	public Date getExpiryTime() {
		return this.expiryTime;
	}

	public void setExpiryTime(Date expiryTime) {
		this.expiryTime = expiryTime;
	}
	
	@Override
	public String toString(){
		return msgTitle + ", " + msgDescription + ", " + latlong;
	}
}


