<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">
        <h1>Create new configuration</h1>
        <form:form method="POST" commandName="configurationForm" action="">
            <div class="form-group row">
                <label for="name" class="col-sm-2 form-control-label">Name</label>
                <div class="col-sm-10">
                    <form:input path="name" cssClass="form-control" />
                </div>
            </div>
            <div class="form-group row">
                <label for="configuration" class="col-sm-2 form-control-label">Configuration</label>
                <div class="col-sm-10">
                    <form:textarea path="configuration" cssClass="form-control" />
                </div>
            </div>
            <div class="form-group row">
                <label for="note" class="col-sm-2 form-control-label">Note</label>
                <div class="col-sm-10">
                    <form:textarea path="note" cssClass="form-control" />
                </div>
            </div>
            <div class="form-group row">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>
            </div>
        </form:form>
    </tiles:putAttribute>
</tiles:insertDefinition>