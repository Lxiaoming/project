<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/res/css/process-flow.css">
<script type="text/javascript">
	$(function() {
		//首页大事记
		$('.course_nr2 li').hover(function() {
			$(this).find('.shiji').slideDown(600);
		}, function() {
			$(this).find('.shiji').slideUp(400);
		});
	});
</script>

</head>
<body>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
  <legend>流程线</legend>
	<div class="clearfix course_nr">
		<c:forEach var="usertask" items="${activityList}">
			<ul class="course_nr2">
				<li>${usertask.name==taskName?"<span style='color:red'>".concat(taskName).concat("</span>"):usertask.name}
					<div class="shiji">
						<h1>${id}</h1>
						<p>${usertask.name}</p>
					</div>
				</li>
			</ul>
		</c:forEach>
	</div>
</fieldset>		
</body>
</html>