<div class="container content">
    <h1><spring:message code="entity.userrole.create" /></h1>
    <form:form commandName="userRoleForm" class="form-signin" role="form" action="${pageContext.request.contextPath}/userrole/create/" method="post" cssClass="form-horizontal pull-top-50">
        <div class="form-group">
            <label class="col-sm-2 control-label"><spring:message code="entity.userrole.name" /></label>
            <div class="col-sm-7">
                <form:input path="roleName" class="form-control" />
            </div>
            <form:errors path="roleName" element="div" class="col-sm-3 alert alert-danger"/>            
        </div>
        <div class="form-group">
            <div class="col-xm-7 col-sm-offset-2">
                <button type="submit" class="btn btn-primary"><spring:message code="general.button.submit" /></button>
            </div>
        </div>
    </form:form>
</div>
