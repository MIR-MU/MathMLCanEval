<%-- 
    Document   : navigation
    Created on : Dec 19, 2015, 10:39:24 AM
    Author     : Dominik Szalai - emptulik at gmail.com
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav class="navbar navbar-fixed-top navbar-dark bg-inverse">
    <a class="navbar-brand" href="#"><span><img class="logo" src="<c:url value="/resources/img/logo.svg" />" alt="Rabbit Silhouette MIR Logo" style="height: 1.8em;"/></span>&nbsp;Mathmlcaneval</a>
    <ul class="nav navbar-nav">
        <li class="nav-item active">
            <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="${context}/tasks/">Tasks</a>
        </li>
        <li class="nav-item">
            <div class="dropdown">
                <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Git control &amp; revisions</a>
                <div class="dropdown-menu">
                    <a class="dropdown-item" href="#">Pull updates</a>
                    <a class="dropdown-item" href="#">Branches</a>
                    <a class="dropdown-item" href="#">Revisions</a>
                </div>
            </div>
        </li>
        <li class="nav-item">
            <div class="dropdown">
                <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Sources &amp; programs</a>
                <div class="dropdown-menu">
                    <h6 class="dropdown-header">Sources</h6>
                    <a class="dropdown-item" href="#">List of sources</a>
                    <a class="dropdown-item" href="#">New source</a>
                    <div class="dropdown-divider"></div>
                    <h6 class="dropdown-header">Programs</h6>
                    <a class="dropdown-item" href="#">List of programs</a>
                    <a class="dropdown-item" href="#">New Program</a>
                </div>
            </div>
        </li>
        <li class="nav-item">
            <div class="dropdown">
                <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Users &amp; roles</a>
                <div class="dropdown-menu">
                    <a class="dropdown-item" href="#">List of users</a>
                    <a class="dropdown-item" href="#">New user</a>
                    <div class="dropdown-divider"></div>
                    <h6 class="dropdown-header">Roles</h6>
                    <a class="dropdown-item" href="#">List of roles</a>
                    <a class="dropdown-item" href="#">New role</a>
                </div>
            </div>
        </li>
        <li class="nav-item">
            <div class="dropdown">
                <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Other</a>
                <div class="dropdown-menu">
                    <h6 class="dropdown-header">Configurations</h6>
                    <a class="dropdown-item" href="${context}/configuration/submit/">New</a>
                    <a class="dropdown-item" href="${context}/configuration/">List</a>                    
                    <div class="dropdown-divider"></div>
                    <h6 class="dropdown-header">AnnotationTags</h6>
                    <a class="dropdown-item" href="#">List</a>
                    <a class="dropdown-item" href="#">New</a>                    
                </div>
            </div>
        </li>
    </ul>
</nav>