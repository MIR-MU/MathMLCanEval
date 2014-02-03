<%-- 
    Document   : revision_list
    Created on : Jan 29, 2014, 4:39:55 PM
    Author     : Empt
--%>

<div class="container content">
    <div class="row">
        <div class="col-lg-10">
            <form method="get" action="${pageContext.request.contextPath}/revision/search/" class="form-inline">
                <div class="form-group">                    
                    <input type="text" name="keywords" class="form-control search-bar" />
                </div>
                <button type="submit" class=" btn btn-primary">!search</button>
            </form>
        </div>
        <div class="col-lg-2">
            <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                <div class="pull-right">        
                    <a href="${pageContext.request.contextPath}/revision/create/" class="btn btn-primary">!create revision</a>        
                </div>
            </sec:authorize>
        </div>
    </div>

    <table class="table table-bordered table-striped pull-top-50">
        <thead>
            <tr>
                <th>!id</th>
                <th>!hash</th>
                <th>!note</th>
                    <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                    <th>!options</th>
                    </sec:authorize>
            </tr>
        </thead>
        <c:choose>
            <c:when test="${fn:length(revisionList) gt 0}">
                <c:forEach items="${revisionList}" var="entry">
                    <tr>
                        <td><c:out value="${entry.id}" /></td>
                        <td><a href="https://github.com/michal-ruzicka/MathMLCanEval/commit/<c:out value="${entry.revisionHash}" />"><c:out value="${entry.revisionHash}" /></a></td>
                        <td><c:out value="${entry.note}" /></td>
                        <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                            <td>
                                <a href="${pageContext.request.contextPath}/revision/delete/<c:out value="${entry.id}" />/">x</a> 
                                <a href="${pageContext.request.contextPath}/revision/edit/<c:out value="${entry.id}" />/">+</a>
                            </td>
                        </sec:authorize>                
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <sec:authorize ifAnyGranted="ROLE_ANONYMOUS">
                        <td colspan="3" class="text-center">!no records so far</td>
                    </sec:authorize>
                    <sec:authorize ifNotGranted="ROLE_ANONYMOUS">
                        <td colspan="4" class="text-center">!no records so far</td>
                    </sec:authorize>
                </tr>
            </c:otherwise>
        </c:choose>
    </table>
</div>