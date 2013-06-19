package com.campusconnect.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "user")
public class User  implements java.io.Serializable {
	
	private String username;
	private String pswd;
   
	@Id	// This annotation means column is PRIMARY KEY
	@Column(name = "username")	// Note Spring only needs this annotation if the member name isn't the same as column in database
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    @Column(name = "pswd")
    public String getPswd() {
        return this.pswd;
    }
    
    public void setPswd(String pswd) {
        this.pswd = pswd;
    }
}


