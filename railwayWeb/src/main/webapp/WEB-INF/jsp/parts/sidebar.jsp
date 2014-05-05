<div class="left-sidebar">
     <sec:authorize access="isAnonymous()">Waiting for authentication</sec:authorize>
     <sec:authorize access="hasRole('ROLE_SPECIALIST')">
        <div id="spec-panel">
            <ul class="nav nav-list bs-docs-sidenav">
                <li class="nav-header">
                    Specialist menu
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/stations">Station management</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/paths">Path management</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/trainTypes">Train type management</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/trains">Train management</a>
                </li>
            </ul>
        </div>
     </sec:authorize>
     <sec:authorize access="hasAnyRole('ROLE_USER', 'ROLE_SPECIALIST')">
        <div id="user-panel">
            <ul class="nav nav-list bs-docs-sidenav">
                <li class="nav-header">
                    User menu
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/findTrains">Find trains</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/tickets">Buy ticket</a>
                </li>
            </ul>
        </div>
     </sec:authorize>
</div>
<%--<sec:authorize access="isAnonymous()"></sec:authorize>
    <sec:authorize access="hasAnyRole('ROLE_USER', 'ROLE_SPECIALIST')">
        <h4>Hello, <sec:authentication property="principal.username"/>!</h4>
        You authenticated as <sec:authentication property="authentication.name"/>
    </sec:authorize>--%>