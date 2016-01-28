<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">
        <h1>Git Dashboard</h1>
        <h2 class="text-muted">${currentBranch}</h2>
        <div class="row">
            <div class="col-md-6">
                <div class="row">
                    <div class="col-md-6">
                        <table class="table">
                            <c:forEach items="${branches}" var="entry">
                                <tr>
                                    <td>
                                        <c:out value="${entry.name}" />
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${entry.active}">
                                                <i class="fa fa-toggle-on"></i>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="${context}/git/branch/${entry.name}/checkout/" class="disable-link-color">
                                                    <i class="fa fa-toggle-off"></i>
                                                </a>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                    <div class="col-md-6">
                        <table class="table">
                            <c:forEach items="${revisions}" var="entry">
                                <tr>
                                    <td>
                                        <c:out value="${entry.revisionHash}" />
                                    </td>
                                    <td>
                                        <i class="fa fa-database"></i>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </div>

                ${branches}
                ${persistedRevisions}
                ${revisions}
            </div>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>