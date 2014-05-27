<%-- 
    Document   : revision_create
    Created on : Jan 29, 2014, 4:59:01 PM
    Author     : Empt
TODO ERROR MESSAGES
--%>

<div class="container content">
    <h1>!statistics</h1>
    statistics are calulated always at midnight.
    latest statistics from: <joda:format value="${statistics.calculationDate}" style="S-" pattern="dd.mm.yyyy" />
    Total formulas: <c:out value="${statistics.totalFormulas}" />
    Total Valid: <c:out value="${statistics.totalValid}" />
    
    <a href="${pageContext.request.contextPath}/statistics/calc/" class="btn btn-primary">recalculate</a>
    
    <!-- http://www.flotcharts.org/-->
    <div id="flot-placeholder" style="width:400px;height:300px"></div>
</div>
