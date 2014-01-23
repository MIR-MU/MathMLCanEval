    
        <div class="container">
            <form:form commandName="newUser" class="form-signin" role="form" action="${pageContext.request.contextPath}/user/register/" method="post">
                <h2 class="form-signin-heading"><spring:message code="register.title"/></h2>
                <form:input path="username" class="form-control" placeholder="User name"/>
                <form:errors path="username"/>
                <form:input path="realName" class="form-control" placeholder="Real name (or Email)"/>
                <form:errors path="realName"/>
                <form:password path="password" class="form-control" placeholder="Password"/>
                <form:errors path="password"/>
                <form:password path="passwordVerify" class="form-control" placeholder="Password (again)"/>
                <form:errors path="passwordVerify"/>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
            </form:form>

        </div> <!-- /container -->


      