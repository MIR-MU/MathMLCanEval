<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">
        <div class="container content">
            <h1>
                <c:choose>
                    <c:when test="${fn:startsWith(requestScope['javax.servlet.forward.servlet_path'],'/user/edit/')}">
                        <spring:message code="entity.user.edit" />
                    </c:when>
                    <c:when test="${fn:startsWith(requestScope['javax.servlet.forward.servlet_path'],'/user/profile/')}">
                        <spring:message code="entity.user.profile" />
                    </c:when>
                </c:choose> 
            </h1>
            <hr />
            <form:form method="post" action="${pageContext.request.contextPath}/user/edit/" commandName="userForm" cssClass="form-horizontal pull-top-50">
                <form:hidden path="id" />
                <div class="form-group">
                    <label class="col-sm-2 control-label"><spring:message code="entity.user.username" /></label>
                    <div class="col-sm-7">
                        <form:input type="text" path="username" cssClass="form-control" />
                    </div>
                    <form:errors path="username" element="div" class="col-sm-3 alert alert-danger"/>            
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label"><spring:message code="entity.user.realname" /></label>
                    <div class="col-sm-7">
                        <form:input type="text" path="realName" cssClass="form-control" />
                    </div>
                    <form:errors path="realName" element="div" class="col-sm-3 alert alert-danger"/>            
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label"><spring:message code="entity.user.email" /></label>
                    <div class="col-sm-7">
                        <form:input type="email" path="email" cssClass="form-control" />
                    </div>
                    <form:errors path="email" element="div" class="col-sm-3 alert alert-danger"/>            
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label"><spring:message code="entity.user.email.verify" /></label>
                    <div class="col-sm-7">
                        <form:input type="email" path="emailVerify" cssClass="form-control" />
                    </div>
                    <form:errors path="emailVerify" element="div" class="col-sm-3 alert alert-danger"/>            
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label"><spring:message code="entity.user.password" /></label>
                    <div class="col-sm-7">
                        <form:password path="password" cssClass="form-control" />
                    </div>
                    <form:errors path="password" element="div" class="col-sm-3 alert alert-danger"/>            
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label"><spring:message code="entity.user.password.verify" /></label>
                    <div class="col-sm-7">
                        <form:password path="passwordVerify" cssClass="form-control" />
                    </div>
                    <form:errors path="passwordVerify" element="div" class="col-sm-3 alert alert-danger"/>            
                </div>
                <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                    <div class="form-group">
                        <label class="col-sm-2 control-label"><spring:message code="entity.user.userroles" /></label>
                        <div class="col-sm-7" id="userRolesRows">
                            <!-- content via jquery goes here -->
                            <%-- 
                            this makes following: if we submit non valid form
                            we have to write he values that were already submited
                            and we have to access it via userForm. because selecting values does
                            not come from form via <form: tag.
                            --%>
                            <c:if test="${fn:length(userForm.userRoleForms) gt 0}">
                                <c:forEach items="${userForm.userRoleForms}" varStatus="i">
                                    <div class="row">
                                        <div class="col-sm-9">
                                            <form:select path="userRoleForms[${i.index}]" multiple="false" class="form-control">    
                                                <form:option value="-1"><spring:message code="general.select.option.pickone" /></form:option>
                                                <form:options items="${userRolesFormList}" itemLabel="roleName" itemValue="id" />
                                            </form:select>
                                        </div>
                                        <div class="col-sm-3">
                                            <span class="btn btn-primary addrow"><i class=" glyphicon glyphicon-plus"></i></span>

                                            <span class="btn btn-primary removerow"><i class=" glyphicon glyphicon-minus"></i></span>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </div>          
                    </div>
                </sec:authorize>

                <div class="form-group">
                    <div class="col-xm-7 col-sm-offset-2">
                        <button type="submit" class="btn btn-primary"><spring:message code="general.button.submit" /></button>
                    </div>
                </div>
                <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                    <!-- clone kopiruje content-->
                    <div class="userRoleTemplate">
                        <div class="row">
                            <div class="col-sm-9">
                                <form:select path="userRoleForms[0]" multiple="false" class="form-control">
                                    <form:option value="-1"><spring:message code="general.select.option.pickone" /></form:option>
                                    <form:options items="${userRolesFormList}" itemLabel="roleName" itemValue="id" />
                                </form:select>
                            </div>
                            <div class="col-sm-3">
                                <span class="btn btn-primary addrow"><i class=" glyphicon glyphicon-plus"></i></span>

                                <span class="btn btn-primary removerow"><i class=" glyphicon glyphicon-minus"></i></span>
                            </div>
                        </div>
                    </div>
                </sec:authorize>
            </form:form>
        </div>

    </tiles:putAttribute>
</tiles:insertDefinition>