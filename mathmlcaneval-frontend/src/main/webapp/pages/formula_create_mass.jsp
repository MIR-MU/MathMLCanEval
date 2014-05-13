<div class="container content">
    <h1><spring:message code="entity.formula.create" /></h1>
    <c:if test="${not empty jarFileErrorMessage}">
        <div class="alert alert-success">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
            <h1><spring:message code="general.label.missingjarfile" /></h1>
            <p>
                <c:out value="${jarFileErrorMessage}" />
            </p>
        </div>
    </c:if>
    <form:form id="fileupload" method="post" action="${pageContext.request.contextPath}/formula/mass/" commandName="formulaForm" cssClass="form-horizontal pull-top-50">

        <div class="form-group">
            <label class="col-sm-2 control-label"><spring:message code="entity.formula.sourceDocument" /></label>
            <div class="col-sm-7">
                <form:select path="sourceDocumentForm" multiple="false" class="form-control">
                    <c:choose>
                        <c:when test="${fn:length(sourceDocumentList) eq 1}">
                            <form:option value="${sourceDocumentList[0].id}"><c:out value="${sourceDocumentList[0].name}" /></form:option>
                        </c:when>
                        <c:otherwise>
                            <form:option value="-1"><spring:message code="general.select.option.pickone" /></form:option>
                            <form:options items="${sourceDocumentList}" itemLabel="name" itemValue="id" />
                        </c:otherwise>
                    </c:choose>
                </form:select>
            </div>
            <form:errors path="sourceDocumentForm" element="div" class="col-sm-3 alert alert-danger"/>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label"><spring:message code="entity.formula.program" /></label>
            <div class="col-sm-7">
                <form:select path="programForm" multiple="false" class="form-control">
                    <c:choose>
                        <c:when test="${fn:length(programList) eq 1}">
                            <form:option value="${programList[0].id}"><c:out value="${programList[0].name}" /></form:option>
                        </c:when>
                        <c:otherwise>
                            <form:option value="-1"><spring:message code="general.select.option.pickone" /></form:option>
                            <form:options items="${programList}" itemLabel="name" itemValue="id" />
                        </c:otherwise>
                    </c:choose>
                </form:select>
            </div>
            <form:errors path="programForm" element="div" class="col-sm-3 alert alert-danger"/>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label"><spring:message code="entity.formula.revision" /></label>
            <div class="col-sm-7">
                <form:select path="revisionForm" multiple="false" class="form-control">
                    <form:option value="-1"><spring:message code="general.select.option.pickone" /></form:option>
                    <form:options items="${revisionList}" itemLabel="revisionHash" itemValue="id" />
                </form:select>
            </div>
            <form:errors path="revisionForm" element="div" class="col-sm-3 alert alert-danger"/>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label"><spring:message code="entity.formula.configuration" /></label>
            <div class="col-sm-7">
                <form:select path="configurationForm" multiple="false" class="form-control">
                    <form:option value="-1"><spring:message code="general.select.option.pickone" /></form:option>
                    <form:options items="${configurationList}" itemLabel="name" itemValue="id" />
                </form:select>
            </div>
            <form:errors path="configurationForm" element="div" class="col-sm-3 alert alert-danger"/>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label"><spring:message code="entity.formula.note" /></label>
            <div class="col-sm-7">
                <form:textarea path="note" cssClass="form-control" />
            </div>
            <form:errors path="note" element="div" class="col-sm-3 alert alert-danger"/>
        </div>

        <div class="form-group">
            <label class="col-sm-2 control-label">!path</label>
            <div class="col-sm-7">
                <input type="text" name="importPath" class="form-control" />
            </div>        
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">!filter</label>
            <div class="col-sm-7">
                <input type="text" name="filter" class="form-control" />
            </div>        
        </div>
        
        <div class="form-group">
            <div class="col-xm-7 col-sm-offset-2">
                <button type="submit" class="btn btn-primary"><spring:message code="general.button.submit" /></button>
            </div>
        </div>
    </form:form>
</div>
