<div class="container content">
    <div class="row">
        <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
            <div class="pull-right">
                <a href="${pageContext.request.contextPath}/configuration/create/" class="btn btn-primary"><spring:message code="entity.configuration.create" /></a>        
            </div>
        </sec:authorize>
    </div>

    <div class="pull-top-50">
        <div class="pull-right"><a href="${pageContext.request.contextPath}/configuration/list/" class="btn btn-primary btn-sm"><spring:message code="general.table.refresh" /></a></div>

        <table class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th><spring:message code="general.field.id" /></th>
                    <th><spring:message code="entity.configuration.name" /></th>
                    <th><spring:message code="entity.configuration.note" /></th>
                        <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                        <th><spring:message code="general.table.option" /></th>
                        </sec:authorize>
                </tr>
            </thead>
            <c:choose>
                <c:when test="${fn:length(configurationList) gt 0}">
                    <c:forEach items="${configurationList}" var="entry">
                        <tr>
                            <td><c:out value="${entry.id}" /></td>
                            <td><a href="${pageContext.request.contextPath}/configuration/view/<c:out value="${entry.id}" />/"><c:out value="${entry.name}" /></a></td>                            
                            <td><c:out value="${entry.note}" /></td>
                            <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                                <td>
                                    <a href="${pageContext.request.contextPath}/configuration/edit/<c:out value="${entry.id}" />/">
                                        <span class="glyphicon glyphicon-wrench"></span>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/configuration/delete/<c:out value="${entry.id}" />/">
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
