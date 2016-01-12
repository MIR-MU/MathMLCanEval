<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">
        <h1>List of enabled configurations</h2>
        <p class="text-muted">For list of all configuration click <a href="${context}/configuration/all/">here</a></p>
        <div class="pull-xs-right">
            <a href="${context}/configuration/submit/" class="btn btn-primary">New configuration</a>
        </div>
        <table class="table">
            <thead class="thead-default">
                <tr>
                    <th>#</th>
                    <th>name</th>
                    <th>note</th>
                    <th>operations</th>
                    <th>delete</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${configurationList}" var="entry">
                    <tr>
                        <td><c:out value="${entry.id}" /></td>
                        <td><c:out value="${entry.name}" /></td>
                        <td><c:out value="${entry.note}" /></td>
                        <td><a href="#">edit</a> <a href="#">delete</a></td>
                        <td><c:out value="${entry.configuration}" /></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </tiles:putAttribute>
</tiles:insertDefinition>