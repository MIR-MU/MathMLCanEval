<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="shortcut icon" href="../../docs-assets/ico/favicon.png">

        <title>MathMLCanEval</title>

        <!-- Bootstrap core CSS -->
        <link href="<c:url value="/resources/css/bootstrap.min.cosmo.css" />" rel="stylesheet">
        <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
        <link href="<c:url value="/resources/css/shCore.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/resources/css/shThemeDefault.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/resources/css/jquery.fileupload.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/resources/css/diffview.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/resources/css/simple-slider.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/resources/css/simple-slider-volume.css" />" rel="stylesheet" type="text/css" />


        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
          <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
          <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
        <![endif]-->
    </head>

    <body>
        <tiles:insertAttribute name="navigation" />

        <tiles:insertAttribute name="body" />

        <tiles:insertAttribute name="footer" />



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
                src="http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML">
        </script>
        <script type="text/javascript" src="<c:url value="/resources/js/diffview.js" />"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/difflib.js" />"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/jquery.flot.min.js" />"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/jquery.flot.categories.min.js" />"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/simple-slider.min.js" />"></script>
        <script>
                    /*
                     * http://stackoverflow.com/questions/229726/using-javascript-within-a-jsp-tag
                     */
                    SyntaxHighlighter.autoloader(
                            'xml  <c:url value="/resources/js/shBrushXml.js" />'
                            );

                    SyntaxHighlighter.all();

                    $(document).ready(function() {

                        $("#flot-placeholder").css('width', $(".stats-panel-body").width());
                        $("#flot-placeholder").css('height', $(".stats-panel-body").width() * 0.8);

                        $("#statisticsForm ").on('change', function() {
                            var val = $("option:selected", this).val();

                            window.location.href = "${pageContext.request.contextPath}/statistics/" + val + "/";
                        });

                        if ($("#userRolesRows > div").length === 0)
                        {
                            $("#userRolesRows").append(getUserRolesHTML());
                        }

                        $(this).on('click', '.addrow', function() {
                            $("#userRolesRows").append(getUserRolesHTML());
                        });

            <c:if test="${not empty statistics}">
                        $(function() {

                            var data = [["isValid", <c:out value="${statistics.totalValid}" />],
                                ["isInvalid", <c:out value="${statistics.totalInvalid}" />],
                                ["uncertain", <c:out value="${statistics.totalUncertain}" />],
                                ["removeResult", <c:out value="${statistics.totalRemove}" />]];

                            $.plot("#flot-placeholder", [data], {
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

                        $(".annotation-option").click(function(e) {
                            var currentValue = $("#annotation-value").val();
                            var spacer = '';
                            if (currentValue.trim().length > 0) {
                                spacer = ' ';
                            }

                            $("#annotation-value").val(currentValue + spacer + $(this).attr('data-annotation'));
                            e.preventDefault();
                        }).tooltip({
                            placement: 'right',
                            container: 'body',
                            title: '<spring:message code="general.label.button.annotate.hint" />' + ' ' + $(this).attr('data-annotation')
                        });


                        $("#clear-form").on('click', function(e) {
                            $("#annotation-value").val('');
                            e.preventDefault();
                        });

                        var form = $("#annotationForm");
                        form.on("submit", function(event) {
                            if ($("#annotation-value").val().trim().length != 0)
                            {
                                $.ajax({
                                    type: form.attr('method'),
                                    url: form.attr('action'),
                                    data: form.serialize(),
                                    dataType: 'json',
                                    success: function(data) {
                                        $("#annotationTable > tbody:last").append("<tr><td>" + data.user + "</td><td class=\"annotation-note-cell\">" + data.note + "</td></tr>");
                                        $("#annotation-value").val('');
                                        formatHashTags(false);
                                        if ($("#annotationTable > tbody > tr:first").attr('class') === "empty-table")
                                        {
                                            $("#annotationTable > tbody > tr:first").fadeOut(1200, function() {
                                                $(this).remove();
                                            });
                                        }
                                    }
                                });
                            }
                            else
                            {
                                alert('<spring:message code="validator.general.noinput" />');
                                console.log('input empty');
                            }

                            event.preventDefault();
                        });


                        formatHashTags(true);

                        $("#similarityFuzzySlider").bind("slider:changed", function(event, data) {
                            var number = data.value.toFixed(2); //does not work ie<= 8.0

                            $(this).val(number);
                            $("#tresholdOutput").text(number);
                        });


            <c:if test="${massDelete eq true}">
                        $(".img-thumbnail").on('click', function(e) {

                            if ($(this).hasClass('formula-delete-checked')) {
                                $(this).removeClass('formula-delete-checked');
                                $(this).children(':checkbox').first().attr('checked', false);

                            } else {
                                $(this).addClass('formula-delete-checked');
                                $(this).children(':checkbox').first().attr('checked', true);
                            }

                            e.preventDefault();
                        });
            </c:if>
                    });

                    function getUserRolesHTML()
                    {
                        var count = $("#userRolesRows > div").length;
                        var $html = $(".userRoleTemplate").clone();
                        $html.find("select").attr({
                            'id': 'userRoleForms' + count,
                            'name': 'userRoleForms[' + count + ']'
                        });

                        return $html.html();
                    }

                    function formatHashTags(onload)
                    {
                        var regEx = /#[a-zA-Z0-9]*/g;   // if used b is number
                        var regEx2 = /(#\S+)/g;         // if used b is string
                        function appendLabel(a, b)
                        {
                            var labelType;
                            var icon;
                            switch (b)
                            {
                                case "#isValid":
                                    labelType = 'success';
                                    icon = 'ok';
                                    break;
                                case "#isInvalid":
                                    labelType = 'warning';
                                    icon = 'flag';
                                    break;
                                case "#uncertain":
                                    labelType = 'info';
                                    icon = 'question-sign';
                                    break;
                                case "#removeResult":
                                    labelType = 'danger';
                                    icon = 'remove';
                                    break;
                                case "#formulaRemove":
                                    labelType = 'danger';
                                    icon = 'remove';
                                    break;
                                case "#formulaMeaningless":
                                    labelType = 'danger';
                                    icon = 'trash'
                                    break;
                                default:
                                    labelType = 'default';
                                    icon = 'comment';
                            }

                            return '<span class="label label-' + labelType + '">' + b + ' <span class="glyphicon glyphicon-' + icon + '"></span></span>';
                        }
                        if (onload)
                        {
                            $("#annotationTable > tbody > tr > td:nth-child(2)").each(function() {
                                $(this).html($(this).html().replace(regEx2, appendLabel));
                            });
                        }
                        else
                        {
                            $("#annotationTable > tbody > tr:last > td:nth-child(2)").each(function() {
                                $(this).html($(this).html().replace(regEx2, appendLabel));
                            });
                        }
                    }
        </script>
        <script>
            function refreshTable()
            {
                $("#uploads-table").empty();
                for (var i = 0; i < $("#fileinput").prop('files').length; ++i) {
                    $("#uploads-table").append('<tr><td>' + $("#fileinput").prop('files')[i].name + '</td><td>' + $("#fileinput").prop('files')[i].size + '</td></tr>');
                }
            }
            ;
            $(document).ready(function() {
                $("#fileinput").change(function() {
                    refreshTable();
                });
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
                            title: data,
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
            $(document).ready(function() {
                $("#btnCanon").click(function() {
                    $.ajax({
                        type: "Get",
                        url: $('#canonicalizeForm').attr('action'),
                        data: $('#canonicalizeForm').serialize(),
                        dataType: 'text',
                        success: function(response) {
                            showTooltip("<spring:message code="entity.formula.started" />", true);
                        },
                        error: function(response) {
                            showTooltip("<spring:message code="entity.formula.crashed" />", false);
                        }
                    });
                });
            });
        </script>
        <script type="text/javascript">
            function diffView() {
                var byId = function(id) {
                    return document.getElementById(id);
                },
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
                'container': 'body'
            });
        </script>
    </body>
</html>


