package com.campusconnect.server.controller.helper;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
	private static final String API_KEY = "AIzaSyCVZ_FxaGmbvCjbJ_vShvdL3g-86pJIZR0";
	Model model;
	
	public CampusConnectWebHelper(Model uiModel) {
		model = uiModel;
	}
	
	public void getIncidentMsgs() {
		IncidentMsgHelper helper = new IncidentMsgHelper();
		List<IncidentMsg> result = helper.getIncidentMessages();
		model.addAttribute("incidentMessages", result);
	}
	
	public void getPostedMessages() {
		CommunityMsgHelper helper = new CommunityMsgHelper();
		List<CommunityMsg> result = helper.getCommunityMessages();
		model.addAttribute("communityMessages", result);
	}
	
	public void showPics(Long incidentId) {
        IncidentMsgHelper msgHelper = new IncidentMsgHelper();
        ArrayList<IncidentPicture> result = new ArrayList<IncidentPicture> (msgHelper.getIncidentById(incidentId).getIncidentPictures());
        model.addAttribute("incidentPictures",result);
	}
	
    public void handlePost(String msgType, String message, String messageTitle, String expiryHours, String expiryDays, String locationList, String bPush) {

    	CommunityMsgHelper helper = new CommunityMsgHelper();
        long delay = Long.parseLong(expiryHours) * 60 * 60 * 1000 + Long.parseLong(expiryDays) * 24 * 60 * 60 * 1000;
        if(delay == 0)
            delay = 24 * 60 * 60 * 1000;

        String location = new String();
        
        if(locationList == null || locationList.equals(""))
        	location = "none";
        else
        	location = formatLatLong(locationList);
        
        CommunityMsg newMessage = new CommunityMsg(messageTitle, message,
        											getCurrentDate(System.currentTimeMillis()), location, msgType,
        											getExpiryDate(System.currentTimeMillis(), delay));
        helper.saveOrUpdate(newMessage);

        int iMsgID = newMessage.getCommMsgId();
        if (bPush!=null)
        	sendPushNotification(messageTitle, iMsgID, msgType);
    }
    
   
    private ArrayList<String> getDeviceList() {
    	return new DeviceHelper().getDeviceList();
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
    		DeviceHelper devHelp = new DeviceHelper();

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
