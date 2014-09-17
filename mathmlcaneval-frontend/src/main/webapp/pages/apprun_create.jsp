<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">
        <div class="container content">
            <h1><spring:message code="entity.appruns.new" /></h1>
            <form:form method="post" action="${pageContext.request.contextPath}/appruns/create/" commandName="applicationRunForm" class="form-horizontal pull-top-50">
                <div class="row">
                    <label class="col-sm-3 control-label"><spring:message code="entity.appruns.revision" /></label>
                    <div class="col-sm-9">
                        <form:select path="revisionForm" multiple="false" class="form-control">
                            <form:option value="-1"><spring:message code="general.select.option.pickone" /></form:option>
                            <form:options items="${revisionList}" itemLabel="revisionHash" itemValue="id" />
                        </form:select>
                    </div>
                    <form:errors path="revisionForm" element="div" class="col-sm-3 alert alert-danger"/>
                </div>
                <div class="row">
                    <label class="col-sm-3 control-label"><spring:message code="entity.appruns.configuration" /></label>
                    <div class="col-sm-9">
                        <form:select path="configurationForm" multiple="false" class="form-control">
                            <form:option value="-1"><spring:message code="general.select.option.pickone" /></form:option>
                            <form:options items="${configurationList}" itemLabel="name" itemValue="id" />
                        </form:select>
                    </div>
                    <form:errors path="configurationForm" element="div" class="col-sm-3 alert alert-danger"/>
                </div>
                <div class="row">
                    <label class="col-sm-3 control-label"><spring:message code="entity.appruns.input" /></label>
                    <div class="col-sm-9">
                        <div class="input-group">
                            <div class="input-group-btn">
                                <button type="button" id="apprun-input-button" class="btn dropdown-toggle" data-toggle="dropdown">
                                    <spring:message code="general.select.option.pickone" /> <span class="caret"></span>
                                </button>

                                <ul class="dropdown-menu" role="menu">
                                    <li>
                                        <a href="#" class="apprun-input-option" data-apprun-input="source-document">
                                            <spring:message code="entity.appruns.method.sourceDocument" />
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#" class="apprun-input-option" data-apprun-input="conversion-program">
                                            <spring:message code="entity.appruns.method.program" />
                                        </a>
                                    </li>
                                    <li>
                                        <a href="${pageContext.request.contextPath}/appruns/create/select/" class="apprun-input-option" data-apprun-input="manual-selection">
                                            <spring:message code="entity.appruns.method.manual" />
                                        </a>
                                    </li>
                                </ul>
                            </div> <!--/input-group-btn -->
                        </div>
                    </div>
                </div>

                <div id="source-document" class="apprun-input-method" style="display: none;">
                    <div class="row">
                        <label class="col-sm-3 control-label"><spring:message code="entity.appruns.method.sourceDocument" /></label>
                        <div class="col-sm-8">
                            <select id="apprun-sourcedocument-id" class="form-control">
                                <option value="-1"><spring:message code="general.select.option.pickone" /></option>
                                <c:forEach items="${sourceDocumentList}" var="entry">
                                    <option value="${entry.id}">${entry.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-sm-1">
                            <button class="btn apprun-input-add" id="source-document"><spring:message code="general.label.add" /></button>
                        </div>
                    </div>
                </div>

                <div id="conversion-program" class="apprun-input-method" style="display: none;">
                    <div class="row">
                        <label class="col-sm-3 control-label"><spring:message code="entity.appruns.method.program" /></label>
                        <div class="col-sm-8">
                            <select id="apprun-program-id" class="form-control">
                                <option value="-1"><spring:message code="general.select.option.pickone" /></option>
                                <c:forEach items="${programList}" var="entry">
                                    <option value="${entry.id}">${entry.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-sm-1">
                            <button class="btn apprun-input-add" id="conversion-program"><spring:message code="general.label.add" /></button>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <label class="col-sm-3 control-label"><spring:message code="entity.appruns.selected" /></label>
                    <div class="col-sm-9">
                        <textarea rows="5" name="formulaCanonicalizeID" id="apprun-input-formulas" class="form-control"></textarea>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-xm-7 col-sm-offset-2">
                        <button type="submit" class="btn btn-primary"><spring:message code="entity.appruns.button.canonicalize" /></button>
                    </div>
                </div>
            </form:form>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>
