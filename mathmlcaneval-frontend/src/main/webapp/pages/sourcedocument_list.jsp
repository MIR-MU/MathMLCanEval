<div class="container content">
    <div class="row">
        <div class="col-lg-10">
            <form method="get" action="${pageContext.request.contextPath}/sourcedocument/search/" class="form-inline">
                <div class="form-group">
                    <input type="text" name="keywords" class="form-control search-bar" />
                </div>
                <button type="submit" class=" btn btn-primary"><spring:message code="general.label.search" /></button>
            </form>
        </div>
        <div class="col-lg-2">
            <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                <div class="pull-right">
                    <a href="${pageContext.request.contextPath}/sourcedocument/create/" class="btn btn-primary"><spring:message code="entity.sourceDocument.create" /></a>
                </div>
            </sec:authorize>
        </div>
    </div>

    <div class="pull-top-50">
        <div class="pull-right"><a href="${pageContext.request.contextPath}/sourcedocument/list/" class="btn btn-primary btn-sm"><spring:message code="general.table.refresh" /></a></div>

        <table class="table table-bordered table-striped">
            <thead>
                <tr>
                    <th><spring:message code="general.field.id" /></th>
                    <th><spring:message code="entity.sourceDocument.documentPath" /></th>
                    <th><spring:message code="entity.sourceDocument.note" /></th>
                    <th>!formulazozdroja</th>
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
                            <td><c:out value="${entry.documentPath}" /></td>
                            <td><a href="${pageContext.request.contextPath}/sourcedocument/view/<c:out value="${entry.id}" />"><c:out value="${entry.note}" /></a></td>                            
                            <td><a href="${pageContext.request.contextPath}/formula/create/sourcedocument/<c:out value="${entry.id}/" />">new formula</a></td>                            
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
