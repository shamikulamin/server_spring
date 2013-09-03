package com.campusconnect.server.controller.helper;

import java.util.ArrayList;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.campusconnect.server.domain.Device;
import com.campusconnect.server.service.DeviceService;

public class DeviceHelper {
	private DeviceService devServ;
	
	public DeviceHelper(ServletContext sc) {
		//GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		//ctx.load("classpath:app-context.xml");
		//ctx.refresh();
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
		devServ = ctx.getBean("jpaDeviceService", DeviceService.class);
	}
	
	public ArrayList<String> getDeviceList() {
		return new ArrayList<String>(devServ.getAllIds());
	}
	
	public void delete(Device dev) {
		devServ.delete(dev);
	}
	
	public void insertDevice(Device dev) {
		devServ.save(dev);
	}
	
	public void deleteById(String id) {
		devServ.deleteById(id);
	}
	
	public Device getById(String id) {
		return devServ.getById(id);
	}
	
	public Device saveOrUpdate(Device dev) {
		return devServ.save(dev);
	}
}
