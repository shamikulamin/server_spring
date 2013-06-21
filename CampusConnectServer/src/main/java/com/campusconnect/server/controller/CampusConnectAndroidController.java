package com.campusconnect.server.controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.campusconnect.server.controller.helper.CommunityMsgHelper;
import com.campusconnect.server.controller.helper.IncidentMsgHelper;
import com.campusconnect.server.domain.IncidentMsg;

@Controller
@RequestMapping(value = "/campus_connect_servlet")
public class CampusConnectAndroidController {
	
	@RequestMapping(value = "/sendReport", method = RequestMethod.POST)
	@ResponseBody
	public void sendReport(HttpServletRequest req, HttpServletResponse res) {
		List<FileItem> items = null;
		String latitude = "", longitude = "";
	    ArrayList<String> imagePaths = new ArrayList<String>();
	    IncidentMsg newMsg = new IncidentMsg();
	    IncidentMsgHelper helper = new IncidentMsgHelper();
	    
		// Check that we have a file upload request
		if( ServletFileUpload.isMultipartContent(req) ) {
			// Create a factory for disk-based file items
			DiskFileItemFactory factory = new DiskFileItemFactory();

			// Configure a repository (to ensure a secure temp location is used)
			ServletContext servletContext = req.getSession().getServletContext();
			File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
			factory.setRepository(repository);

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			// Parse the request
			try {
				items = upload.parseRequest(req);
			} catch (FileUploadException e) {
				System.out.println("Couldn't parse request");
				return;
			}
			
			// Persist new msg to db so we can use it's id as a path
			helper.saveOrUpdate(newMsg);
    		String sImagePath = "C:/incidentImages/"+newMsg.getIncidentId()+"/";
    		
    		// Set up our new directory for this report
    		boolean success = (new File(sImagePath)).mkdirs();
 	        if (success) {
 	            System.out.println("Directory: " + sImagePath + " created");
 	        }  
 	        new File(sImagePath);
			
			// Process the uploaded items
			Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext()) {
			    FileItem item = iter.next();

			    if (item.isFormField()) {
			    	if(item.getFieldName().equals("message")) {
			    		newMsg.setMsgDescription(item.getString());
					} else if(item.getFieldName().equals("latitude")) {
						latitude = item.getString();
					} else if(item.getFieldName().equals("longitude")) { 
						longitude = item.getString();
					} else if(item.getFieldName().equals("message_title")) {
						newMsg.setMsgTitle(item.getString());
					} else if(item.getFieldName().equals("reporting_time")) {
						newMsg.setReportingTime(new java.sql.Timestamp(Long.parseLong(item.getString())));
					} 
			    } else {	// File Part
			    	if (item.getFieldName().equals("image"))
					{
			    		try {
							String fileName = item.getName();
							InputStream fileContent = item.getInputStream();
	
							BufferedImage bImageFromConvert = ImageIO.read(fileContent);
							imagePaths.add(sImagePath+fileName);
	
							File vImageFile = new File(sImagePath+fileName);
	
							ImageIO.write(bImageFromConvert, "jpg", vImageFile);
	
							BufferedImage img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
							img.createGraphics().drawImage(ImageIO.read(vImageFile).getScaledInstance(100, 100, Image.SCALE_SMOOTH),0,0,null);
							ImageIO.write(img, "jpg", new File(sImagePath+fileName+"_thumb.jpg"));
			    		} catch ( IOException e ) {
			    			System.out.println("Error writing images: " + e.getMessage());
			    			helper.delete(newMsg);
			    		}
					}
			    }
			}
			newMsg.setLatlong(latitude + "," + longitude);
			helper.saveOrUpdate(newMsg);
		}
	}
	
	@RequestMapping(value = "/getCommunityMsg", method = RequestMethod.POST)
	@ResponseBody
	public String getCommunityMsg(HttpServletRequest req, HttpServletResponse res) {
		return new CommunityMsgHelper().getCommunityMsgJSON().toString();
	}
	
	@RequestMapping(value = "/getCommunityMsgById/{id}", method = RequestMethod.POST)
	@ResponseBody
	public String getCommunityMsgById(@PathVariable int id, HttpServletRequest req, HttpServletResponse res) {
		return new CommunityMsgHelper().getCommunityMsgByID(id).toString();
	}
	
	@RequestMapping(value = "/getCommunityMsgForMap", method = RequestMethod.POST)
	@ResponseBody
	public String getCommunityMsgForMap(HttpServletRequest req, HttpServletResponse res) {
		return new CommunityMsgHelper().getCommunityMsgForMap().toString();
	}
	
	@RequestMapping(value = "/getCommMsgDesc/{id}", method = RequestMethod.POST)
	@ResponseBody
	public String getCommMsgDesc(@PathVariable int id, HttpServletRequest req, HttpServletResponse res) {
		return new CommunityMsgHelper().getCommunityMsgDesc(id);
	}
}
