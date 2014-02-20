<div class="container content">
    <h1><spring:message code="entity.formula.entry" /></h1>
    <div class="pull-right">
        <div class="pull-right">
            <a href="${pageContext.request.contextPath}/formula/list/" class="btn btn-primary space-bottom-10"><spring:message code="general.label.goback" /></a>        
        </div>
    </div>
    <div class="row pull-top-50">
        <div class="col-lg-12">
            <div class="panel panel-primary">
                <!-- Formula details -->
                <div class="panel-heading"><spring:message code="entity.formula.detail" /></div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-2">
                            <spring:message code="general.field.id" />
                        </div>
                        <div class="col-lg-10">
                            <c:out value="${formulaEntry.id}" />
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-2">
                            <spring:message code="entity.formula.user" />
                        </div>
                        <div class="col-lg-10">
                            <c:out value="${formulaEntry.user.username}" />
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-2">
                            <spring:message code="entity.formula.sourceDocument" />
                        </div>
                        <div class="col-lg-10">
                            <a href="${pageContext.request.contextPath}/sourcedocument/view/<c:out value="${formulaEntry.sourceDocument.id}" />">${formulaEntry.sourceDocument.documentPath}</a>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-2">
                            <spring:message code="entity.formula.program" />
                        </div>
                        <div class="col-lg-10">
                            <a href="${pageContext.request.contextPath}/program/view/<c:out value="${formulaEntry.program.id}" />">${formulaEntry.program.name}</a>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-2">
                            <spring:message code="entity.formula.note" />
                        </div>
                        <div class="col-lg-10">
                            <c:out value="${formulaEntry.note}" />
                        </div>
                    </div>
                </div>
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
<c:out value="${formulaEntry.xml}" />
                        </div>

                    </div>
                </div>
            </div>

            <div class="panel panel-primary">
                <div class="panel-heading"><spring:message code="entity.formula.canonicalizations" /></div>
                <div class="panel-body">
                    <!-- Performed canonicalizations -->
                    <div class="row">
                    </div>
                </div>
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
