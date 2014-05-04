<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <%@include file = "parts/includes.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=utf8">
        <title>Train service</title>
    </head>

    <body> 
        <div class="wrapper">
            <%@include file = "parts/header.jsp" %>
            <div class="middle">
                <%@include file = "parts/sidebar.jsp" %>
                <div class="container">
                    <h2>Registration was successful!</h2>
                    <span>Now you may <a href="${pageContext.request.contextPath}/login.jsp">Login</a></span>
                </div>
            </div>
        </div>
        <%@include file = "parts/footer.jsp" %>
    </body>

</html>
