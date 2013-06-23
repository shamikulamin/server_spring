package com.campusconnect.server.service;

import java.util.List;

import com.campusconnect.server.domain.IncidentPicture;

public interface IncidentPictureService {
		// Find all CommunityMsgs
		public List<IncidentPicture> getAll();
		
		// Insert or update an IncidentMsg
		public IncidentPicture save(IncidentPicture pic);
		
		// Delete an IncidentMsg
		public void delete(IncidentPicture pic);
}

