<%-- 
    Document   : showLocationInMap
    Created on : Jul 10, 2012, 5:49:42 PM
    Author     : Adnan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Show Description</title>

        <style type="text/css">
             html { height: 100% }
		     body { height: 100%; margin: 0px; padding: 0px }
		     #map_canvas { height: 100% }
        </style>   
    </head>
    <body>
    	<textarea>
			<%=request.getParameter("desc")%>
		</textarea>
    </body>
</html>
