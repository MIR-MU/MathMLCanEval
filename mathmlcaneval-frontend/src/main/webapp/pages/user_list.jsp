<%-- 
    Document   : revision_list
    Created on : Jan 29, 2014, 4:39:55 PM
    Author     : Empt
--%>

<div class="container content">
    <div class="row">
        <div class="col-lg-10">
            <form method="get" action="${pageContext.request.contextPath}/user/search/" class="form-inline">
                <div class="form-group">                    
                    <input type="text" name="keywords" class="form-control search-bar" />
                </div>
                <button type="submit" class=" btn btn-primary">!search</button>
            </form>
        </div>
        <div class="col-lg-2">
            <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                <div class="pull-right">        
                    <a href="${pageContext.request.contextPath}/user/create/" class="btn btn-primary">!create user</a>        
                </div>
            </sec:authorize>
        </div>
    </div>

    <table class="table table-bordered table-striped pull-top-50">
        <thead>
            <tr>
                <th>!id</th>
                <th>!username</th>
                <th>!realName</th>
                <th>!email</th>
                <th>!roles</th>
                <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                    <th>!options</th>
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
                        <td>!x</td>
                        <td>
                            <c:forEach items="${entry.userRoles}" var="roleEntry">
                                <c:out value="${roleEntry.roleName}" />
                            </c:forEach>
                        </td>
                        <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                            <td>
                                <a href="${pageContext.request.contextPath}/user/delete/<c:out value="${entry.id}" />/">x</a> 
                                <a href="${pageContext.request.contextPath}/user/edit/<c:out value="${entry.id}" />/">+</a>
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