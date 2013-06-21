package com.campusconnect.server.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campusconnect.server.controller.helper.CampusConnectWebHelper;
import com.campusconnect.server.controller.helper.CommunityMsgHelper;

@Controller
@RequestMapping(value = "/")
public class CampusConnectWebController {
	
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
	
	@RequestMapping(value = "/postMessage", method = RequestMethod.GET)
	public String postMessage(Model uiModel) {
		return "userLogged";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model uiModel) {
		return "userLogged";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model uiModel) {
		return "userLogged";
	}
	
	@RequestMapping(value = "showLocationInMap", method = RequestMethod.GET)
	public String location(@RequestParam("latLong") String latLong, Model uiModel) {
		uiModel.addAttribute("latLong", latLong);
		return "showLocationInMap";
	}
	
	@RequestMapping(value = "showPics", method = RequestMethod.GET)
	public String showPics(@RequestParam("noOfPics") int noOfPics, Model uiModel) {
		uiModel.addAttribute("noOfPics", noOfPics);
		return "showPics";
	}

	@RequestMapping(value = "post", method = RequestMethod.GET)
	public String postJSP(Model uiModel) {
		return "post";
	}
}
