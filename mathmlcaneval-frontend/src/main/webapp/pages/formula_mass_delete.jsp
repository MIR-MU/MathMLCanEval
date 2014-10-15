<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">
        <div class="container content">
            <div class="row pull-top-50">
                <div class="col-md-12">
                    <div class="pull-right">
                        <a href="${pageContext.request.contextPath}/formula/search/" class="btn btn-danger"><spring:message code="general.label.goback" /></a>
                    </div>
                    <div class="pull-right">
                        <a href="#" class="btn btn-default invert-selection-button"><spring:message code="general.button.invert" /></a>
                    </div>
                </div>
            </div>

            <div class="row">
            </div>
            <form method="POST" action="${pageContext.request.contextPath}/formula/massdelete/">
                <div class="row">
                    <c:choose>
                        <c:when test="${fn:length(formulaList) gt 0}">
                            <c:forEach items="${formulaList}" var="entry" varStatus="i">
                                <c:if test="${not i.first and i.index % 4 == 0}">
                                </div>
                                <div class="row">
                                </c:if>
                                <div class="col-md-3 img-thumbnail">
                                    <input type="checkbox" value="${entry.id}" style="display:none" name="formulaDeleteID" />
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
                <div class="row">
                    <div class="col-md-12">
                        <div class="text-center pull-top-50"> 
                            <input type="submit" value="<spring:message code="entity.formula.button.massdelete"/>" class="btb btn-warning btn-lg"/>
                        </div>
                    </div>
                </div>
            </form>
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
