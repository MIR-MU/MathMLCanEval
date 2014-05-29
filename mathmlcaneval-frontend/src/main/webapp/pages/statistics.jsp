<%-- 
    Document   : revision_create
    Created on : Jan 29, 2014, 4:59:01 PM
    Author     : Empt
TODO ERROR MESSAGES
--%>

<div class="container content">
    <h1>!statistics</h1>
    <div class="alert alert-success">
            <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>            
            <p>
                Statistics are calulated always at midnight.
            </p>
    </div>
    <div class="row">
        <div class="col-md-8">
            <table class="table">
                <tr>
                    <td>Statistics from date</td>
                    <td><joda:format value="${statistics.calculationDate}" style="S-" pattern="dd.M.yyyy" /></td>
                </tr>
                <tr>
                    <td>Total formulas</td>
                    <td><c:out value="${statistics.totalFormulas}" /></td>
                </tr>
                <tr>
                    <td>Total formulas having canonic output</td>
                    <td><c:out value="${statistics.totalFormulasWithCanonicOutput}" /></td>
                </tr>
                <tr>
                    <td>Total canonic outputs</td>
                    <td><c:out value="${statistics.totalCanonicalized}" /></td>
                </tr>
                <tr>
                    <td>Total marked as valid</td>
                    <td><c:out value="${statistics.totalValid}" /></td>
                </tr>
                <tr>
                    <td>Total marked as invalid</td>
                    <td><c:out value="${statistics.totalInvalid}" /></td>
                </tr>
                <tr>
                    <td>Total marked as uncertain</td>
                    <td><c:out value="${statistics.totalUncertain}" /></td>
                </tr>
                <tr>
                    <td>Total marked as to remove</td>
                    <td><c:out value="${statistics.totalRemove}" /></td>
                </tr>        
            </table>
        </div>
        <div class="col-md-4">
            <!-- http://www.flotcharts.org/-->
            <div id="flot-placeholder" style="width:400px;height:300px"></div> 
        </div>
    </div>
    
    
    <a href="${pageContext.request.contextPath}/statistics/calc/" class="btn btn-primary">recalculate</a>
    
      
</div>
