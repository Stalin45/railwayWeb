<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

    <head>
        <%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
        <%@include file = "parts/includes.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=utf8">
        <title>Registration</title>
    </head>


    <body> 
        <div class="wrapper">
            <%@include file = "parts/header.jsp" %>
            <div class="middle">
                <%@include file = "parts/sidebar.jsp" %>
                <div class="container">
                    <h2>Registration</h2>
                    <form:form method="post" action="${pageContext.request.contextPath}/registration/newUser" commandName="user" >
                        <table id="register-box">
                            <c:if test="${error ne null}">
                                <tr>
                                    <td colspan="2" class="error">Register error: ${error}<br/></td>
                                </tr>
                            </c:if>
                            <tr>
                                <td align="right">Login</td>
                                <td><form:input path="login"/></td>
                            </tr>
                            <tr>
                                <td align="right">Password</td>
                                <td><form:input id="pass" path="password"/></td>
                            </tr>
                            <tr>
                                <td colspan="2" align="right">
                                    <input type="submit" value="Register" />
                                    <input type="reset" value="Reset" />
                                </td>
                            </tr>
                        </table>
                    </form:form>
                </div>
            </div>
        </div>
        <%@include file = "parts/footer.jsp" %>
    </body>

</html>
