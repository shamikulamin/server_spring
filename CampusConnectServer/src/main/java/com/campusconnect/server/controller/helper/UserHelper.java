package com.campusconnect.server.controller.helper;

import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.campusconnect.server.domain.Authority;
import com.campusconnect.server.domain.User;
import com.campusconnect.server.service.UserService;

public class UserHelper {
	private UserService uServ;

	public UserHelper(ServletContext sc) {
		//GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("classpath:app-context.xml");
		//ctx.load("classpath:app-context.xml");
		//ctx.refresh();
		//uServ = ctx.getBean("jpaUserService", UserService.class);
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
		uServ = ctx.getBean("jpaUserService", UserService.class);
	}
	
	public JSONObject getAllUsersJSON(String exception) {
		JSONArray vReturnObjects = new JSONArray();
		List<User> message = uServ.getAllExcept(exception);
		for(int i=0; i < message.size(); i++){
			JSONArray object = new JSONArray();
			User u = message.get(i);
			try {
				object.put(u.getUsername());
				
				Set<Authority> auths = u.getAuthorities();
				String delim = "";
				StringBuilder roles = new StringBuilder();
				for (Authority a : auths) {
			        roles.append(delim).append(a.getAuthority());
			        delim = ", ";
			    }
				
				object.put(roles.toString());
				object.put("<button onclick='checkDelete(this.id)' type='button' id="+ u.getUsername() +">Delete</button>");
			} catch (Exception e) {
				System.out.println("Exception in Object.put");
			}
			vReturnObjects.put(object);
		}
		JSONObject ret = new JSONObject();
		ret.put("aaData", vReturnObjects);
		return ret;
	}

	public User updateUsername(User u, String newname) {
		uServ.delete(u);
		u.setUsername(newname);
		uServ.save(u);
		return u;
	}
	
	public User updateAuthority(User u, Set<Authority> newAuths) {
		uServ.delete(u);
		u.setAuthorities(newAuths);
		uServ.save(u);
		return u;
	}
	
	public void delete(User u) {
		uServ.delete(u);
	}
	
	public void insertUser(User u) {
		uServ.save(u);
	}
	
	public User getById(String id) {
		return uServ.getById(id);
	}
	
	public User save(User u) {
		return uServ.save(u);
	}
	
}
