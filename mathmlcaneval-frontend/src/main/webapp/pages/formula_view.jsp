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
            <h1><spring:message code="entity.formula.entry" /></h1>
            <div class="btn-group pull-right space-bottom-10">
                <sec:authentication var="user" property="principal" />
                <sec:authorize access="hasRole('ROLE_USER')">
                    <button type="submit" class="btn btn-warning" data-toggle="modal" data-target="#canonModal"><spring:message code="entity.formula.canonicalizer.run" /></button>
                </sec:authorize>
                <!-- superuser -->
                <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                    <a href="${pageContext.request.contextPath}/formula/delete/${formulaEntry.id}" class="btn btn-danger"><spring:message code="general.label.delete" /></a>
                </sec:authorize>
                <!-- normal users -->
                <sec:authorize access="hasRole('ROLE_USER') and !hasRole('ROLE_ADMINISTRATOR')">
                    <a href="${pageContext.request.contextPath}/formula/delete/${formulaEntry.id}" class="btn btn-danger" <c:if test="${user.username ne formulaEntry.user.username}">disabled="disabled"</c:if>><spring:message code="general.label.delete" /></a>
                </sec:authorize>

            </div>

            <div class="modal fade" id="canonModal" tabindex="-1" role="dialog" aria-labelledby="canonModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <br />
                        </div>
                        <div class="modal-body">
                            <form:form id="canonicalizeForm" action="${pageContext.request.contextPath}/formula/run/" commandName="applicationRunForm" class="form-horizontal pull-top-50">
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
                            </form:form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="general.label.close" /></button>
                            <button id="btnCanon" type="button" class="btn btn-warning"><spring:message code="entity.formula.canonicalizer.run" /></button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row pull-top-50">
                <div class="col-lg-12">
                    <div class="row">
                        <div class="col-md-5">
                            <div class="panel panel-primary">
                                <!-- Formula details -->
                                <div class="panel-heading"><spring:message code="entity.formula.detail" /></div>
                                <table class="table table-bordered table-striped">
                                    <tr>
                                        <td><spring:message code="general.field.id" /></td>
                                        <td><c:out value="${formulaEntry.id}" /></td>
                                    </tr>
                                    <tr>
                                        <td><spring:message code="entity.formula.user" /></td>
                                        <td><c:out value="${formulaEntry.user.username}" /></td>
                                    </tr>
                                    <tr>
                                        <td><spring:message code="entity.formula.sourceDocument" /></td>
                                        <td><a href="${pageContext.request.contextPath}/sourcedocument/view/${formulaEntry.sourceDocument.id}"><c:out value="${formulaEntry.sourceDocument.name}" /></a></td>
                                    </tr>
                                    <tr>
                                        <td><spring:message code="entity.formula.program" /></td>
                                        <td><a href="${pageContext.request.contextPath}/program/view/${formulaEntry.program.id}"><c:out value="${formulaEntry.program.name}" /></a></td>
                                    </tr>
                                    <tr>
                                        <td><spring:message code="entity.formula.note" /></td>
                                        <td><c:out value="${formulaEntry.note}" /></td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                        <div class="col-md-7">                            
                            <div class="panel panel-success">
                                <div class="panel-heading"><spring:message code="entity.canonicOutput.annotations" /></div>
                                <table class="table table-striped" id="annotationTable">
                                    <tbody>
                                        <c:choose>
                                            <c:when test="${fn:length(formulaEntry.annotations)  == 0}">
                                                <tr class="empty-table">
                                                    <td>
                                                        <spring:message code="general.table.norecords" />
                                                    </td>
                                                </tr>
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach items="${formulaEntry.annotations}" var="entry">
                                                    <tr>
                                                        <td>${entry.user.username}</td>
                                                        <td class="annotation-note-cell">${entry.note}</td>
                                                    </tr>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>
                                    </tbody>
                                </table>
                                <sec:authorize access="hasRole('ROLE_USER')">
                                    <form method="POST" action="${pageContext.request.contextPath}/formula/annotate/" id="annotationForm">
                                        <div class="panel-body">
                                            <div class="row">
                                                <div class="col-md-12">
                                                    <div class="input-group">
                                                        <div class="input-group-btn">
                                                            <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
                                                                <spring:message code="general.label.annotate" /> <span class="caret"></span>
                                                            </button>

                                                            <ul class="dropdown-menu" role="menu">
                                                                <li>
                                                                    <a href="#" class="annotation-option" data-annotation="#formulaRemove">
                                                                        <span class="glyphicon glyphicon-trash"></span> #formulaRemove
                                                                    </a>
                                                                </li>
                                                                <li>
                                                                    <a href="#" class="annotation-option" data-annotation="#formulaMeaningless">
                                                                        <span class="glyphicon glyphicon glyphicon-remove"></span> #formulaMeaningless
                                                                    </a>
                                                                </li>
                                                                <li class="divider"></li>
                                                                <li>
                                                                    <a href="#" id="clear-form">
                                                                        <spring:message code="general.label.clear.input" />
                                                                    </a>
                                                                </li>
                                                            </ul>
                                                        </div> <!--/input-group-btn --> 

                                                        <input type="hidden" name="canonicOutputId" value="${canonicOutput.id}" />
                                                        <input type="hidden" name="formulaID" value="<c:out value="${formulaEntry.id}" />" />
                                                        <input type="text" id="annotation-value" name="annotation-value" class="form-control" />
                                                        <span class="input-group-btn">
                                                            <input type="submit" class="btn btn-primary" value="<spring:message code="general.button.submit" />" />
                                                        </span>
                                                    </div> <!--/input-group -->
                                                </div> <!-- /col-md-12-->
                                            </div> <!-- /row -->
                                        </div> <!-- /panel-body -->
                                    </form>
                                </sec:authorize>
                            </div>
                        </div>
                    </div>


                    <div class="panel panel-primary">
                        <!-- Formula in MathML & rendered -->
                        <div class="panel-heading">
                            <spring:message code="entity.formula.xml" />
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-md-6">
                                    <pre class="brush: xml">
                                        <c:out value="${formulaEntry.xml}" />
                                    </pre>
                                </div>
                                <div class="col-md-6">
                                    <div class="well-sm">
                                        <c:out value="${formulaEntry.xml}" escapeXml="false" />
                                    </div>                                    
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="panel panel-primary">
                        <div class="panel-heading"><spring:message code="entity.formula.canonicalizations" /></div>
                        <!-- Performed canonicalizations -->
                        <c:if test="${fn:length(formulaEntry.outputs) gt 0}">
                            <table class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th><spring:message code="general.field.timestamp" /></th>
                                        <th><spring:message code="general.field.id" /></th>
                                        <th><spring:message code="entity.revision.hash" /></th>
                                        <th><spring:message code="entity.configuration.config" /></th>
                                        <th><spring:message code="entity.canonicOutput.runningTime" /></th>
                                    </tr>
                                </thead>
                                <c:forEach items="${formulaEntry.outputs}" var="entry">
                                    <tbody>
                                        <tr>
                                            <td><joda:format value="${entry.applicationRun.startTime}" style="SS" /></td>
                                            <td><a href="${pageContext.request.contextPath}/canonicoutput/view/${entry.id}">${entry.id}</a></td>
                                            <td><a href="https://github.com/formanek/MathMLCan/commit/<c:out value="${entry.applicationRun.revision.revisionHash}" />"><c:out value="${entry.applicationRun.revision.revisionHash}" /></a></td>
                                            <td><a href="${pageContext.request.contextPath}/configuration/view/${entry.applicationRun.configuration.id}">${entry.applicationRun.configuration.id}</a></td>
                                            <td>${entry.runningTime} ms</td>
                                        </tr>
                                    </tbody>
                                </c:forEach>
                            </table>
                        </c:if>
                    </div>
                    <c:if test="${fn:length(formulaEntry.similarFormulas) > 0}">
                        <div class="panel panel-primary">
                            <div class="panel-heading"><spring:message code="entity.formula.equivalent" /></div>
                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th><spring:message code="general.field.id" /></th>
                                        <th><spring:message code="entity.formula.rendered" /></th>
                                    </tr>
                                </thead>
                                <c:forEach items="${formulaEntry.similarFormulas}" var="entry">
                                    <tr>
                                        <td><a href="${pageContext.request.contextPath}/formula/view/<c:out value="${entry.id}" />/"><c:out value="${entry.id}" /></a></td>
                                        <td><c:out value="${entry.xml}" escapeXml="false" /></td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </c:if>
                </div>
            </div>

        </tiles:putAttribute>
    </tiles:insertDefinition>