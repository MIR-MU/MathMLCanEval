<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<tiles:insertDefinition name="errorTemplate">
    <tiles:putAttribute name="body">
        <div class="content container">
            <h3>A following exception has occurred: </h3>    

            <div class="center-text">
                <h1><span class="text-warning"><c:out value="${exception}" /></span></h1>    
            </div>
            <h3>Exception was raised with following message:</h3>

            <div class="center-text">
                <div class="alert alert-danger alert-dismissible" role="alert">
                    <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4><c:out value="${message}" /></h4>
                </div>
            </div>
            <h3>Together with stacktrace below</h3>

            <pre>
                <c:out value="${stackTrace}" />
            </pre>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>