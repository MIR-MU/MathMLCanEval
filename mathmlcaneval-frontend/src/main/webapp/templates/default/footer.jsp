<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<div class="container">    
    <hr />
    <footer>
        <div class="row">
            <div class="col-lg-6"><p>&copy; MIR&#64;MU 2013-2014</p></div>
            <div class="col-lg-6">
                <form id="language" method="GET" class="pull-right">
                    <select name="lang" onchange="this.form.submit()" class="form-control">
                        <c:forTokens items="en,cs_CZ,sk_SK" delims="," var="locale">
                            <option value="${locale}"<c:if test="${locale == pageContext.response.locale}"> selected="selected"</c:if>>
                                <spring:message code="general.label.locale.${locale}" />
                            </option>
                        </c:forTokens>
                    </select>
                </form>
            </div>
        </div>
    </footer>
</div>