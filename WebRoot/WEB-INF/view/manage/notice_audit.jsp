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
	<script src="${CASD_PATH}/res/layui/format/dateformat.js?HJ_v=<%=Math.random()%>" charset="utf-8"></script>
	<script src="<%=request.getContextPath()%>/res/layui/jquery.min.js?HJ_v=<%=Math.random()%>" charset="utf-8"></script>
    <script charset="utf-8" src="<%=request.getContextPath()%>/res/jquery-easyui/wangEditor/release/wangEditor.min.js"></script>
  <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
  <style type="text/css">
 .layui-table td, .layui-table th{
      font-size: 13px !important;
      color: #3a3a3a;
  }
  /*设置隔行颜色  */
   table tr:nth-child(2n){
   background-color:#FFF0F5;
   }
  .layui-input, .layui-select, .layui-textarea{
      height: 35px !important;
  }
   .layui-table-cell .layui-form-checkbox[lay-skin=primary]{
       top: 1px !important;
   }
   .layui-table-cell{
   padding:0 5px !important;
   }
  </style>
</head>
<body style="background-color: #eee;">

<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
  <legend></legend>
</fieldset>
 
<form class="layui-form" action="">
  <div class="layui-form-item">
    <div class="layui-input-block">
     <div id="div1"  style= "width:850px;margin:0 auto;background-color: #fff;padding:0 25px;">
      <input type="hidden" id="notice_id" value="${notice_id}">
       ${notice_content}
    </div>
    </div>
  </div>
    ${renderedTaskForm}
  <div class="layui-form-item">
    <div class="layui-input-block">
       <input type="hidden" id="taskid" value="${taskid}" class="layui-input">
       <input type="hidden" id="taskName" value="${taskName}" class="layui-input">
    </div>
  </div>
</form>
 <textarea id="text1" style="width:100%; height:200px;" hidden="hidden"></textarea>
<script type="text/javascript">


layui.use(['table','laypage','layer','laydate','jquery'], function(){
	 var table = layui.table
	   ,$ = layui.$ 
	   ,laypage = layui.laypage
	   ,layer = layui.layer
	   ,laydate = layui.laydate;
	 });
var E = window.wangEditor
var editor = new E('#div1')
var $text1 = $('#text1')

editor.customConfig.onchange = function (html) {
    // 监控变化，同步更新到 textarea
    $text1.val(html)
}
editor.create()
// 初始化 textarea 的值
$text1.val(editor.txt.html())


	function notice_pass(){
	  var  content=$text1.val();
	  var userid=$("#userid").val();
	  var taskName=$("#taskName").val();
	  var taskid=$("#taskid").val(); 
	
	if (taskName !='董事长') {
		if(userid=='' || userid == undefined){
			alert("请选择审核人"); 
			return false;
	
		  }	 
		}
		$.ajax({
			type : 'POST',
			url :'notice_pass.do',
			data :{
				   'userid':userid,
				   'taskid':${taskid},
				
			},
			success : function(data){
				if (data.Success) {
					alert(data.Msg);
						location.href = "findTaskList.do";
						
			      	}else {
					alert(data.Msg);
				      }
				
			}
		   	 
			});
	}

function updateNot(){
  var  content=$text1.val(); 
	$.ajax({
		type : 'POST',
		url :'updateNot.do',
		data :{
			   'content':content,
			   'taskid':${taskid},
		},success : function(data){
		
			if (data.Success) {
				alert(data.Msg);							
		      window.location.reload(true);
			} else {
				alert(data.Msg);
			}

		
	
		}}); 
	
}
//打开选择人窗口
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
	  $("#username").val(data[0].name);
	 
}


</script>
</body>

</html>