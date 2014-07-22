<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only"><spring:message code="navigation.toggle" /></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}">MathMLCanEval</a>
        </div>
        <div class="navbar-collapse collapse">  
            <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                <ul class="nav navbar-nav">                        
                    <li><a href="#"><spring:message code="navigation.element.browse" /></a></li>                                          
                </ul>  
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_ANONYMOUS')">
                <form class="navbar-form navbar-right" role="form" action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
                    <div class="form-group">
                        <input type="text" name="j_username" placeholder="<spring:message code="login.label.username" />" class="form-control">
                    </div>
                    <div class="form-group">
                        <input type="password" name="j_password" placeholder="<spring:message code="login.label.password" />" class="form-control">
                    </div>
                    <button type="submit" class="btn btn-success"><spring:message code="login.label.signin" /></button>
                </form>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_USER')">
                <ul class="nav navbar-nav navbar-right"> 
                    <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="navigation.section.management" /><b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="${pageContext.request.contextPath}/revision/list/"><spring:message code="navigation.revision.list" /></a></li>                                    
                                <li><a href="${pageContext.request.contextPath}/program/list/"><spring:message code="navigation.program.list" /></a></li>                                    
                                <li><a href="${pageContext.request.contextPath}/sourcedocument/list/"><spring:message code="navigation.sourcedocument.list" /></a></li>
                                <li><a href="${pageContext.request.contextPath}/configuration/list/"><spring:message code="navigation.configuration.list" /></a></li>
                            </ul>
                        </li>  
                    </sec:authorize>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="navigation.section.user" /><b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="${pageContext.request.contextPath}/user/profile/"><spring:message code="navigation.user.myprofile" /></a></li>
                            <li class="divider"></li>
                                <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                                <li><a href="${pageContext.request.contextPath}/user/list/"><spring:message code="navigation.user.list" /></a></li>
                                <li><a href="${pageContext.request.contextPath}/userrole/list/"><spring:message code="navigation.userrole.list" /></a></li>
                                <li class="divider"></li>
                                </sec:authorize>
                            <li><a href="${pageContext.request.contextPath}/user/logout/"><spring:message code="navigation.section.logout" /></a></li>
                        </ul>
                    </li>
                </ul>
            </sec:authorize>

        </div><!--/.navbar-collapse -->
    </div>
</div>