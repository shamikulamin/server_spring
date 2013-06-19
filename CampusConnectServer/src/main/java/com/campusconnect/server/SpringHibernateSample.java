package com.campusconnect.server;

import java.util.List;

import org.springframework.context.support.GenericXmlApplicationContext;

import com.campusconnect.server.domain.CommunityMsg;
import com.campusconnect.server.domain.Device;
import com.campusconnect.server.domain.IncidentMsg;
import com.campusconnect.server.service.CommunityMsgService;
import com.campusconnect.server.service.DeviceService;
import com.campusconnect.server.service.IncidentMsgService;

public class SpringHibernateSample {
	public static void main(String[] args) {
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:app-context.xml");
		ctx.refresh();
		
		DeviceService devServ = ctx.getBean("jpaDeviceService", DeviceService.class);
		CommunityMsgService commServ = ctx.getBean("jpaCommunityMsgService", CommunityMsgService.class);
		IncidentMsgService imServ = ctx.getBean("jpaIncidentMsgService", IncidentMsgService.class);
		Integer idtest = 1;
		System.out.println(commServ.getById(idtest));
		
		List<Device> devices  = devServ.getAll();
		System.out.println("Devices before delete:");
		for( Device d: devices ) {
			System.out.println(d.getRegId());
		}
		
		List<CommunityMsg> commMsgs = commServ.getAll();
		System.out.println("\nCommMsgs:");
		for( CommunityMsg cm: commMsgs ) {
			System.out.println(cm);
		}
		
		List<IncidentMsg> incMsgs = imServ.getAll();
		System.out.println("\nIncMsgs:");
		for( IncidentMsg im: incMsgs ) {
			System.out.println(im);
		}
		/*devServ.deleteById("1");
		devices = devServ.getAll();
		System.out.println("Devices after delete:");
		for( Device d: devices ) {
			System.out.println(d.getRegId());
		}*/
		/*
		ContactDao contactDao = ctx.getBean("contactDao", ContactDao.class);
		List<Contact> contacts = contactDao.findAllWithDetail();
		listContactsWithDetail(contacts);
		
		// Find contact by ID
		Contact contact = contactDao.findById(1l);
		System.out.println("");
		System.out.println("Contact with id 1:" + contact);
		System.out.println("");
		
		// Add new contact
		contact = new Contact();
		contact.setFirstName("Michael");
		contact.setLastName("Jackson");
		contact.setBirthDate(new Date());
		ContactTelDetail contactTelDetail =
		new ContactTelDetail("Home", "1111111111");
		contact.addContactTelDetail(contactTelDetail);
		contactTelDetail = new ContactTelDetail("Mobile", "2222222222");
		contact.addContactTelDetail(contactTelDetail);
		contactDao.save(contact);
		contacts = contactDao.findAllWithDetail();
		listContactsWithDetail(contacts);
		
		// Update contact
		contact = contactDao.findById(1l);
		contact.setFirstName("Kim Fung");
		Set<ContactTelDetail> contactTels = contact.getContactTelDetails();
		ContactTelDetail toDeleteContactTel = null;
		for (ContactTelDetail contactTel: contactTels) {
			if (contactTel.getTelType().equals("Home")) {
				toDeleteContactTel = contactTel;
			}
		}
		contact.removeContactTelDetail(toDeleteContactTel);
		contactDao.save(contact);
		contacts = contactDao.findAllWithDetail();
		listContactsWithDetail(contacts);
		
		// Delete contact
		contact = contactDao.findById(1l);
		contactDao.delete(contact);
		contacts = contactDao.findAllWithDetail();
		listContactsWithDetail(contacts);*/
	}
}