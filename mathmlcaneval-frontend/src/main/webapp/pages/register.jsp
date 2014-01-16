<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="shortcut icon" href="../../docs-assets/ico/favicon.png">
        <title><spring:message code="register.title"/></title>

        <!-- Bootstrap core CSS -->
        <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">


        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
          <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
        <![endif]-->
    </head>

    <body>
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


        <!-- Bootstrap core JavaScript:
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
        <script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
    </body>
</html>