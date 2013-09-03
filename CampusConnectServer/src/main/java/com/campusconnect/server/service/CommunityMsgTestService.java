package com.campusconnect.server.service;

import java.util.List;

import com.campusconnect.server.domain.CommunityMsg;

public interface CommunityMsgTestService {

	public List<CommunityMsg> findAll();
	
	// Find all Valid CommunityMsgs
	public List<CommunityMsg> findAllValid();
	
	// Find all with Location
	public List<CommunityMsg> findAllWithLocation();
	
	// Find all with Location
	public List<CommunityMsg> findAllValidWithLocation();
}
