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
	 <script src="<%=request.getContextPath()%>/res/layui/format/dateformat.js" charset="utf-8"></script>
</head>
<body style="background-color:#FFD9EC;">
<div class="layui-fluid">        
	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;border-color:#BEBEBE;">
	  <legend>合同信息</legend>
	</fieldset>
	 
	<form class="layui-form" action="" id="form1">
  <div class="layui-form-item">
    <input type="hidden"  id="taskid" value="${taskid}" class="layui-input">
    <input type="hidden"  id="taskName" value="${taskName}" class="layui-input">
  
  </div>
	   <div class="layui-form-item">
	    <div class="layui-inline">
	      <label class="layui-form-label">项目名称</label>
	      <div class="layui-input-inline">
	        <input type="hidden" name="manage_contractapprove_id" id="manage_contractapprove_id" value="${contractapprove.manage_contractapprove_id}"  class="layui-input">
	        <input type="hidden" name="manage_contractapprove_num" id="manage_contractapprove_num" value="${contractapprove.manage_contractapprove_num}" class="layui-input">
	        <input type="text" name="manage_contractapprove_name" id="manage_contractapprove_name" value="${contractapprove.manage_contractapprove_name}" class="layui-input">
	          <input type="hidden" name="manage_contractapprove_attachAddress" id="manage_contractapprove_attachAddress" value="${contractapprove.manage_contractapprove_attachAddress}"  class="layui-input">
	      </div>
	    </div>
	    <div class="layui-inline">
	      <label class="layui-form-label">所属公司</label>
			    <div class="layui-input-inline">
			   <select name="manage_contractapprove_company" id="manage_contractapprove_company">
					<option value="1">建设公司</option>
					<option value="2">科技公司</option>
					<option value="3">加盟合作</option>
					<option value="4">诚安教育</option>
					<option value="5">传诚管理</option>
			   </select>
		  </div>
	    </div>
	    <div class="layui-inline">
	      <label class="layui-form-label">项目地址</label>
	      <div class="layui-input-inline">
	        <input type="text" name="manage_contractapprove_address" id="manage_contractapprove_address" value="${contractapprove.manage_contractapprove_address}" class="layui-input">
	      </div>
	    </div>
	  </div>
	  
	  <div class="layui-form-item">
	    <div class="layui-inline">
	      <label class="layui-form-label">发包方(甲方)</label>
	      <div class="layui-input-inline">
	        <input type="text" name="manage_contractapprove_firstParty" id="manage_contractapprove_firstParty" value="${contractapprove.manage_contractapprove_firstParty}" class="layui-input">
	      </div>
	    </div>
	    <div class="layui-inline">
	      <label class="layui-form-label">合同金额(元)</label>
	      <div class="layui-input-inline">
	        <input type="text" name="manage_contractapprove_amount" id="manage_contractapprove_amount" value="${contractapprove.manage_contractapprove_amount}"class="layui-input">
	      </div>
	    </div>
	    <div class="layui-inline">
	      
	      <label class="layui-form-label"></label>
    <div class="layui-input-block">
      <input type="radio"  name="manage_contractapprove_taxIncluded" value="1" title="含税">
      <input type="radio"  name="manage_contractapprove_taxIncluded" value="2" title="不含税">

    </div>
	    </div>
	  </div>
	   <div class="layui-form-item">
	    <div class="layui-inline">
	      <label class="layui-form-label">乙方</label>
	      <div class="layui-input-inline">
	        <input type="text" name="manage_contractapprove_secondParty" id="manage_contractapprove_secondParty" value="${contractapprove.manage_contractapprove_secondParty}" class="layui-input">
	      </div>
	    </div>
	    <div class="layui-inline">
	      <label class="layui-form-label">开始时间</label>
	      <div class="layui-input-inline">
	        <input type="text" name="manage_contractapprove_startTime" id="manage_contractapprove_startTime" value="${contractapprove.manage_contractapprove_startTime}" placeholder="yyyy-MM-dd"class="layui-input">
	      </div>
	    </div>
	    <div class="layui-inline">
	      <label class="layui-form-label">结束时间</label>
	      <div class="layui-input-inline">
	        <input type="text" name="manage_contractapprove_endTime" id="manage_contractapprove_endTime" value="${contractapprove.manage_contractapprove_endTime}" placeholder="yyyy-MM-dd" class="layui-input">
	      </div>
	    </div>
	  </div>
	  
	    <div class="layui-form-item">
	    <div class="layui-inline">
	      <label class="layui-form-label">付款比例</label>
	      <div class="layui-input-inline">
	        <input type="text" id="manage_contractapprove_payment" name="manage_contractapprove_payment" value="${contractapprove.manage_contractapprove_payment}" autocomplete="off" class="layui-input">
	      </div>
	    </div>
	       <div class="layui-inline">
	       <input type="radio"  name="category" value="1" title="施工合同">
           <input type="radio"  name="category" value="2" title="供应商合同">
           <input type="radio"  name="category" value="3" title="合作合同">
	  
	    </div>
	  </div>
	 <div class="layui-form-item layui-form-text" style="width:950px">
    <label class="layui-form-label">备注</label>
    <div class="layui-input-block">
      <textarea  name="manage_contractapprove_remark" id="manage_contractapprove_remark" class="layui-textarea">${contractapprove.manage_contractapprove_remark}</textarea>
    </div>
  </div>
	
	  <div class="layui-form-item">
        ${startForm}
 
	  </div>
	  
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
		
	</form>
