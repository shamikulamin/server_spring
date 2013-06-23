package com.campusconnect.server.controller.helper;

import org.springframework.context.support.GenericXmlApplicationContext;

import com.campusconnect.server.domain.IncidentPicture;
import com.campusconnect.server.service.IncidentPictureService;

public class IncidentPictureHelper {
	private IncidentPictureService incPicServ;
	
	public IncidentPictureHelper() {
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:app-context.xml");
		ctx.refresh();
		incPicServ = ctx.getBean("jpaIncidentPictureService", IncidentPictureService.class);
	}
	
	public IncidentPicture saveOrUpdate(IncidentPicture pic) {
		return incPicServ.save(pic);
	}
}
