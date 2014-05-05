<%@ page language="java" contentType="text/html; charset=utf8"
         pageEncoding="utf8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <%@include file = "WEB-INF/jsp/parts/includes.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=utf8">
        <title>Authentification</title>
    </head>
    <body> 
        <div class="wrapper">
            <%@include file = "WEB-INF/jsp/parts/header.jsp" %>
            <div class="middle">
                <%@include file = "WEB-INF/jsp/parts/sidebar.jsp" %>
                <div class="container">
                    <h2>Authentication</h2>
                    <form method="POST" action="<c:url value="/j_spring_security_check" />">
                        <table id="login-box">
                            <tr>
                            <h3>Please sign in:</h3>
                            </tr>
                            <tr>
                                <td colspan="2"><input class="form-control" placeholder="login" type="text" name="j_username" /></td>
                            </tr>
                            <tr>
                                <td colspan="2"><input class="form-control" placeholder="password" type="password" name="j_password" /></td>
                            </tr>
                            <tr>
                                <td colspan="2"><input type="checkbox" name="_spring_security_remember_me" />  remember me<br/><br/></td>
                            </tr>
                            <tr>
                                <td><input class="btn btn-large" type="submit" value="Login" /></td>
                                <td><input class="btn btn-large" type="reset" value="Reset" /></td>
                            </tr>
                        </table>
                    </form>
                    <div class="operation-error">
                        <c:if test="${not empty param.error}">Login error: ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</c:if>
                    </div>
                </div>
            </div>
        </div>
        <%@include file = "WEB-INF/jsp/parts/footer.jsp" %>
    </body>

</html>