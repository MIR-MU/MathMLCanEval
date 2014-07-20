<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">
        <div class="container content">
            <h1>!user role create</h1>
            <form:form commandName="userRoleForm" class="form-signin" role="form" action="${pageContext.request.contextPath}/userrole/edit/" method="post">                
                <form:input type="hidden" path="id" />
                <div class="form-group">
                    <spring:message code="entity.userrole.name" var="roleName" />
                    <label class="col-sm-2 control-label"><c:out value="${roleName}" /></label>
                    <div class="col-sm-10">
                        <form:input path="roleName" class="form-control" placeholder="${roleName}"/>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary"><spring:message code="general.button.submit" /></button>
            </form:form>
        </div>     
    </tiles:putAttribute>
</tiles:insertDefinition>