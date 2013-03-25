<%-- 
    Document   : StudentsActivityView
    Created on : 27.02.2013, 21:58:03
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
	<!--[if lt IE 9]><script language="javascript" type="text/javascript" src="excanvas.js"></script><![endif]-->
        <script language="javascript" type="text/javascript" src="resources/js/jqPlot/jquery.min.js"></script>
        <script language="javascript" type="text/javascript" src="resources/js/jqPlot/jquery.jqplot.min.js"></script>
        <script type="text/javascript" src="resources/js/jqPlot/plugins/jqplot.pieRenderer.min.js"></script>
        <script type="text/javascript" src="resources/js/jqPlot/plugins/jqplot.donutRenderer.min.js"></script>
        <link rel="stylesheet" type="text/css" href="resources/js/jqPlot/jquery.jqplot.css" />
        <script type="text/javascript" src="../src/plugins/jqplot.barRenderer.min.js"></script>
        <script type="text/javascript" src="../src/plugins/jqplot.categoryAxisRenderer.min.js"></script>
        <script type="text/javascript" src="../src/plugins/jqplot.pointLabels.min.js"></script>
	<title>Отчет посещаемости собеседований</title>
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
			
			<div class="span9">
			<center> <div id="chartdiv" style="height:500px;width:500px; alignment-adjust: middle " ></div></center>
        <script language="javascript" type="text/javascript">
            var data = [${data}];
            var plot1 = jQuery.jqplot ('chartdiv', [data],
            {
                title:'Отчет посещаемости собеседований',
                seriesDefaults: {
                    renderer: jQuery.jqplot.PieRenderer,
                    rendererOptions: {
                        seriesColors: [ "red", "blue"],
                        showDataLabels: true
                    }
                },
                legend: { show:true, location: 'e' }
            }
        );
            
        </script>
			</div>
		</div>
	</div>
</body>
</html>