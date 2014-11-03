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
                            <spring:message code="general.field.id" />
                        </th>
                        <th>
                            <spring:message code="entity.annotationvalue.category" />
                        </th>
                        <th>
                            <spring:message code="entity.annotationvalue.priority" />
                        </th>
                        <th>
                            <spring:message code="entity.annotationvalue.value" />
                        </th>
                        <th>
                            <spring:message code="entity.annotationvalue.icon" />
                        </th>
                        <th>
                            <spring:message code="entity.annotationvalue.label" />
                        </th>
                        <th>
                            <spring:message code="entity.annotationvalue.description" />
                        </th>
                        <th>
                            <spring:message code="general.table.option" />
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
                            <c:out value="${entry.priority}" />
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
                            <a href="${pageContext.request.contextPath}/annotationvalue/edit/<c:out value="${entry.id}"/>"><span class="glyphicon glyphicon-pencil"></span></a>
                            <a href="${pageContext.request.contextPath}/annotationvalue/delete/<c:out value="${entry.id}"/>"><span class="glyphicon glyphicon-remove"></span></a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>