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
  <link rel="stylesheet" href="<%=request.getContextPath()%>/res/css/process-flow.css">
      <link rel="stylesheet" href="<%=request.getContextPath()%>/res/layui/css/layui.css?HJ_v=<%=Math.random()%>"  media="all">
	 <script src="<%=request.getContextPath()%>/res/layui/layui.js?HJ_v=<%=Math.random()%>" charset="utf-8"></script>
	 <script src="<%=request.getContextPath()%>/res/jquery-easyui/jquery.min.js" charset="utf-8"></script>
	 <script src="<%=request.getContextPath()%>/res/layui/layui.js" charset="utf-8"></script>
</head>
<body style="background-color:#FFD9EC;">
<div class="layui-fluid">   
   <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;border-color: #B0C4DE"">
  <legend>盖章信息</legend>
</fieldset>
 
<form class="layui-form" action="" id="form1">
 
  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">文件名称</label>
      <div class="layui-input-inline">
        <input type="hidden" name="own_seal_id" id="own_seal_id" value="${seal.own_seal_id}" class="layui-input">
        <input type="text" name="own_seal_fileName" id="own_seal_fileName" value="${seal.own_seal_fileName}"class="layui-input">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">预结算金额</label>
      <div class="layui-input-inline">
        <input type="text" name="own_seal_settle" id="own_seal_settle" value="${seal.own_seal_settle}"class="layui-input">
      </div>
    </div>
    
     <div class="layui-inline">
     <label class="layui-form-label">用章公司</label>
    <div class="layui-input-inline">
      <select name="own_seal_company" id="own_seal_company">
        <option value="12">诚安时代(1)</option>
        <option value="13">传诚管理(2)</option>
        <option value="14">诚安科技(3)</option>
        <option value="15">传承教育(5)</option>
        <option value="16">诚安建设(6)</option>
        <option value="17">分供方</option>
        <option value="18">诚安投资</option>
      </select>
    </div>
    <%--   <label class="layui-form-label">用章公司</label>
      <div class="layui-input-inline">
        <input type="text" name="own_seal_companyName" id="own_seal_companyName" value="${seal.company_name}"class="layui-input">
      </div>
    </div> --%>
  </div>


   <div class="layui-form-item">
    <label class="layui-form-label">用章类别</label>
    <div class="layui-input-block">
      <input type="checkbox" name="own_seal_chapCategory" value="1" lay-skin="primary" lay-filter="chapCategory" title="公章" checked="" >
      <input type="checkbox" name="own_seal_chapCategory" value="2" lay-skin="primary" lay-filter="chapCategory" title="业务章">
      <input type="checkbox" name="own_seal_chapCategory" value="3" lay-skin="primary" lay-filter="chapCategory" title="出图章" >
      <input type="checkbox" name="own_seal_chapCategory" value="4" lay-skin="primary" lay-filter="chapCategory" title="竣工章 ">
      <input type="checkbox" name="own_seal_chapCategory" value="5" lay-skin="primary" lay-filter="chapCategory" title="项目章">
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">主送单位</label>
    <div class="layui-input-block">
      <input type="text" name="own_seal_sender" id="own_seal_sender" value="${seal.own_seal_sender}" style="width:515px" class="layui-input">
      <input type="hidden" name="own_seal_filePath" id="own_seal_filePath" value="${seal.own_seal_filePath}"  class="layui-input">
    </div>
  </div>
  
   <div class="layui-form-item layui-form-text" >
    <label class="layui-form-label">盖章用途</label>
    <div class="layui-input-block">
      <textarea name="own_seal_remark" id="own_seal_remark" style="width:515px;min-height:50px" class="layui-textarea">${seal.own_seal_remark}</textarea>
    </div>
  </div>
  

  <div class="layui-form-item">
        ${startForm}
  </div>
</form>
</div> 
<!-- 流程线 -->
  
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
  <legend>流程线</legend>
	 <div>
		  <c:forEach var="userlist" varStatus="st" items="${activityList}">
			<div class="clearfix course_nr" id="process${st.index +1}" style="display:none;">
				<c:forEach var="usertask" items="${userlist}">
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
		  </c:forEach>
     </div> 
</fieldset>	


<div class="layui-fluid">   
<fieldset class="layui-elem-field layui-field-title" style="border-color: #B0C4DE">
  <legend>审核记录</legend>
</fieldset>

<div class="layui-row">
	<table class="layui-table" lay-size="sm" id="demo"></table>
</div>
</div>

