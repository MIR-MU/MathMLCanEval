<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">  
        <div class="content">
            <div class="container">
                <h2><spring:message code="similarity.match.title" /></h2>                
                <form action="${pageContext.request.contextPath}/formula/submitsimilar/" method="POST">
                    <input type="hidden" name="requestFormula" value="<c:out value="${requestFormula.id}" />" />
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th><spring:message code="general.field.id" /></th>
                                <th><spring:message code="general.label.include" /></th>
                                <th><spring:message code="entity.formula.rendered" /></th>
                                <th>!request Formula</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${similarForms}" var="entry">
                                <tr>
                                    <td>
                                        ${entry.id}
                                    </td>
                                    <td>
                                        <input type="checkbox" name="similarFormulaID" value="${entry.id}" />
                                    </td>
                                    <td><c:out value="${entry.xml}" escapeXml="false" /></td>
                                    <td> <c:out value="${requestFormula.xml}" escapeXml="false"/></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <spring:message code="similarity.override" /> <input type="checkbox" name="overrideCurrent" />
                    <input type="submit" class="btn btn-default "/>
                </form>
                <!-- pagination -->
                <div class="text-center">
                    <form:form method="GET" action="${pageContext.request.contextPath}/formula/similar/" commandName="pagination">
                        <ul class="pager">
                            <c:if test="${pagination.pageSize != pagination.defaultPageSize}">
                                <c:set var="size" value="&pageSize=${pagination.pageSize}" />
                                <form:hidden path="pageSize" />
                            </c:if>
                            <li><a href="?pageNumber=1${size}">&laquo;</a></li>
                                <c:choose>
                                    <c:when test="${pagination.pageNumber == 1}">
                                    <li class="disabled"><a href="javascript:;">&lsaquo;</a></li>
                                    </c:when>
                                    <c:otherwise>
                                    <li><a href="?pageNumber=${pagination.pageNumber - 1}${size}">&lsaquo;</a></li>
                                    </c:otherwise>
                                </c:choose>
                            <li><form:input type="text" path="pageNumber" size="2" /></li>
                            <li><button type="submit" class="btn btn-primary btn-sm"><spring:message code="pagination.go" /></button></li>
                            <li>/ ${pagination.pages}</li>
                                <c:choose>
                                    <c:when test="${pagination.pageNumber == pagination.pages}">
                                    <li class="disabled"><a href="javascript:;">&rsaquo;</a></li>
                                    </c:when>
                                    <c:otherwise>
                                    <li><a href="?pageNumber=${pagination.pageNumber + 1}${size}">&rsaquo;</a></li>
                                    </c:otherwise>
                                </c:choose>
                            <li><a href="?pageNumber=${pagination.pages}${size}">&raquo;</a></li>
                        </ul>
                    </form:form>
                </div>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>
