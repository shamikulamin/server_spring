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
	
	<title>Index</title>
	<script>
		$(document).ready(function() {
			var oTable = $('#example').dataTable({
					"aoColumns": [	{ "sWidth": "5%", "sClass": "center" },
					              	{ "sWidth": "20%", "sClass": "center" },
					              	{ "sWidth": "40%", "sClass": "center" },
				             		{ "sWidth": "10%", "sClass": "center", "bSortable": false }],
					"sAjaxSource": './ajax/communityMsgs',
					"bProcessing": true,
					"bAutoWidth": false
			});
			
			$(".fancybox").fancybox({
				openEffect	: 'none',
				closeEffect	: 'none'
			});
			
			slide("#left_nav", 25, 15, 150, .8);
		});
	</script>
</head>

<body>
	<div id='content_wrapper'>
    	<div id='header'>
        	<div id='welcome'>
            	<a href='#'><img src="./media/images/CampusConnectLogo.png" alt="Campus Connect"></img></a>
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
            	<!--<div id='filtering'>
                </div>-->
            	<div id='data'>
	            	<h1>Posted Messages</h1>
					<table id ="example" style="width: 100%;">
						<thead>
							  <tr>
									<th>No</th>
									<th>Title</th>
									<th>Description</th>
									<th>Location</th>
							  </tr>
						</thead>
					</table>
                </div>
            </div>
        </div>
        <div id='footer'>
        	<p>Copyright &copy <a href="#">Campus Connect</a></p>
        </div>
    </div>
</body>
</html>
