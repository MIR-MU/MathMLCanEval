<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">    
        <div class="content container">
            <p class="bg-success">
            <h1 class="center-block">!success</h1>
        </p>
    </div>
</tiles:putAttribute>
</tiles:insertDefinition>