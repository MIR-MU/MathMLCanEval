<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">
        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">
            <li class="nav-item">
                <a class="nav-link active" data-toggle="tab" href="#userstab" role="tab">Users</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" data-toggle="tab" href="#userrolestab" role="tab">User Roles</a>
            </li>
        </ul>

        <!-- Tab panes -->
        <div class="tab-content">
            <div class="tab-pane active" id="userstab" role="tabpanel">
                <h1>Users</h1>
                <div class="row">
                    <div class="col-sm-6">
                        <table class="table">
                            <thead class="thead-default">
                                <tr>
                                    <th>#</th>
                                    <th>username</th>
                                    <th>Roles</th>
                                    <th>ops</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${userList}" var="entry">
                                    <tr>
                                        <td><c:out value="${entry.id}" /></td>
                                        <td><c:out value="${entry.username}" /></td>
                                        <td>
                                            <c:forEach items="${entry.roles}" var="subentry">
                                                <c:out value="${subentry.roleName}"/> 
                                            </c:forEach>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="col-sm-6">
                        <h1>Create new user</h1>
                        <form:form method="POST" commandName="userForm" action="${context}/users/user/submit/">
                            <fieldset>
                                <div class="form-group row">
                                    <label for="name" class="col-sm-2 form-control-label">Username</label>
                                    <div class="col-sm-10">
                                        <form:input path="username" cssClass="form-control" />
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="name" class="col-sm-2 form-control-label">Name</label>
                                    <div class="col-sm-10">
                                        <form:input path="realName" cssClass="form-control" />
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="name" class="col-sm-2 form-control-label">Email</label>
                                    <div class="col-sm-10">
                                        <form:input path="email" cssClass="form-control" type="email"/>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="name" class="col-sm-2 form-control-label">Password</label>
                                    <div class="col-sm-10">
                                        <form:password path="password" cssClass="form-control"/>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="name" class="col-sm-2 form-control-label">Password again</label>
                                    <div class="col-sm-10">
                                        <form:password path="realName" cssClass="form-control"/>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="name" class="col-sm-2 form-control-label">Roles</label>
                                    <div class="col-sm-10">
                                        <table class="table" id="userRoles">
                                            <tr>
                                                <td>
                                                    <form:select cssClass="form-control" path="roles[0]" items="${userRoleList}" itemLabel="roleName" itemValue="id"/>
                                                </td>
                                                <td>
                                                    <a href="#" class="add-userrole">addable</a>
                                                    <a href="#" class="remove-userrole">addable</a>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>

                                <div class="form-group row">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <input type="submit" value="submit" class="btn btn-primary" />
                                    </div>
                                </div>    
                            </fieldset>
                        </form:form>
                    </div>
                </div>
            </div>
            <div class="tab-pane" id="userrolestab" role="tabpanel">
                <h1>User roles</h1>
                <div class="row">
                    <div class="col-sm-6">
                        <table class="table">
                            <thead class="thead-default">
                                <tr>
                                    <th>#</th>
                                    <th>name</th>
                                    <th>ops</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${userRoleList}" var="entry">
                                    <tr>
                                        <td>
                                            <c:out value="${entry.id}" />
                                        </td>
                                        <td>
                                            <c:out value="${entry.roleName}" />
                                        </td>
                                        <td>
                                            <a href="#">x</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="col-sm-6">
                        <h1>Create new user role</h1>

                        <form:form method="POST" commandName="userRoleForm" action="${context}/users/userrole/submit/">
                            <fieldset>
                                <div class="form-group row">
                                    <label for="name" class="col-sm-2 form-control-label">Name</label>
                                    <div class="col-sm-10">
                                        <form:input path="roleName" cssClass="form-control" />
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <div class="col-sm-offset-2 col-sm-10">
                                        <input type="submit" value="submit" class="btn btn-primary" />
                                    </div>
                                </div>        
                            </fieldset>
                        </form:form> 
                    </div>
                </div>
            </div>

        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>