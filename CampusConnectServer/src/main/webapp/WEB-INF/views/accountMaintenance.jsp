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
	
	<!-- Include all of our scripts -->
	<script type='text/javascript' src="./media/js/jquery.js"></script>
	<script type='text/javascript' src="./media/ui/jquery.ui.core.js"></script>
    <script type='text/javascript' src="./media/js/sliding.js"></script>
	<script type="text/javascript" src="./media/js/jquery.dataTables.js"></script>
	<script type="text/javascript" src="./media/js/jquery.jeditable.js"></script>
	
	<title>Account Maintenance</title>
	<script>
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
		}
		
		function validateNewPass() {
			var currpass = document.forms["newPasswordForm"]["currentpassword"].value;
			var pass1 = document.forms["newPasswordForm"]["newpassword"].value;
			var pass2 = document.forms["newPasswordForm"]["repeatnewpassword"].value;
			
			if (currpass==null || currpass=="") {
				alert("Current password must be filled out");
				return false;
			}
			if (pass1==null || pass1=="") {
				alert("New password must be filled out");
				return false;
			}
			if (pass2==null || pass2=="") {
				alert("Please repeat the password in the second box");
				return false;
			}
			if( pass1 !== pass2 ) {
				alert("The two new passwords did not match");
				document.forms["newPasswordForm"]["currentpassword"].value = "";
				document.forms["newPasswordForm"]["repeatpassword"].value = "";
				document.forms["newPasswordForm"]["repeatnewpassword"].value = "";
				return false;
			}
		}
		
		function validateForm() {
			var username = document.forms["newUserForm"]["username"].value;
			var pass1 = document.forms["newUserForm"]["password"].value;
			var pass2 = document.forms["newUserForm"]["repeatpassword"].value;
			
			if (username==null || username=="") {
				alert("User name must be filled out");
				return false;
			}
			if (pass1==null || pass1=="") {
				alert("Password must be filled out");
				return false;
			}
			if (pass2==null || pass2=="") {
				alert("Please repeat the password in the second box");
				return false;
			}
			if( pass1 !== pass2 ) {
				alert("The two passwords did not match");
				document.forms["newUserForm"]["password"].value = "";
				document.forms["newUserForm"]["repeatpassword"].value = "";
				return false;
			}
		}
	
		function checkDelete( id ) {
			var r=confirm("Are you sure you want to delete this account?");
			if (r==true) {
				var xmlhttp=new XMLHttpRequest();
				xmlhttp.open("POST","./ajax/removeUser",true);
				xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
				xmlhttp.send("username="+id);
				$("#example").dataTable().fnReloadAjax();
			}
		}
	
		$(document).ready(function() {
			slide("#left_nav", 25, 15, 150, .8);
			
			var oTable = $('#example').dataTable({
				"aoColumns": [	{ "sWidth": "20%", "sClass": "center", "bSortable": false },
			             		{ "sWidth": "20%", "sClass": "center", "bSortable": false },
			             		{ "sWidth": "10%", "sClass": "center", "bSortable": false }],
				"sAjaxSource": './ajax/users',
				"fnRowCallback" : function (nRow, aData, iDisplayIndex) {
                    $(nRow).attr('id', aData[0]);

                    for (i = 0; i < aData.length-1; i ++) {		// -1 to leave out delete button
                    	if( i == 1 ) { 
                    		$('td:eq(' + i + ')', nRow).editable('./ajax/userupdate/roles', {
	                            'callback': function (sValue, y) {
	                                var aPos = oTable.fnGetPosition(this);
	                                oTable.fnUpdate(sValue, aPos[0], aPos[1]);
	                            },
	                            "submitdata": function ( value, settings ) {
	                                return {
	                                	"username": this.parentNode.getAttribute('id'),
	                                    "column": oTable.fnGetPosition( this )[2]
	                                 };
	                            },
	                            'type'   : 'select',
	                            'data'  : " {'ROLE_USER':'User','ROLE_ADMIN':'Administrator', 'selected':'User'}",
	                            'submit' : 'OK',
	                            'tooltip' : 'click to edit',
	                            'height': '14px'
	                        });
                    	} else {
	                        $('td:eq(' + i + ')', nRow).editable('./ajax/userupdate/name', {
	                            'callback': function (sValue, y) {
	                                var aPos = oTable.fnGetPosition(this);
	                                oTable.fnUpdate(sValue, aPos[0], aPos[1]);
	                            },
	                            "submitdata": function ( value, settings ) {
	                                return {
	                                	"username": this.parentNode.getAttribute('id'),
	                                    "column": oTable.fnGetPosition( this )[2]
	                                 };
	                            },
	                            'submit' : 'OK',
	                            'tooltip' : 'click to edit',
	                            'height': '14px'
	                        });
                    	}
                    }
                     
                    return nRow;
                },
				"bProcessing": true,
				"bAutoWidth": false
			});
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
                	<h1>Incident Messages</h1>
					<table id ="example" style="width: 100%;">
						<thead>
							  <tr>
									<th>No</th>
									<th>Title</th>
									<th>Description</th>
									<th>Location</th>
									<th>Pictures</th>
							  </tr>
						</thead>
					</table>
                </div>-->
            	<div id='data'>
            		<h1>Maintain Your Account</h1>
            		<div id ="test" style="text-align: center;">
            			<div id="innertest" style="width: 38%; text-align: left; margin: 0 auto;" >
							<form action="changePassword" name="newPasswordForm" onsubmit="return validateNewPass()" class="cmxform" style="display: block;" method="post">
								<fieldset>
								  <legend>Change Password</legend>
								  <ol>
								  <li style="display:none;">
									  <input type="text" class="input" name="username" id="username" value='<sec:authentication property="principal.username" />' style="display: none;" size="30" readonly/>
									</li>
								  <li>
									  <span><label for="currentpassword">Current Password:<em>*</em></label>
									  <input type="password" class="input" name="currentpassword" id="currentpassword" size="30" /></span>
									</li>
								  <li>
									  <label for="password">New Password:<em>*</em></label>
									  <input type="password" class="input" name="newpassword" id="newpassword" size="30" />
									</li>
								  <li>
									  <label for="repeatpassword">Retype New Password:<em>*</em></label>
									  <input type="password" class="input" name="repeatnewpassword" id="repeatnewpassword" size="30" />
									</li>
								  <li>
									  <input type="submit" name="Submit"  class="button" value="Submit" />
									</li>
							  </ol>
							</fieldset>
							</form>
						</div>
					</div>
					
					<sec:authorize access="hasRole('ROLE_ADMIN')">
					
	            		<h1>Current Accounts</h1>
	            		<table id ="example" style="width: 100%;">
							<thead>
								  <tr>
										<th>User Name</th>
										<th>Roles</th>
										<th>Delete</th>
								  </tr>
							</thead>
						</table>
	
	                	<h1 style="clear:right;">Create New Account</h1>
						<form action="addUser" name="newUserForm" onsubmit="return validateForm()" class="cmxform" style="display: block;" method="post">
							<p>Please complete the form below. Mandatory fields marked <em>*</em></p>
							<fieldset>
							  <legend>Account Details</legend>
							  <ol>
							  <li>
								  <label for="username">User Name:<em>*</em></label>
								  <input type="text" class="input" name="username" id="username" size="30" />
								</li>
							  <li>
								  <label for="password">Password:<em>*</em></label>
								  <input type="password" class="input" name="password" id="password" size="30" />
								</li>
							  <li>
								  <label for="repeatpassword">Retype Password:<em>*</em></label>
								  <input type="password" class="input" name="repeatpassword" id="repeatpassword" size="30" />
								</li>
							  <li>
								  <label for="accounttype">Account Type<em>*</em></label>
								  <select id="accounttype" name="accounttype">
									<option value="ROLE_USER" selected="ROLE_USER">User</option>
									<option value="ROLE_ADMIN">Administrator</option>
								  </select>
								</li>
							  <li>
								  <input type="submit" name="Submit"  class="button" value="Submit" />
								</li>
						  </ol>
						</fieldset>
						</form>
					
					</sec:authorize>
                </div>
            </div>
        </div>
        <div id='footer'>
        	<p>Copyright &copy <a href="#">Campus Connect</a></p>
        </div>
    </div>
</body>
</html>
