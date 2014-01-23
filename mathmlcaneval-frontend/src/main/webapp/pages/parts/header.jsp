<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="shortcut icon" href="../../docs-assets/ico/favicon.png">

        <title>MathMLCanEval</title>

        <!-- Bootstrap core CSS -->
        <link href="<c:url value="/resources/css/bootstrap.min.cosmo.css" />" rel="stylesheet">


        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
          <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
        <![endif]-->
    </head>

    <body>

        <div class="navbar navbar-default navbar-fixed-top" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">MathMLCanEval</a>
                </div>
                <div class="navbar-collapse collapse">
                    <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                        <ul class="nav navbar-nav">                            
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">uzivatelia <b class="caret"></b></a>
                                <ul class="dropdown-menu">
                                    <li><a href="#">Vytvorit</a></li>
                                    <li><a href="#">zoznam</a></li>
                                    <li><a href="#">Something else here</a></li>
                                    <li class="divider"></li>
                                    <li><a href="#">Separated link</a></li>
                                </ul>
                            </li>
                            <li><a href="${pageContext.request.contextPath}/user/logout/">Logout</a></li>
                        </ul>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_ANONYMOUS')">
                        <form class="navbar-form navbar-right" role="form" action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
                            <div class="form-group">
                                <input type="text" name="j_username" placeholder="Email" class="form-control">
                            </div>
                            <div class="form-group">
                                <input type="password" name="j_password" placeholder="Password" class="form-control">
                            </div>
                            <button type="submit" class="btn btn-success">Sign in</button>
                        </form>
                    </sec:authorize>
                    <sec:authorize access="hasRole('ROLE_USER')">
                        <ul class="nav navbar-nav navbar-right">                            
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <b class="caret"></b></a>
                                <ul class="dropdown-menu">
                                    <li><a href="#">Action</a></li>
                                    <li><a href="#">Another action</a></li>
                                    <li><a href="#">Something else here</a></li>
                                    <li class="divider"></li>
                                    <li><a href="#">Separated link</a></li>
                                </ul>
                            </li>
                            <li><a href="${pageContext.request.contextPath}/user/logout/">Logout</a></li>
                        </ul>
                    </sec:authorize>

                </div><!--/.navbar-collapse -->
            </div>
        </div>