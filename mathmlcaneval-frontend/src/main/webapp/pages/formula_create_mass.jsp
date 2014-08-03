<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">        
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
                        <c:choose>
                            <c:when test="${fn:length(sourceDocumentList) eq 0}">
                                <c:set var="hasError" value="true" />
                                <form:select path="sourceDocumentForm" multiple="false" class="form-control" disabled="true">
                                    <form:option value="-1"><spring:message code="general.label.form.select.empty" /></form:option>
                                </form:select>
                            </c:when>
                            <c:otherwise>
                                <form:select path="sourceDocumentForm" multiple="false" class="form-control">
                                    <form:option value="-1"><spring:message code="general.select.option.pickone" /></form:option>
                                    <form:options items="${sourceDocumentList}" itemLabel="name" itemValue="id" />
                                </form:select>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <c:if test="${fn:length(sourceDocumentList) eq 0}">
                        <div class="col-sm-3 alert alert-danger">
                            <spring:message code="general.label.form.select.empty.error" />
                        </div>
                    </c:if>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label"><spring:message code="entity.formula.program" /></label>
                    <div class="col-sm-7">
                        <c:choose>
                            <c:when test="${fn:length(programList) eq 0}">
                                <c:set var="hasError" value="true" />
                                <form:select path="programForm" multiple="false" class="form-control" disabled="true">
                                    <form:option value="-1"><spring:message code="general.label.form.select.empty" /></form:option>
                                </form:select>
                            </c:when>
                            <c:otherwise>
                                <form:select path="programForm" multiple="false" class="form-control">
                                    <form:option value="-1"><spring:message code="general.select.option.pickone" /></form:option>
                                    <form:options items="${programList}" itemLabel="name" itemValue="id" />
                                </form:select>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <c:if test="${fn:length(programList) eq 0}">
                        <div class="col-sm-3 alert alert-danger">
                            <spring:message code="general.label.form.select.empty.error" />
                        </div>
                    </c:if>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label"><spring:message code="entity.formula.revision" /></label>
                    <div class="col-sm-7">
                        <c:choose>
                            <c:when test="${fn:length(revisionList) eq 0}">
                                <c:set var="hasError" value="true" />
                                <form:select path="revisionForm" multiple="false" class="form-control" disabled="true">
                                    <form:option value="-1"><spring:message code="general.label.form.select.empty" /></form:option>
                                </form:select>
                            </c:when>
                            <c:otherwise>
                                <form:select path="revisionForm" multiple="false" class="form-control">
                                    <form:option value="-1"><spring:message code="general.select.option.pickone" /></form:option>
                                    <form:options items="${revisionList}" itemLabel="revisionHash" itemValue="id" />
                                </form:select>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <c:if test="${fn:length(revisionList) eq 0}">
                        <div class="col-sm-3 alert alert-danger">
                            <spring:message code="general.label.form.select.empty.error" />
                        </div>
                    </c:if>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label"><spring:message code="entity.formula.configuration" /></label>
                    <div class="col-sm-7">
                        <c:choose>
                            <c:when test="${fn:length(configurationList) eq 0}">
                                <c:set var="hasError" value="true" />
                                <form:select path="configurationForm" multiple="false" class="form-control" disabled="true">
                                    <form:option value="-1"><spring:message code="general.select.option.pickone" /></form:option>
                                    <form:options items="${configurationList}" itemLabel="name" itemValue="id" />
                                </form:select>
                            </c:when>
                            <c:otherwise>
                                <form:select path="configurationForm" multiple="false" class="form-control">
                                    <form:option value="-1"><spring:message code="general.select.option.pickone" /></form:option>
                                    <form:options items="${configurationList}" itemLabel="name" itemValue="id" />
                                </form:select>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <c:if test="${fn:length(configurationList) eq 0}">
                        <div class="col-sm-3 alert alert-danger">
                            <spring:message code="general.label.form.select.empty.error" />
                        </div>
                    </c:if>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label"><spring:message code="entity.formula.note" /></label>
                    <div class="col-sm-7">
                        <form:textarea path="note" cssClass="form-control" />
                    </div>
                    <form:errors path="note" element="div" class="col-sm-3 alert alert-danger"/>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label"><spring:message code="general.label.path" /></label>
                    <div class="col-sm-7">
                        <input type="text" name="importPath" class="form-control" placeholder="<spring:message code="general.hint.path" />"/>
                    </div>        
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label"><spring:message code="general.label.filter" /></label>
                    <div class="col-sm-7">
                        <input type="text" name="filter" class="form-control" id="inputFilter"/>
                    </div>        
                </div>

                <div class="form-group">
                    <div class="col-xm-7 col-sm-offset-2">
                        <c:choose>
                            <c:when test="${not empty hasError}">
                                <button type="submit" class="btn btn-primary" disabled="true"><spring:message code="general.button.submit" /></button>
                            </c:when>
                            <c:otherwise>
                                <button type="submit" class="btn btn-primary"><spring:message code="general.button.submit" /></button>
                            </c:otherwise>
                        </c:choose>                        
                    </div>
                </div>
            </form:form>
        </div>

    </tiles:putAttribute>
</tiles:insertDefinition>