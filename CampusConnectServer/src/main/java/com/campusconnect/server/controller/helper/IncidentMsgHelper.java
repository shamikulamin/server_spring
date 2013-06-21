package com.campusconnect.server.controller.helper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.ui.Model;

import com.campusconnect.server.domain.IncidentMsg;
import com.campusconnect.server.domain.IncidentPicture;
import com.campusconnect.server.service.IncidentMsgService;

public class IncidentMsgHelper {
	private IncidentMsgService incServ;
	
	public IncidentMsgHelper() {
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:app-context.xml");
		ctx.refresh();
		incServ = ctx.getBean("jpaIncidentMsgService", IncidentMsgService.class);
	}
	
	public List<IncidentMsg> getIncidentMessages() {
		return incServ.getAll();
	}
	
	public IncidentMsg getIncidentById(Long id) {
		return incServ.getById(id);
	}
	
	public void insertIncidentMsg(String msg_title, String msg_description, String time, String latLong, ArrayList<String> imagePaths){
		Set<IncidentPicture> iPics = new HashSet<IncidentPicture>(0);
		
		IncidentMsg newMessage = new IncidentMsg(msg_title, msg_description, new java.sql.Timestamp(Long.parseLong(time)), latLong, iPics);
		incServ.save(newMessage);
		for(int i=0; i < imagePaths.size(); i++){
			IncidentPicture newPic = new IncidentPicture();
			newPic.setPicture(imagePaths.get(i));
			iPics.add(newPic);
		}
	}
	
	public void delete(IncidentMsg msg) {
		incServ.delete(msg);
	}
	
	public IncidentMsg saveOrUpdate(IncidentMsg msg) {
		return incServ.save(msg);
	}
}
