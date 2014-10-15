<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">        
        <div class="container content">
            <h1><spring:message code="entity.appruns.list" /></h1>
            <table class="table table-bordered table-striped">
                <thead>
                    <tr>
                        <th><spring:message code="general.field.id" /></th>
                        <th><spring:message code="entity.appruns.start" /></th>
                        <th><spring:message code="entity.appruns.stop" /></th>
                        <th><spring:message code="entity.appruns.note" /></th>
                        <th><spring:message code="entity.appruns.user" /></th>
                        <th><spring:message code="entity.appruns.configuration" /></th>
                        <th><spring:message code="entity.appruns.revision" /></th>
                        <th><spring:message code="entity.appruns.outputs" /></th>
                        <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                        <th>
                            <spring:message code="general.table.option" />
                        </th>
                        </sec:authorize>
                    </tr>
                </thead>
                <c:choose>
                    <c:when test="${fn:length(apprunList) gt 0}">
                        <c:forEach items="${apprunList}" var="entry">
                            <tr>
                                <td><c:out value="${entry.id}" /></td>
                                <td><joda:format value="${entry.startTime}" style="SS" /></td>
                                <td><joda:format value="${entry.stopTime}" style="SS" /></td>
                                <td><c:out value="${entry.note}" /></td>
                                <td><c:out value="${entry.user.username}" /></td>
                                <td><a href="${pageContext.request.contextPath}/configuration/view/<c:out value="${entry.configuration.id}" />/"><c:out value="${entry.configuration.name}" /></a></td>
                                <td><c:out value="${entry.revision.revisionHash}" /></td>
                                <td><c:out value="${entry.canonicOutputCount}" /></td>
                                <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                                    <td>
                                        <a href="${pageContext.request.contextPath}/appruns/delete/<c:out value="${entry.id}" />/">X</a>
                                    </td>                                    
                                </sec:authorize>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>                            
                            <td colspan="7" class="text-center"><spring:message code="general.table.norecords" /></td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </table>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>