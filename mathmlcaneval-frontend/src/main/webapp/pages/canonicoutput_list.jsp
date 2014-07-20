<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">
        <div class="container content">
    <div class="row">
        <div class="col-lg-10 pull-right">
            <form method="get" action="${pageContext.request.contextPath}/canonicoutput/search/" class="form-inline">
                <div class="form-group">
                    <input type="text" name="keywords" class="form-control search-bar" />
                </div>
                <button type="submit" class="btn btn-primary" disabled><spring:message code="general.label.search" /></button>
            </form>
        </div>
    </div>
    <div class="row text-center">
       <a href="${pageContext.request.contextPath}/canonicoutput/view/${reference.id}" class="img-thumbnail">
           <div class="preview">
               <c:out value="${reference.outputForm}" escapeXml="false" />
           </div>
       </a>
    </div>
    <div class="row pull-top-50 text-center">
        <c:choose>
            <c:when test="${fn:length(outputList) gt 0}">
               <c:forEach items="${outputList}" var="entry">
                   <a href="${pageContext.request.contextPath}/canonicoutput/view/${entry.id}" class="img-thumbnail">
                       <div class="preview">
                           <c:out value="${entry.outputForm}" escapeXml="false" />
                       </div>
                   </a>
               </c:forEach>
            </c:when>
            <c:otherwise>
                <h2 class="text-center"><spring:message code="general.table.norecords" /></h2>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="text-center">
        <ul class="pagination pagination-sm">
          <li<c:if test="${pagination.startPage == 1}"> class="disabled"</c:if>>
            <a href="?pageNumber=${pagination.startPage - 1}">&laquo;</a>
          </li>
          <c:forEach var="i" begin="${pagination.startPage}" end="${pagination.startPage + pagination.pages - 1}">
          <li<c:if test="${pagination.pageNumber == i}"> class="active"</c:if>>
            <a href="?pageNumber=${i}">${i}</a>
          </li>
          </c:forEach>
          <li><a href="?pageNumber=${pagination.startPage + pagination.pages}">&raquo;</a></li>
        </ul>
    </div>
</div>
    </tiles:putAttribute>
</tiles:insertDefinition>

