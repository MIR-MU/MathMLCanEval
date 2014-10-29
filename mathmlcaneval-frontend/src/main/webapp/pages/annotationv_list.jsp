<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">        
        <div class="container content">
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>
                            id
                        </th>
                        <th>
                            category
                        </th>
                        <th>
                            position
                        </th>
                        <th>
                            value
                        </th>
                        <th>
                            icon
                        </th>
                        <th>
                            label
                        </th>
                        <th>
                            description
                        </th>
                        <th>
                            options
                        </th>
                    </tr>
                </thead>
                <c:forEach items="${annotationValueList}" var="entry">
                    <tr>
                        <td>
                            <c:out value="${entry.id}" />
                        </td>
                        <td>
                            <c:out value="${entry.type}" />
                        </td>
                        <td>
                            <c:out value="${entry.position}" />
                        </td>
                        <td>
                            <c:out value="${entry.value}" />
                        </td>
                        <td>
                            <c:out value="${entry.icon}" />
                        </td>
                        <td>
                            <c:out value="${entry.label}" />
                        </td>
                        <td>
                            <c:out value="${entry.description}" />
                        </td>
                        <td>
                            <span class="glyphicon glyphicon-pencil"></span>
                            <span class="glyphicon glyphicon-remove"></span>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>