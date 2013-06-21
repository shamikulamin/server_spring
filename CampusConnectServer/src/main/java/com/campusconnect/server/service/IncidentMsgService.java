package com.campusconnect.server.service;

import java.util.List;

import com.campusconnect.server.domain.IncidentMsg;

public interface IncidentMsgService {
	// Find all CommunityMsgs
		public List<IncidentMsg> getAll();
		
		// Find an IncidentMsg by id
		public IncidentMsg getById(Long id);
		
		// Insert or update an IncidentMsg
		public IncidentMsg save(IncidentMsg msg);
		
		// Delete an IncidentMsg
		public void delete(IncidentMsg msg);
}
