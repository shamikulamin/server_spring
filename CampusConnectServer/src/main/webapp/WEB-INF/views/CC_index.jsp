<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	
	<!-- Include all of our styles -->
	<link rel="stylesheet" href="./media/css/campusconnectstyle.css" type="text/css" media="screen" charset="utf-8"/>
	<link rel="stylesheet" href="./media/css/demo_page.css" type="text/css" media="screen" charset="utf-8" />
	<link rel="stylesheet" href="./media/css/demo_table.css" type="text/css" media="screen" charset="utf-8" />
	<link rel="stylesheet" href="./media/css/jquery.fancybox.css?v=2.1.5" type="text/css" media="screen" />
	
	<!-- Include all of our scripts -->
	<script type='text/javascript' src="./media/js/jquery.js"></script>
	<script type='text/javascript' src="./media/ui/jquery.ui.core.js"></script>
    <script type='text/javascript' src="./media/js/sliding.js"></script>
	<script type="text/javascript" src="./media/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="./media/js/jquery.fancybox.pack.js?v=2.1.5"></script>
	<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?v=3.exp&amp;sensor=false"></script>
	
	<title>Index</title>
	<script>
	
		function openFancyBox( element ) {
			var parent = element.parentNode;
			var spanchild = $(parent).find("span");
			spanchild.children(":first").click();
			return false;
		}
		
		function autoReload(table) {
			table.fnReloadAjax();
			setTimeout(function(){autoReload(table);}, 30000);
		}
		
		function check() {
            var msgTitle = document.getElementById('msgTitle').value;
            var message = document.getElementById('message').value;
            var expiryDateTime = document.getElementById('expirydatetime').value;
            
            if(msgTitle == null || msgTitle == ""){
                alert ('Message Title cannot be empty!');
                return false;
            }
            if(message == null || message == ""){
                alert ('Message cannot be empty!');
                return false;
            }
            if(expiryDateTime == null || expiryDateTime == ""){
                alert ('Expiry Date & Time cannot be empty!');
                return false;
            }
            
            return true;
        }
		
		$.fn.dataTableExt.oApi.fnReloadAjax = function ( oSettings, sNewSource, fnCallback, bStandingRedraw )
		{
		    if ( typeof sNewSource != 'undefined' && sNewSource != null )
		    {
		        oSettings.sAjaxSource = sNewSource;
		    }
		    this.oApi._fnProcessingDisplay( oSettings, true );
		    var that = this;
		    var iStart = oSettings._iDisplayStart;
		     
		    oSettings.fnServerData( oSettings.sAjaxSource, [], function(json) {
		        /* Clear the old information from the table */
		        that.oApi._fnClearTable( oSettings );
		         
		        /* Got the data - add it to the table */
		        for ( var i=0 ; i<json.aaData.length ; i++ )
		        {
		            that.oApi._fnAddData( oSettings, json.aaData[i] );
		        }
		         
		        oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
		        that.fnDraw();
		         
		        if ( typeof bStandingRedraw != 'undefined' && bStandingRedraw === true )
		        {
		            oSettings._iDisplayStart = iStart;
		            that.fnDraw( false );
		        }
		         
		        that.oApi._fnProcessingDisplay( oSettings, false );
		         
		        /* Callback user function - for event handlers etc */
		        if ( typeof fnCallback == 'function' && fnCallback != null )
		        {
		            fnCallback( oSettings );
		        }
		    }, oSettings );
		};
		
		var map;
		var locations = new Array();
		//var locations = new  Array();
		function initialize() {
			var myLatlng = new google.maps.LatLng(32.730500,-97.113047);
			var myOptions = {
				zoom: 16,
				center: myLatlng,
				mapTypeId: google.maps.MapTypeId.HYBRID
			}
			map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
			
			
			google.maps.event.addListener(map, 'click', function(event) {
				placeMarker(event.latLng);
				document.getElementById("location_list").value = locations.toString();
			});
		}
		
		function placeMarker(location) {
			var marker = new google.maps.Marker({
				position: location,
				map: map

			});
			locations.push(marker.getPosition().toString());
			google.maps.event.addListener(marker,'rightclick',function(event){
				removeByValue(locations,marker.getPosition().toString());
				document.getElementById("location_list").value = locations.toString();
				marker.setMap(null);
			   
			});
		}
		
		$(document).ready(function() {
			var oTable = $('#example').dataTable({
					"aoColumns": [	{ "sWidth": "5%", "sClass": "center" },
					              	{ "sWidth": "20%", "sClass": "center" },
					              	{ "sWidth": "40%", "sClass": "center" },
				             		{ "sWidth": "10%", "sClass": "center", "bSortable": false },
				             		{ "sWidth": "10%", "sClass": "center", "bSortable": false },
				             		{ "sWidth": "10%", "sClass": "center", "bSortable": false }],
				    "aaSorting":	[[0,'desc']],
					"sAjaxSource": './ajax/incidentMsgs',
					"bProcessing": true,
					"bAutoWidth": false
			});
			
			$(".fancybox").fancybox({
				openEffect	: 'none',
				closeEffect	: 'none'
			});
			
			slide("#left_nav", 25, 15, 150, .8);
			setTimeout(function(){autoReload(oTable);}, 30000);
		});
	</script>
