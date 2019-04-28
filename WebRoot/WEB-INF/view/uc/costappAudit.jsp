<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>首页</title>
  <meta charset="utf-8">
  <title></title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
     <link rel="stylesheet" href="<%=request.getContextPath()%>/res/layui/css/layui.css?HJ_v=<%=Math.random()%>"  media="all">
	<script src="<%=request.getContextPath()%>/res/layui/layui.js?HJ_v=<%=Math.random()%>" charset="utf-8"></script>
	<script src="${CASD_PATH}/res/layui/format/dateformat.js?HJ_v=<%=Math.random()%>" charset="utf-8"></script>
	<script src="<%=request.getContextPath()%>/res/layui/jquery.min.js?HJ_v=<%=Math.random()%>" charset="utf-8"></script>
<style type="text/css">
<style type="text/css">
	.layui-form-label {
    float: left;
    display: block;
    padding: 9px 15px;
    width: 10%;
    font-weight: 400;
    line-height: 20px;
    text-align: left;
    }
    .layui-form-item .layui-input-inline {
    float: left;
    width: 35%;
    margin-right: 10px;
}
</style>
  </head>
  <body>
 	<form class="layui-form" action=""  id="form1"style="width: 60%;margin:0px auto;">
		<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;text-align: center">
  			<legend>费用申请单</legend>
		</fieldset>
		<div class="layui-form-item">
			<label class="layui-form-label">申请人</label>
			<div class="layui-input-inline">
				<input type="text" name="applicantName" id="applicantName" lay-verify="title" readonly="readonly"
					autocomplete="off" class="layui-input" value="${sessionScope.loginUser.username}">
			</div>
			<label class="layui-form-label">申请时间</label>
			<div class="layui-input-inline">
				<input type="text" name="ApplicationDate" id="ApplicationDate" lay-verify="title" readonly="readonly"
					autocomplete="off" class="layui-input">
			</div>
			
		</div>
		
		<div class="layui-form-item">
			<label class="layui-form-label"><a style="color: red;" onclick="openCompany()">公司/部门</a></label>
				<div class="layui-input-inline">
					<input type="text" name="costapp_company" id="costapp_company" lay-verify="title"
						autocomplete="off" class="layui-input" value="${data.costapp_company}">
			</div>
			<label class="layui-form-label">申请类型</label>
			<div class="layui-input-inline">
				<select name="costapp_application" id="costapp_application">
					<option value=""></option>
					<option value="借支申请">借支申请</option>
					<option value="费用审批">费用审批</option>
					<option value="报销申请">报销申请</option>
				</select>
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">费用金额</label>
			<div class="layui-input-inline">
				<input type="tel" name="costapp_amountText" lay-verify="required|phone"
						autocomplete="off" class="layui-input" value="${data.costapp_amount}" onkeyup="moneyFormat(this)">
					<input type="hidden" name="costapp_amount" id="costapp_amount" lay-verify="required|phone"
						autocomplete="off" class="layui-input" value="${data.costapp_amount}">
			</div>
			
			<label class="layui-form-label">费用金额(大写)</label>
			<div class="layui-input-inline">
				<input type="tel" id="costapp_amountCapital" lay-verify="required|phone" readonly="readonly"
						autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">申请事项</label>
			<div class="layui-input-inline" style="width: 84%;">
				<textarea name="costapp_appitem" id="costapp_appitem"
					 placeholder="请输入申请事项" class="layui-textarea">${data.costapp_appitem}</textarea>
			</div>
		</div>
		<div>
		<input type="hidden" name="costapp_id" id="costapp_id" value="${data.costapp_id}">
		${startForm}
		</div>
		<%-- <input type="hidden" name="costapp_id" id="costapp_id" value="${data.costapp_id}">
		<button type="button" class="layui-btn" onclick="clearForm()">返回</button> --%>
	</form> 

</body>

	
  <script>
  layui.use(['form','layer','layedit','laydate'], function(){
	  var form = layui.form
	  ,layer = layui.layer
	  ,layedit = layui.layedit
	  ,laydate = layui.laydate;
	  
	  
	  //各种基于事件的操作，下面会有进一步介绍
	});
	 
	//$("#ApplicationDate").val(formatDate(new Date()));//申请日期赋值
	
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
	    
	    
	   function  save_Leave(){

		      $.ajax({
				type : 'POST',
				url : 'startCostapp.do?'+$("#form1").serialize()+"&userid="+$("#userid").val(),
				data :{},
				success : function(data) {
					if (data.Success) {
						
	                   
					} else {
						
					}
					
			    }
	        
		   })
	  
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
</html>
