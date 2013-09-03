package com.campusconnect.server.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ExtendedModelMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.campusconnect.server.controller.helper.CommunityMsgHelper;
import com.campusconnect.server.domain.CommunityMsg;
import com.campusconnect.server.service.CommunityMsgService;

public class CampusConnectServletTest extends AbstractControllerTest {
	
	private final List<CommunityMsg> commMsgs = new ArrayList<CommunityMsg>();
	
	private CommunityMsgService commServ;
	
	@Before
	public void initMsgs() {
		// Initialize list of CommunityMsgs for mocked CommunityMsgService
		CommunityMsg commMsg = new CommunityMsg();
		commMsg.setCommMsgId(1);
		commMsg.setMsgTitle("TestTitle");
		commMsg.setMsgDescription("Test Description");
		commMsg.setReportingTime(new Date());
		commMsg.setLatlong("none");
		commMsg.setExpiryTime(new Date());
		commMsgs.add(commMsg);
	}
	
	@Test
	public void testGetCommunityMsg() {
		/*
		commServ = mock(CommunityMsgService.class);	// Service is mocked by Mockito
		when(commServ.getAllValid()).thenReturn(commMsgs);	// mocked method getAll()
		
		CommunityMsgHelper msgHelper = new CommunityMsgHelper();
		ReflectionTestUtils.setField(msgHelper, "commServ", commServ);
		
		JSONArray result = msgHelper.getCommunityMsgJSON();

		assertNotNull(result);
		assertEquals(1, result.length());
		*/
	}
	
	@Test
	public void testGetCommunityMsgByID() {
		/*
		commServ = mock(CommunityMsgService.class);
		when(commServ.getById(1)).thenReturn(commMsgs.get(0));
		
		CommunityMsgHelper msgHelper = new CommunityMsgHelper();
		ReflectionTestUtils.setField(msgHelper, "commServ", commServ);
		
		JSONObject result = msgHelper.getCommunityMsgByID(1);
		
		assertNotNull(result);
		assertEquals(1, result.getInt("comm_msg_id"));
		*/
	}
}
