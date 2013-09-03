package com.campusconnect.server.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;

@SuppressWarnings("serial")
@Entity
@Table(name = "user")
@NamedQueries({
	@NamedQuery(name="User.getById", query="select distinct u from User u where u.username = :id"),
	@NamedQuery(name="User.getByIdExcept", query="select u from User u where u.username <> :id")})
public class User implements java.io.Serializable, UserDetails {
	
	private String username;
	private String pswd;
	private Boolean enabled;
	private Set<Authority> authorities;
	
	public User() {}
	
	public User(String username, String pswd, Set<Authority> authorities) {
		this.username = username;
		this.pswd = pswd;
		this.enabled = true;
		this.authorities = authorities;
	}
	
	public User(String username, String pswd) {
		this.username = username;
		this.pswd = BCrypt.hashpw(pswd, BCrypt.gensalt());
		this.enabled = true;
		this.authorities = new HashSet<Authority>();
		Authority newAuth = new Authority(this,"ROLE_USER");
		authorities.add(newAuth);
	}
	
	public User(String username, String pswd, String role) {
		this.username = username;
		this.pswd = BCrypt.hashpw(pswd, BCrypt.gensalt());
		this.enabled = true;
		this.authorities = new HashSet<Authority>();
		Authority newAuth = new Authority(this,role);
		authorities.add(newAuth);
		if( role.equals("ROLE_ADMIN") ) {
			Authority userAuth = new Authority(this,"ROLE_USER");
			authorities.add(userAuth);
		}
	}
	
	@Id	// This annotation means column is PRIMARY KEY
	@Column(name = "username")	// Note Spring only needs this annotation if the member name isn't the same as column in database
    public String getUsername() {
        return this.username;
    }
	
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    @Column(name = "password")
    public String getPassword() {
        return this.pswd;
    }
    
    public void setPassword(String pswd) {
        this.pswd = pswd;
    }
    
    public void changePassword(String pswd) {
    	this.pswd = BCrypt.hashpw(pswd, BCrypt.gensalt());
    }
     
    @Column(name="enabled", columnDefinition="TINYINT")
    @Type(type="org.hibernate.type.NumericBooleanType")
    public Boolean getEnabled() {
    	return this.enabled;
    }
    
    public void setEnabled(Boolean enabled) {
    	this.enabled = enabled;
    }
    
    @OneToMany(mappedBy = "user", fetch=FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true)
    public Set<Authority> getAuthorities() {
        return authorities;
    }
    
    public void setAuthorities(Set<Authority> authorities) {
    	this.authorities = authorities;
    }

	@Override
	@Transient 
	public boolean isAccountNonExpired() {
		return true;
	}


	@Override
	@Transient 
	public boolean isAccountNonLocked() {
		return true;
	}


	@Override
	@Transient 
	public boolean isCredentialsNonExpired() {
		return true;
	}


	@Override
	@Transient 
	public boolean isEnabled() {
		return true;
	}
}


