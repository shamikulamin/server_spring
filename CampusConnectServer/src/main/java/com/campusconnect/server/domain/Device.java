package com.campusconnect.server.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "device")
@NamedQueries({
@NamedQuery(name="Device.getById", query="select distinct d from Device d where d.id = :id")})
public class Device  implements Serializable {
	private String regId;
	
	public Device() {}
	
	public Device(String regID) {
		this.regId = regID;
	}

	@Id	// This annotation means column is PRIMARY KEY
	@Column(name = "regID")	// Note Spring only needs this annotation if the member name isn't the same as column in database
	public String getRegId() {
		return this.regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}
}


