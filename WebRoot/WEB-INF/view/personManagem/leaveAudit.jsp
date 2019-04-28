<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta charset="utf-8">
  <title></title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
     <link rel="stylesheet" href="<%=request.getContextPath()%>/res/layui/css/layui.css?HJ_v=<%=Math.random()%>"  media="all">
	<script src="<%=request.getContextPath()%>/res/layui/layui.js?HJ_v=<%=Math.random()%>" charset="utf-8"></script>
	 <script src="<%=request.getContextPath()%>/res/jquery-easyui/jquery.min.js" charset="utf-8"></script>
	 <script src="<%=request.getContextPath()%>/res/layui/format/dateformat.js" charset="utf-8"></script>
</head>
<body style="background-color:#FFD9EC;">
 
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
  <legend>请假单</legend>
</fieldset>
    <div class="layui-row" id="fromdiv">
<form class="layui-form" id="form1" action="">
  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">申请人</label>
      <div class="layui-input-inline">
	     <input type="hidden" name="taskid" id="taskid" value="${taskid}"class="layui-input">
	     <input type="hidden" name="taskName" id="taskName" value="${taskName}"class="layui-input">
        <input type="text" name="applicant" id="Applicant" value="${leave.applicant}"class="layui-input">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">岗位/职位</label>
      <div class="layui-input-inline">
        <input type="text" name="position" id="position"  value="${leave.position}" readonly="readonly" class="layui-input">
      </div>
    </div>
    <div class="layui-inline" style="display: none;">
      <label class="layui-form-label">部门</label>
      <div class="layui-input-inline">
        <input type="text" name="department" id="department" value="${leave.department}" class="layui-input">
      </div>
    </div>
  </div>
    <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">开始时间</label>
      <div class="layui-input-inline">
        <input type="text" name="start_time" value="${leave.start_time}" id="start_time" placeholder="yyyy-MM-dd HH:mm:ss" class="layui-input">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">结束时间</label>
      <div class="layui-input-inline">
        <input type="text" id="end_time" name="end_time" value="${leave.end_time}" placeholder="yyyy-MM-dd HH:mm:ss" class="layui-input">
      </div>
    </div>
  </div>
    <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">请假类型</label>
      <div class="layui-input-inline">
        <select name="leave_category" id="leave_category" >
	        <option  value="0">事假</option>
			<option  value="1">病假</option>
			<option  value="2">婚假</option>
			<option  value="3">产假</option>
			<option  value="4">丧假</option>
			<option  value="5">年假</option>
      </select>
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">共计天数</label>
      <div class="layui-input-inline">
        <input type="number" name="day_count" id="day_count" value="${leave.day_count}"  class="layui-input">
      </div>
    </div>
  </div>
  
 
  <div class="layui-form-item layui-form-text">
    <label class="layui-form-label">事由</label>
    <div class="layui-input-block">
      <textarea placeholder="请输入内容" name="reason" class="layui-textarea" style="width:513px;min-height: 50px">${leave.reason}</textarea>
    </div>
  </div>   
  <div class="layui-form-item">
        ${startForm}
    </div>
<div>
  
	<jsp:include page="../../view/checkBox/process-flow.jsp"/>
 
</div>
</form>
 </div>

<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;border-color:#BEBEBE;">
  <legend style="font-size: 14px">审核记录</legend>
  </fieldset>
<div class="layui-row">
	<table class="layui-table" lay-size="sm" id="demo"></table>
 
</div>
<script>
layui.use(['table','laypage','layer','laydate','jquery'], function(){
	  var table = layui.table
	   ,$ = layui.jquery
	   ,laypage = layui.laypage
	   ,layer = layui.layer
	   ,laydate = layui.laydate;
	  
	  //日期
	  laydate.render({
	    elem: '#start_time'
	    ,type: 'datetime'
	  });
	  laydate.render({
	    elem: '#end_time'
	    ,type: 'datetime'
	  });
	  

		//展示历史记录
		table.render({
			elem : '#demo'
			,height:300
			,cols : [[
			         {field:'',width:88,title:'步骤序号',align:'center',type:'numbers'}
			        ,{field:'name_',title:'步骤名称',width:150}
			        ,{field:'username',title:'相关人员',width:90,}
			        ,{field:'START_TIME_',title:'发生时间',width:160,templet:function(res){
			        	
			        return  dateFormat(res.START_TIME_.time, "yyyy-MM-dd HH:mm:ss");
			        	
			        }}
			        ,{field:'MESSAGE_',title:'审核意见',width:200,}
			        ,{field:'description_',title:'内容'}
			        ]]
		           ,data:${history}
		           ,even:true
           });
	});


	function leave_pass(sign) {
		
		var userid = $("#userid").val();//获取审核人
		var taskid = $("#taskid").val();  //获取任务id
		var taskName = $("#taskName").val();  //获取节点名称
		var reasons = $("#reasons").val(); //获取审核意见
		
		if(reasons==""){
			 layer.msg("审核意见不能为空!", {icon:6, time: 1500,shade:[0.3]})
			 return
		}
		if(sign !='false'){
			if(taskName!='人力资源中心'){
			   if(userid =='' ||userid==undefined){
				   layer.msg("审核人不能为空!", {icon:6, time: 1500,shade:[0.3]})
				   return
			   }
			}
		}
		 layer.confirm('确定提交吗？', {
      		  btn: ['确定', '取消'] //可以无限个按钮
      		 ,btn1: function(index, layero){
      			layer.msg('数据加载中', {
      			   icon: 16
      			  ,shade: 0.2
      			});
      			$.ajax({
					url : "leave_pass.do",
					type : 'POST',
					data : {'userid':userid,
						    'taskid':taskid,
						    'reasons':reasons,
						    'sign':sign,
						     },
					success : function(data) {
						if (data.Success) {
							 layer.msg(data.Msg, {icon:6, time: 1500,shade:[0.3]},function(){
								 location.href="findTaskList.do";
								});
						} else {
							 layer.msg(data.Msg, {icon:5, time: 1500,shade:[0.3]});
						}
					}
				});
      		 }
		 
		 });
		
	}

	   //打开审核人窗口
	  function openWindow(){
		  layer.open({
			  type: 2,
			  area: ['50%','50%'],	
			  maxmin: true,
			  content: 'userListCheck2.do',
			  success:function(layero,index){
				
			  },end:function(data){
				
			  }
			});
		  
	  }
	   

	  //选择审核人赋值
	 function setUserName(data){
		  $("#userid").val(data[0].id);
		  $("#getUsername").val(data[0].name);
		 
	 }
	
</script>

</body>
</html>