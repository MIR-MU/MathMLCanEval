<div class="container content">
    <h1><spring:message code="entity.program.edit" /></h1>
    <form:form method="post" action="${pageContext.request.contextPath}/program/edit/" commandName="programForm" cssClass="form-horizontal pull-top-50">
        <form:hidden path="id" />
        <div class="form-group">
            <label class="col-sm-2 control-label"><spring:message code="entity.program.name" /></label>
            <div class="col-sm-7">
                <form:input type="text" path="name" cssClass="form-control" />
            </div>
            <form:errors path="name" element="div" class="col-sm-3 alert alert-danger"/>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label"><spring:message code="entity.program.version" /></label>
            <div class="col-sm-7">
                <form:input type="text" path="version" cssClass="form-control" />
            </div>
            <form:errors path="version" element="div" class="col-sm-3 alert alert-danger"/>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label"><spring:message code="entity.program.parameters" /></label>
            <div class="col-sm-7">
                <form:input type="text" path="parameters" cssClass="form-control" />
            </div>
            <form:errors path="parameters" element="div" class="col-sm-3 alert alert-danger"/>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label"><spring:message code="entity.program.note" /></label>
            <div class="col-sm-7">
                <form:textarea path="note" cssClass="form-control" />
            </div>
            <form:errors path="note" element="div" class="col-sm-3 alert alert-danger"/>
        </div>
        <div class="form-group">
            <div class="col-xm-7 col-sm-offset-2">
                <button type="submit" class="btn btn-primary"><spring:message code="general.button.submit" /></button>
            </div>
        </div>
    </form:form>
</div>
