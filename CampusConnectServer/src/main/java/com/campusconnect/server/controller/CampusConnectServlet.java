package com.campusconnect.server.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campusconnect.server.domain.helper.CommunityMsgHelper;

@Controller
@RequestMapping(value = "/campus_connect_servlet")
public class CampusConnectServlet {
	
	@RequestMapping(value = "/getCommunityMsg", method = RequestMethod.POST)
	@ResponseBody
	public String getCommunityMsg(HttpServletRequest req, HttpServletResponse res) {
		return new CommunityMsgHelper().getCommunityMsg().toString();
	}
	
	@RequestMapping(value = "/getCommunityMsgById/{id}", method = RequestMethod.POST)
	@ResponseBody
	public String getCommunityMsgById(@PathVariable int id, HttpServletRequest req, HttpServletResponse res) {
		return new CommunityMsgHelper().getCommunityMsgByID(id).toString();
	}
	
	@RequestMapping(value = "/getCommunityMsgForMap", method = RequestMethod.POST)
	@ResponseBody
	public String getCommunityMsgForMap(HttpServletRequest req, HttpServletResponse res) {
		return new CommunityMsgHelper().getCommunityMsgForMap().toString();
	}
	
	@RequestMapping(value = "/getCommMsgDesc/{id}", method = RequestMethod.POST)
	@ResponseBody
	public String getCommMsgDesc(@PathVariable int id, HttpServletRequest req, HttpServletResponse res) {
		return new CommunityMsgHelper().getCommunityMsgDesc(id);
	}
}
