<div class="container content">
    <h1><spring:message code="entity.sourceDocument.create" /></h1>
    <form:form method="post" action="${pageContext.request.contextPath}/sourcedocument/create/" commandName="sourceDocumentForm" cssClass="form-horizontal pull-top-50">
        <div class="form-group">
            <label class="col-sm-2 control-label"><spring:message code="entity.sourceDocument.note" /></label>
            <div class="col-sm-7">
                <form:textarea path="note" cssClass="form-control" />
            </div>
        <form:errors path="note" element="div" class="col-sm-3 alert alert-danger"/>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label"><spring:message code="entity.sourceDocument.documentPath" /></label>
            <div class="col-sm-7">
                <form:textarea path="documentPath" cssClass="form-control" />
            </div>
        <form:errors path="documentPath" element="div" class="col-sm-3 alert alert-danger"/>
        </div>
        <div class="form-group">
            <div class="col-xm-7 col-sm-offset-2">
                <button type="submit" class="btn btn-primary"><spring:message code="general.button.submit" /></button>
            </div>
        </div>
    </form:form>
</div>
