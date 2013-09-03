package com.campusconnect.server.controller.helper;

import java.util.ArrayList;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.campusconnect.server.domain.IosDevice;
import com.campusconnect.server.service.IosDeviceService;

public class IosDeviceHelper {
	private IosDeviceService iosDevServ;
	
	public IosDeviceHelper(ServletContext sc) {
		//GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		//ctx.load("classpath:app-context.xml");
		//ctx.refresh();
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
		iosDevServ = ctx.getBean("jpaIosDeviceService", IosDeviceService.class);
	}
	
	public ArrayList<String> getDeviceList() {
		return new ArrayList<String>(iosDevServ.getAllIds());
	}
	
	public void delete(IosDevice dev) {
		iosDevServ.delete(dev);
	}
	
	public void insertDevice(IosDevice dev) {
		iosDevServ.save(dev);
	}
	
	public void deleteById(String id) {
		iosDevServ.deleteById(id);
	}
	
	public IosDevice getById(String id) {
		return iosDevServ.getById(id);
	}
	
	public IosDevice saveOrUpdate(IosDevice dev) {
		return iosDevServ.save(dev);
	}
}
