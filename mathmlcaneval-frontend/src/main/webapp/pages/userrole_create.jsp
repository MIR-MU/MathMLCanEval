<div class="container content">
    <h1>!user role create</h1>
    <form:form commandName="userRoleForm" class="form-signin" role="form" action="${pageContext.request.contextPath}/userrole/create/" method="post">
        <spring:hasBindErrors name="programForm">
            <div class="error">
                <c:forEach var="error" items="${errors.allErrors}">
                    <p>Errors ${error.defaultMessage}</p>
                </c:forEach>
            </div>
        </spring:hasBindErrors>

        <div class="form-group">
            <label class="col-sm-2 control-label">!role name</label>
            <div class="col-sm-10">
                <form:input path="roleName" class="form-control" placeholder="!User name"/>
            </div>
        </div>
        <button type="submit" class="btn btn-primary">!submit</button>
    </form:form>
</div>     