package com.campusconnect.server.service;

import java.util.List;
import com.campusconnect.server.domain.CommunityMsg;

public interface CommunityMsgService {
	// Find all CommunityMsgs
	public List<CommunityMsg> getAll();
	
	// Find all Valid CommunityMsgs
	public List<CommunityMsg> getAllValid();
	
	// Find all with Location
	public List<CommunityMsg> getAllWithLocation();
	
	// Find all with Location
	public List<CommunityMsg> getAllValidWithLocation();
	
	// Find a CommunityMsg by id
	public CommunityMsg getById(Integer id);
	
	// Insert or update a CommunityMsg
	public CommunityMsg save(CommunityMsg commMsg);
	
	// Delete a CommunityMsg
	public void delete(CommunityMsg commMsg);
}