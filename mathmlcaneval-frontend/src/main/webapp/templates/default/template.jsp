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

        <title><c:out value="${websiteTitle}" /></title>

        <!-- Bootstrap core CSS -->
        <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
        <link href="<c:url value="/resources/css/styles.css" />" rel="stylesheet">
        <link href="<c:url value="/resources/css/shCore.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/resources/css/shThemeDefault.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/resources/css/jquery.fileupload.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/resources/css/diffview.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/resources/css/simple-slider.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/resources/css/simple-slider-volume.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/resources/css/select2.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/resources/css/select2-bootstrap.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/resources/css/jquery.dataTables.min.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/resources/css/dataTables.bootstrap.min.css" />" rel="stylesheet" type="text/css" />


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
                src="https://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML">
        </script>
        <script type="text/javascript" src="<c:url value="/resources/js/diffview.js" />"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/difflib.js" />"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/simple-slider.min.js" />"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/flotr2.min.js" />"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/select2.min.js" />"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/jquery.dataTables.min.js" />"></script>
        <script type="text/javascript" src="<c:url value="/resources/js/dataTables.bootstrap.js" />"></script>
        <script type="text/javascript">
                    /*
                     * http://stackoverflow.com/questions/229726/using-javascript-within-a-jsp-tag
                     */
                    SyntaxHighlighter.autoloader(
                            'xml  <c:url value="/resources/js/shBrushXml.js" />'
                            );

                    SyntaxHighlighter.all();

                    //document.ready start
                    $(document).ready(function () {
                        $("#minifeedtable").dataTable({
                            "processing": true,
                            "serverSide": true,
                            "ajax": {
                                "url": '${pageContext.request.contextPath}/databaseevent/list',
                                "type": "GET",
                                dataType: 'json'
                            },
                            "columns": [
                                {data: "user"},
                                {data: "message"},
                                {data: "date"}
                            ],
                        });
                        
                        $(".elementList").select2();
                        $("#teest").select2();

                        $("#flot-placeholder").css('width', $(".stats-panel-body").width());
//                        $("#flot-placeholder").css('height', $(".stats-panel-body").width() * 0.8);

                        $("#statisticsForm ").on('change', function () {
                            var val = $("option:selected", this).val();

                            window.location.href = "${pageContext.request.contextPath}/statistics/" + val + "/";
                        });

                        if ($("#userRolesRows > div").length === 0)
                        {
                            $("#userRolesRows").append(getUserRolesHTML());
                        }

                        $(this).on('click', '.addrow', function () {
                            $("#userRolesRows").append(getUserRolesHTML());
                        });

                        $(this).on('click', '.addelementrow', function () {
                            $("#elementRowsDiv").append(getElementsSelectRow())
                                    .find("select:last").select2();
                        });

                        $(this).on('click', '.removeelementrow', function () {
                            $(this).parents("div.row").first().remove();
                            if ($("#elementRowsDiv > div").length === 0) {
                                $("#elementRowsDiv").append(getElementsSelectRow())
                                        .find("select:last").select2();
                            }
                        });

                        $(".annotation-option").click(function (e) {
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
                            title: function () {
                                var val = $(this).attr('data-description');
                                return '<spring:message code="general.label.button.annotate.hint" />' + ' ' + val;
                            }
                        });


                        $("#clear-form").on('click', function (e) {
                            $("#annotation-value").val('');
                            e.preventDefault();
                        });

                        var form = $("#annotationForm");
                        form.on("submit", function (event) {
                            if ($("#annotation-value").val().trim().length != 0)
                            {
                                $.ajax({
                                    type: form.attr('method'),
                                    url: form.attr('action'),
                                    data: form.serialize(),
                                    dataType: 'json',
                                    success: function (data) {
                                        var output = "<tr><td>" + data.username;
                                        output += "</td><td class=\"annotation-note-cell\">";
                                        output += data.annotationContent + "</td>";

            <sec:authorize access="hasRole('ROLE_USER')">
                                        output += '<td><a href="#" class="annotation-remove" id="' + data.id;
                                        output += '"><span class="glyphicon glyphicon-remove"></span></a></td>';
            </sec:authorize>
                                        output += "</tr>";
                                        $("#annotationTable > tbody:last").append(output);
                                        $("#annotation-value").val('');
                                        formatHashTags(false);
                                        if ($("#annotationTable > tbody > tr:first").attr('class') === "empty-table")
                                        {
                                            $("#annotationTable > tbody > tr:first").remove();
                                        }
                                    }
                                });
                                event.preventDefault();
                            }
                            else
                            {
                                alert('<spring:message code="validator.general.noinput" />');
                                console.log('input empty');
                            }

                            event.preventDefault();
                        });

                        $(document).on('click', '.annotation-remove', function (e) {
                            var selector = "#" + $(this).attr("id") + "." + $(this).attr("class");
                            var onSuccess = function () {
                                window.location.reload(true);
                            };
                            $.ajax({
                                type: "GET",
                                url: "${pageContext.request.contextPath}/annotation/delete/" + $(this).attr("id"),
                                dataType: 'json',
                                async: true,
                                success: function (response) {
                                    if (response == true) {
                                        showTooltip(selector, "<spring:message code="entity.annotation.removed" />", onSuccess);
                                    } else {
                                        showTooltip(selector, "<spring:message code="entity.annotation.notRemoved" />");
                                    }
                                },
                                error: function (response) {
                                    showTooltip(selector, "<spring:message code="entity.annotation.notRemoved" />");
                                }
                            });
                            e.preventDefault();
                        });

                        formatHashTags(true);

                        $("#similarityFuzzySlider").bind("slider:changed", function (event, data) {
                            var number = data.value.toFixed(2); //does not work ie<= 8.0

                            $(this).val(number);
                            $("#tresholdOutput").text(number);
                        });

                        $("#resizeWindow").click(function () {
                            var windowVar = $("#formulaWindow");
                            if (windowVar.hasClass('page-wide-window'))
                            {
                                windowVar.removeClass('page-wide-window').next('.moveMe').css('margin-top', '0');
                            }
                            else
                            {
                                windowVar.addClass('page-wide-window').next(".moveMe").css('margin-top', windowVar.height() + 15);
                            }
                        });

                        $(".invert-selection-button").click(function (e) {
                            $(".img-thumbnail").each(function () {
                                if ($(this).hasClass('formula-select-checked')) {
                                    $(this).removeClass('formula-select-checked');
                                    $(this).children(':checkbox').first().attr('checked', false);
                                } else {
                                    $(this).addClass('formula-select-checked');
                                    $(this).children(':checkbox').first().attr('checked', true);
                                }
                            });

                            e.preventDefault();
                        });

                        $("#fileinput").change(function () {
                            refreshTable();
                        });

                        $("#filereset").click(function (e) {
                            e.preventDefault();
                            $(this).parents("form:first")[0].reset();
                            refreshTable();
                        });


                        $("#xml").on('load keyup keypress paste', function () {
                            $("#mathml-preview").html($(this).val());
                            MathJax.Hub.Queue(["Typeset", MathJax.Hub, "mathml-preview"]);
                        }).trigger("keyup");

                        $("#btnCanon").click(function () {
                            $.ajax({
                                type: "Get",
                                url: $('#canonicalizeForm').attr('action'),
                                data: $('#canonicalizeForm').serialize(),
                                dataType: 'text',
                                success: function (response) {
                                    showTooltip("#btnCanon", "<spring:message code="entity.formula.started" />", function () {
                                        $('#canonModal').modal('hide');
                                    });
                                },
                                error: function (response) {
                                    showTooltip("#btnCanon", "<spring:message code="entity.formula.crashed" />");
                                }
                            });
                        });

                        $('#inputFilter').tooltip({
                            'title': "<spring:message code="general.hint.filter" />",
                            'placement': 'right',
                            'container': 'body'
                        });
                        
                        $("#b-submitCompare").click(function(e){
                            var appRunsID = [];
                            $("#applicationRunsTable input:checked").each(function(){
                                appRunsID.push(parseInt($(this).attr('value')));
                            });
                                                        
                            var $form = $("<form>",
                            {
                                action: '${pageContext.request.contextPath}/comparison/',
                                method:'get'
                            });
                            
                            $("#applicationRunsTable input:checked").clone().appendTo($form);
                            
                            $form.appendTo("body").submit();
                        });
                        
                        $("#loadMoreAppRuns").click(function(e){
                            var start = $("#applicationRunsTable > tbody > tr").length;
                            $.ajax({
                                type: "GET",
                                url: "${pageContext.request.contextPath}/appruns/load/",
                                data: {'start' : start,'end' : start+10},
                                dataType: 'json',
                                async: true,
                                success: function (response){
                                    $.each(response,function(index,value){
                                        var $tr = $("<tr>").append(
                                            $('<td>').text(value.id),
                                            $('<td>').text(formatDate(value.startTime)),
                                            $('<td>').text(formatDate(value.stopTime)),
                                            $('<td>').text(value.user.username),
                                            $('<td>').append($('<a>',{
                                                href:'${pageContext.request.contextPath}/configuration/view/'+value.configuration.id+'/'
                                            }).text(value.configuration.name)),
                                            $('<td>').text(value.revision.revisionHash),
                                            $('<td>').text(value.canonicOutputCount),
                                            $('<td>').append($('<input type="checkbox" />').attr({'value':value.id,'name':'appRunsID'})),
                                            $('<td>').text('x')
                                        ).appendTo("#applicationRunsTable > tbody:last");
                                    });
                                }
                            });
                            e.preventDefault()
                            console.log($("#applicationRunsTable > tbody > tr").length);
                        });
                        
                        $("#revisionForm").change(function(){
                            var revisionHash = $("#revisionForm option:selected").text();
                            $.ajax({
                                type : 'get',
                                url : '${pageContext.request.contextPath}/ajax/revisionexists/',
                                data : {'revisionHash' : revisionHash},
                                success : function(response){
                                    if(response == false)
                                    {
                                        alert('Given canonicalizer with revision ['+revisionHash+'] does not exists.');
                                    }
                                }
                            });
                        });

          <c:if test="${not empty graph}">
                (function drawCharts(container){
                    var data = [
                                  <c:forEach items="${graph}" var="entry" varStatus="status">
                                              [<c:out value="${status.index}" />,<c:out value="${entry.value}" />]<c:if test="${not status.last}"><c:out value="," /></c:if>
                                  </c:forEach>];
                    var graph;
                    
                    graph = Flotr.draw(container, [data],{
                        bars : {
                        show : true,
                        horizontal : false,
                        shadowSize : 0,
                        barWidth : 1
                      },
                        xaxis: {
                            minorTickFreq: 0,
                            ticks : [
                                <c:forEach items="${graph}" var="entry" varStatus="status">
                                              [<c:out value="${status.index}" />,"<c:out value="${entry.key}" />"]<c:if test="${not status.last}"><c:out value="," /></c:if>
                                  </c:forEach>
                            ],
                            labelsAngle : 45
                        }, 
                        grid: {
                          minorVerticalLines: true
                        }
                    });
                })(document.getElementById("flot-placeholder"));
            </c:if>


            <c:if test="${massDelete eq true}">
                        $(".img-thumbnail").on('click', function (e) {

                            if ($(this).hasClass('formula-select-checked')) {
                                $(this).removeClass('formula-select-checked');
                                $(this).children(':checkbox').first().attr('checked', false);

                            } else {
                                $(this).addClass('formula-select-checked');
                                $(this).children(':checkbox').first().attr('checked', true);
                            }

                            e.preventDefault();
                        });
            </c:if>

            <c:if test="${massCanonicalize eq true}">
                        var fetchFormulaIds = function (prefix, entityId) {
                            $.ajax({
                                type: 'GET',
                                url: prefix + entityId,
                                dataType: 'json',
                                async: true,
                                success: function (result) {
                                    $.each(result, function (i, id) {
                                        $("#apprun-input-formulas").append(id + " ");
                                    });
                                }
                            });
                        };

                        $(".apprun-input-add#source-document").click(function (e) {
                            fetchFormulaIds("${pageContext.request.contextPath}/formula/bySourceDocument/", $("#apprun-sourcedocument-id").val());
                            e.preventDefault();
                        });
                        $(".apprun-input-add#conversion-program").click(function (e) {
                            fetchFormulaIds("${pageContext.request.contextPath}/formula/byProgram/", $("#apprun-program-id").val());
                            e.preventDefault();
                        });

                        $(".apprun-input-option").click(function (e) {
                            var method = $(this).attr('data-apprun-input');
                            var className = "apprun-input-method";
                            var theDiv = $('#' + method + '.' + className);

                            theDiv.siblings('.' + className).css('display', 'none');
                            theDiv.css('display', 'block');
                            $("#apprun-input-button").html($(this).html() + '<span class="caret"></span>');
                            //e.preventDefault();
                        });
                        $(".img-thumbnail").on('click', function (e) {

                            if ($(this).hasClass('formula-select-checked')) {
                                $(this).removeClass('formula-select-checked');
                                $(this).children(':checkbox').first().attr('checked', false);
                            } else {
                                $(this).addClass('formula-select-checked');
                                $(this).children(':checkbox').first().attr('checked', true);
                            }

                            e.preventDefault();
                        });
            </c:if>
                                <c:if test="${not empty compareDiff}">
                                    <c:forEach items="${comparedResult}" var="entry">
                                        diffView("#comp<c:out value="${entry.key.id}" />",
                                        "#comp<c:out value="${entry.value.id}" />",
                                        "comp<c:out value="${entry.key.id}" />comp<c:out value="${entry.value.id}" />");
                                    </c:forEach>
                                </c:if>

                    });//document.ready end

                    function diffView(formula1,formula2,output) {
                        var byId = function (id) {
                            return document.getElementById(id);
                        }
                        if(formula1 && formula2){
                            console.log($(formula1).text());
                            base = difflib.stringAsLines($(formula1).text());
                            newtxt = difflib.stringAsLines($(formula2).text());
                        }else{
                            base = difflib.stringAsLines(MathJax.Hub.getJaxFor(byId("MathJax-Element-2")).originalText);
                            newtxt = difflib.stringAsLines(MathJax.Hub.getJaxFor(byId("MathJax-Element-1")).originalText);
                        }
                        sm = new difflib.SequenceMatcher(base, newtxt);
                        opcodes = sm.get_opcodes();
                        diffoutputdiv = byId(output ? output : "diff");

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

                    function showTooltip(selector, data, onClose, timeout)
                    {
                        $(selector).tooltip({
                            title: data,
                            trigger: 'manual',
                            placement: 'top'
                        }).attr('data-original-title', data).tooltip('fixTitle').tooltip('show');
                        setTimeout(function () {
                            $(selector).tooltip('hide');
                            if (onClose) {
                                onClose();
                            }
                        }, timeout || 2000);
                    }

                    function refreshTable()
                    {
                        $("#uploads-table").empty();
                        for (var i = 0; i < $("#fileinput").prop('files').length; ++i) {
                            $("#uploads-table").append('<tr><td>' + $("#fileinput").prop('files')[i].name + '</td><td>' + $("#fileinput").prop('files')[i].size + '</td></tr>');
                        }
                    }

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

                    function getElementsSelectRow()
                    {
                        var count = $("#elementRowsDiv > div").length;
                        var $html = $("#elementRowTemplate").clone();

                        $html.find("select").attr({
                            'name': function (_, name) {
                                return name.replace("X", count);
                            }
                        });

                        $html.find("input").attr({
                            'name': function (_, name) {
                                return name.replace("X", count);
                            }
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
            <c:if test="${not empty annotationValueList}">
                <c:forEach items="${annotationValueList}" var="entry">
                            case "${entry.value}":
                                        {
                    <c:choose>
                        <c:when test="${not empty entry.label}">
                            labelType = '${entry.label}';
                        </c:when>
                        <c:otherwise>
                            labelType = 'default';
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${not empty entry.icon}">
                            icon = '${entry.icon}';
                        </c:when>
                        <c:otherwise>
                            icon = 'comment';
                        </c:otherwise>
                    </c:choose>
                            break;}
                </c:forEach>
            </c:if>
                            default:
                                    labelType = 'default';
                            icon = 'comment';
                        }

                        return '<span class="label label-' + labelType + '">' + b + ' <span class="glyphicon glyphicon-' + icon + '"></span></span>';
                    }
                    if (onload)
                    {
                        $("#annotationTable > tbody > tr > td:nth-child(2)").each(function () {
                            $(this).html($(this).html().replace(regEx2, appendLabel));
                        });
                    }
                    else
                    {
                        $("#annotationTable > tbody > tr:last > td:nth-child(2)").each(function () {
                            $(this).html($(this).html().replace(regEx2, appendLabel));
                        });
                        }
                    }
                    
                    function formatDate(inputDate)
                    {
                        var outputDate = inputDate.monthOfYear;
                        outputDate += '/'+inputDate.dayOfMonth+"/"+inputDate.yearOfCentury+' ';
                        outputDate += inputDate.hourOfDay+':'+inputDate.minuteOfHour;
                        
                        return outputDate;
                    }
        </script>
    </body>
</html>


