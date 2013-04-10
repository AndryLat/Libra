<%-- 
    Document   : RegReport
    Created on : 01.03.2013, 17:04:06
    Author     : MorrDeck
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->

<html class="no-js">
    <!--<![endif]-->
    <head>
        <jsp:include page="resources.jsp" />
        <script language="javascript" type="text/javascript" src="../resources/js/jqPlot/jquery.min.js"></script>
        <script language="javascript" type="text/javascript" src="../resources/js/jqPlot/jquery.jqplot.min.js"></script>
        <link rel="stylesheet" type="text/css" href="../resources/js/jqPlot/jquery.jqplot.css" />
        <script type="text/javascript" src="../resources/js/jqPlot/plugins/jqplot.canvasTextRenderer.min.js"></script>
        <script type="text/javascript" src="../resources/js/jqPlot/plugins/jqplot.canvasAxisTickRenderer.min.js"></script>
        <script type="text/javascript" src="../resources/js/jqPlot/plugins/jqplot.categoryAxisRenderer.min.js"></script>
        <script type="text/javascript" src="../resources/js/jqPlot/plugins/jqplot.barRenderer.min.js"></script>
        <script type="text/javascript" src="../resources/js/jqPlot/plugins/jqplot.canvasTextRenderer.min.js"></script>
        <script type="text/javascript" src="../resources/js/jqPlot/plugins/jqplot.canvasAxisLabelRenderer.min.js"></script>

        <title>Libra: Отчет пришедших/зарегестрированных</title>
    </head>

    <body>
        <div class="navmenu">
            <jsp:include page="navbar.jsp" />
        </div>

        <div class="container-fluid">
            <div class="row-fluid">

                <div class="sidebar">
                    <jsp:include page="sidebar.jsp" /> 
                </div>

                <div class="span9" >
                    <center> <div id="chartdiv" style="height:500px;width:50%;" ></div></center>
                    <div class="alert alert-info">
                        <strong>Описание</strong><br> Данный отчет показывает соотношение людей, которые зарегистрировались на сайте, и тех, кто явился на собеседование.
                    </div>

                    <script language="javascript" type="text/javascript">
                    $(document).ready(function(){
                        var data = [${data}];
                        var plot1b = $.jqplot('chartdiv', [data], {
                            title: 'Отчет пришедших и зарегестрированных',
                            animate: true,
                            animateReplot: true,
                            seriesColors: ["green"],
                            series: [{renderer: $.jqplot.BarRenderer}],
                            axesDefaults: {
                                tickRenderer: $.jqplot.CanvasAxisTickRenderer,
                                tickOptions: {
                                    fontFamily: 'Georgia',
                                    fontSize: '10pt',
                                    angle: -30
                                }
                            },
                            axes: {
                                xaxis: {
                                    renderer: $.jqplot.CategoryAxisRenderer
                                }
                            }
                        });
                    });
                    </script>
                </div>
            </div>
        </div>
    </body>
</html>