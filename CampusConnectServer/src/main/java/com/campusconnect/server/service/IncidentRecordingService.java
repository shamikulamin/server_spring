package com.campusconnect.server.service;

import java.util.List;

import com.campusconnect.server.domain.IncidentRecording;

public interface IncidentRecordingService {
		// Find all CommunityMsgs
		public List<IncidentRecording> getAll();
		
		// Insert or update an IncidentMsg
		public IncidentRecording save(IncidentRecording pic);
		
		// Delete an IncidentMsg
		public void delete(IncidentRecording pic);
}

