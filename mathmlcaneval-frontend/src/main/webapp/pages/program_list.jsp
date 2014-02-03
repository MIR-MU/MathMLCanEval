<%-- 
    Document   : revision_list
    Created on : Jan 29, 2014, 4:39:55 PM
    Author     : Empt
--%>

<div class="container content">
    <div class="row">
        <div class="col-lg-10">
            <form method="get" action="${pageContext.request.contextPath}/program/search/" class="form-inline">
                <div class="form-group">                    
                    <input type="text" name="keywords" class="form-control search-bar" />
                </div>
                <button type="submit" class=" btn btn-primary">!search</button>
            </form>
        </div>
        <div class="col-lg-2">
            <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                <div class="pull-right">        
                    <a href="${pageContext.request.contextPath}/program/create/" class="btn btn-primary">!create Program</a>        
                </div>
            </sec:authorize>
        </div>
    </div>

    <table class="table table-bordered table-striped pull-top-50">
        <thead>
            <tr>
                <th>!id</th>
                <th>!name</th>
                <th>!version</th>
                <th>!parameters</th>
                <th>!note</th>
                <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                    <th>!options</th>
                </sec:authorize>
            </tr>
        </thead>
        <c:choose>
            <c:when test="${fn:length(programList) gt 0}">
                <c:forEach items="${programList}" var="entry">
                    <tr>
                        <td><c:out value="${entry.id}" /></td>
                        <td><c:out value="${entry.name}" /></td>
                        <td><c:out value="${entry.version}" /></td>
                        <td><c:out value="${entry.parameters}" /></td>
                        <td><c:out value="${entry.note}" /></td>
                        <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                            <td>
                                <a href="${pageContext.request.contextPath}/program/delete/<c:out value="${entry.id}" />/">x</a> 
                                <a href="${pageContext.request.contextPath}/program/edit/<c:out value="${entry.id}" />/">+</a>
                            </td>
                        </sec:authorize>                
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <sec:authorize ifAnyGranted="ROLE_ANONYMOUS">
                        <td colspan="5" class="text-center">!no records so far</td>
                    </sec:authorize>
                    <sec:authorize ifNotGranted="ROLE_ANONYMOUS">
                        <td colspan="6" class="text-center">!no records so far</td>
                    </sec:authorize>
                </tr>
            </c:otherwise>
        </c:choose>
    </table>
</div>