<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<tiles:insertDefinition name="default">
    <tiles:putAttribute name="body">    
        <div class="content container">
            <h1>!load sourcedocuments</h1>
            <hr />
            <form action="${pageContext.request.contextPath}/setup/step3/" method="post" css="form-horizontal pull-top-50">    
                <div class="form-group">
                    <label class="col-sm-2 control-label">!path</label>
                    <div class="col-sm-7">
                        <input type="text" name="path" class="form-control" />
                    </div>      
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">!filenames</label>
                    <div class="col-sm-7">
                        <input type="text" name="filename" class="form-control" />
                    </div>      
                </div>
                <div class="form-group">
                    <div class="col-xm-7 col-sm-offset-2">
                        <button type="submit" class="btn btn-primary"><spring:message code="general.button.submit" /></button>
                    </div>
                </div>
            </form>
        </div>
    </tiles:putAttribute>
</tiles:insertDefinition>