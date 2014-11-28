<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">
        <div class="content">
            <div class="container">
                <sec:authorize access="hasRole('ROLE_USER')">
                    <div class="row blocks">
                        <div class="col-md-4">
                            <a href="${pageContext.request.contextPath}/appruns/" class="thumbnail well well-sm">
                                <div class="caption">
                                    <h2><spring:message code="index.page.button.appruns" /></h2>
                                    <p><spring:message code="index.page.button.appruns.description" /></p>
                                </div>
                            </a>
                        </div>
                        <div class="col-md-4">
                            <a href="${pageContext.request.contextPath}/annotationvalue/" class="thumbnail well well-sm">
                                <div class="caption">
                                    <h2><spring:message code="index.page.button.annotationvalue" /></h2>
                                    <p><spring:message code="index.page.button.annotationvalue.description" /></p>
                                </div>
                            </a>
                        </div>
                        <div class="col-md-4">
                            <a href="${pageContext.request.contextPath}/formula/mass/" class="thumbnail well well-sm">
                                <div class="caption">
                                    <h2><spring:message code="index.page.button.import" /></h2>
                                    <p><spring:message code="index.page.button.import.description.mass" /></p>
                                </div>
                            </a>
                        </div>
                    </div>
                </sec:authorize>
                <div class="row blocks">
                    <div class="col-md-4">
                        <a href="${pageContext.request.contextPath}/formula/list/" class="thumbnail well well-sm">
                            <div class="caption">
                                <h2><spring:message code="index.page.button.explore" /></h2>
                                <p><spring:message code="index.page.button.explore.description" /></p>
                            </div>
                        </a>
                    </div>
                    <div class="col-md-4">
                        <a href="${pageContext.request.contextPath}/statistics/" class="thumbnail well well-sm">
                            <div class="caption">
                                <h2><spring:message code="index.page.button.statistics" /></h2>
                                <p><spring:message code="index.page.button.statistics.description" /></p>
                            </div>
                        </a>
                    </div>
                </div>
                <sec:authorize access="hasRole('ROLE_USER')">
                    <div class="row">
                        <div class="col-md-12">
                            <table id="minifeedtable">
                                <thead>
                                    <tr>
                                        <th><spring:message code="entity.user.username" /></th>
                                        <th><spring:message code="general.label.event" /></th>
                                        <th><spring:message code="general.label.date" /></th>
                                    </tr>
                                </thead>
                                <tbody></tbody>
                            </table>
                        </div>
                    </div>
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_ANONYMOUS')">
                    <div class="row">
                        <div class="col-md-12">
                            <h2><spring:message code="index.page.about" /></h2>
                            <p>Goal of this project is to create an application in Java language which performs canonicalization of mathematical expressions written in MathML (Mathematical Markup Language).</p>
                            <p>The output should be canonical form of given MathML document. This canonicalized form of MathML can then be used for easy decision if two differently written MathML formulae represent the same expression, or by MathML search and comparison engines.</p>
                            <p><a class="btn btn-default" href="${pageContext.request.contextPath}/about/" role="button">View details &raquo;</a></p>
                        </div>
                    </div> 
                </sec:authorize>
            </div>
        </div>
    </div>
</tiles:putAttribute>
</tiles:insertDefinition>
