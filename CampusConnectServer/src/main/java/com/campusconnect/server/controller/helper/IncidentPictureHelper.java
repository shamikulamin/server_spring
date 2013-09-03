package com.campusconnect.server.controller.helper;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.campusconnect.server.domain.IncidentPicture;
import com.campusconnect.server.service.IncidentPictureService;

public class IncidentPictureHelper {
	private IncidentPictureService incPicServ;
	
	public IncidentPictureHelper(ServletContext sc) {
		//GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		//ctx.load("classpath:app-context.xml");
		//ctx.refresh();
		//incPicServ = ctx.getBean("jpaIncidentPictureService", IncidentPictureService.class);
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
		incPicServ = ctx.getBean("jpaIncidentPictureService", IncidentPictureService.class);
	}
	
	public IncidentPicture saveOrUpdate(IncidentPicture pic) {
		return incPicServ.save(pic);
	}
}
