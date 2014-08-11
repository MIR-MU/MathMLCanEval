<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
    <head>
        <title>404 error page | Bootstrap 3.x Admin Theme</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- Bootstrap -->
        <!--        <link rel="stylesheet" media="screen" href="css/bootstrap.min.css">
                <link rel="stylesheet" media="screen" href="css/bootstrap-theme.min.css">
        
                 Bootstrap Admin Theme 
                <link rel="stylesheet" media="screen" href="css/bootstrap-admin-theme.css">
                <link rel="stylesheet" media="screen" href="css/bootstrap-admin-theme-change-size.css">
        
                 Bootstrap Error Page 
                <link rel="stylesheet" media="screen" href="css/bootstrap-error-page.css">-->
        <link href="<c:url value="/resources/css/bootstrap.min.cosmo.css" />" rel="stylesheet" />
        <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
           <script type="text/javascript" src="js/html5shiv.js"></script>
           <script type="text/javascript" src="js/respond.min.js"></script>
        <![endif]-->
    </head>
    <body>

        <tiles:insertAttribute name="navigation" />
        <!-- content -->
        <tiles:insertAttribute name="body" />

        <div class="container">    
            <hr />
            <footer>
                <div class="row">
                    <div class="col-lg-6"><p>&copy; MIR&#64;MU 2013-2014 </p></div>                    
                </div>
            </footer>
        </div>

        <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
        <script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
    </body>
</html>
