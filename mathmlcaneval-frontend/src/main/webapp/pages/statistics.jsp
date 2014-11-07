<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">
        <div class="container content">
            <h1><spring:message code="statistics.title" /></h1>
            <sec:authorize ifNotGranted="ROLE_ANONYMOUS">
                <div class="alert alert-success">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>            
                    <p>
                        <c:choose>
                            <c:when test="${not empty statisticsMessage}">
                                <spring:message code="${statisticsMessage}" />
                            </c:when>
                            <c:otherwise>
                                <spring:message code="statistics.message" />
                            </c:otherwise>
                        </c:choose>                    
                    </p>
                </div>
            </sec:authorize>            

            <div class="row pull-top-50">
                <div class="col-md-5">
                    <form method="get" action="${pageContext.request.contextPath}/statistics/" class="form-inline" id="statisticsForm">
                        <div class="form-group">
                            <label class="col-sm-5 control-label" for="statID"><spring:message code="statistics.choose" /></label>
                            <div class="col-sm-7">
                                <select class="form-control" name="id" id="statID">
                                    <c:forEach items="${statisticsDropdown}" var="entry">
                                        <option value="${entry.key}"><joda:format value="${entry.value}" style="LS" /></option>
                                    </c:forEach>
                                </select>
                            </div>                            
                        </div>
                    </form> 
                    <hr />
                    <table class="table">
                        <tr>
                            <td><spring:message code="statistics.date" /></td>
                            <td><joda:format value="${statisticsDate}" style="LS" /></td>
                        </tr>                        
                        <tr>
                            <td><spring:message code="statistics.total.formulas" /></td>
                            <td><c:out value="${formulaCount}" /></td>
                        </tr>                        
                        <tr>
                            <td><spring:message code="statistics.total.canonic" /></td>
                            <td><c:out value="${coCount}" /></td>
                        </tr>                        
                    </table>
                </div>
                <div class="col-md-7">
                    <div class="stats-panel-body">
                        <!-- http://www.flotcharts.org/-->
                        <div id="flot-placeholder" style="width:400px;height:600px"></div> 
                    </div>
                </div>
            </div>
            <div class="row pull-top-50">
                <div class="col-md-12">
                    <div class="table-responsive">
                        <table class="table table-bordered disable-word-wrap">
                            <thead>
                                <tr>
                                    <td></td>
                                    <c:forEach items="${statisticsColumns}" var="entry">
                                        <td>
                                            <c:out value="${entry}" />
                                        </td>
                                    </c:forEach>
                                </tr>
                                <c:forEach items="${statisticsMap}" var="statEntry">
                                    <tr>
                                        <td><c:out value="${statEntry.key.key.name}" />,<br /><c:out value="${fn:substring(statEntry.key.value.revisionHash,0,8)}" /></td>
                                            <c:forEach items="${statEntry.value}" var="cell">
                                            <td>
                                                <c:choose>
                                                    <c:when test="${cell.value ne 0}">
                                                        <%-- small workaround TODO in future :)
                                                        # from annotationTag has to be removed and replaced with
                                                        urlencoded version %23
                                                        thus we drop first symbol which is for sure # and add %23
                                                        as hardcoded string --%>
                                                        <spring:url value="/formula/search/?configuration=${statEntry.key.key.id}&revision=${statEntry.key.value.id}&annotationContent=%23${fn:substring(cell.key,1,fn:length(cell.key))}" var="myUrl" htmlEscape="true"/>                                                                
                                                        <a href="${myUrl}">
                                                            <c:out value="${cell.value}" />
                                                        </a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:out value="${cell.value}" />
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </c:forEach>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>

            <sec:authorize ifNotGranted="ROLE_ANONYMOUS">
                <a href="${pageContext.request.contextPath}/statistics/calc/" class="btn btn-primary"><spring:message code="statistics.recalculate" /></a>
            </sec:authorize>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>