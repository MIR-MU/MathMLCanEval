<%-- 
    Document   : index
    Created on : Dec 19, 2015, 11:08:36 AM
    Author     : Dominik Szalai - emptulik at gmail.com
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">
        <div class="jumbotron jumbotron-fluid">
            <div class="container">
                <h1 class="display-4">About project</h1>
                <p class="lead">Goal of this project is to create an application in Java language which performs canonicalization of mathematical expressions written in MathML (Mathematical Markup Language).

                    The output should be canonical form of given MathML document. This canonicalized form of MathML can then be used for easy decision if two differently written MathML formulae represent the same expression, or by MathML search and comparison engines.</p>
            </div>
        </div>
        <div class="card-deck-wrapper">
            <div class="card-deck">
                <div class="card">
                    <img class="card-img-top" data-src="..." alt="Card image cap">
                    <div class="card-block">
                        <h4 class="card-title">MathML</h4>
                        <p class="card-text">Discover thousands of MathML formulas.</p>
                    </div>
                </div>
                <div class="card">
                    <img class="card-img-top" data-src="..." alt="Card image cap">
                    <div class="card-block">
                        <h4 class="card-title">Canonicalizer</h4>
                        <p class="card-text">Observe how different versions of canonicalizers change the canonic form of formula.</p>

                    </div>
                </div>
                <div class="card">
                    <img class="card-img-top" data-src="..." alt="Card image cap">
                    <div class="card-block">
                        <h4 class="card-title">Card title</h4>
                        <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional content. This card has even longer content than the first to show that equal height action.</p>

                    </div>
                </div>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>
