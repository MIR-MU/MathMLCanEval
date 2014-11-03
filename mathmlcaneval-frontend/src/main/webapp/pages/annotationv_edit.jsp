<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">
        <div class="container content">
            <h1><spring:message code="entity.annotationvalue.edit" /></h1>
            <form:form method="post" action="${pageContext.request.contextPath}/annotationvalue/edit/" commandName="annotationValueForm" cssClass="form-horizontal pull-top-50">
                <form:hidden path="id" />
                <div class="form-group">
                    <label class="col-sm-2 control-label"><spring:message code="entity.annotationvalue.category" /></label>
                    <div class="col-sm-7">
                        <form:select cssClass="form-control" path="type">
                            <form:option label="FORMULA" value="FORMULA" />
                            <form:option label="CANONICOUTPUT" value="CANONICOUTPUT" />
                            <form:option label="BOTH" value="BOTH" />
                        </form:select>
                    </div>
                    <form:errors path="type" element="div" class="col-sm-3 alert alert-danger"/>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label"><spring:message code="entity.annotationvalue.value" /></label>
                    <div class="col-sm-7">
                        <form:input type="text" path="value" cssClass="form-control" />
                    </div>
                    <form:errors path="value" element="div" class="col-sm-3 alert alert-danger"/>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label"><spring:message code="entity.annotationvalue.priority" /></label>
                    <div class="col-sm-7">
                        <form:input type="text" path="priority" cssClass="form-control" />
                    </div>
                    <form:errors path="priority" element="div" class="col-sm-3 alert alert-danger"/>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label"><spring:message code="entity.annotationvalue.icon" /></label>
                    <div class="col-sm-7">
                        <form:input type="text" path="icon" cssClass="form-control" />
                    </div>
                    <form:errors path="icon" element="div" class="col-sm-3 alert alert-danger"/>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label"><spring:message code="entity.annotationvalue.label" /></label>
                    <div class="col-sm-7">
                        <form:input type="text" path="label" cssClass="form-control" />
                    </div>
                    <form:errors path="label" element="div" class="col-sm-3 alert alert-danger"/>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label"><spring:message code="entity.annotationvalue.description" /></label>
                    <div class="col-sm-7">
                        <form:textarea cssClass="form-control" path="description" />
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-xm-7 col-sm-offset-2">
                        <button type="submit" class="btn btn-primary"><spring:message code="general.button.submit" /></button>
                    </div>
                </div>
            </form:form>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>