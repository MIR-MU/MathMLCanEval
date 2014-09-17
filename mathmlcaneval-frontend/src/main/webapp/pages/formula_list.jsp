<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">
        <div class="container content">
            <div class="row">
                <div class="col-lg-10 pull-right">
                    <form method="get" action="${pageContext.request.contextPath}/formula/search/" class="form-inline">
                        <div class="form-group">
                            <input type="text" name="keywords" class="form-control search-bar" />
                        </div>
                        <button type="submit" class="btn btn-primary" disabled><spring:message code="general.label.search" /></button>
                    </form>
                </div>
            </div>
            <div class="row pull-top-50">
                <div class="col-md-12">
                    <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                        <div class="pull-right">
                            <a href="${pageContext.request.contextPath}/formula/massdelete/" class="btn btn-danger"><spring:message code="general.label.massdelete" /></a>
                        </div>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_USER')">
                        <div class="pull-right">
                            <a href="${pageContext.request.contextPath}/appruns/create/select/" class="btn btn-warning"><spring:message code="entity.formula.canonicalizer.run" /></a>
                        </div>
                    </sec:authorize>
                </div>
            </div>
            <div class="row">
                <c:choose>
                    <c:when test="${fn:length(formulaList) gt 0}">
                        <c:forEach items="${formulaList}" var="entry" varStatus="i">
                            <c:if test="${not i.first and i.index % 4 == 0}">
                            </div>
                            <div class="row">
                            </c:if>
                            <div class="col-md-3 img-thumbnail">
                                <a href="${pageContext.request.contextPath}/formula/view/${entry.id}">
                                    <div class="preview text-center">
                                        <c:out value="${entry.xml}" escapeXml="false" />
                                    </div>
                                </a>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div class="col-md-12 text-center">
                            <h2 class="text-center"><spring:message code="general.table.norecords" /></h2>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
            <!-- pagination -->
            <div class="text-center"> 
                <form:form method="get" action="${pageContext.request.contextPath}/formula/list/" commandName="pagination">
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
    </tiles:putAttribute>
</tiles:insertDefinition>
