package com.campusconnect.server.controller.helper;

import java.util.List;

import javax.servlet.ServletContext;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.campusconnect.server.domain.CommunityMsg;
import com.campusconnect.server.service.CommunityMsgService;

public class CommunityMsgHelper {
	
	private CommunityMsgService commServ;
	
	public CommunityMsgHelper(ServletContext sc) {
		//GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		//ctx.load("classpath:app-context.xml");
		//ctx.refresh();
		//commServ = ctx.getBean("jpaCommunityMsgService", CommunityMsgService.class);
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
		commServ = ctx.getBean("jpaCommunityMsgService", CommunityMsgService.class);
	}

	public void setCommMsgService( CommunityMsgService commServ ) {
		this.commServ = commServ;
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
	
	public List<CommunityMsg> getCommunityMessages() 
	{
		return commServ.getAll();
	}
	
	public JSONObject getAllCommunityMsgJSON() {
		JSONArray vReturnObjects = new JSONArray();
		List<CommunityMsg> message = commServ.getAll();
		for(int i=0; i < message.size(); i++){
			JSONArray object = new JSONArray();
			CommunityMsg msg = message.get(i);
			try {
				object.put(msg.getCommMsgId());
				object.put(msg.getMsgTitle());
				object.put(msg.getMsgDescription());
				if( msg.getLatlong() == null || msg.getLatlong().equals("none") || msg.getLatlong().equals("") ) {
					object.put("None");
				} else {
					object.put("<a class='fancybox fancybox.iframe' href='showLocationInMap?latLong="+msg.getLatlong()+"'>Location</a>");
				}
			} catch (Exception e) {
				System.out.println("Exception in Object.put");
				e.printStackTrace();
			}
			vReturnObjects.put(object);
		}
		JSONObject ret = new JSONObject();
		ret.put("aaData", vReturnObjects);
		return ret;
	}
	
	public JSONArray getCommunityMsgJSON()
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
	
	public CommunityMsg saveOrUpdate( CommunityMsg msg ) {
		return commServ.save(msg);
	}
}
