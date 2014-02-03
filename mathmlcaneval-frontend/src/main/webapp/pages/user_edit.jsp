<div class="container content">
    <h1>!edit user</h1>
    <form:form commandName="userForm" class="form-signin" role="form" action="${pageContext.request.contextPath}/user/create/" method="post">
        <spring:hasBindErrors name="programForm">
            <div class="error">
                <c:forEach var="error" items="${errors.allErrors}">
                    <p>Errors ${error.defaultMessage}</p>
                </c:forEach>
            </div>
        </spring:hasBindErrors>
        <form:input type="hidden" path="id" />
        <div class="form-group">
            <label class="col-sm-2 control-label">!username</label>
            <div class="col-sm-10">
                <form:input path="username" class="form-control" placeholder="!User name"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">!realname</label>
            <div class="col-sm-10">
                <form:input path="realName" class="form-control" placeholder="Real name"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">!email</label>
            <div class="col-sm-10">
                <span class="form-control">!NYI</span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">!password</label>
            <div class="col-sm-10">
                <form:password path="password" class="form-control" placeholder="Password"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">!passwordverif</label>
            <div class="col-sm-10">
                <form:password path="passwordVerify" class="form-control" placeholder="Password (again)"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">!userRole</label>
            <div class="col-sm-10">
                <form:select path="userRoleForms[0]" multiple="false" class="form-control">
                    <form:option value="">!select one</form:option>
                    <form:options items="${userRolesFormList}" itemLabel="roleName" itemValue="id" />
                </form:select>
            </div>            
        </div>
        <button type="submit" class="btn btn-primary">!submit</button>
    </form:form>
</div>     