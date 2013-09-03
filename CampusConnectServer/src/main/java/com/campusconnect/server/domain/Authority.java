package com.campusconnect.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

@SuppressWarnings("serial")
@Entity
@Table(name ="authority")
@NamedQueries({
	@NamedQuery(name="Authority.getById", query="select distinct a from Authority a where a.user = :id")})
public class Authority implements GrantedAuthority {
	private Integer Id;
	private User user;
	private String authority;
	
	public Authority() {}
	
	public Authority(String authority) {
		this.authority = authority;
	}
	
	public Authority(User u, String authority) {
		this.user = u;
		this.authority = authority;
	}
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return Id;
	}
	
	public void setId( Integer id ) {
		this.Id = id;
	}
	
	@ManyToOne
    @JoinColumn(name="username")
	public User getUser() {
		return user;
	}
	
	public void setUser( User u ) {
		this.user = u;
	}

	@Column(name="authority")
	public String getAuthority() {
		return authority;
	}
	
	public void setAuthority(String auth) {
		this.authority = auth;
	}
}