<div class="left-sidebar">
    <c:if test="${authentication.getName() ne 'guest'}">
        <div id="spec-panel">
            <ul>
                <li>
                    <a href="${pageContext.request.contextPath}/stations">Station management</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/paths">Path management</a>
                </li>
            </ul>
        </div>
        <div id="user-panel">
            
        </div>
    </c:if>
</div>
<%--<sec:authorize access="isAnonymous()"></sec:authorize>
    <sec:authorize access="hasAnyRole('ROLE_USER', 'ROLE_SPECIALIST')">
        <h4>Hello, <sec:authentication property="principal.username"/>!</h4>
        You authenticated as <sec:authentication property="authentication.name"/>
    </sec:authorize>--%>