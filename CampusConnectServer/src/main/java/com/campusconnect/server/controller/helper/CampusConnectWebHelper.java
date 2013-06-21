package com.campusconnect.server.controller.helper;

import java.util.List;

import org.springframework.ui.Model;

import com.campusconnect.server.domain.CommunityMsg;
import com.campusconnect.server.domain.IncidentMsg;

public class CampusConnectWebHelper {
	Model model;
	
	public CampusConnectWebHelper(Model uiModel) {
		model = uiModel;
	}
	
	public void getIncidentMsgs() {
		IncidentMsgHelper helper = new IncidentMsgHelper();
		List<IncidentMsg> result = helper.getIncidentMessages();
		model.addAttribute("incidentMessages", result);
	}
	
	public void getPostedMessages() {
		CommunityMsgHelper helper = new CommunityMsgHelper();
		List<CommunityMsg> result = helper.getCommunityMessages();
		model.addAttribute("communityMessages", result);
	}
}
