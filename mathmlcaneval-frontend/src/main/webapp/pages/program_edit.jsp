<%-- 
    Document   : revision_create
    Created on : Jan 29, 2014, 4:59:01 PM
    Author     : Empt
TODO ERROR MESSAGES
--%>

<div class="container content">
    <h1>!editacia programu konverzie</h1>
    <form:form method="post" action="${pageContext.request.contextPath}/program/edit/" commandName="programForm" cssClass="form-horizontal pull-top-50">
        <spring:hasBindErrors name="programForm">
            <div class="error">
                <c:forEach var="error" items="${errors.allErrors}">
                    <p>Errors ${error.defaultMessage}</p>
                </c:forEach>
            </div>
        </spring:hasBindErrors>
        <form:input type="hidden" path="id" />
        <div class="form-group">
            <label class="col-sm-2 control-label">!program name</label>
            <div class="col-sm-10">
                <form:input type="text" path="name" cssClass="form-control" />
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">!program version</label>
            <div class="col-sm-10">
                <form:input type="text" path="version" cssClass="form-control" />
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">!program parameters</label>
            <div class="col-sm-10">
                <form:input type="text" path="parameters" cssClass="form-control" />
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">!program note</label>
            <div class="col-sm-10">
                <form:textarea path="note" cssClass="form-control" />
            </div>
        </div>
        <button type="submit" class="btn btn-primary">!submit</button>
    </form:form>
</div>
