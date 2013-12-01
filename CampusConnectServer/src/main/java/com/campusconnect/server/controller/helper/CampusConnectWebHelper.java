package com.campusconnect.server.controller.helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.ui.Model;

import com.campusconnect.server.domain.CommunityMsg;
import com.campusconnect.server.domain.IncidentMsg;
import com.campusconnect.server.domain.IncidentPicture;
import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

public class CampusConnectWebHelper {
	private static final String API_KEY = "AIzaSyD9Iciki8Kgx1vgQXtfrGcSQK14vAsDlTk";
	private static final Logger logger = Logger.getLogger(CampusConnectWebHelper.class);
	private Model model;
	private ServletContext servletContext;

	
	public CampusConnectWebHelper(Model uiModel, ServletContext sc) {
		model = uiModel;
		servletContext = sc;
	}
	
	public void getIncidentMsgs() {
		IncidentMsgHelper helper = new IncidentMsgHelper(servletContext);
		List<IncidentMsg> result = helper.getIncidentMessages();
		model.addAttribute("incidentMessages", result);
	}
	
	public void getPostedMessages() {
		CommunityMsgHelper helper = new CommunityMsgHelper(servletContext);
		List<CommunityMsg> result = helper.getCommunityMessages();
		model.addAttribute("communityMessages", result);
	}
	
	public void showPics(Long incidentId) {
        IncidentMsgHelper msgHelper = new IncidentMsgHelper(servletContext);
        ArrayList<IncidentPicture> result = new ArrayList<IncidentPicture> (msgHelper.getIncidentById(incidentId).getIncidentPictures());
        model.addAttribute("incidentPictures",result);
	}
	
    public void handlePost(String msgType, String message, String messageTitle, String expiryDateTime, String locationList, String bPush) {

    	CommunityMsgHelper helper = new CommunityMsgHelper(servletContext);
    	
        long time = 0;
        Date date = null;
        String location = new String();
        
        if(locationList == null || locationList.equals(""))
        	location = "none";
        else
        	location = formatLatLong(locationList);
        
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			date = dateFormat.parse(expiryDateTime);
			time = date.getTime();
			if( time <= System.currentTimeMillis() ) {
				time = System.currentTimeMillis() + 86400000;	// + 24 hours
				date = new Date(time);
			}
		} catch (ParseException e) {
			date = new Timestamp(System.currentTimeMillis());
		}
        
        CommunityMsg newMessage = new CommunityMsg(messageTitle, message,getCurrentDate(System.currentTimeMillis()), location, msgType, date);
        helper.saveOrUpdate(newMessage);

        int iMsgID = newMessage.getCommMsgId();
        if (bPush!=null) {
        	sendPushNotification(messageTitle, iMsgID, msgType);
        	sendPushNotificationIos(messageTitle, iMsgID, msgType);
        }
    }
    
   
    private ArrayList<String> getDeviceList() {
    	return new DeviceHelper(servletContext).getDeviceList();
	}
    
    private ArrayList<String> getIosDeviceList() {
    	return new IosDeviceHelper(servletContext).getDeviceList();
    }
    
    private void sendPushNotificationIos(String alertTitle, int iMsgID, String msgType) {

    	List<PushedNotification> notifications = null;
    	ArrayList<String> devices = getIosDeviceList();
    	System.out.println("IOS DEVICES: " + devices);
    	/* Send Notifications to IOS Devices */
    	PushNotificationPayload payload = PushNotificationPayload.complex();
    	try {
    		payload.addAlert(alertTitle);
    		payload.addCustomDictionary("msgID",Integer.toString(iMsgID));
 
			notifications = Push.payload(payload, servletContext.getResourceAsStream("/WEB-INF/keys/keystore.p12"), "crewman123", false, devices);
		} catch (CommunicationException e) {
			e.printStackTrace();
		} catch (KeystoreException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	
    	IosDeviceHelper iosDevHelp = new IosDeviceHelper(servletContext);

    	for( int i = 0; i < notifications.size(); i++ ) {
    		if( !notifications.get(i).isSuccessful() ) {
    			String invalidToken = notifications.get(i).getDevice().getToken();
    			iosDevHelp.deleteById(invalidToken);
    			System.out.println("Exception: " + notifications.get(i).getException());
    		} else {
    			System.out.println("Push Sent to " + notifications.get(i).getDevice().getToken());
    		}
    	}
    	
    	
	}
    
    private void sendPushNotification(String alertTitle, int iMsgID, String msgType) {

    	ArrayList<String> devices = getDeviceList();
    	/* Send Notifications and update our database if some error occurs */
    	try {

    		/* Send Push Notifications to all devices in list */
    		Sender sender = new Sender(API_KEY);
    		Message message = new Message.Builder().addData("title", alertTitle).addData("msgType",msgType).addData("msgID",Integer.toString(iMsgID)).build();
    		MulticastResult result = sender.send(message, devices, 5);
    		System.out.println( result.getSuccess()+" notifications successfully sent");
    		DeviceHelper devHelp = new DeviceHelper(servletContext);

    		for (int i = 0; i < result.getTotal(); i++) {
    			Result r = result.getResults().get(i);

    			if (r.getMessageId() != null) {
    				String canonicalRegId = r.getCanonicalRegistrationId();
    				if (canonicalRegId != null) {
    					devHelp.saveOrUpdate(devHelp.getById(canonicalRegId));                                
    				}
    			} else {
    				String error = r.getErrorCodeName();
    				if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
    					devHelp.deleteById(devices.get(i));                                                               
    				}
    			}
    		}
    	} catch (IOException e) {
    		e.printStackTrace();
    	} 
	}
    
    private String formatLatLong(String latLongList){
        String formattedLatLong = "";
        StringBuilder locationListModified = new StringBuilder();
        for(int i = 0 ; i < latLongList.length();++i){
            if(latLongList.charAt(i) == '(')
                continue;
            if(latLongList.charAt(i) == ')'){
                locationListModified.append('|');
                i+=1;
            } else{
                locationListModified.append(latLongList.charAt(i));
            }
        }
        formattedLatLong = locationListModified.toString();
        formattedLatLong = formattedLatLong.substring(0,formattedLatLong.length()-1);
        return formattedLatLong;
    }
    
    private static Timestamp getCurrentDate(long curTime) {
        return new java.sql.Timestamp(curTime);
    }
    
     private static Timestamp getExpiryDate(long curTime,long delay) {
        return new java.sql.Timestamp(curTime + delay);
    }
}
