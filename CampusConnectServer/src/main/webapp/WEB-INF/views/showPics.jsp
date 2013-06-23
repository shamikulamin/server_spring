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
		 @import "js/pics/css/zoom.css";		
        </style>

        <script type="text/javascript" charset="utf-8" src="js/DataTables-1.9.2/media/js/jquery.js">
        </script>
        
        <script type="text/javascript" src="js/pics/js/zoom.js"></script>
        
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
         %>
           		<li><a href="<%=aPic.getPicture()%> title="PIC"><img src="<%=aPic.getPicture()%>.jpg_thumb.jpg" title="PIC"/></a></li>
         <%
                i++;
            }
         %>
        </ul>
        
        </div>
         
         
    </body>
</html>
