<div class="container content">
    <h1>!vytvorenie novej revizie</h1>
    <form:form method="post" action="${pageContext.request.contextPath}/sourcedocument/create/" commandName="sourceDocumentForm" cssClass="form-horizontal pull-top-50"
               enctype="multipart/form-data">
        
        <div class="form-group">
            <label class="col-sm-2 control-label">!revision note</label>
            <div class="col-sm-7">
                <form:textarea path="note" cssClass="form-control" />
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">!revision note</label>
            <div class="col-sm-7">
                <input type="file" name="fileUpload" class="form-control"/>
            </div>
        </div>
        <div class="form-group">
            <div class="col-xm-7 col-sm-offset-2">
                <button type="submit" class="btn btn-primary">!submit</button>
            </div>
        </div>        
    </form:form>
</div>
