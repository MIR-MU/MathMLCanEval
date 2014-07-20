<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">
        <div class="container content">
            <div class="row">
                <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                    <div class="pull-right">
                        <a href="${pageContext.request.contextPath}/userrole/create/" class="btn btn-primary"><spring:message code="entity.userrole.create" /></a>        
                    </div>
                </sec:authorize>
            </div>

            <div class="pull-top-50">
                <div class="pull-right"><a href="${pageContext.request.contextPath}/userrole/list/" class="btn btn-primary btn-sm"><spring:message code="general.table.refresh" /></a></div>
                <table class="table table-bordered table-striped pull-top-50">
                    <thead>
                        <tr>
                            <th><spring:message code="general.field.id" /></th>
                            <th><spring:message code="entity.userrole.name" /></th>
                                <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                                <th><spring:message code="general.table.option" /></th>
                                </sec:authorize>
                        </tr>
                    </thead>
                    <c:choose>
                        <c:when test="${fn:length(userRoleList) gt 0}">
                            <c:forEach items="${userRoleList}" var="entry">
                                <tr>
                                    <td><c:out value="${entry.id}" /></td>
                                    <td><a href="${pageContext.request.contextPath}/user/list/role_name=<c:out value="${entry.roleName}" />;/"><c:out value="${entry.roleName}" /></a></td>
                                        <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                                        <td>                                
                                            <a href="${pageContext.request.contextPath}/userrole/edit/<c:out value="${entry.id}" />/">
                                                <span class="glyphicon glyphicon-wrench"></span>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/userrole/delete/<c:out value="${entry.id}" />/">
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
                                    <td colspan="5" class="text-center"><spring:message code="general.table.norecords" /></td>
                                </sec:authorize>
                                <sec:authorize ifNotGranted="ROLE_ANONYMOUS">
                                    <td colspan="6" class="text-center"><spring:message code="general.table.norecords" /></td>
                                </sec:authorize>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </table>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>