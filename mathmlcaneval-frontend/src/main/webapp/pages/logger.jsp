<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">        
        <!-- just a wrapper so we don't lose the navbar -->
        <div class="container content">
            <object type="text/html" data="${pageContext.request.contextPath}/log4jwebtracker/" style="width: 100%; height: 100%; position: fixed; left: 0;">
                <p>Log4j Web Tracker</p>
            </object>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>
