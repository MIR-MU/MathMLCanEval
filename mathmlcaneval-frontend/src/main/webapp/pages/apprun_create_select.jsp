<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">
        <div class="container content">
            <h1><spring:message code="entity.appruns.new" /></h1>
            <form:form method="POST" commandName="applicationRunForm" action="${pageContext.request.contextPath}/appruns/create/select" class="form-horizontal pull-top-50">
                <div class="row">
                    <label class="col-sm-3 control-label"><spring:message code="entity.appruns.revision" /></label>
                    <div class="col-sm-9">
                        <form:select path="revisionForm" multiple="false" class="form-control">
                            <form:option value="-1"><spring:message code="general.select.option.pickone" /></form:option>
                            <form:options items="${revisionList}" itemLabel="revisionHash" itemValue="id" />
                        </form:select>
                    </div>
                    <form:errors path="revisionForm" element="div" class="col-sm-3 alert alert-danger"/>
                </div>
                <div class="row">
                    <label class="col-sm-3 control-label"><spring:message code="entity.appruns.configuration" /></label>
                    <div class="col-sm-9">
                        <form:select path="configurationForm" multiple="false" class="form-control">
                            <form:option value="-1"><spring:message code="general.select.option.pickone" /></form:option>
                            <form:options items="${configurationList}" itemLabel="name" itemValue="id" />
                        </form:select>
                    </div>
                    <form:errors path="configurationForm" element="div" class="col-sm-3 alert alert-danger"/>
                </div>

                <div class="row">
                    <label class="col-sm-3 control-label"><spring:message code="entity.appruns.selected" /></label>
                </div>

                <div class="row">            
                    <c:choose>
                        <c:when test="${fn:length(formulaList) gt 0}">
                            <input type="hidden" name="formulaCanonicalizeID" value=""/>
                            <c:forEach items="${formulaList}" var="entry" varStatus="i">
                                <c:if test="${not i.first and i.index % 4 == 0}">
                                </div>
                                <div class="row">
                                </c:if>
                                <div class="col-md-3 img-thumbnail">
                                    <input type="checkbox" value="${entry.id}" style="display:none" name="formulaCanonicalizeID" />
                                    <a href="${pageContext.request.contextPath}/formula/view/${entry.id}">
                                        <div class="preview text-center">
                                            <c:out value="${entry.xml}" escapeXml="false" />
                                        </div>
                                    </a>
                                </div>
                            </c:forEach>

                        </c:when>
                        <c:otherwise>
                            <div class="col-md-12 text-center">
                                <h2 class="text-center"><spring:message code="general.table.norecords" /></h2>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="text-center pull-top-50"> 
                            <input type="submit" value="<spring:message code="entity.appruns.button.canonicalize" />" class="btb btn-warning btn-lg"/>
                        </div>
                    </div>
                </div>
            </form:form>
            <!-- pagination -->
            <div class="text-center"> 
                <form:form method="get" action="${pageContext.request.contextPath}/appruns/create/select/" commandName="pagination">
                    <ul class="pager">
                        <c:if test="${pagination.pageSize != pagination.defaultPageSize}">
                            <c:set var="size" value="&pageSize=${pagination.pageSize}" />
                            <form:hidden path="pageSize" />
                        </c:if>
                        <li><a href="?pageNumber=1${size}">&laquo;</a></li>
                            <c:choose>
                                <c:when test="${pagination.pageNumber == 1}">
                                <li class="disabled"><a href="javascript:;">&lsaquo;</a></li>
                                </c:when>
                                <c:otherwise>
                                <li><a href="?pageNumber=${pagination.pageNumber - 1}${size}">&lsaquo;</a></li>
                                </c:otherwise>
                            </c:choose>
                        <li><form:input type="text" path="pageNumber" size="2" /></li>
                        <li><button type="submit" class="btn btn-primary btn-sm"><spring:message code="pagination.go" /></button></li>
                        <li>/ ${pagination.pages}</li>
                            <c:choose>
                                <c:when test="${pagination.pageNumber == pagination.pages}">
                                <li class="disabled"><a href="javascript:;">&rsaquo;</a></li>
                                </c:when>
                                <c:otherwise>
                                <li><a href="?pageNumber=${pagination.pageNumber + 1}${size}">&rsaquo;</a></li>
                                </c:otherwise>
                            </c:choose>
                        <li><a href="?pageNumber=${pagination.pages}${size}">&raquo;</a></li>
                    </ul>
                </form:form>
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>