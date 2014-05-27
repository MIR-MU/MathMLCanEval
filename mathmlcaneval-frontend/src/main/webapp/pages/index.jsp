
<div class="content">
    <div class="container">
        <div class="row">
            <div class="col-md-3">
                <div class="row blocks">
                    <sec:authorize access="hasRole('ROLE_USER')">
                        <a href="${pageContext.request.contextPath}/formula/create/" class="thumbnail well well-sm">
                            <div class="caption">
                                <h2><spring:message code="index.page.button.import" /></h2>
                                <p>Upload and canonicalize new formulas.</p>
                            </div>
                        </a>
                        <a href="${pageContext.request.contextPath}/formula/mass/" class="thumbnail well well-sm">
                            <div class="caption">
                                <h2><spring:message code="index.page.button.import" /></h2>
                                <p>Mass import</p>
                            </div>
                        </a>
                    </sec:authorize>
                    <a href="${pageContext.request.contextPath}/formula/list/" class="thumbnail well well-sm">
                        <div class="caption">
                            <h2><spring:message code="index.page.button.explore" /></h2>
                            <p>Browse formulas others have been uploading or search for specific formula.</p>
                        </div>
                    </a>
                    <a href="${pageContext.request.contextPath}/statistics/" class="thumbnail well well-sm">
                        <div class="caption">
                            <h2><spring:message code="index.page.button.statistics" /></h2>
                            <p>See the evaluation and statistics about performance and correctness of canonicalizer.</p>
                        </div>
                    </a>
                </div>
            </div>

            <div class="col-md-9" role="main">
                <h2><spring:message code="index.page.about" /></h2>
                <p>Goal of this project is to create an application in Java language which performs canonicalization of mathematical expressions written in MathML (Mathematical Markup Language).</p>
                <p>The output should be canonical form of given MathML document. This canonicalized form of MathML can then be used for easy decision if two differently written MathML formulae represent the same expression, or by MathML search and comparison engines.</p>
                <p><a class="btn btn-default" href="${pageContext.request.contextPath}/about/" role="button">View details &raquo;</a></p>

                <h2><spring:message code="index.page.recent" /></h2>
            </div>
        </div>
    </div>
</div>
