<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>新增费用申请</title>
<jsp:include page="../common/scripts.jsp"></jsp:include>
     <link rel="stylesheet" href="<%=request.getContextPath()%>/res/layui/css/layui.css?HJ_v=<%=Math.random()%>"  media="all">
	<script src="<%=request.getContextPath()%>/res/layui/layui.js?HJ_v=<%=Math.random()%>" charset="utf-8"></script>
	 <script src="<%=request.getContextPath()%>/res/jquery-easyui/jquery.min.js" charset="utf-8"></script>
	 <script src="<%=request.getContextPath()%>/res/layui/format/dateformat.js" charset="utf-8"></script>
<style type="text/css">
.layui-disabled, .layui-disabled:hover {
	     color: black !important; 
	    cursor: not-allowed !important;
	}
</style>
  </head>
  <body>
 	<input type="hidden" name="bc_id" id="bc_id" value="${mpaList.bc_id}"/>
	<input type="hidden"  id="taskName" value="${taskName}"/>
	<input type="hidden"  id="taskid" value="${taskid}"/>
	
 	<form class="layui-form" action="" id="form1" style="width: 60%;margin:0px auto;">
		<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;text-align: center">
  			<legend>转正申请单</legend>
		</fieldset>
		<div class="layui-form-item">
			<div class="layui-row">
				<div class="layui-col-xs6">
					<label class="layui-form-label">姓名</label>
					<div class="layui-input-block">
						<input type="text" name="username" id="username" lay-verify="title" readonly="readonly"
							autocomplete="off" class="layui-input" value="${mpaList.username}">
					</div>
				</div>
				<div class="layui-col-xs6">
					<label class="layui-form-label">职位</label>
					<div class="layui-input-block">
						<input type="text" name="role_name" id="role_name" lay-verify="title" readonly="readonly"
							autocomplete="off" class="layui-input" value="${mpaList.role_name}">
					</div>
				</div>
			</div>
		</div>
		
		<div class="layui-form-item">
			<div class="layui-row">
				<div class="layui-col-xs6">
					<label class="layui-form-label">试用期</label>
						<div class="layui-input-block">
							<input type="text" name="incorporation_date" id="incorporation_date" lay-verify="title" readonly="readonly"
								autocomplete="off" class="layui-input" value="${mpaList.incorporation_date}">
					</div>
				</div>
				<div class="layui-col-xs6">
					<label class="layui-form-label">至</label>
						<div class="layui-input-block">
							<input type="text" name="close_time" id="close_time" lay-verify="title" readonly="readonly"
								autocomplete="off" class="layui-input" value="${mpaList.close_time}">
					</div>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-row">
				<div class="layui-col-xs6">
					<label class="layui-form-label">部门</label>
					<div class="layui-input-block">
						<input type="tel" id="bc_department" name="bc_department" class="layui-input" value="${mpaList.bc_department}">
					</div>
				</div>
				<div class="layui-col-xs6">
					<label class="layui-form-label">试用待遇</label>
					<div class="layui-input-block">
						<input type="tel" id="on_trial" name="on_trial" value="${mpaList.on_trial}" readonly="readonly"
								autocomplete="off" class="layui-input">
					</div>
				</div>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">自我评价</label>
			<div class="layui-input-block">
				<textarea name="personal" id="personal"
					 placeholder="请输入自我评价" class="layui-textarea">${mpaList.bc_personal}</textarea>
			</div>
		</div>
		<!-- 审批意见开始 -->
		<div>
		  ${startForm }
		</div>
		<!-- 审批意见结束 -->
	</form> 
	<jsp:include   page="../../view/checkBox/process-flow.jsp"/>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;border-color:#BEBEBE;">
  <legend style="font-size: 14px">审核记录</legend>
  </fieldset>
<div class="layui-row">
	<table class="layui-table" lay-size="sm" id="demo"></table>
