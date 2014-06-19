<%-- 
    Document   : revision_list
    Created on : Jan 29, 2014, 4:39:55 PM
    Author     : Empt
--%>

<div class="container content">
    <div class="row">
        <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
            <div class="pull-right">
                <a href="${pageContext.request.contextPath}/revision/create/" class="btn btn-primary"><spring:message code="entity.revision.create" /></a>        
            </div>
        </sec:authorize>
    </div>

    <table class="table table-bordered table-striped pull-top-50">
        <thead>
            <tr>
                <th><spring:message code="general.field.id" /></th>
                <th><spring:message code="entity.revision.hash" /></th>
                <th><spring:message code="entity.revision.note" /></th>
                    <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                    <th><spring:message code="general.table.option" /></th>
                    </sec:authorize>
            </tr>
        </thead>
        <c:choose>
            <c:when test="${fn:length(revisionList) gt 0}">
                <c:forEach items="${revisionList}" var="entry">
                    <tr>
                        <td><c:out value="${entry.id}" /></td>
                        <td><a href="http://github.com/formanek/MathMLCan/commit/<c:out value="${entry.revisionHash}" />"><c:out value="${entry.revisionHash}" /></a></td>
                        <td><c:out value="${entry.note}" /></td>
                        <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                            <td>
                                <a href="${pageContext.request.contextPath}/revision/edit/<c:out value="${entry.id}" />/">
                                    <span class="glyphicon glyphicon-wrench"></span>
                                </a>
                                <a href="${pageContext.request.contextPath}/revision/delete/<c:out value="${entry.id}" />/">
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
