package com.campusconnect.server.controller.helper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.campusconnect.server.domain.IncidentMsg;
import com.campusconnect.server.domain.IncidentPicture;
import com.campusconnect.server.domain.IncidentRecording;
import com.campusconnect.server.service.IncidentMsgService;

public class IncidentMsgHelper {
	private IncidentMsgService incServ;
	
	public IncidentMsgHelper(ServletContext sc) {
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
		incServ = ctx.getBean("jpaIncidentMsgService", IncidentMsgService.class);
	}
	
	public List<IncidentMsg> getIncidentMessages() {
		return incServ.getAll();
	}
	
	public IncidentMsg getIncidentById(Long id) {
		return incServ.getById(id);
	}
	
	public void insertIncidentMsg(String msg_title, String msg_description, String time, String latLong, ArrayList<String> imagePaths, ArrayList<String> recPaths){
		Set<IncidentPicture> iPics = new HashSet<IncidentPicture>(0);
		Set<IncidentRecording> iRecs = new HashSet<IncidentRecording>(0);
		
		IncidentMsg newMessage = new IncidentMsg(msg_title, msg_description, new java.sql.Timestamp(Long.parseLong(time)), latLong, iPics, iRecs);
		incServ.save(newMessage);
		for(int i=0; i < imagePaths.size(); i++){
			IncidentPicture newPic = new IncidentPicture();
			newPic.setPicture(imagePaths.get(i));
			iPics.add(newPic);
		}
		for(int i=0; i < recPaths.size(); i++){
			IncidentRecording newRec = new IncidentRecording();
			newRec.setRecording(recPaths.get(i));
			iRecs.add(newRec);
		}
	}
	
	public JSONObject getAllIncidentMsgJSON() {
		JSONArray vReturnObjects = new JSONArray();
		List<IncidentMsg> message = incServ.getAll();
		for(int i=0; i < message.size(); i++){
			JSONArray object = new JSONArray();
			IncidentMsg msg = message.get(i);
			try {
				object.put(msg.getIncidentId());
				object.put(msg.getMsgTitle());
				if( msg.getMsgDescription().length() > 100 )
					object.put("<a class='fancybox fancybox.iframe' href='showLongDesc?desc="+msg.getMsgDescription()+"'>Description</a>");
				else 
					object.put(msg.getMsgDescription());
				if( !msg.getLatlong().equals("none") ) {
					object.put("<a class='fancybox fancybox.iframe' href='showLocationInMap?latLong="+msg.getLatlong()+"'>Location</a>");
				} else {
					object.put("None");
				}
				ArrayList<IncidentPicture> pics = new ArrayList<IncidentPicture> (msg.getIncidentPictures());
				String listOfPicAnchors = "";
				for( IncidentPicture aPic: pics ) {
					String path = aPic.getPicture();
					if( path.startsWith("C:/"))
						path = path.substring(3);	// Cut off the C:/
					else if( path.startsWith("/home/crewman/") )
						path = path.substring(14);
	            	listOfPicAnchors += "<a class='fancybox' rel='gallery"+msg.getIncidentId()+"'href='"+path+"' title='PIC'></a>\n";
				}
				if( pics.size() > 0 ) {
					object.put("<a href='#group"+msg.getIncidentId()+"' onclick='return openFancyBox(this)'>Images</a> <span style='display:none;'>"+listOfPicAnchors+"</span>");
				} else {
					object.put("None");
				}
				
				ArrayList<IncidentRecording> recs = new ArrayList<IncidentRecording> (msg.getIncidentRecordings());
				if( recs.size() > 0 ) {
					String path = recs.get(0).getRecording();
					if( path.startsWith("C:/"))
						path = path.substring(3);	// Cut off the C:/
					else if( path.startsWith("/home/crewman/") )
						path = path.substring(14);
					String name = msg.getContactName();
					if( name == null )
						name = "";
					String num = msg.getContactNumber();
					if( num == null )
						num = "";
					String address = msg.getContactAddress();
					if( address == null )
						address = "";
					address = address.replace('$', ' ');
					object.put("<a class='fancybox fancybox.iframe' href='showRecording?recName="+path+"&conName="+name+"&conNum="+num+"&conAdd="+address+"'>Info</a>");
				} else {
					String name = msg.getContactName();
					if( name == null )
						name = "";
					String num = msg.getContactNumber();
					if( num == null )
						num = "";
					String address = msg.getContactAddress();
					if( address == null )
						address = "";
					address = address.replace('$', ' ');
					object.put("<a class='fancybox fancybox.iframe' href='showRecording?recName="+"none"+"&conName="+name+"&conNum="+num+"&conAdd="+address+"'>Info</a>");
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
	
	public void delete(IncidentMsg msg) {
		incServ.delete(msg);
	}
	
	public IncidentMsg saveOrUpdate(IncidentMsg msg) {
		return incServ.save(msg);
	}
}
