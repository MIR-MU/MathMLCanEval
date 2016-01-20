<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">
        <h1>List of enabled configurations</h2>
        <p class="text-muted">For list of all configuration click <a href="${context}/configuration/all/">here</a></p>
        <div class="pull-xs-right">
            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal" data-whatever="@mdo">New configuration</button>
        </div>
        <table class="table">
            <thead class="thead-default">
                <tr>
                    <th>#</th>
                    <th>name</th>
                    <th>note</th>
                    <th>operations</th>
                    <th>delete</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${configurationList}" var="entry">
                    <tr>
                        <td><c:out value="${entry.id}" /></td>
                        <td><c:out value="${entry.name}" /></td>
                        <td><c:out value="${entry.note}" /></td>
                        <td><a href="#">edit</a> <a href="#">delete</a></td>
                        <td></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <form:form method="POST" commandName="configurationForm" action="${context}/configuration/submit/">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title" id="exampleModalLabel">New Configuration</h4>
                        </div>
                        <div class="modal-body">                        
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
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary">Submit</button>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>