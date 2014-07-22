<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">        
        <div class="container content">


            <table class="table table-bordered table-striped pull-top-50">
                <thead>
                    <tr>
                        <th><spring:message code="general.field.id" /></th>
                        <th>start</th>
                        <th>stop</th>
                        <th>note</th>
                        <th>user</th>
                        <th>config</th>
                        <th>revision</th>
                        <th>!outputs</th>
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