package com.campusconnect.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campusconnect.server.controller.helper.CampusConnectWebHelper;

@Controller
@RequestMapping(value = "/")
public class CampusConnectWebController {
	
	@RequestMapping(value = "/restful/test", method = RequestMethod.GET)
	@ResponseBody
	public String test(Model uiModel) {
		return "authenticated";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model uiModel) {
		return "userLogged";
	}
	
	@RequestMapping(value = "/getPostedMessages", method = RequestMethod.GET)
	public String getPostedMessages(Model uiModel) {
		new CampusConnectWebHelper(uiModel).getPostedMessages();
		return "PostedMessages";
	}
	
	@RequestMapping(value = "/getIncidentMessages", method = RequestMethod.GET)
	public String getIncidentMessages(Model uiModel) {
		new CampusConnectWebHelper(uiModel).getIncidentMsgs();
		return "messages";
	}
	
	@RequestMapping(value = "/postMessage", method = RequestMethod.POST)
	public String postMessage(@RequestParam("messagetype") String msgType, @RequestParam("message") String message,
							  @RequestParam("msgTitle") String messageTitle, @RequestParam("expiryhours") String expiryHours,
							  @RequestParam("expiryDays") String expiryDays, @RequestParam("pushCheck") String pushCheck,
							  @RequestParam("location_list") String locationList,
							Model uiModel) {
		new CampusConnectWebHelper(uiModel).handlePost(msgType, message, messageTitle, expiryHours, expiryDays, locationList, pushCheck);
		return "userLogged";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model uiModel) {
		return "login";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model uiModel) {
		return "logout";
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
		new CampusConnectWebHelper(uiModel).showPics(id);
		return "showPics";
	}

	@RequestMapping(value = "post", method = RequestMethod.GET)
	public String postJSP(Model uiModel) {
		return "post";
	}
}
