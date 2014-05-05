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
                            <tr>
                                <td colspan="2"><form:input class="form-control" placeholder="login" path="login"/></td>
                            </tr>
                            <tr>
                                <td colspan="2"><form:input id="pass" class="form-control" placeholder="password" path="password"/><br/><br/></td>
                            </tr>
                            <tr>
                                <td><input class="btn btn-large" type="submit" value="Register" /></td>
                                <td><input class="btn btn-large" type="reset" value="Reset" /></td>
                            </tr>
                        </table>
                    </form:form>
                    <div class="operation-error">${error}</div>
                </div>
            </div>
        </div>
        <%@include file = "parts/footer.jsp" %>
    </body>

</html>
