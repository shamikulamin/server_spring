<%-- 
    Document   : showPics
    Created on : Jul 11, 2012, 12:13:17 PM
    Author     : Adnan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.campusconnect.server.controller.helper.IncidentMsgHelper" %>
<%@ page import="com.campusconnect.server.domain.IncidentPicture" %>
<%@ page import="com.campusconnect.server.domain.IncidentMsg" %>
<%@ page import="java.util.ArrayList" %>
<jsp:useBean id="incidentPictures" type="ArrayList<IncidentPicture>" scope="request" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
         
        <style type="text/css" title="currentStyle">
		 @import "resources/js/pics/css/zoom.css";		
        </style>

        <script type="text/javascript" charset="utf-8" src="resources/js/DataTables-1.9.2/media/js/jquery.js">
        </script>
        
        <script type="text/javascript" src="resources/js/pics/js/zoom.js"></script>
        
        <script type="text/javascript">
               
             $(function(){
                 //alert("adnan");
                 $.zoom();
             });   
            
        </script>
    </head>
    <body>
         
        <div class="container">
	<h3>Images</h3>
        <ul class="gallery">
         <% 
            int i=0;
            for(IncidentPicture aPic : incidentPictures)
            {
            	String path = aPic.getPicture();
            	path = path.substring(3);	// Cut off the C:/
         %>
           		<li><a href="<%=path%>" title="PIC"><img src="<%=path%>.jpg_thumb.jpg" title="PIC"/></a></li>
         <%
                i++;
            }
         %>
        </ul>
        
        </div>
         
         
    </body>
</html>
