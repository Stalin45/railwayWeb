<div class="header">
    <div id="logo">
        <a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/static/images/logo.png" alt="Main page"></a> 
    </div>
    <div id="user-details">
        <c:if test="${authentication.getName() ne null}">
            <h2>Hello, ${authentication.getName()}!</h2>
            <c:if test="${authentication.getName() ne 'guest'}">
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
            </c:if>
            <c:if test="${authentication.getName() eq 'guest'}">
                Please, <a href="${pageContext.request.contextPath}/login.jsp">Login</a> or <a href="${pageContext.request.contextPath}/registration">Registrer</a>
            </c:if>
        </c:if> 
    </div>
</div>