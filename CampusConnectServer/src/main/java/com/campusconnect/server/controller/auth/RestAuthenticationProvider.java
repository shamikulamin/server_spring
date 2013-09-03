package com.campusconnect.server.controller.auth;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.campusconnect.server.controller.helper.CampusConnectAndroidHelper;


@Component
public class RestAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	ServletContext servletContext;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String netid_in = authentication.getName();
        String pass_in = authentication.getCredentials().toString();

        if( netid_in == null || pass_in == null )
        	return null;
        
        CampusConnectAndroidHelper helper = new CampusConnectAndroidHelper(servletContext);
        String result = helper.loginAndroid(netid_in, pass_in, CampusConnectAndroidHelper.minSupportedVersion+"_ANDROID");
        if( result.equals("true") ) {
        	List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
            Authentication auth = new UsernamePasswordAuthenticationToken(netid_in, pass_in, grantedAuths);
            return auth;
        } else {
        	String ios_result = helper.loginAndroid(netid_in, pass_in, CampusConnectAndroidHelper.minSupportedVersion+"_IOS");
        	if( ios_result.equals("true") ) {
        		List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
                grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
                Authentication auth = new UsernamePasswordAuthenticationToken(netid_in, pass_in, grantedAuths);
                return auth;
        	}
            return null;
        }
    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}