</head>

<body onload="initialize()">
	<div id='content_wrapper'>
    	<div id='header'>
        	<div id='welcome'>
            	<a href='.'><img src="./media/images/CampusConnectLogo.png" alt="Campus Connect"></img></a>
            </div>
            <div id='top_nav'>
            </div>
        </div>
        <div id='content'>
            <div id='left_nav'>
            	<ul>
                	<li class='sliding-element'><a href=".">Home</a></li>
                    <li class='sliding-element'><a href="accountMaint">Account Maintenance</a></li>
                    <li class='sliding-element'><a href="viewCommunityMsgs">Check Posted Messages</a></li>
                    <li class='sliding-element'><a href="logout">Logout</a></li>
                </ul>
            </div>
            <div id='main'>
            	<div id='filtering'>
                	<h1>Incident Messages</h1>
					<table id ="example" style="width: 100%;">
						<thead>
							  <tr>
									<th>No</th>
									<th>Title</th>
									<th>Description</th>
									<th>Location</th>
									<th>Pictures</th>
									<th>Info/Recordings</th>
							  </tr>
						</thead>
					</table>
                </div>
            	<div id='data'>
                	<h1>Post Messages</h1>
					<form action="postMessage" class="cmxform" name="msgForm"  method="post" onsubmit="return check()" style="display: block; ">
						<p>Please complete the form below. Mandatory fields marked <em>*</em></p>
						<fieldset>
						  <legend>Delivery Details</legend>
						  <ol>
						  <li>
							  <div id="map_canvas" style="width: 1107px; height: 400px"></div>
							  <input type="hidden" name="location_list" id="location_list" value="" /> 
						  </li>
						  <li>
							  <label for="messagetype">Message Type<em>*</em></label>
							  <select id="messagetype" name="messagetype">
								<option value="parking">Parking Alert</option>
								<option value="crimealert">Crime Alert</option>
								<option value="weather">Weather Alert</option>
								<option value="general">General Information</option>
							  </select>
							</li>
						  <li>
							  <label for="expirydatetime">Expire Date/Time<em>*</em> (Ex: yyyy-MM-dd'T'HH:mm)</label>
							  <input type="datetime-local" id="expirydatetime" name="expirydatetime"/>
							</li>
						  <li>
							  <label for="msgTitle">Message title:<em>*</em></label>
							  <input type="text" class="input" name="msgTitle" id="msgTitle" />
							</li>
						  <li>
							  <label for="message">Message text:<em>*</em></label>
							  <textarea class="input textarea" style="height:120px; width:84%;" name="message" id="message"></textarea>
							</li>
						  <li>
							  <label for="pushCheck">Push this message?</label>
							  <input id="pushCheck" type="checkbox" name="pushCheck"/>
							</li>
						  <li>
							  <input type="submit" name="Submit"  class="button" value="Submit" />
							</li>
					  </ol>
					</fieldset>
					 </form>
                </div>
            </div>
        </div>
        <div id='footer'>
        	<p>Copyright &copy <a href="#">Campus Connect</a></p>
        </div>
    </div>
</body>
</html>
