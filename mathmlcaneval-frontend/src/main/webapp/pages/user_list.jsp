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
                        <a href="${pageContext.request.contextPath}/user/create/" class="btn btn-primary"><spring:message code="entity.user.create" /></a>        
                    </div>
                </sec:authorize>
            </div>
            <div class="pull-top-50">
                <div class="pull-right"><a href="${pageContext.request.contextPath}/user/list/" class="btn btn-primary btn-sm"><spring:message code="general.table.refresh" /></a></div>
                <table class="table table-bordered table-striped pull-top-50">
                    <thead>
                        <tr>
                            <th><spring:message code="general.field.id" /></th>
                            <th><spring:message code="entity.user.username" /></th>
                            <th><spring:message code="entity.user.realname" /></th>
                            <th><spring:message code="entity.user.email" /></th>
                            <th><spring:message code="entity.user.userroles" /></th>
                                <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                                <th><spring:message code="general.table.option" /></th>
                                </sec:authorize>
                        </tr>
                    </thead>
                    <c:choose>
                        <c:when test="${fn:length(userList) gt 0}">
                            <c:forEach items="${userList}" var="entry">
                                <tr>
                                    <td><c:out value="${entry.id}" /></td>
                                    <td><c:out value="${entry.username}" /></td>
                                    <td><c:out value="${entry.realName}" /></td>
                                    <td><c:out value="${entry.email}" /></td>
                                    <td>
                                        <c:forEach items="${entry.userRoles}" var="roleEntry" varStatus="stat">
                                            <a href="${pageContext.request.contextPath}/user/list/role_name=<c:out value="${roleEntry.roleName}" />" /><c:out value="${roleEntry.roleName}" /></a>
                                            <c:set var="ROLES_ALL" value="${stat.first ? '' : ROLES_ALL}${roleEntry.roleName}" />
                                            <c:set var="ROLES_ALL" value="${ROLES_ALL}${stat.last ? '' : ','}" />
                                        </c:forEach>
                                        <a href="${pageContext.request.contextPath}/user/list/role_name=<c:out value="${ROLES_ALL}" />;" />ALL ROLES</a>
                                    </td>
                                    <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                                        <td>                                
                                            <a href="${pageContext.request.contextPath}/user/edit/<c:out value="${entry.id}" />/">
                                                <span class="glyphicon glyphicon-wrench"></span>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/user/delete/<c:out value="${entry.id}" />/">
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