</div>
</body>
<div id="centers" class="easyui-window"
			data-options="region:'center',title:'请选择值'" closed="true"
			style="height: 500px; width: 800px"></div>
  <script>
	 layui.use(['table','laypage','layer','laydate','jquery'], function(){
		 var table = layui.table
		   ,$ = layui.jquery
		   ,laypage = layui.laypage
		   ,layer = layui.layer
		   ,laydate = layui.laydate;
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
	  var form = layui.form;
	  $("#costapp_application").val("${costapp_application}");
	  var taskName = $("#taskName").val();  //获取节点名称
	  if(taskName != "申请人"){
		  $("#costapp_application").prop("disabled","disabled");
		  $("#costapp_appitem").prop("readonly","readonly");
	  }
	  form.render('select');
	  //各种基于事件的操作，下面会有进一步介绍
	  
	});
	 
	//打开公司子页面
	 function openCompany(){
		 layer.open({
			  type: 2,
			  title:"部门选择框",
			  area: ['50%','50%'],
			  fixed: false, //不固定
			  maxmin: true,
			  content: 'orgCheck.do',
			  success:function(layero,index){
			
			  },end:function(data){
				
			  }
			});
	 }
	//子页面传值事件
	 function setCompany(data){
		var company_name= data.company_name===undefined?"":data.company_name;
		var center_name=  data.center_name===undefined?"":data.center_name;
		var department_name=  data.department_name===undefined?"":data.department_name;
		$("#costapp_company").val(company_name+center_name+department_name);
	  }
	
	  //监控金额控件输入,格式为千分位
	  function moneyFormat(obj){
	    //去除前后空格
	    var num = $.trim($(obj).val());
	    //去除千分位符号，恢复为原始数字
	    num = num.replace(/,/g,'');
	    //如果为数值或负数开头则格式化为千分位
	    if(isNaN(num)&&num!="-"){
	      $(obj).val("");
	    }else{
	      var source = String(num).split(".");//按小数点分成2部分
	      source[0] = source[0].replace(new RegExp('(\\d)(?=(\\d{3})+$)','ig'),"$1,");//只将整数部分进行都好分割
	      //判断是否负数，如果是的话就标红
	      if(Number(num) < 0){
	        $(obj).css("color","red"); 
	      }else{
	        $(obj).css("color",""); 
	      }
	      $(obj).val(source.join("."));
	    }
	  //真正保存到数据库的金额
	    $(obj).next().val(num);
	    $("#costapp_amountCapital").val(digitUppercase(num));
	  }
	  
	 
	//金额转换大小写

	  var digitUppercase = function(n) {  
		var fraction = ['角', '分'];  
		var digit = [  
			'零', '壹', '贰', '叁', '肆',  
			'伍', '陆', '柒', '捌', '玖'  
		];  
		var unit = [  
			['元', '万', '亿'],  
			['', '拾', '佰', '仟']  
		];  
		var head = n < 0 ? '欠' : '';  
		n = Math.abs(n);  
		var s = '';  
		for (var i = 0; i < fraction.length; i++) {  
			s += (digit[Math.floor(n * 10 * Math.pow(10, i)) % 10] + fraction[i]).replace(/零./, '');  
		}  
		s = s || '整';  
		n = Math.floor(n);  
		for (var i = 0; i < unit[0].length && n > 0; i++) {  
			var p = '';  
			for (var j = 0; j < unit[1].length && n > 0; j++) {  
				p = digit[n % 10] + unit[1][j] + p;  
				n = Math.floor(n / 10);  
			}  
			s = p.replace(/(零.)*零$/, '').replace(/^$/, '零') + unit[0][i] + s;  
		}  
		return head + s.replace(/(零.)*零元/, '元')  
			.replace(/(零.)+/g, '零')  
			.replace(/^整$/, '零元整');  
	    }; 
	    
	    
		function leave_pass(sign) {
			
			var userid = $("#userid").val();//获取审核人
			var taskid = $("#taskid").val();  //获取任务id
			var taskName = $("#taskName").val();  //获取节点名称
			var reasons = $("#reasons").val(); //获取审核意见
			var costapp_id = $("#costapp_id").val();
			var role_name = $("#role_name").val();
			var personal = $("#personal").val();
			var bc_id = $("#bc_id").val();
			
			if(personal==""){
				 layer.msg("自我评价不能为空!", {icon:6, time: 1500,shade:[0.3]});
				 return;
			}
			
			if(reasons==""){
				 layer.msg("审核意见不能为空!", {icon:6, time: 1500,shade:[0.3]});
				 return;
			}
			
			if(sign == "true"){
				if(taskName == "董事长" || taskName == "总裁"){
				   
				}else{
					if(userid =='' ||userid==undefined){
						   layer.msg("审核人不能为空!", {icon:6, time: 1500,shade:[0.3]});
						   return;
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
						url : "become_pass.do",
						type : 'POST',
						data : {'userid':userid,
							    'taskid':taskid,
							    'reasons':reasons,
							    'costapp_id':costapp_id,
							    'sign':sign,
							    'role_name':role_name,
							    'personal':personal,
							    'bc_id':bc_id
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
    	$("#costapp_amountText").keyup();//大写金额赋值
  </script>
</html>
