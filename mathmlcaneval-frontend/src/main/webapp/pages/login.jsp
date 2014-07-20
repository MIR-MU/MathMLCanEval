<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">
        <div class="container content">
            <form class="form-signin" role="form" action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
                <h2 class="form-signin-heading"><spring:message code="login.page.header" /></h2>
                <input type="text" name="j_username" class="form-control" placeholder="<spring:message code="login.label.username" />" required autofocus>
                <input type="password" name="j_password" class="form-control" placeholder="<spring:message code="login.label.password" />" required>
                <label class="checkbox">
                    <input type="checkbox" value="remember-me"> <spring:message code="login.page.label.remember" />
                </label>
                <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="login.label.signin" /></button>
            </form>
        </div></tiles:putAttribute>
</tiles:insertDefinition>