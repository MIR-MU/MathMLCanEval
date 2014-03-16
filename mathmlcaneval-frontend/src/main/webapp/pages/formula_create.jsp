<div class="container content">
    <h1><spring:message code="entity.formula.create" /></h1>
    <form:form id="fileupload" method="post" action="${pageContext.request.contextPath}/formula/create/" commandName="formulaForm" cssClass="form-horizontal pull-top-50"
               enctype="multipart/form-data">

        <div class="form-group">
            <label class="col-sm-2 control-label"><spring:message code="entity.formula.sourceDocument" /></label>
            <div class="col-sm-7">
                <form:select path="sourceDocumentForm" multiple="false" class="form-control">
                    <c:choose>
                        <c:when test="${fn:length(sourceDocumentFormList) eq 1}">
                            <form:option value="${sourceDocumentFormList[0].id}"><c:out value="${sourceDocumentFormList[0].documentPath}" /></form:option>
                        </c:when>
                        <c:otherwise>
                            <form:option value="-1"><spring:message code="general.select.option.pickone" /></form:option>
                            <form:options items="${sourceDocumentFormList}" itemLabel="documentPath" itemValue="id" />
                        </c:otherwise>
                    </c:choose>
                </form:select>
            </div>
            <form:errors path="sourceDocumentForm" element="div" class="col-sm-3 alert alert-danger"/>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">!revision: </label>
            <div class="col-sm-7">
                <form:select path="programForm" multiple="false" class="form-control">
                    <form:option value="-1"><spring:message code="general.select.option.pickone" /></form:option>
                    <form:options items="${programFormList}" itemLabel="name" itemValue="id" />
                </form:select>
            </div>
            <form:errors path="programForm" element="div" class="col-sm-3 alert alert-danger"/>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label"><spring:message code="entity.formula.note" /></label>
            <div class="col-sm-7">
                <form:textarea path="note" cssClass="form-control" />
            </div>
            <form:errors path="note" element="div" class="col-sm-3 alert alert-danger"/>
        </div>
        <div clas="row">
            <form:errors path="xml" element="div" class="col-sm-12 alert alert-danger"/>
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="custom-label"><spring:message code="entity.formula.xml" /></div>
                <form:textarea path="xml" cssClass="form-control" rows="15" />
            </div>
            <div class="col-md-6">
                <div class="custom-label"><spring:message code="entity.formula.rendered" /></div>
                <div id="mathml-preview"></div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label"><spring:message code="entity.formula.upload" /></label>
            <div class="col-sm-7 fileupload-buttonbar">
                <span class="btn btn-default fileinput-button">
                   <span><spring:message code="general.label.addfiles" /></span>
                   <input id="fileinput" type="file" name="uploadedFiles" multiple>
                </span>
                <input type="button" id="filereset" class="btn btn-warning cancel" value="<spring:message code="general.label.reset" />">

                </button>
                <table class="table">
                    <thead>
                       <tr>
                           <td><spring:message code="general.label.filename" /></td>
                           <td><spring:message code="general.label.filesize" /></td>
                       </tr>
                    </thead>
                    <tbody id="uploads-table" />
                </table>
            </div>
        </div>
        <div class="form-group">
            <button class="btn btn-default col-xm-7 col-sm-offset-2" data-toggle="modal" data-target="#canonModal"><spring:message code="entity.formula.run" /></button>
        </div>
        <div class="modal fade" id="canonModal" tabindex="-1" role="dialog" aria-labelledby="canonModalLabel" aria-hidden="true">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <br />
              </div>
              <div class="modal-body">
                  <input type="hidden" name="formulaId" value="${formulaEntry.id}" />
                  <div class="row">
                      <label class="col-sm-3 control-label"><spring:message code="entity.formula.revision" /></label>
                      <div class="col-sm-9">
                      <form:select path="revisionForm" multiple="false" class="form-control">
                          <form:option value="-1"><spring:message code="general.select.option.pickone" /></form:option>
                          <form:options items="${revisionList}" itemLabel="revisionHash" itemValue="id" />
                      </form:select>
                      </div>
                  </div>
                  <div class="row">
                      <label class="col-sm-3 control-label"><spring:message code="entity.formula.configuration" /></label>
                      <div class="col-sm-9">
                      <form:select path="configurationForm" multiple="false" class="form-control">
                          <form:option value="-1"><spring:message code="general.select.option.pickone" /></form:option>
                          <form:options items="${configurationList}" itemLabel="name" itemValue="id" />
                      </form:select>
                      </div>
                  </div>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><spring:message code="general.label.close" /></button>
              </div>
            </div>
          </div>
        </div>

        <div class="form-group">
            <div class="col-xm-7 col-sm-offset-2">
                <button type="submit" class="btn btn-primary"><spring:message code="general.button.submit" /></button>
            </div>
        </div>
    </form:form>
</div>
