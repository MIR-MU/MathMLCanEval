<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body"> 
        <div class="container content">  
            <div class="row">
                <div class="col-md-6">
                    <div class="panel panel-primary">
                        <div class="panel-heading">                            
                            <div class="text-center">
                                <spring:message code="entity.revision"/>: <c:out value="${applicationRun1.revision.revisionHash}" />
                            </div>
                        </div>
                        <div class="panel-body">
                            <pre class="brush: xml">
                                <c:out value="${applicationRun1.configuration.config}" />
                            </pre>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="panel panel-primary">
                        <div class="panel-heading">                            
                            <div class="text-center">
                                <spring:message code="entity.revision"/>: <c:out value="${applicationRun2.revision.revisionHash}" />
                            </div>
                        </div>
                        <div class="panel-body">
                            <pre class="brush: xml">
                                <c:out value="${applicationRun2.configuration.config}" />
                            </pre>
                        </div>
                    </div>
                </div>
            </div>
            <c:forEach items="${comparedResult}" var="entry">
                <div id="comp<c:out value="${entry.key.id}" />" class="hidden">
                    <c:out value="${entry.key.outputForm}" />
                </div>
                <div id="comp<c:out value="${entry.value.id}" />" class="hidden">
                    <c:out value="${entry.value.outputForm}" />
                </div>
                <div class="panel panel-default">
                    <div class="panel-body">   
                        <div class="row">
                            <div class="col-md-6">
                                <a href="${pageContext.request.contextPath}/canonicoutput/view/<c:out value="${entry.key.id}" />" class="black-link">
                                    <div class="well well-sm text-center">                                    
                                        <c:out value="${entry.key.outputForm}" escapeXml="false" />                                   
                                    </div>
                                </a>
                            </div>
                            <div class="col-md-6">
                                <a href="${pageContext.request.contextPath}/canonicoutput/view/<c:out value="${entry.value.id}" />" class="black-link">
                                    <div class="well well-sm text-center">
                                        <c:out value="${entry.value.outputForm}" escapeXml="false" />
                                    </div>
                                </a>
                            </div>
                        </div>

                        <div id="comp<c:out value="${entry.key.id}" />comp<c:out value="${entry.value.id}" />">
                            <!--see footer for the javascript  -->
                        </div>
                    </div>
                </div>
            </c:forEach>            
        </div>        
    </tiles:putAttribute>
</tiles:insertDefinition>