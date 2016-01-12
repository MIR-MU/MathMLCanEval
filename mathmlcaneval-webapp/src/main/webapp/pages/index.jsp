<%-- 
    Document   : index
    Created on : Dec 19, 2015, 11:08:36 AM
    Author     : Dominik Szalai - emptulik at gmail.com
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">
        <div class="jumbotron">
            <div class="container">
                <h1 class="display-4">About project <c:out value="${context}" /></h1>
                <p class="lead">Goal of this project is to create an application in Java language which performs canonicalization of mathematical expressions written in MathML (Mathematical Markup Language).

                    The output should be canonical form of given MathML document. This canonicalized form of MathML can then be used for easy decision if two differently written MathML formulae represent the same expression, or by MathML search and comparison engines.</p>

            </div>
        </div>
<!--        <div class="row">
            <div class="col-xs-1 sidebar-nav-block-purple">
                new task
            </div>
        </div>-->
        <div class="row">
            <div class="col-md-6">
                <div class="row">
                    <div class="col-xs-8">
                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">Current stats</h4>
                            </div>
                            <table class="table">
                                <tr>
                                    <td>Current version</td>
                                    <td>946e8bd4934b10613f7e5b2a9c5668e8a0ddbf21</td>
                                </tr>
                                <tr>
                                    <td>Mathml revision</td>
                                    <td>7ddcf9ee19e3381c9be7f72490f69ae3d40227c1</td>
                                </tr>
                                <tr>
                                    <td>No. of formulas</td>
                                    <td>4567</td>
                                </tr>
                                <tr>
                                    <td>Free heap</td>
                                    <td>150 MB</td>
                                </tr>
                                <tr>
                                    <td>Max heap</td>
                                    <td>200 MB</td>
                                </tr>
                                <tr>
                                    <td>Uptime</td>
                                    <td>1d 14hrs 21 min</td>
                                </tr>
                            </table>
                        </div>
                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">Running tasks</h4>
                            </div>
                            <table class="table">
                                <tr>
                                    <td colspan="2">
                                        <progress class="progress progress-success" value="25" max="100">25%</progress>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        Total tasks:
                                    </td>
                                    <td>
                                        5
                                    </td>
                                </tr>
                                <tr>    
                                    <td>
                                        Finished tasks:
                                    </td>
                                    <td>
                                        5
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                    <div class="col-xs-4">
                        <div class="card">
                            <div class="card-header">
                                <h4 class="card-title">Quick actions</h4>
                            </div>
                            <ul class="list-group list-group-flush">
                                <li class="list-group-item"><a href="#">Update git</a></li>
                                <li class="list-group-item"><a href="#">Reindex</a></li>
                                <li class="list-group-item"><a href="#">Optimize index</a></li>
                                <li class="list-group-item"><a href="#">Calculate statistics</a></li>
                                <li class="list-group-item"><a href="#">Clear finished tasks</a></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header">
                        <h4 class="card-title">Latest activity</h4>
                    </div>
                    <table class="table" id="activityTable">
                        <thead>
                            <tr>
                                <th>WHO</th>
                                <th>what</th>
                                <th>time</th>
                            </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>
