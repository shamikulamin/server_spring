package com.campusconnect.server.service.jpa;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campusconnect.server.controller.helper.UserHelper;
import com.campusconnect.server.domain.User;

@Service("userDetailsService") 
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private ServletContext servletContext;

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User u = new UserHelper(servletContext).getById(username);
		
		if (u == null)
			throw new UsernameNotFoundException("user not found");

		return u;
	}
}