<!DOCTYPE html>
<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script src="js/jquery-1.10.2.js"></script>
<script src="js/jquery-migrate-1.2.1.js"></script>
<!-- auto select -->
<script type="text/javascript" src="js/input.js"></script>
<script type="text/javascript">
	$(function() {
		$("#loginName").changeTips({
			divTip : ".on_changes"
		});
	})
</script>
<link rel="stylesheet" type="text/css" href='css/input.css' />
<!-- auto select end-->
<!-- node map -->
<link rel="stylesheet" href="css/bootstrap.min.css" />
<link rel="stylesheet" href="css/jquery.jOrgChart.css" />
<link rel="stylesheet" href="css/custom.css" />
<link href="css/prettify.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script src="js/jquery.jOrgChart.js"></script>
<script src="js/nodemap.js"></script>
<!-- node map end-->
</head>
<body>
	<div style="display: none">Dubbo Monitor IP: ${message}</div>
	<div class="login">
		<div class="ln">
			<font color="blue">选择APP：</font> <input type="text" maxlength="128"
				name="loginName" id="loginName" placeholder="Application" /> <input
				type="button" value="查 询" onclick="createNodemap()" />
		</div>
		<ul class="on_changes">
			<li item="">请选择Application</li>
			<li item=""></li>
			<c:forEach items="${as}" var="ap">
				<li item="${ap}">${ap}</li>
			</c:forEach>
		</ul>
	</div>
	<div id="loading" class="loadin-center"></div>
	<div id="nodemap" style="display: none"></div>
	<div id="chart" class="orgChart"></div>
</body>
</html>