</div> 
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;border-color:#BEBEBE;">
  <legend style="font-size: 14px">审核记录</legend>
  </fieldset>
<div class="layui-row">
	<table class="layui-table" lay-size="sm" id="demo"></table>
 
</div>

<script>

$(document).ready(function() {
	//加载审核历史数据
	 var obj=${contractapprove}
	 var category = ${contractapprove.category};
	 $("input[name='category'][value="+category+"]").prop("checked","true");
	if(JSON.stringify(obj) != "{}"){
		var  taxIncluded=obj.manage_contractapprove_taxIncluded;
		 if(taxIncluded==1){
		        $("input[name='manage_contractapprove_taxIncluded'][value=1]").prop("checked","true");
			}else{
		        $("input[name='manage_contractapprove_taxIncluded'][value=2]").prop("checked","true");

			}
			$("#manage_contractapprove_company").val(${contractapprove.manage_contractapprove_company});
	}
	
});


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
	    elem: '#manage_contractapprove_startTime'
	  });
	  laydate.render({
	    elem: '#manage_contractapprove_endTime'
	  });
	  
		//展示历史记录
		table.render({
			elem : '#demo',
			height:300,
			cols : [[
			         {field:'',width:88,title:'步骤序号',align:'center',type:'numbers'}
			        ,{field:'name_',title:'步骤名称',width:120}
			        ,{field:'username',title:'相关人员',width:90,}
			        ,{field:'START_TIME_',title:'发生时间',width:160,templet:function(res){
			        	
				        return  dateFormat(res.START_TIME_.time, "yyyy-MM-dd HH:mm:ss");
				        	
				        }}
			        ,{field:'MESSAGE_',title:'审核意见',width:200,}
			        ,{field:'description_',title:'内容',}
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
	    	    	    'oldPath':'',
	    	    	    'newPath':"contractFile/"+timestamp,
	    	    	  }
	    	    ,accept: 'file'  
	    	    ,bindAction: '#test9'
	    	    ,choose: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
	        	  obj.preview(function(index, file, result){
	        	          var paht="http://img.ca315189.com/casd/contractFile/"+timestamp+(file.name);
	        	    	  $("#manage_contractapprove_attachAddress").val(paht);
	        	    	  $("#newfileName").text(file.name);
	        	       
	        	    });
	    	    		
	    	  }
	    	 
	        
	       });
		//显示流程线
	     var category = $('input[name="category"]:checked').val(); 
		  if(category == 1){
			  $("#process1").css("display","block");
		  }else if(category == 2){
			  $("#process2").css("display","block");
		  }else if(category == 3){
			  $("#process3").css("display","block");
		  }
	  
	});
	//保存
	function contractapprovePass(type) {
		var userid=$("#userid").val();	
		var taskid = $("#taskid").val();		
		var taskName = $("#taskName").val(); //获取节点名称
		var reason = $("#reason").val();
		var category = $('input[name="category"]:checked').val(); 
		
		if (reason == "") {
			 layer.msg("请填写审核意见", {icon: 5, time: 1500,shade:[0.3]});	
			return false;

		}

		if (taskName  != '董事长' || taskName !='总裁') {
			if(userid==''){
				 layer.msg("请选择审核人", {icon: 5, time: 1500,shade:[0.3]});
				 return;
			}
		  }
		if(taskName =='申请人'){
			 if(category ==undefined){
				   layer.msg("请选择合同类型", {icon: 5, time: 1500,shade:[0.3]});
				   return;
			 }
		}
		var arr = $("#form1").serialize()+"&userid="+userid+"&category="+category+"&taskid="+taskid+"&reason="+reason+"&type="+type+"&taskName="+taskName;
		$.ajax({
			type : 'POST',
			url : 'contractapprovePass.do',
			data :arr,
			success : function(data) {
				if (data.Success) {
					   layer.msg(data.Msg, {icon: 5, time: 1500,shade:[0.3]},function(){
							location.href = "findTaskList.do";
					   });		
				} else {
					  layer.msg(data.Msg, {icon: 5, time: 1500,shade:[0.3]});
				}

			}

		});
		
	}

	function save_conApprove() {
		var userid=$("#userid").val();
		var category = $('input[name="category"]:checked').val(); 
	   if(category ==undefined){
		   layer.msg("请选择合同类型", {icon: 5, time: 1500,shade:[0.3]});
		   return;
	     }
		var arr = $("#form1").serialize()+"&userid="+userid+"&category="+category;
		$.ajax({
			url : 'save_conApprove.do',
			type : 'POST',
			data : arr,

			success : function(data) {
				
				if (data.Success) {
					/* $.messager.alert("提示", data.Msg, "info", function() {
						   location.href="findTaskList.do";
						});
				 */
				} else {
					//$.messager.alert("提示", data.Msg, "error");
				

				}
			
			}
			
		});
		
		
		/*  var start_time=$("#start_time").val();
		  var end_time=$("#end_time").val();
	
		
		if (start_time ==''||end_time =='') {
			 layer.msg("开始时间和结束时间不能为空", {icon: 5, time: 1500,shade:[0.3]});
			return false;
		}
	 //数据提
		var userid = $("#userid").val();
		var day_count = $("#day_count").val();
		var position = $("#position").val();

		if (day_count == '') {
			 layer.msg("请选择请假的天数", {icon: 5, time: 1500,shade:[0.3]});
				return ;
		}
		if (position == '') {
			 layer.msg("职位为空,请到人事部填写完整信息后方可请假!", {icon: 5, time: 1500,shade:[0.3]});
			return ;
		}
		if (userid == '') {
			 layer.msg("审核人不能为空", {icon: 5, time: 1500,shade:[0.3]});
			return;
		}
		
		 var arr = $("#form1").serialize() + "&userid=" + userid;
		 layer.confirm('确定提交吗？', {
      		  btn: ['确定', '取消'] //可以无限个按钮
      		 ,btn1: function(index, layero){
      			$.ajax({
					url : "save_Leave.do",
					type : 'POST',
					data : arr,
					success : function(data) {
						if (data.Success) {
							 layer.msg(data.Msg, {icon:6, time: 1500,shade:[0.3]},function(){
								location.href="leavePersonal.do";
								});
						} else {
							 layer.msg(data.Msg, {icon:5, time: 1500,shade:[0.3]});
						}
					}
				});
      		 }
		 
		 }); */
		
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