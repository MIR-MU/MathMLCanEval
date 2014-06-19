<div class="container">    
    <hr>
    <footer>
        <div class="row">
            <div class="col-lg-6"><p>&copy; MIR&#64;MU 2013-2014</p></div>
            <div class="col-lg-6">
                <form id="language" method="GET" class="pull-right">
                    <select name="lang" onchange="this.form.submit()">
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



<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<script type='text/javascript' src="<c:url value="/resources/js/jquery-fileupload/vendor/jquery.ui.widget.js" />"></script>
<script type='text/javascript' src="<c:url value="/resources/js/jquery-fileupload/jquery.iframe-transport.js" />"></script>
<script type='text/javascript' src="<c:url value="/resources/js/jquery-fileupload/jquery.fileupload.js" />"></script><script type="text/javascript" src="<c:url value="/resources/js/shCore.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/shAutoloader.js" />"></script>
<script type="text/javascript"
        src="https://c328740.ssl.cf1.rackcdn.com/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML">
</script>
<script type="text/javascript" src="<c:url value="/resources/js/diffview.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/difflib.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.flot.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.flot.categories.min.js" />"></script>
<script>
    SyntaxHighlighter.autoloader(
            'xml  <c:url value="/resources/js/shBrushXml.js" />'
            );

    SyntaxHighlighter.all();

    $(document).ready(function() {
        if($("#userRolesRows > div").length === 0)
        {
            $("#userRolesRows").append(getUserRolesHTML()); 
        }  

        $(this).on('click','.addrow',function() {
            $("#userRolesRows").append(getUserRolesHTML()); 
        });
        
        <c:if test="${not empty statistics}">
            $(function() {

		var data = [ ["isValid", <c:out value="${statistics.totalValid}" />], 
                    ["isInvalid", <c:out value="${statistics.totalInvalid}" />], 
                    ["uncertain", <c:out value="${statistics.totalUncertain}" />], 
                    ["removeResult", <c:out value="${statistics.totalRemove}" />]];

		$.plot("#flot-placeholder", [ data ], {
			series: {
				bars: {
					show: true,
					barWidth: 0.6,
					align: "center"
				}
			},
			xaxis: {
				mode: "categories",
				tickLength: 0
			}
		});
	});
        </c:if>
        
        $("#annotate-isvalid").click(function(event){
            $("#annotation-note").val($("#annotation-note").val()+" #isValid ");            
        });
        
        $("#annotate-isinvalid").click(function(){
            $("#annotation-note").val($("#annotation-note").val()+" #isInvalid ");
        });
        
        $("#annotate-uncertain").click(function(){
            $("#annotation-note").val($("#annotation-note").val()+" #uncertain ");
        });
        
        $("#annotate-remove").click(function(){
            $("#annotation-note").val($("#annotation-note").val()+" #removeResult ");
        });
        
        
        var form = $("#annotationForm");
        form.on("submit",function(event){
            event.preventDefault();
           $.ajax({
               type: form.attr('method'),
               url: form.attr('action'),
               data: form.serialize(),
               dataType: 'json',
               success: function(data){
                   console.log(data);
                   $("#annotationTable > tbody:last").append("<tr><td>"+data.user+"</td><td class=\"annotation-note-cell\">"+data.note.replace(/(#\S+)/g,"<span class=\"hashtag\">$1</span>")+"</td></tr>");
                   $("#annotation-note").val('');
                   formatHashTags(true);
               }
           });
           event.preventDefault();
        });   
        
        
        formatHashTags(false);
    });

    function getUserRolesHTML()
    {
        var count = $("#userRolesRows > div").length;
        var $html = $(".userRoleTemplate").clone();
        $html.find("select").attr({
            'id': 'userRoleForms' + count,
            'name' : 'userRoleForms['+count+']'
        });
        
        return $html.html();
    }
    
    function formatHashTags()
    {
        $("#annotationTable > tbody > tr > td:nth-child(2)").each(function(){
           //val.replace(/#(\S)*/g,"<span class=\"hashtag\">$1</span>");
           $(this).html($(this).html().replace(/(#\S+)/g,"<span class=\"hashtag\">$1</span>"));
        });       
    }
</script>
<script>
function refreshTable()
{
    $("#uploads-table").empty();
    for (var i = 0; i < $("#fileinput").prop('files').length; ++i) {
        $("#uploads-table").append('<tr><td>' + $("#fileinput").prop('files')[i].name + '</td><td>' + $("#fileinput").prop('files')[i].size + '</td></tr>');
    }
};
$(document).ready(function() {
    $("#fileinput").change(function() { refreshTable(); });
    $("#filereset").click(function(e) { 
        e.preventDefault();
        $(this).parents("form:first")[0].reset();
        refreshTable(); 
    });
});
</script>
<script>
$(document).ready(function() {
    $("#xml").on('load keyup keypress paste', function() {
        $("#mathml-preview").html($(this).val());
        MathJax.Hub.Queue(["Typeset", MathJax.Hub, "mathml-preview"]);
    }).trigger("keyup");
});
</script>
<script>
function showTooltip(data, close)
{
$('#btnCanon')
    .tooltip({
        title : data,
        trigger: 'manual',
        placement: 'top'
    }).attr('data-original-title', data).tooltip('fixTitle').tooltip('show');
    setTimeout(function() {
        $('#btnCanon').tooltip('hide');
        if (close) {
            $('#canonModal').modal('hide');
        }
    }, 2000);
}
$(document).ready(function() { $("#btnCanon").click(function(){
 $.ajax({
     type : "Get",
     url : $('#canonicalizeForm').attr('action'),  
     data : $('#canonicalizeForm').serialize(),
     dataType: 'text',
     success : function(response) {  
         showTooltip("<spring:message code="entity.formula.started" />", true);
     },
     error : function(response) {  
         showTooltip("<spring:message code="entity.formula.crashed" />", false);
     }
  });
});
});
</script>
<script type="text/javascript">
function diffView() {
    var byId = function (id) { return document.getElementById(id); },
        base = difflib.stringAsLines(MathJax.Hub.getJaxFor(byId("MathJax-Element-2")).originalText),
        newtxt = difflib.stringAsLines(MathJax.Hub.getJaxFor(byId("MathJax-Element-1")).originalText),
        sm = new difflib.SequenceMatcher(base, newtxt),
        opcodes = sm.get_opcodes(),
        diffoutputdiv = byId("diff");

    diffoutputdiv.innerHTML = "";
    diffoutputdiv.appendChild(diffview.buildView({
        baseTextLines: base,
        newTextLines: newtxt,
        opcodes: opcodes,
        baseTextName: "<spring:message code="entity.canonicOutput.original" />",
        newTextName: "<spring:message code="entity.canonicOutput.outputForm" />",
        viewType: 0
    }));
}
</script>

<script>
    
    $('#inputFilter').tooltip({
        'title': "<spring:message code="general.hint.filter" />",
           'placement': 'right',
           'container' : 'body'
       });
</script>
</body>
</html>
