<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">  
        <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
            <div class="modal fade" id="findSimilarModal" tabindex="-1" role="dialog" aria-labelledby="findSimilarModal" aria-hidden="true">
                <div class="modal-dialog modal-lg">
                    <div class="modal-content">
                        <form:form modelAttribute="findSimilarForm" action="${pageContext.request.contextPath}/formula/similar/" method="POST">
                            <input type="hidden" value="<c:out value="${canonicOutput.parents[0].id}" />" name="formulaID" />
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                                <h4 class="modal-title" id="myModalLabel"><spring:message code="general.label.title.similarity.match" /></h4>
                            </div>
                            <div class="modal-body">
                                <div class="row">
                                    <div class="col-md-8">
                                        <h3 class="disable-top-margin"><spring:message code="similarity.method.distance" /></h3>

                                    </div>
                                    <div class="col-md-4">
                                        <div class="checkbox">
                                            <label>
                                                <spring:message code="general.label.method.use" /> 
                                                <form:checkbox path="useDistance" /> 
                                            </label>
                                        </div>                                    
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-8 col-md-offset-4">
                                        <div class="row">
                                            <div class="col-md-4">
                                                <h4 class="disable-top-margin"><spring:message code="similarity.method.distance.treshold" /></h4>
                                            </div>
                                            <div class="col-md-6">
                                                <input 
                                                    id="similarityFuzzySlider"
                                                    name="distanceMethodValue" 
                                                    data-slider="true"
                                                    data-slider-range="0,1"
                                                    data-slider-step="0.05"
                                                    data-slider-theme="volume"
                                                    />
                                            </div>
                                            <div class="col-md-2" id="tresholdOutput">
                                                0.00
                                            </div>
                                        </div>                                    
                                    </div>
                                </div>
                                <hr />
                                <div class="row">
                                    <div class="col-md-8">
                                        <h3 class="disable-top-margin"><spring:message code="similarity.method.count" /></h3>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="checkbox">
                                            <label>
                                                <spring:message code="general.label.method.use" /> 
                                                <form:checkbox path="useCount"/> 
                                            </label>                                           
                                        </div>                                    
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-4">
                                        <form:select path="distanceCondition" cssClass="form-control">
                                            <form:option value="must"><spring:message code="general.label.logic.and" /></form:option>
                                            <form:option value="should"><spring:message code="general.label.logic.or" /></form:option>
                                        </form:select>                                                                   
                                    </div>
                                    <div class="col-md-8">                                  
                                        <div class="row">
                                            <div class="col-md-4">
                                                <h4 class="disable-top-margin"><spring:message code="similarity.method.count.profile" /></h4>
                                            </div>
                                            <div class="col-md-8">
                                                <form:select path="countElementMethodValue" cssClass="form-control">
                                                    <form:option value="must"><spring:message code="similarity.method.count.must" /></form:option>
                                                    <form:option value="should"><spring:message code="similarity.method.count.should" /></form:option>
                                                </form:select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <hr />
                                <div class="row">
                                    <div class="col-md-8">
                                        <h3 class="disable-top-margin"><spring:message code="similarity.method.branch" /></h3>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="checkbox">
                                            <label>
                                                <spring:message code="general.label.method.use" /> 
                                                <form:checkbox path="useBranch" /> 
                                            </label>
                                        </div>                                    
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-4">
                                        <form:select path="countCondition" cssClass="form-control">
                                            <form:option value="must"><spring:message code="general.label.logic.and" /></form:option>
                                            <form:option value="should"><spring:message code="general.label.logic.or" /></form:option>
                                        </form:select>                                                                                                             
                                    </div>
                                    <div class="col-md-8">
                                        <div class="row">
                                            <div class="col-md-4">
                                                <h4 class="disable-top-margin"><spring:message code="similarity.method.branch.value" /></h4>
                                            </div>
                                            <div class="col-md-8">
                                                <form:input cssClass="form-control" path="branchMethodValue" />
                                            </div>
                                        </div>                                    
                                    </div>
                                </div>
                                <hr />
                                <div class="row">
                                    <div class="col-md-12">
                                        <h3 class="disable-top-margin"><spring:message code="similarity.method.other.options" /></h3>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="checkbox">
                                            <label>
                                                <spring:message code="similarity.override" />                                            
                                                <form:checkbox path="override" />
                                            </label>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="checkbox">
                                            <label>
                                                <spring:message code="similarity.direct.import" />
                                                <form:checkbox path="directWrite" />
                                            </label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="general.label.close" /></button>
                                <input type="submit" class="btn btn-default" value="<spring:message code="general.button.submit" />"/>
                            </div>
                        </div>
                    </form:form>
                </div>
            </div>
        </sec:authorize>

        <div class="container content">
            <div class="row">
                <div class="col-md-12">
                    <h1><spring:message code="entity.output.entry" /></h1>
                </div> <!-- /col-md-12> -->
            </div> <!-- /row -->

            <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                <div class="row space-bottom-10">
                    <div class="col-md-12">
                        <div class="btn-group pull-right">                        
                            <button class="btn btn-primary" data-toggle="modal" data-target="#findSimilarModal">
                                <spring:message code="entity.canonicOutput.findSimilar" />
                            </button>                
                            <a href="${pageContext.request.contextPath}/canonicoutput/delete/${canonicOutput.id}" class="btn btn-danger">
                                <spring:message code="general.label.delete" />
                            </a>                        
                        </div> <!-- /btn-group -->
                    </div> <!-- /col-md-12 -->
                </div> <!-- /row -->
            </sec:authorize>

            <div class="row">
                <div class="col-md-5">
                    <div class="panel panel-primary">                        
                        <div class="panel-heading">
                            <spring:message code="general.label.details" />
                        </div>
                        <table class="table table-bordered table-striped">
                            <tr>
                                <td><spring:message code="general.field.id" /></td>
                                <td><c:out value="${canonicOutput.id}" /></td>
                            </tr>
                            <tr>
                                <td><spring:message code="entity.canonicOutput.parents" /></td>
                                <td>
                                    <c:forEach items="${canonicOutput.parents}" var="parent" varStatus="loop">
                                        <a href="${pageContext.request.contextPath}/formula/view/${parent.id}">${parent.id}</a>
                                        ${!loop.last ? ', ' : ''}
                                    </c:forEach>
                                </td>
                            </tr>
                            <tr>
                                <td><spring:message code="entity.canonicOutput.runningTime" /></td>
                                <td><c:out value="${canonicOutput.runningTime}" /> ms</td>
                            </tr>
                        </table>
                    </div> <!-- /panel -->                
                </div> <!-- /col-md-6 -->

                <div class="col-md-7">
                    <div class="panel panel-success">
                        <div class="panel-heading">
                            <spring:message code="entity.canonicOutput.annotations" />
                        </div> <!-- /panel-heading -->
                        <table id="annotationTable" class="table table-striped">
                            <tbody>
                                <c:choose>
                                    <c:when test="${fn:length(canonicOutput.annotations) == 0}">
                                        <tr class="empty-table">
                                            <td>
                                                <spring:message code="general.table.norecords" />
                                            </td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach items="${canonicOutput.annotations}" var="annotationRow">
                                            <tr>
                                                <td><c:out value="${annotationRow.user.username}" /></td>
                                                <td class="annotation-note-cell"><c:out value="${annotationRow.note}" /></td>
                                            </tr>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                        <sec:authorize access="hasRole('ROLE_USER')">
                            <form:form id="annotationForm" method="post" action="${pageContext.request.contextPath}/canonicoutput/annotate/" commandName="annotationForm" cssClass="form-horizontal" cssStyle="margin-top: 5px;">
                                <div class="panel-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="input-group">
                                                <div class="input-group-btn">
                                                    <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
                                                        <spring:message code="general.label.annotate" /> <span class="caret"></span>
                                                    </button>

                                                    <ul class="dropdown-menu" role="menu">
                                                        <li>
                                                            <a href="#" class="annotation-option" data-annotation="#isValid">
                                                                <span class="glyphicon glyphicon-ok"></span> #isValid
                                                            </a>
                                                        </li>
                                                        <li>
                                                            <a href="#" class="annotation-option" data-annotation="#isInvalid">
                                                                <span class="glyphicon glyphicon-flag"></span> #isInvalid
                                                            </a>
                                                        </li>
                                                        <li>
                                                            <a href="#" class="annotation-option" data-annotation="#uncertain">
                                                                <span class="glyphicon glyphicon-question-sign"></span> #uncertain
                                                            </a>
                                                        </li>
                                                        <li>
                                                            <a href="#" class="annotation-option" data-annotation="#removeResult">
                                                                <span class="glyphicon glyphicon-remove"></span> #removeResult
                                                            </a>
                                                        </li>
                                                        <li class="divider"></li>
                                                        <li>
                                                            <a href="#" id="clear-form">
                                                                <spring:message code="general.label.clear.input" />
                                                            </a>
                                                        </li>
                                                    </ul>
                                                </div> <!--/input-group-btn --> 

                                                <input type="hidden" name="canonicOutputId" value="${canonicOutput.id}" />
                                                <form:input path="note" placeholder="Note" id="annotation-value" cssClass="form-control"/>
                                                <span class="input-group-btn">
                                                    <input type="submit" class="btn btn-primary" value="<spring:message code="general.button.submit" />" />
                                                </span>
                                            </div> <!--/input-group -->
                                        </div> <!-- /col-md-12-->
                                    </div> <!-- /row -->
                                </div> <!-- /panel-body -->
                            </form:form>
                        </sec:authorize>
                    </div> <!-- /panel -->  
                </div> <!-- /col-md-6 -->
            </div> <!-- /row -->

            <div class="row">
                <div class="col-md-12">


                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <spring:message code="entity.formula.xml" />
                        </div> <!-- /panel-heading -->
                        <div class="panel-body">
                            <ul class="nav nav-tabs">
                                <li>
                                    <a href="#original" data-toggle="tab">
                                        <spring:message code="entity.canonicOutput.original" />
                                    </a>
                                </li>
                                <li class="active">
                                    <a href="#canonicalized" data-toggle="tab">
                                        <spring:message code="entity.canonicOutput.outputForm" />
                                    </a>
                                </li>
                                <li>
                                    <a href="#diff" data-toggle="tab" onclick="diffView();">
                                        <spring:message code="entity.canonicOutput.diff" />
                                    </a>
                                </li>
                            </ul>

                            <div class="tab-content">
                                <div class="tab-pane active" id="canonicalized">
                                    <div class="col-md-6">
                                        <pre class="brush: xml">
                                            <c:out value="${canonicOutput.outputForm}" />
                                        </pre>
                                    </div> <!-- /col-md-6 -->

                                    <div class="col-md-6">
                                        <div class="well-sm">
                                            <c:out value="${canonicOutput.outputForm}" escapeXml="false" />
                                        </div>        <!-- /well-sm -->                                
                                    </div> <!-- /col-md-6 -->
                                </div>

                                <div class="tab-pane" id="original">
                                    <div class="col-md-6">                                        
                                        <pre class="brush: xml">
                                            <c:out value="${canonicOutput.parents[0].xml}" />
                                        </pre>
                                    </div><!-- /col-md-6 -->

                                    <div class="col-md-6">
                                        <div class="well-sm">
                                            <c:out value="${canonicOutput.parents[0].xml}" escapeXml="false" />
                                        </div><!-- /well-sm -->
                                    </div> <!--/col-md-6-->
                                </div> <!-- /tab-pane -->

                                <div class="tab-pane" id="diff">
                                    <!--see footer for the javascript  -->
                                </div> <!-- /tab-pane -->
                            </div> <!-- /tab-content--> 
                        </div> <!-- /panel-body -->
                    </div><!-- /panel -->
                </div> <!--/col-md-12 -->
            </div> <!-- /row -->
        </div> <!-- /container -->
    </tiles:putAttribute>
</tiles:insertDefinition>