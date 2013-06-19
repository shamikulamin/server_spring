package com.campusconnect.server.domain.helper;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.campusconnect.server.domain.CommunityMsg;
import com.campusconnect.server.service.CommunityMsgService;

public class CommunityMsgHelper {
	private CommunityMsgService commServ;
	
	public CommunityMsgHelper() {
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:app-context.xml");
		ctx.refresh();
		commServ = ctx.getBean("jpaCommunityMsgService", CommunityMsgService.class);
	}
	
	public JSONArray getCommunityMsgForMap()
	{
		JSONArray vReturnObjects = new JSONArray();
		List<CommunityMsg> result = commServ.getAllWithLocation();
		for (int i = 0; i < result.size(); i++) {
			CommunityMsg msg = result.get(i);
			JSONObject object = new JSONObject();
			try {
				object.put("comm_msg_id", msg.getCommMsgId());
				object.put("msg_title", msg.getMsgTitle());
				object.put("msg_description", msg.getMsgDescription());
				object.put("reporting_time", msg.getReportingTime().toString());
				object.put("latlong", msg.getLatlong());
				object.put("msg_type", msg.getMsgType());
				object.put("expiry_time", msg.getExpiryTime().toString());
			} catch (Exception e) {
				System.out.println(e);
			}
			vReturnObjects.put(object);

		}
		return vReturnObjects;
	}

	public JSONObject getCommunityMsgByID(int iMsgID)
	{
		JSONObject object = new JSONObject();
		CommunityMsg message = commServ.getById(iMsgID);
		object.put("comm_msg_id", message.getCommMsgId());
		object.put("msg_title", message.getMsgTitle());
		object.put("msg_description", message.getMsgDescription());
		object.put("reporting_time", message.getReportingTime().toString());
		object.put("latlong", message.getLatlong());
		object.put("msg_type", message.getMsgType());
		object.put("expiry_time", message.getExpiryTime().toString());
		return object;
	}
	
	public String getCommunityMsgDesc(int id)
	{   
		return commServ.getById(id).toString();
	}
	
	public JSONArray getCommunityMsg()
	{
		JSONArray vReturnObjects = new JSONArray();
		List<CommunityMsg> message = commServ.getAllValid();
		for(int i=0; i < message.size(); i++){
			JSONObject object = new JSONObject();
			CommunityMsg msg = message.get(i);
			try {
				object.put("comm_msg_id", msg.getCommMsgId());
				object.put("msg_title", msg.getMsgTitle());
				object.put("msg_description", msg.getMsgDescription());
				object.put("reporting_time", msg.getReportingTime().toString());
				object.put("latlong", msg.getLatlong());
				object.put("msg_type", msg.getMsgType());
				object.put("expiry_time", msg.getExpiryTime().toString());
			} catch (Exception e) {
				System.out.println("Exception in Object.put");
			}
			vReturnObjects.put(object);
		}
		return vReturnObjects;
	}
}
