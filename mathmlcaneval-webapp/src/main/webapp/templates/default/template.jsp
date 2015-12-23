<%-- 
    Document   : template
    Created on : Dec 19, 2015, 10:39:30 AM
    Author     : Dominik Szalai - emptulik at gmail.com
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="icon" href="../../favicon.ico">

        <title>Starter Template for Bootstrap</title>

        <!-- Bootstrap core CSS -->
        <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
        <link href="<c:url value="/resources/css/styles.min.css" />" rel="stylesheet">
    </head>

    <body>
        <tiles:insertAttribute name="navigation" />  
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-2 col-xs-4">
                    <tiles:insertAttribute name="sidebar" />  
                </div>
                <div class="col-md-6 col-xs-8">
                    <div class="row">
                        <div class="col-xs-12">
                            <tiles:insertAttribute name="body" />  
                        </div>
                    </div>
                </div>
            </div>            
            <footer class="row">
                <div class="col-md-6 col-xs-8 col-md-offset-2 col-xs-offset-4">
                    <tiles:insertAttribute name="footer" />                    
                </div>
            </footer>
        </div><!-- /.container -->

        <tiles:insertAttribute name="footer-javascript" />
        
    </body>
</html>