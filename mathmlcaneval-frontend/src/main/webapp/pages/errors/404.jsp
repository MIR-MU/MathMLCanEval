<%-- 
    Document   : 404
    Created on : Jul 2, 2012, 11:06:26 AM
    Author     : emptak
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>404 Not Found</title>
    </head>
    <body>
        <div align="center">
            <h1>We're sorry but requested page has not been found.</h1>
            <img src="<c:url value="/resources/img/funny_404.jpg"/>"/><br />
            Check your URL address. If you think it's correct send email, or continue at main page <a href="${pageContext.request.contextPath}">here</a>
        </div>
    </body>
</html>