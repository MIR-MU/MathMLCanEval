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
            <form:form id="fileupload" method="post" action="${pageContext.request.contextPath}/formula/create/" commandName="formulaForm" cssClass="form-horizontal pull-top-50"
                       enctype="multipart/form-data">

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
                    <label class="col-sm-2 control-label"><spring:message code="entity.formula.note" /></label>
                    <div class="col-sm-7">
                        <form:textarea path="note" cssClass="form-control" />
                    </div>
                    <form:errors path="note" element="div" class="col-sm-3 alert alert-danger"/>
                </div>
                <div clas="row">
                    <form:errors path="xml" element="div" class="col-sm-12 alert alert-danger"/>
                </div>
                <div class="row">
                    <div class="col-md-6">
                        <div class="custom-label"><spring:message code="entity.formula.xml" /></div>
                        <form:textarea path="xml" cssClass="form-control" rows="15" />
                    </div>
                    <div class="col-md-6">
                        <div class="custom-label"><spring:message code="entity.formula.rendered" /></div>
                        <div id="mathml-preview"></div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label"><spring:message code="entity.formula.upload" /></label>
                    <div class="col-sm-7 fileupload-buttonbar">
                        <span class="btn btn-default fileinput-button">
                            <span><spring:message code="general.label.addfiles" /></span>
                            <input id="fileinput" type="file" name="uploadedFiles" multiple>
                        </span>
                        <input type="button" id="filereset" class="btn btn-warning cancel" value="<spring:message code="general.label.reset" />">

                        </button>
                        <table class="table">
                            <thead>
                                <tr>
                                    <td><spring:message code="general.label.filename" /></td>
                                    <td><spring:message code="general.label.filesize" /></td>
                                </tr>
                            </thead>
                            <tbody id="uploads-table" />
                        </table>
                    </div>
                </div>
                <div class="form-group">
                    <button class="btn btn-default col-xm-7 col-sm-offset-2" data-toggle="modal" data-target="#canonModal"><spring:message code="entity.formula.canonicalizer.configure" /></button>
                </div>
                <div class="modal fade" id="canonModal" tabindex="-1" role="dialog" aria-labelledby="canonModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                <br />
                            </div>
                            <div class="modal-body">
                                <input type="hidden" name="formulaId" value="${formulaEntry.id}" />
                                <div class="row">
                                    <label class="col-sm-3 control-label"><spring:message code="entity.formula.revision" /></label>
                                    <div class="col-sm-9">
                                        <form:select path="revisionForm" multiple="false" class="form-control">
                                            <form:option value="-1"><spring:message code="general.select.option.pickone" /></form:option>
                                            <form:options items="${revisionList}" itemLabel="revisionHash" itemValue="id" />
                                        </form:select>
                                    </div>
                                </div>
                                <div class="row">
                                    <label class="col-sm-3 control-label"><spring:message code="entity.formula.configuration" /></label>
                                    <div class="col-sm-9">
                                        <form:select path="configurationForm" multiple="false" class="form-control">
                                            <form:option value="-1"><spring:message code="general.select.option.pickone" /></form:option>
                                            <form:options items="${configurationList}" itemLabel="name" itemValue="id" />
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="general.label.ok" /></button>
                            </div>
                        </div>
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