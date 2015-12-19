<%-- 
    Document   : navigation
    Created on : Dec 19, 2015, 10:39:24 AM
    Author     : Dominik Szalai - emptulik at gmail.com
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav class="navbar navbar-fixed-top navbar-dark bg-inverse">
    <a class="navbar-brand" href="#">
        <span><img id="logo-img" src="logo.svg" alt="Rabbit Silhouette MIR Logo" style="height: 1.8em;"/></span>
        <span>Mathmlcaneval</span></a>
    <ul class="nav navbar-nav">
        <li class="nav-item active">
            <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#">About</a>
        </li>
        <li class="nav-item">
            <a class="nav-link" href="#">Contact</a>
        </li>
        <li class="nav-item">
            <div class="dropdown">
                <a href="#" class="nav-link dropdown-toggle" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Dropdown</a>
                <!--                        <button class="btn btn-secondary dropdown-toggle" type="button" >
                                            Dropdown
                                        </button>-->
                <div class="dropdown-menu" aria-labelledby="dropdownMenu1">
                    <a class="dropdown-item" href="#">Action</a>
                    <a class="dropdown-item" href="#">Another action</a>
                    <a class="dropdown-item" href="#">Something else here</a>
                </div>
            </div>
        </li>
    </ul>
</nav>