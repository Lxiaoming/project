<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    
	  <meta charset="utf-8">
	  <title>任务详情</title>
	  <meta name="renderer" content="webkit">
	  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	  <link rel="stylesheet" href="${CASD_PATH}/res/layui/css/layui.css"  media="all">

  </head>
  
<body>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
  <legend>任务流程详情</legend>
</fieldset>
<ul class="layui-timeline">
  <li class="layui-timeline-item">
    <i class="layui-icon layui-timeline-axis"></i>
    <c:forEach items="${workDetails.userlist}" var="user">
	    <div class="layui-timeline-content layui-text">
	      <h3 class="layui-timeline-title">8月18日&nbsp;&nbsp; 由&nbsp;甲方&nbsp;指派给&nbsp;乙方</h3>
	      <p>
	        layui 2.0 的一切准备工作似乎都已到位。发布之弦，一触即发。
	        <br>不枉近百个日日夜夜与之为伴。因小而大，因弱而强。
	        <br>无论它能走多远，抑或如何支撑？至少我曾倾注全心，无怨无悔 <i class="layui-icon"></i>
	      </p>
	    </div>
    </c:forEach>
  <li class="layui-timeline-item">
    <i class="layui-icon layui-timeline-axis"></i>
    <div class="layui-timeline-content layui-text">
      <div class="layui-timeline-title">结束</div>
    </div>
  </li>
</ul>  
 
          
<script src="${CASD_PATH}/res/layui/layui.js" charset="utf-8"></script>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
<script>
</script>

</body>
</html>
