
<div class="content container">
    <h1>!create initial admin accout</h1>
    <hr />
    <form:form method="post" action="${pageContext.request.contextPath}/setup/step2/" commandName="userForm" cssClass="form-horizontal pull-top-50">
        <div class="form-group">
            <label class="col-sm-2 control-label"><spring:message code="entity.user.username" /></label>
            <div class="col-sm-7">
                <form:input type="text" path="username" cssClass="form-control" />
            </div>
            <form:errors path="username" element="div" class="col-sm-3 alert alert-danger"/>            
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label"><spring:message code="entity.user.realname" /></label>
            <div class="col-sm-7">
                <form:input type="text" path="realName" cssClass="form-control" />
            </div>
            <form:errors path="realName" element="div" class="col-sm-3 alert alert-danger"/>            
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label"><spring:message code="entity.user.email" /></label>
            <div class="col-sm-7">
                <form:input type="email" path="email" cssClass="form-control" />
            </div>
            <form:errors path="email" element="div" class="col-sm-3 alert alert-danger"/>            
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label"><spring:message code="entity.user.email.verify" /></label>
            <div class="col-sm-7">
                <form:input type="email" path="emailVerify" cssClass="form-control" />
            </div>
            <form:errors path="emailVerify" element="div" class="col-sm-3 alert alert-danger"/>            
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label"><spring:message code="entity.user.password" /></label>
            <div class="col-sm-7">
                <form:password path="password" cssClass="form-control" />
            </div>
            <form:errors path="password" element="div" class="col-sm-3 alert alert-danger"/>            
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label"><spring:message code="entity.user.password.verify" /></label>
            <div class="col-sm-7">
                <form:password path="passwordVerify" cssClass="form-control" />
            </div>
            <form:errors path="passwordVerify" element="div" class="col-sm-3 alert alert-danger"/>            
        </div>
        <div class="form-group">
            <div class="col-xm-7 col-sm-offset-2">
                <button type="submit" class="btn btn-primary"><spring:message code="general.button.submit" /></button>
            </div>
        </div>
    </form:form>
</div>
