<div class="container">    
    <hr>
    <footer>
        <p>&copy; MIR&#64;MU 2013-2014</p>
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
        
        $(function() {

		var data = [ ["January", 10], ["February", 8], ["March", 4], ["April", 13], ["May", 17], ["June", 9] ];

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


    });

    function getUserRolesHTML()
    {
        var count = $("#userRolesRows > div").length;
        console.log(count);
        var $html = $(".userRoleTemplate").clone();
        $html.find("select").attr({
            'id': 'userRoleForms' + count,
            'name' : 'userRoleForms['+count+']'
        });
        
        return $html.html();
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
