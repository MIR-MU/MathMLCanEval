<div class="container content">
    <h1><spring:message code="entity.revision.create" /></h1>
    <form:form method="post" action="${pageContext.request.contextPath}/revision/create/" commandName="revisionForm" cssClass="form-horizontal pull-top-50">
        <div class="form-group">
            <label class="col-sm-2 control-label"><spring:message code="entity.revision.hash" /></label>
            <div class="col-sm-7">
                <form:input type="text" path="revisionHash" cssClass="form-control" />
            </div>
            <form:errors path="revisionHash" element="div" class="col-sm-3 alert alert-success"/>            
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label"><spring:message code="entity.revision.note" /></label>
            <div class="col-sm-7">
                <form:textarea path="note" cssClass="form-control" />
            </div>
        </div>
        <div class="form-group">
            <div class="col-xm-7 col-sm-offset-2">
                <button type="submit" class="btn btn-primary"><spring:message code="general.button.submit" /></button>
            </div>
        </div>        
    </form:form>
</div>
