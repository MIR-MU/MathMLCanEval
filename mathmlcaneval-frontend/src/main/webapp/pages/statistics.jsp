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
            <form method="get" action="${pageContext.request.contextPath}/statistics/" class="form-inline" id="statisticsForm">
                <div class="form-group">
                    <label class="col-sm-5 control-label" for="statID"><spring:message code="statistics.choose" /></label>
                    <div class="col-sm-7">
                        <select class="form-control" name="id" id="statID">
                            <c:forEach items="${statisticsList}" var="entry">
                                <option value="${entry.key}"><joda:format value="${entry.value}" style="LS" /></option>
                            </c:forEach>
                        </select>
                    </div>                            
                </div>
            </form> 

            <div class="row pull-top-50">
                <div class="col-md-8">
                    <table class="table">
                        <tr>
                            <td><spring:message code="statistics.date" /></td>
                            <td><joda:format value="${statistics.calculationDate}" style="S-" pattern="dd.M.yyyy" /></td>
                        </tr>
                        <tr>
                            <td><spring:message code="statistics.total.formulas" /></td>
                            <td><c:out value="${statistics.totalFormulas}" /></td>
                        </tr>
                        <tr>
                            <td><spring:message code="statistics.formulaswithoutput" /></td>
                            <td><c:out value="${statistics.totalFormulasWithCanonicOutput}" /></td>
                        </tr>
                        <tr>
                            <td><spring:message code="statistics.total.canonic" /></td>
                            <td><c:out value="${statistics.totalCanonicalized}" /></td>
                        </tr>
                        <tr>
                            <td><spring:message code="statistics.total.valid" /></td>
                            <td><c:out value="${statistics.totalValid}" /></td>
                        </tr>
                        <tr>
                            <td><spring:message code="statistics.total.invalid" /></td>
                            <td><c:out value="${statistics.totalInvalid}" /></td>
                        </tr>
                        <tr>
                            <td><spring:message code="statistics.total.uncertain" /></td>
                            <td><c:out value="${statistics.totalUncertain}" /></td>
                        </tr>
                        <tr>
                            <td><spring:message code="statistics.total.remove" /></td>
                            <td><c:out value="${statistics.totalRemove}" /></td>
                        </tr>        
                        <tr>
                            <td><spring:message code="statistics.total.formula.remove" /></td>
                            <td><c:out value="${statistics.totalFormulaRemove}" /></td>
                        </tr>
                        <tr>
                            <td><spring:message code="statistics.total.formula.meaningless" /></td>
                            <td><c:out value="${statistics.totalFormulaMeaningless}" /></td>
                        </tr>        
                    </table>
                </div>
                <div class="col-md-4 stats-panel-body">
                    <!-- http://www.flotcharts.org/-->
                    <div id="flot-placeholder" style="width:400px;height:300px"></div> 
                </div>
            </div>

            <sec:authorize ifNotGranted="ROLE_ANONYMOUS">
                <a href="${pageContext.request.contextPath}/statistics/calc/" class="btn btn-primary"><spring:message code="statistics.recalculate" /></a>
            </sec:authorize>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>