package com.campusconnect.server.service;

import java.util.List;

import com.campusconnect.server.domain.IncidentMsg;

public interface IncidentMsgService {
	// Find all CommunityMsgs
		public List<IncidentMsg> getAll();
		
		// Find a IncidentMsg by id
		public IncidentMsg getById(Long id);
		
		// Insert or update a IncidentMsg
		public IncidentMsg save(IncidentMsg msg);
}
