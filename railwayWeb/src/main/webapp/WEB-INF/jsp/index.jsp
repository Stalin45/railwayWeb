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
                    <h2>Switzerland railway : SBB/CFF/FFS</h2><br/>
                    <table>
                        <tr>
                            <td>
                                <h3>Swiss Federal Railways</h3> 
                                <h3>(SBB in German, CFF in French, FFS - Italian)</h3>
                                <p>is Switzerland's biggest travel and transport company.</p>
                        
                            </td>
                            <td>
                                <img src="${pageContext.request.contextPath}/static/images/welcome-train.jpg">
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <%@include file = "parts/footer.jsp" %>
    </body>

</html>
