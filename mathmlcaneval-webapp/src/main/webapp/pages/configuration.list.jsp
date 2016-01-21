<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">
        <h1><spring:message code="configuration.header" /></h1>
        <p class="text-muted"><spring:message code="configuration.header.muted" /> <a href="${context}/configuration/all/"><spring:message code="global.here" /></a></p>
        <div class="pull-xs-right">
            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#createConfigModal" data-whatever="@mdo"><spring:message code="configuration.operation.new" /></button>
        </div>
        <table class="table">
            <thead class="thead-default">
                <tr>
                    <th><spring:message code="global.id" /></th>
                    <th><spring:message code="configuration.label.name" /></th>
                    <th><spring:message code="configuration.label.note" /></th>
                    <th><spring:message code="configuration.label.enabled" /></th>
                    <th><spring:message code="global.operation" /></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${configurationList}" var="entry">
                    <tr>
                        <td><c:out value="${entry.id}" /></td>
                        <td>
                            <a href="#" class="show-config" data-config="${entry.id}">
                                <c:out value="${entry.name}" /> 
                            </a>
                        </td>
                        <td><c:out value="${entry.note}" /></td>
                        <td>                            
                            <c:choose>
                                <c:when test="${entry.active}">
                                    <a href="${context}/configuration/disable/${entry.id}/" class="disable-link-color">
                                        <i class="fa fa-toggle-on"></i>
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${context}/configuration/enable/${entry.id}/" class="disable-link-color">
                                        <i class="fa fa-toggle-off"></i>
                                    </a>
                                </c:otherwise>
                            </c:choose>          
                        </td>
                        <td>
                            <a href="${context}/configuration/download/${entry.id}/" class="disable-link-color" title="<spring:message code="global.operation.download" />">
                                <i class="fa fa-download"></i>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div class="modal fade" id="createConfigModal" tabindex="-1" role="dialog" aria-labelledby="createConfigModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <form:form method="POST" commandName="configurationForm" action="${context}/configuration/submit/">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title" id="exampleModalLabel"><spring:message code="configuration.header.new" /></h4>
                        </div>
                        <div class="modal-body">                        
                            <div class="form-group row">
                                <label for="name" class="col-sm-2 form-control-label"><spring:message code="configuration.label.name" /></label>
                                <div class="col-sm-10">
                                    <form:input path="name" cssClass="form-control" />
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="configuration" class="col-sm-2 form-control-label"><spring:message code="configuration.label.configuration" /></label>
                                <div class="col-sm-10">
                                    <form:textarea path="configuration" cssClass="form-control" />
                                </div>
                            </div>
                            <div class="form-group row">
                                <label for="note" class="col-sm-2 form-control-label"><spring:message code="configuration.label.note" /></label>
                                <div class="col-sm-10">
                                    <form:textarea path="note" cssClass="form-control" />
                                </div>
                            </div>                     
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code="global.button.close" /></button>
                            <button type="submit" class="btn btn-primary"><spring:message code="global.button.submit" /></button>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
        <div class="modal fade" id="showConfigModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-lg" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title" id="myModalLabel"><spring:message code="configuration.header.view" /></h4>
                    </div>
                    <div class="modal-body config-body">
                        <pre>
                            <code class="xml"></code>
                        </pre>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal"><spring:message code="global.button.close" /></button>
                    </div>
                </div>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>