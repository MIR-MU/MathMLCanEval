<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">  
        <div class="modal fade" id="findSimilarModal" tabindex="-1" role="dialog" aria-labelledby="findSimilarModal" aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <form:form modelAttribute="findSimilarForm" action="${pageContext.request.contextPath}/formula/similar/" method="POST">
                        <input type="hidden" value="<c:out value="${formulaEntry.parents[0].id}" />" name="formulaID" />
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
                                    <div class="pull-right">
                                        <spring:message code="general.label.method.use" /> <form:checkbox path="useDistance" cssClass="form-control-checkbox"/> 
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
                                    <div class="pull-right">
                                        <spring:message code="general.label.method.use" /> <form:checkbox path="useCount" cssClass="form-control-checkbox"/> 
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
                                    <div class="pull-right">
                                        <spring:message code="general.label.method.use" /> <form:checkbox path="useBranch" cssClass="form-control-checkbox" /> 
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
                                <div class="row">
                                    <div class="col-md-6"><spring:message code="similarity.override" /> <form:checkbox path="override" /></div>
                                    <div class="col-md-6"><spring:message code="similarity.direct.import" /><form:checkbox path="directWrite" /></div>
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
        <div class="container content">
            <h1><spring:message code="entity.output.entry" /></h1>
            <div class="btn-group pull-right space-bottom-10">
                <button class="btn btn-primary" data-toggle="modal" data-target="#findSimilarModal">
                    <spring:message code="entity.canonicOutput.findSimilar" />
                </button>
                <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
                    <a href="${pageContext.request.contextPath}/canonicoutput/delete/${formulaEntry.id}" class="btn btn-danger"><spring:message code="general.label.delete" /></a>
                </sec:authorize>
            </div>
            <div class="row pull-top-50">
                <div class="col-lg-12">
                    <div class="row">
                        <div class="col-lg-6">
                            <div class="panel panel-primary">
                                <!-- Formula details -->
                                <div class="panel-heading"><spring:message code="general.label.details" /></div>
                                <table class="table table-bordered table-striped">
                                    <tr>
                                        <td><spring:message code="general.field.id" /></td>
                                        <td><c:out value="${formulaEntry.id}" /></td>
                                    </tr>
                                    <tr>
                                        <td><spring:message code="entity.canonicOutput.parents" /></td>
                                        <td>
                                            <c:forEach items="${formulaEntry.parents}" var="parent" varStatus="loop">
                                                <a href="${pageContext.request.contextPath}/formula/view/${parent.id}">${parent.id}</a>
                                                ${!loop.last ? ', ' : ''}
                                            </c:forEach>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><spring:message code="entity.canonicOutput.runningTime" /></td>
                                        <td><c:out value="${formulaEntry.runningTime}" /></td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                        <div class="col-lg-6">
                            <div class="panel panel-primary">
                                <!-- Annotations (view & add) -->
                                <div class="panel-heading"><spring:message code="entity.canonicOutput.annotations" /></div>
                                <table id="annotationTable">
                                    <tbody>
                                        <c:forEach items="${formulaEntry.annotations}" var="annotationRow">
                                            <tr>
                                                <td><c:out value="${annotationRow.user.username}" /></td>
                                                <td class="annotation-note-cell"><c:out value="${annotationRow.note}" /></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <form:form id="annotationForm" method="post" action="${pageContext.request.contextPath}/canonicoutput/annotate/" commandName="annotationForm" cssClass="form-horizontal" cssStyle="margin-top: 5px;">
                                    <input type="hidden" name="canonicOutputId" value="${formulaEntry.id}" />
                                    <form:input style="width: 80%;" path="note" placeholder="Note" id="annotation-note"/>
                                    <input type="submit" class="btn btn-primary btn-sm pull-right" value="<spring:message code="general.button.submit" />" />
                                    <div class="row">
                                        <div class="col-md-2"><span class="btn btn-warning" id="annotate-isvalid"><span class="glyphicon glyphicon-ok"></span></span></div>
                                        <div class="col-md-2"><span class="btn btn-warning" id="annotate-isinvalid"><span class="glyphicon glyphicon-flag"></span></span></div>
                                        <div class="col-md-2"><span class="btn btn-warning" id="annotate-uncertain"><span class="glyphicon glyphicon-question-sign"></span></span></div>
                                        <div class="col-md-2"><span class="btn btn-warning" id="annotate-remove"><span class="glyphicon glyphicon-remove"></span></span></div>
                                    </div>
                                </form:form>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="panel panel-primary">
                            <!-- Formula in MathML & rendered -->
                            <div class="panel-heading"><spring:message code="entity.formula.xml" /></div>
                            <div class="panel-body">
                                <div class="row">
                                    <ul class="nav nav-tabs">
                                        <li><a href="#original" data-toggle="tab"><spring:message code="entity.canonicOutput.original" /></a></li>
                                        <li class="active"><a href="#canonicalized" data-toggle="tab"><spring:message code="entity.canonicOutput.outputForm" /></a></li>
                                        <li><a href="#diff" data-toggle="tab" onclick="diffView();"><spring:message code="entity.canonicOutput.diff" /></a></li>
                                    </ul>
                                    <div class="tab-content">
                                        <div class="tab-pane active" id="canonicalized">
                                            <div class="col-lg-6">
                                                <pre class="brush: xml">
                                                    <c:out value="${formulaEntry.outputForm}" />
                                                </pre>
                                            </div>
                                            <div class="col-lg-6">
                                                <c:out value="${formulaEntry.outputForm}" escapeXml="false" />
                                            </div>
                                        </div>

                                        <div class="tab-pane" id="original">
                                            <div class="col-lg-6">
                                                <pre class="brush: xml">
                                                    <c:out value="${formulaEntry.parents[0].xml}" />
                                                </pre>
                                            </div>
                                            <div class="col-lg-6">
                                                <c:out value="${formulaEntry.parents[0].xml}" escapeXml="false" />
                                            </div>
                                        </div>
                                        <div class="tab-pane" id="diff">
                                            <!-- see footer for the javascript -->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>  <!-- /row -->
                    </div>  <!-- /col -->
                </div>  <!-- /row -->
            </tiles:putAttribute>
        </tiles:insertDefinition>