<script>
	layui.use(['upload','table','form','layedit','layer','laydate','jquery'], function(){
	  var form = layui.form
	  ,table = layui.table
	  ,$ = layui.$ 
	  ,layer = layui.layer
	  ,layedit = layui.layedit
	  ,upload = layui.upload
	  ,laydate = layui.laydate
	  
	  //日期
	  laydate.render({
	    elem: '#start_time'
	  });
	  laydate.render({
	    elem: '#end_time'
	  });
	  
	  $("#process1").css("display","block");
	  // 监听事件,动态流程显示
	  form.on('checkbox(chapCategory)', function(data){
		  var	obj = document.getElementsByName("own_seal_chapCategory");
	      check_val = [];
		  for(k in obj){
		      if(obj[k].checked){
		          check_val.push(obj[k].value);
		      }
		    }
		  
		  if(check_val=='1'){
				$("#process1").css("display","block");
				$("#process2").css("display","none");
				$("#process3").css("display","none");
			}else if(check_val.indexOf('2') !=-1 || check_val.indexOf('3') !=-1 || check_val.indexOf('4') !=-1){
				if(check_val.indexOf('1') !=-1 || check_val.indexOf('5') !=-1){
					 return ;
				}else{
					$("#process1").css("display","none");
					$("#process2").css("display","block");
					$("#process3").css("display","none");
				}
			}else if(check_val=='5'){
				$("#process1").css("display","none");
				$("#process2").css("display","none");
				$("#process3").css("display","block");
			}else{
					return ;
			}
	  });
	  
		//展示历史记录
		table.render({
			elem : '#demo'
		    ,height:200
			,cols : [[
			         {field:'',width:88,title:'步骤序号',align:'center',type:'numbers'}
			        ,{field:'name_',title:'步骤名称',width:150}
			        ,{field:'username',title:'相关人员',width:90,}
			        ,{field:'START_TIME_',title:'发生时间',width:160,templet:'#createTime'}
			        ,{field:'MESSAGE_',title:'审核意见',width:200,}
			        ,{field:'description_',title:'内容'}
			        ]]
		           ,data:${history}
		           ,even:true
         });
         
		   //创建文件上传时间戳
	     var timestamp=new Date().getTime();
		//选完文件后不自动上传
	     upload.render({
    		elem: '#test8'
    		,url: 'uploadFile.do'
    		,auto: false
    		,data:{'timestamp':timestamp,
    				'oldPath':$("#own_seal_filePath").val(),
    				'newPath':"sealFile/"+timestamp,
    		}
    		,accept: 'file'  
    		,bindAction: '#test9'
    		,choose: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
    			obj.preview(function(index, file, result){
    				var paht="http://img.ca315189.com/casd/sealFile/"+timestamp+(file.name);
    				$("#own_seal_filePath").val(paht);
    				$("#newfileName").text(file.name); 
    			});			
    		}	        
    	});
	  
	});
	
	//保存
	function save_Seal() {
		var	obj = document.getElementsByName("own_seal_chapCategory");
		    check_val = [];
		    for(k in obj){
		        if(obj[k].checked)
		            check_val.push(obj[k].value);
		    }

		if(check_val==""){
			 layer.msg("请选择章类型!", {icon: 5, time: 1500,shade:[0.3]});	
			return false;
		}else{
			if(check_val=='1'){
				
			}else if(check_val.indexOf('2') !=-1 || check_val.indexOf('3') !=-1 || check_val.indexOf('4') !=-1){
				if(check_val.indexOf('1') !=-1 || check_val.indexOf('5') !=-1){
					 layer.msg("请选择章类型不对,无法启动流程!", {icon: 5, time: 1500,shade:[0.3]});	
					 return ;
				}
			}else if(check_val=='5'){
				
			}else{
				 layer.msg("请选择章类型不对,无法启动流程!", {icon: 5, time: 1500,shade:[0.3]});	
					return ;
			}
			
			
		}
		var userid=$('#userid').val();
		if(userid==""){
			 layer.msg("审核人不能为空!", {icon: 5, time: 1500,shade:[0.3]});
			return false;
		}
         var arr=$("#form1").serialize()+"&userid="+userid;
		  $.ajax({
			url : "save_seal.do",
			type : 'POST',
			data : arr,
			success : function(data) {
				if (data.Success) {
					 layer.msg(data.Msg, {icon: 6, time: 1500,shade:[0.3]},function(){
						 location.href = "sealList.do";
					 });	
						
				} else {
					 layer.msg(data.Msg, {icon: 5, time: 1500,shade:[0.3]})
				}

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
	  
	 $(function() {
			//首页大事记
			$('.course_nr2 li').hover(function() {
				$(this).find('.shiji').slideDown(600);
			}, function() {
				$(this).find('.shiji').slideUp(400);
			});
		});
	  
	  
</script>

</body>
</html>