<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">        
        <div class="container content">
            <div class="row">
                <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                    <div class="pull-right">
                        <a href="${pageContext.request.contextPath}/sourcedocument/create/" class="btn btn-primary"><spring:message code="entity.sourceDocument.create" /></a>
                    </div>
                </sec:authorize>
            </div>

            <div class="pull-top-50">
                <div class="pull-right"><a href="${pageContext.request.contextPath}/sourcedocument/list/" class="btn btn-primary btn-sm"><spring:message code="general.table.refresh" /></a></div>

                <table class="table table-bordered table-striped">
                    <thead>
                        <tr>
                            <th><spring:message code="general.field.id" /></th>
                            <th><spring:message code="entity.sourceDocument.name" /></th>
                            <th><spring:message code="entity.sourceDocument.note" /></th>
                                <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                                <th><spring:message code="general.table.option" /></th>
                                </sec:authorize>
                        </tr>
                    </thead>
                    <c:choose>
                        <c:when test="${fn:length(sourceDocumentList) gt 0}">
                            <c:forEach items="${sourceDocumentList}" var="entry">
                                <tr>
                                    <td><c:out value="${entry.id}" /></td>
                                    <td><c:out value="${entry.name}" /></td>
                                    <td><c:out value="${entry.note}" /></td>                            
                                    <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                                        <td>
                                            <a href="${pageContext.request.contextPath}/sourcedocument/edit/<c:out value="${entry.id}" />/">
                                                <span class="glyphicon glyphicon-wrench"></span>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/sourcedocument/delete/<c:out value="${entry.id}" />/">
                                                <span class="glyphicon glyphicon-remove"></span>
                                            </a>
                                        </td>
                                    </sec:authorize>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <sec:authorize ifAnyGranted="ROLE_ANONYMOUS">
                                    <td colspan="3" class="text-center"><spring:message code="general.table.norecords" /></td>
                                </sec:authorize>
                                <sec:authorize ifNotGranted="ROLE_ANONYMOUS">
                                    <td colspan="4" class="text-center"><spring:message code="general.table.norecords" /></td>
                                </sec:authorize>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </table>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>