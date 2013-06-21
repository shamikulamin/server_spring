
<!DOCTYPE html>
<html> 
    <head> 
        <meta http-equiv="Content-Type" 
              content="text/html; charset=windows-1256"> 
              <title> User Logged Successfully </title> 
              <style type="text/css">
                body {background-color:#b0c4de;} 
              </style>
    </head> 
    <body> 
        <%
        String userName = (String) session.getAttribute("User");
        String error = (String) session.getAttribute("Error");
        if (false ) {
           // request.setAttribute("Error", "Session has ended.  Please login.");
           // RequestDispatcher rd = request.getRequestDispatcher("LoginExample.jsp");
            //rd.forward(request, response);
           
        } else { %>
       <table height="100%" width="100%">
           
        <td alighn="center"> You have logged in successfully. </td> 
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
          
           
         
        <tr>
         <td align="center"><a valign="middle" href="getIncidentMessages">Check Messages</a></td>
        </tr>
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
        
        <tr>
          <td align="center"><a valighn="middle" href="post">Post Messages</a></td>
        </tr>
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
        
        <tr>
         <td align="center"><a valighn="middle" href="getPostedMessages">Check Posted Messages</a></td>
        </tr>
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
        <tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr><tr></tr>
        
        <tr>
         <td align="center"><a valighn="middle" href="LogoutServlet">Logout</a></td>
        </tr>
        <%}
        %>
        </table>
    </body>
     
</html>
