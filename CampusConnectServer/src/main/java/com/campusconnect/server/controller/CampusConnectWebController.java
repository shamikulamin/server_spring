package com.campusconnect.server.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campusconnect.server.controller.helper.CampusConnectWebHelper;
import com.campusconnect.server.controller.helper.CommunityMsgHelper;
import com.campusconnect.server.controller.helper.IncidentMsgHelper;
import com.campusconnect.server.controller.helper.UserHelper;
import com.campusconnect.server.domain.Authority;
import com.campusconnect.server.domain.User;

@Controller
@RequestMapping(value = "/")
public class CampusConnectWebController {
	
	@Autowired
	ServletContext servletContext;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model uiModel) {
		return "CC_index";
	}
	
	@RequestMapping(value = "/ajax/communityMsgs", method = RequestMethod.GET)
	@ResponseBody
	public String ajaxCommunityMsgs(Model uiModel) {
		return new CommunityMsgHelper(servletContext).getAllCommunityMsgJSON().toString();
	}
	
	@RequestMapping(value = "/ajax/incidentMsgs", method = RequestMethod.GET)
	@ResponseBody
	public String ajaxIncidentMsgs(Model uiModel) {
		return new IncidentMsgHelper(servletContext).getAllIncidentMsgJSON().toString();
	}
	
	@RequestMapping(value = "/ajax/users", method = RequestMethod.GET)
	@ResponseBody
	public String ajaxUsers(Model uiModel, Principal principal) {
		return new UserHelper(servletContext).getAllUsersJSON(principal.getName()).toString();
	}
	
	@RequestMapping(value = "/ajax/userupdate/name", method = RequestMethod.POST)
	@ResponseBody
	public String ajaxUpdateUserName(@RequestParam("username") String username, @RequestParam("value") String newname, Model uiModel) {
		UserHelper helper = new UserHelper(servletContext);
		User u = helper.getById(username);
		helper.updateUsername(u, newname);
		return newname;
	}
	
	@RequestMapping(value = "/ajax/userupdate/roles", method = RequestMethod.POST)
	@ResponseBody
	public String ajaxUpdateUserRoles(@RequestParam("username") String username, @RequestParam("value") String newrole, Model uiModel) {
		UserHelper helper = new UserHelper(servletContext);
		User u = helper.getById(username);
		Set<Authority> auths = new HashSet<Authority>();
		if( newrole.equals("ROLE_ADMIN") ) {
			newrole += ", ROLE_USER";
			auths.add(new Authority(u, "ROLE_ADMIN"));
		}
		auths.add(new Authority(u, "ROLE_USER"));
		helper.updateAuthority(u, auths);
		return newrole;
	}
	
	@RequestMapping(value = "/ajax/removeUser", method = RequestMethod.POST)
	public String addUser(@RequestParam("username") String username, Model uiModel) {
		UserHelper helper = new UserHelper(servletContext);
		User toDel = helper.getById(username);
		helper.delete(toDel);
		return "CC_index";
	}
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String addUser(@RequestParam("username") String username, @RequestParam("password") String password,  
						  @RequestParam("accounttype") String role, Model uiModel) {
		new UserHelper(servletContext).insertUser(new User(username,password,role));
		return "CC_index";
	}
	
	@RequestMapping(value = "/viewCommunityMsgs", method = RequestMethod.GET)
	public String viewCommunityMsgs(Model uiModel) {
		return "viewCommunityMsg";
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public String changePassword(@RequestParam("username") String username, @RequestParam("currentpassword") String currPass, @RequestParam("newpassword") String newpass, Model uiModel) {
		UserHelper helper = new UserHelper(servletContext);
		User u = helper.getById(username);
		if( BCrypt.checkpw(currPass, u.getPassword()) ) {
			u.changePassword(newpass);
			helper.save(u);
			return "redirect:/logout";
		} else {
			return "error";
		}
	}
	
	@RequestMapping(value = "/accountMaint", method = RequestMethod.GET)
	public String newAccount(Model uiModel, Principal principal) {
	    uiModel.addAttribute("username", principal.getName());
		return "accountMaintenance";
	}
	
	@RequestMapping(value = "/getPostedMessages", method = RequestMethod.GET)
	public String getPostedMessages(Model uiModel) {
		new CampusConnectWebHelper(uiModel, servletContext).getPostedMessages();
		return "PostedMessages";
	}
	
	@RequestMapping(value = "/getIncidentMessages", method = RequestMethod.GET)
	public String getIncidentMessages(Model uiModel) {
		new CampusConnectWebHelper(uiModel, servletContext).getIncidentMsgs();
		return "messages";
	}
	
	@RequestMapping(value = "/postMessage", method = RequestMethod.POST)
	public String postMessage(@RequestParam("messagetype") String msgType, @RequestParam("message") String message,
							  @RequestParam("msgTitle") String messageTitle, @RequestParam("expirydatetime") String expiryDateTime,
							  @RequestParam(value="pushCheck", required = false) String pushCheck, @RequestParam("location_list") String locationList,
							  Model uiModel) {
		new CampusConnectWebHelper(uiModel, servletContext).handlePost(msgType, message, messageTitle, expiryDateTime, locationList, pushCheck);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model uiModel) {
		return "login";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response, Model uiModel) {
		String cookieName = "SPRING_SECURITY_REMEMBER_ME_COOKIE";
		Cookie cookie = new Cookie(cookieName, null);
		cookie.setMaxAge(0);
		cookie.setPath(StringUtils.hasLength(request.getContextPath()) ? request.getContextPath() : "/");
		response.addCookie(cookie);
		return "redirect:login";
	}
	
	@RequestMapping(value = "userLogged", method = RequestMethod.GET)
	public String userLogged(Model uiModel) {
		return "userLogged";
	}
	
	
	@RequestMapping(value = "showLocationInMap", method = RequestMethod.GET)
	public String location(@RequestParam("latLong") String latLong, Model uiModel) {
		uiModel.addAttribute("latLong", latLong);
		return "showLocationInMap";
	}
	
	@RequestMapping(value = "showPics", method = RequestMethod.GET)
	public String showPics(@RequestParam("incidentId") Long id, Model uiModel) {
		new CampusConnectWebHelper(uiModel, servletContext).showPics(id);
		return "showPics";
	}
	
	@RequestMapping(value = "showRecording", method = RequestMethod.GET)
	public String showRec(@RequestParam("recName") String recName, 
						  @RequestParam("conName") String conName, 
						  @RequestParam("conNum") String conNum, 
						  @RequestParam("conAdd") String conAdd, Model uiModel) {
		uiModel.addAttribute("recName", recName);
		uiModel.addAttribute("conName", conName);
		uiModel.addAttribute("conNum", conNum);
		uiModel.addAttribute("conAdd", conAdd);
		return "showRecording";
	}

	@RequestMapping(value = "post", method = RequestMethod.GET)
	public String postJSP(Model uiModel) {
		return "post";
	}
}
