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
<!-- jquery-fileupload plugin -->
<script> 
    $(function () {
        $('#fileupload').fileupload({
            dataType: 'json',
 
            done: function (e, data) {
                $("tr:has(td)").remove();
                $.each(data.result, function (index, file) {
 
                    $("#uploaded-files").append(
                            $('<tr/>')
                            .append($('<td/>').text(file.name))
                            .append($('<td/>').text(file.size))
                            )
                });
            },
 
            progressall: function (e, data) {
                var progress = parseInt(data.loaded / data.total * 100, 10);
                $('#progress .bar').css(
                    'width',
                    progress + '%'
                );
            },

            dropZone: $('#dropzone')
        });
    });
</script>
</body>
</html>s