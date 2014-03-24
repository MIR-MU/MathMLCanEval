<div class="container content">
    <h1><spring:message code="entity.output.entry" /></h1>
    <div class="btn-group pull-right space-bottom-10">
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
                       <form:form method="post" action="${pageContext.request.contextPath}/canonicoutput/annotate/" commandName="annotationForm" cssClass="form-horizontal">
                       <input type="hidden" name="canonicOutputId" value="${formulaEntry.id}" />
                       <table class="table table-bordered table-striped" style="table-layout: fixed;"><tbody>
                       <c:forEach items="${annotationFlagList}" var="flag">
                           <tr><td><sec:authorize access="hasRole('ROLE_USER')">
                               <form:radiobutton path="annotationFlagForm" value="${flag.id}" />
                               </sec:authorize> ${flag.flagValue}</td>
                               <td><div class="progress-bar" style="width: <c:out value="${annotationFlagHits[flag.id] * 100.0 / totalAnnotations}" default="0" />%;" title="${annotationFlagHits[flag.id]}">&nbsp;</div></td>
                           </tr>
                       </c:forEach>
                       </tbody></table>
                       <sec:authorize access="hasRole('ROLE_USER')">
                       <div class="panel-footer">
                           <form:input style="width: 80%;" path="note" placeholder="Note" />
                           <button type="submit" class="btn btn-primary btn-sm pull-right"><spring:message code="general.button.submit" /></button>
                       </div>
                       </sec:authorize>
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
                                <li class="active"><a href="#canonicalized" data-toggle="tab"><spring:message code="entity.canonicOutput.outputForm" /></a></li>
                                <li><a href="#original" data-toggle="tab"><spring:message code="entity.canonicOutput.original" /></a></li>
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
                            </div>
                        </div>
                    </div>
                </div>
            </div>  <!-- /row -->
        </div>  <!-- /col -->
    </div>  <!-- /row -->