<div class="header">
    <div id="logo">
        <a href="${pageContext.request.contextPath}/"><img src="${pageContext.request.contextPath}/static/images/logo.png" alt="Main page"></a> 
    </div>
    <div id="user-details">
        <h2>Hello, <sec:authorize access="isAnonymous()">guest</sec:authorize>
            <sec:authorize access="isAuthenticated()"><sec:authentication property="principal.username" /></sec:authorize>!</h2>
        <sec:authorize access="isAuthenticated()">
            <a href="${pageContext.request.contextPath}/logout">Logout</a>
        </sec:authorize>

        <sec:authorize access="isAnonymous()">
            Please, <a href="${pageContext.request.contextPath}/login.jsp">Login</a> or <a href="${pageContext.request.contextPath}/registration">Registrer</a>
        </sec:authorize>
    </div>
</div>