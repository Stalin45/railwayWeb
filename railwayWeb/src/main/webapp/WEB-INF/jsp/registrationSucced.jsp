<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf8">
        <title>Registration</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/style.css"/>
    </head>
    <body>
        <%@include file="header.jsp" %>
        <table class=".msg">
            <tr>
                <td align="center">
                    <h2>Registration was successful!</h2>
                    <span>Now you may <a href="${pageContext.request.contextPath}/login.jsp">Login</a></span>
                </td>
            </tr>
        </table>
    </body>
</html>