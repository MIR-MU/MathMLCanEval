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
<script src="<c:url value="/resources/js/highlight.pack.min.js"/>"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->

<script>
    $(document).ready(function () {
        console.log($(document).height()+';'+$(window).height());
        for (var i = 0; i < 25; i++) {
            var $tr = $('<tr>').append(
                    $('<td>').text(randomString(5)),
                    $('<td>').text(randomString(25)),
                    $('<td>').text(randomString(10))
                    );

            $tr.appendTo($("#activityTable"));
        }

        $(this).on('click', '.add-userrole', function () {
            var $row = $("#userRoles tr:last").clone();

            $.each($row.find('select'), function () {
                console.log(this);
                var no = parseInt($(this).attr('name').match(/\d+/)) + 1;
                $(this).attr('name', $(this).attr('name').replace(/(\d+)(?!.*\d)/g, no));
                $(this).attr('id', $(this).attr('id').replace(/(\d+)(?!.*\d)/g, no));
            });

            $("#userRoles").append($row);

        });

        $(".show-config").on('click', function () {
            $.ajax({
                url: '${context}/ajax/configuration/',
                data: {'id': $(this).data('config')}
            }).done(function (result) {
                var $modal = $("#showConfigModal").modal({show: false});
                $modal.find('.modal-body').css({
                    'overflow-y':'scroll',
                    'height' : ($(document).height()-200)
                });
                $configBody = $modal.find('code.xml');
                $configBody.append(result.replace(/</g, '&lt;'));
                hljs.highlightBlock($configBody.get(0));
                $modal.modal('show');
            });
        });
    });



    function randomString(size) {
        var s = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        return Array(size).join().split(',').map(function () {
            return s.charAt(Math.floor(Math.random() * s.length));
        }).join('');
    }
</script>