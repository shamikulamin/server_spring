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
        <title>ShowLocationInMap</title>
        
        <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?v=3.exp&amp;sensor=false"></script>
        <script type='text/javascript' src="./media/js/jquery.js"></script>
		<script type='text/javascript' src="./media/ui/jquery.ui.core.js"></script>
        <style type="text/css">
             html { height: 100% }
		     body { height: 100%; margin: 0px; padding: 0px }
		     #map_canvas { height: 100% }
        </style>
        
        <script type="text/javascript">

            function show(latLongList) {
                var myLatlng;
                var locations = [];
                if( latLongList == "none" ) {
                	myLatlng = new google.maps.LatLng(32.730500,-97.113047);
                	locations.length = 0;
                } else {
                 	locations = latLongList.split("|");
                	var firstLatLong = locations[0].split(",");
                	myLatlng = new google.maps.LatLng(firstLatLong[0],firstLatLong[1]);
                }
                
                
                var myOptions = {
                    zoom: 16,
                    center: myLatlng,
                    mapTypeId: google.maps.MapTypeId.HYBRID
                }
                map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
                google.maps.event.addListenerOnce(map, 'idle', function() {
                	var latLongList = "<%= request.getParameter("latLong")%>";
    	            if( latLongList == "none" ) {
    	            	alert("This report has no location");
    	            }
                });
                
                if( latLongList != "none" ) 
                var marker = new google.maps.Marker({
                        position: myLatlng,
                        map: map

                    });
                for(i = 1; i < locations.length; i++) {
                    var parts = locations[i].split(",");
                    var singleLatLong = new google.maps.LatLng(parts[0],parts[1]);
                
                
                    var marker1 = new google.maps.Marker({
                        position: singleLatLong,
                        map: map

                    });
                
                }
            } 
        </script>
    </head>
    <body onload="show('<%= request.getParameter("latLong")%>');" >
    	<div id="map_canvas"></div>
    </body>
</html>
