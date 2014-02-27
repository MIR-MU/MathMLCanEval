<div class="container content">
    <h1><spring:message code="entity.formula.entry" /></h1>
    <div class="btn-group pull-right space-bottom-10">
        <a href="${pageContext.request.contextPath}/formula/run/${formulaEntry.id}" class="btn btn-warning"><spring:message code="entity.formula.run" /></a>
        <sec:authentication var="user" property="principal" />
        <!-- superuser -->
        <sec:authorize access="hasRole('ROLE_ADMINISTRATOR')">
        <a href="${pageContext.request.contextPath}/formula/delete/${formulaEntry.id}" class="btn btn-danger"><spring:message code="general.label.delete" /></a>
        </sec:authorize>
        <!-- normal users -->
        <sec:authorize access="hasRole('ROLE_USER') and !hasRole('ROLE_ADMINISTRATOR')">
        <a href="${pageContext.request.contextPath}/formula/delete/${formulaEntry.id}" class="btn btn-danger" <c:if test="${user.username ne formulaEntry.user.username}">disabled="disabled"</c:if>><spring:message code="general.label.delete" /></a>
        </sec:authorize>
        
    </div>
    <div class="row pull-top-50">
        <div class="col-lg-12">
            <div class="panel panel-primary">
                <!-- Formula details -->
                <div class="panel-heading"><spring:message code="entity.formula.detail" /></div>
                <table class="table table-bordered table-striped">
                    <tr>
                        <td><spring:message code="general.field.id" /></td>
                        <td><c:out value="${formulaEntry.id}" /></td>
                    </tr>
                    <tr>
                        <td><spring:message code="entity.formula.user" /></td>
                        <td><c:out value="${formulaEntry.user.username}" /></td>
                    </tr>
                    <tr>
                        <td><spring:message code="entity.formula.sourceDocument" /></td>
                        <td><a href="${pageContext.request.contextPath}/sourcedocument/view/${formulaEntry.sourceDocument.id}"><c:out value="${formulaEntry.sourceDocument.documentPath}" /></a></td>
                    </tr>
                    <tr>
                        <td><spring:message code="entity.formula.program" /></td>
                        <td><a href="${pageContext.request.contextPath}/program/view/${formulaEntry.program.id}"><c:out value="${formulaEntry.program.name}" /></a></td>
                    </tr>
                    <tr>
                        <td><spring:message code="entity.formula.note" /></td>
                        <td><c:out value="${formulaEntry.note}" /></td>
                    </tr>
                </table>
            </div>

            <div class="panel panel-primary">
                <!-- Formula in MathML & rendered -->
                <div class="panel-heading"><spring:message code="entity.formula.xml" /></div>
                <div class="panel-body">

                    <div class="row">
                        <div class="col-lg-6">
                            <pre class="brush: xml">
<c:out value="${formulaEntry.xml}" />
                            </pre>
                        </div>
                        <div class="col-lg-6">
<c:out value="${formulaEntry.xml}" escapeXml="false" />
                        </div>

                    </div>
                </div>
            </div>

            <div class="panel panel-primary">
                <div class="panel-heading"><spring:message code="entity.formula.canonicalizations" /></div>
                <!-- Performed canonicalizations -->
                <c:if test="${fn:length(formulaEntry.outputs) gt 0}">
                    <table class="table table-bordered table-striped">
                        <thead>
                            <tr>
                                <th><spring:message code="general.field.timestamp" /></th>
                                <th><spring:message code="general.field.id" /></th>
                                <th><spring:message code="entity.revision.hash" /></th>
                                <th><spring:message code="entity.configuration.config" /></th>
                                <th><spring:message code="entity.canonicOutput.runningTime" /></th>
                            </tr>
                        </thead>
                    <c:forEach items="${formulaEntry.outputs}" var="entry">
                        <tbody>
                            <tr>
                                <td><c:out value="${entry.applicationRun.startTime}" /></td>
                                <td><a href="${pageContext.request.contextPath}/canonicoutput/view/${entry.id}">${entry.id}</a></td>
                                <td><a href="https://github.com/michal-ruzicka/MathMLCanEval/commit/<c:out value="${entry.applicationRun.revision.revisionHash}" />"><c:out value="${entry.applicationRun.revision.revisionHash}" /></a></td>
                                <td><a href="${pageContext.request.contextPath}/configuration/view/${entry.applicationRun.configuration.id}">${entry.applicationRun.configuration.id}</a></td>
                                <td>${entry.runningTime}</td>
                            </tr>
                        </tbody>
                    </c:forEach>
                    </table>
                </c:if>
            </div>

            <div class="panel panel-primary">
                <div class="panel-heading"><spring:message code="entity.formula.equivalent" /></div>
                <div class="panel-body">
                    <!-- Equivalent formulae -->
                    <div class="row">
                    </div>
                </div>
            </div>
        </div>
    </div>
