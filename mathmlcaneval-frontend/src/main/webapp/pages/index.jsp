<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">
        <div class="content">
            <div class="container">
                <div class="row">
                    <div class="col-md-3">
                        <div class="row blocks">
                            <sec:authorize access="hasRole('ROLE_USER')">
                                <a href="${pageContext.request.contextPath}/appruns/" class="thumbnail well well-sm">
                                    <div class="caption">
                                        <h2>!app runs</h2>
                                        <p>!browse app runs</p>
                                    </div>
                                </a>
                                <a href="${pageContext.request.contextPath}/#" class="thumbnail well well-sm">
                                    <div class="caption">
                                        <h2>!element browser</h2>
                                        <p>!browse formulas by elements</p>
                                    </div>
                                </a>
                            </sec:authorize>
                            <a href="${pageContext.request.contextPath}/formula/list/" class="thumbnail well well-sm">
                                <div class="caption">
                                    <h2><spring:message code="index.page.button.explore" /></h2>
                                    <p><spring:message code="index.page.button.explore.description" /></p>
                                </div>
                            </a>
                            <a href="${pageContext.request.contextPath}/statistics/" class="thumbnail well well-sm">
                                <div class="caption">
                                    <h2><spring:message code="index.page.button.statistics" /></h2>
                                    <p><spring:message code="index.page.button.statistics.description" /></p>
                                </div>
                            </a>
                        </div>
                    </div>

                    <div class="col-md-9" role="main">
                        <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                            <div clas="row">
                                <div class="col-md-5">
                                    <div class="panel panel-default">
                                        <div class="panel-heading">Panel heading without title</div>
                                        <div class="panel-body">
                                            Panel content
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6 col-md-offset-1">
                                    <div class="panel panel-default">
                                        <div class="panel-heading"><spring:message code="index.page.briefstats" /></div>
                                        <div class="panel-body stats-panel-body">
                                            <div id="flot-placeholder"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </sec:authorize>
                        <div class="row">
                            <div class="col-md-12">
                                <h2><spring:message code="index.page.about" /></h2>
                                <p>Goal of this project is to create an application in Java language which performs canonicalization of mathematical expressions written in MathML (Mathematical Markup Language).</p>
                                <p>The output should be canonical form of given MathML document. This canonicalized form of MathML can then be used for easy decision if two differently written MathML formulae represent the same expression, or by MathML search and comparison engines.</p>
                                <p><a class="btn btn-default" href="${pageContext.request.contextPath}/about/" role="button">View details &raquo;</a></p>

                                <h2><spring:message code="index.page.recent" /></h2>
                            </div>
                        </div>                        
                    </div>
                </div>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>