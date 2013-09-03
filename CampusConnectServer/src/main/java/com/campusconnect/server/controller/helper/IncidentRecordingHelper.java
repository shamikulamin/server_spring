package com.campusconnect.server.controller.helper;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.campusconnect.server.domain.IncidentRecording;
import com.campusconnect.server.service.IncidentRecordingService;

public class IncidentRecordingHelper {
	private IncidentRecordingService incRecServ;
	
	public IncidentRecordingHelper(ServletContext sc) {
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
		incRecServ = ctx.getBean("jpaIncidentRecordingService", IncidentRecordingService.class);
	}
	
	public IncidentRecording saveOrUpdate(IncidentRecording rec) {
		return incRecServ.save(rec);
	}
}
