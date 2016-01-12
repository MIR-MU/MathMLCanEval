<%-- 
    Document   : footer-javascript
    Created on : Dec 19, 2015, 10:39:06 AM
    Author     : Dominik Szalai - emptulik at gmail.com
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Bootstrap core JavaScript
        ================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js" />"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->

<script>
    $(document).ready(function () {
        for (var i = 0; i < 25; i++) {
            var $tr = $('<tr>').append(
                    $('<td>').text(randomString(5)),
                    $('<td>').text(randomString(25)),
                    $('<td>').text(randomString(10))
                    );

            $tr.appendTo($("#activityTable"));
        }
    });



    function randomString(size) {
        var s = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        return Array(size).join().split(',').map(function () {
            return s.charAt(Math.floor(Math.random() * s.length));
        }).join('');
    }
    //            $(".navbar-toggler").click(function () {
    //                $("#sideBarCollapse").collapse('toggle');
    //                $(".sidebar").hide();
    //                $(".main").addClass(".col-xs-offset-1");
    //            });
    //            
    //            $(".hide-sidebar").click(function(){
    //                $("#sideBarCollapse").collapse('toggle');
    //                $(".sidebar").show();
    //            });
</script>