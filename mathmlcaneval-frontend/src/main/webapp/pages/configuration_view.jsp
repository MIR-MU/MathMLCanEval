<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">        
        <div class="container content">
            <h1><spring:message code="entity.configuration.entry" /></h1>
            <div class="pull-right">
                <div class="pull-right">        
                    <a href="${pageContext.request.contextPath}/configuration/list/" class="btn btn-primary space-bottom-10"><spring:message code="general.label.goback" /></a>        
                </div>
            </div>
            <div class="row pull-top-50">
                <div class="col-lg-12">
                    <div class="panel panel-primary">
                        <!-- Default panel contents -->
                        <div class="panel-heading"><spring:message code="entity.configuration.detail" /></div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-2">
                                    <spring:message code="general.field.id" />
                                </div>
                                <div class="col-lg-10">
                                    <c:out value="${configurationEntry.id}" />
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-2">
                                    <spring:message code="entity.configuration.name" />
                                </div>
                                <div class="col-lg-10">
                                    <c:out value="${configurationEntry.name}" />
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-2">
                                    <spring:message code="entity.configuration.note" />
                                </div>
                                <div class="col-lg-10">
                                    <c:out value="${configurationEntry.note}" />
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-2">
                                    <spring:message code="entity.configuration.config" />
                                </div>
                                <div class="col-lg-10">
                                    <pre class="brush: xml">
                                        <c:out value="${configurationEntry.config}" />
                                    </pre>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </tiles:putAttribute>
    </tiles:insertDefinition>
