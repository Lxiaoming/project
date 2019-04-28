<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta content="text/html; charset=utf-8">
<!-- TemplateBeginEditable name="doctitle" -->
<title>无标题文档</title>


<link
	href="<%=request.getContextPath()%>/res/jquery-easyui/themes/default/easyui.css"
	rel="stylesheet" type="text/css" />
<link href="<%=request.getContextPath()%>/res/admin/css/icon.css"
	rel="stylesheet" type="text/css" />
<script
	src="<%=request.getContextPath()%>/res/jquery-easyui/jquery.min.js"
	type="text/javascript"></script> 
<script
	src="<%=request.getContextPath()%>/res/jquery-easyui/jquery.easyui.min.js"
	type="text/javascript"></script>

<style type="text/css">
.inputab{
		width: 99%;
		border:0;
		outline:none;
		font-size: 15px;
}
.texta{
 
   resize:none;
   font-size: 15px;
   font-family: sans-serif;
   color:black;
   width:99%; 
     outline: none;
    border: 0px solid #f60;
}
   
</style>

</head>

<body>
   <div id="header">
		<div id="main" style="margin:0 auto;">
	  <div>
			<input type="hidden" id="taskid" value="${taskid}"> 
			<input type="hidden" id="taskName" value="${taskName}">
	 </div>
		<div style="margin:0 auto; center;width: 951px;">
			<p align="center"><strong>开发票申请表 </strong></p>
			<form action="" id="jvform">
				<table   width="951" border="1" cellpadding="0" cellspacing="0">
				<tr  hidden="hidden">
				      <td><input type="text" name="manage_reqfunds_id" value="${manage_reqfunds_id}"></td>
				   
				  </tr>
				  <tr style="height: 40px;">
				      <td style="width:130px;text-align:center;color: red;" onclick="wen('orgCheck.do')">申请部门</td>
				    <td colspan="5">
				    <input type="text" class="inputab" value="${manage_department}" id="manage_department"  name="manage_department"></input>
				    </td>
				  </tr>
				 <tr style="height: 40px;">
				   <td style="width:130px;text-align:center;">甲方单位名称 </td>
				    <td colspan="5" valign="top">
				    <input class="inputab" style="padding-top:10px;" value="${manage_first_party}" type="text" name="manage_first_party">
				    </td>
				  </tr>
				    <tr style="height: 20px;">
				   <td style="width:130px;text-align:center;">开票内容</td>
				    <td colspan="5" valign="middle"><br>
					     <textarea name="manage_ticket_content" class="texta">${manage_ticket_content}</textarea>
				    </td>
				  </tr>
				<tr style="height: 35px;">
				  <td style="width:130px;text-align:center;">开票金额 </td>
				    <td colspan="2">
				    <input class="inputab" value="${manage_reqfunds_ticketAmount}"  type="text" name="manage_reqfunds_ticketAmount">
				    </td>
				      <td style="text-align:center;">甲方单位电话</td>
				    <td>
				    <input class="inputab" style="text-align: center;" value="${manage_telephone}" type="text" name="manage_telephone">
				    </td>
				  </tr>
			   <tr style="height: 30px;">
				    <td style="width:130px;text-align:center;">纳税人类别 </td>
					    <td colspan="2" valign="top"> 
					    <input type="radio" checked value="1"  name="manage_pay_taxes">一般纳税
					    <input type="radio" value="2" name="manage_pay_taxes">小规模纳税
					    </td>
				    <td style="text-align:center;">增值税类别</td>
					    <td colspan="2" valign="top">
					    <input type="radio" checked value="1" name="manage_vat_category">增值税专票
					    <input type="radio" value="2" name="manage_vat_category">增值税普票
					    </td>
				  </tr>
			 	    <tr style="height: 35px;">
				     <td style="width:130px;text-align:center;">统一社会信用代码</td>
				    <td colspan="5" valign="middle"><input class="inputab" value="${manage_credit_code}" name="manage_credit_code" type="text"></td>
				  </tr>
				  <tr>
				   <td style="width:130px;text-align:center;">公司地址 </td>
				    <td colspan="5" valign="top"><br>
				    <textarea class="texta" name="manage_company_address" >${manage_company_address}</textarea>
				    </td>
				  </tr>
				  <tr style="height: 35px;">
				     <td style="width:130px;text-align:center;">开户行</td>
				      <td width="302"
				      ><input class="inputab" name="manage_opening_bank" value="${manage_opening_bank}" type="text">
				      </td>
				     <td style="text-align:center;">银行账号</td>
				    <td colspan="2" valign="top">
				    <input class="inputab"  name="manage_bank_account" value="${manage_bank_account}" style="height:30px;" type="text">
				    </td>
				  </tr>
				 <tr style="height: 35px;">
				    <td width="130"><p align="center">备  注 </p></td>
				    <td colspan="5" valign="middle"><br>
				    <textarea name="manage_reqfunds_remark" class="texta">${manage_reqfunds_remark}</textarea></td>
				  </tr>
				   <tr style="height: 20px;">
				    <td width="130"><p align="center">审核意见</td>
				    <td colspan="5" valign="middle">
				    <textarea id="options" class="texta"></textarea></td>
				  </tr>
				</table>
			</form>
		
			</div>
			<p>
		   <div>
			<div class="selecuser" style="margin-left: 40%;">
			<c:if test="${taskName !='财务会计'}">
			   <select name="username" id="username" style="width:120px; height: 25px;">
				<c:forEach items="${userList}" var="user">
					<option class="usernames" value="${user.userid}">${user.username}</option>
				</c:forEach>
			</select>
			</c:if>
			<a class="easyui-linkbutton" href="javascript:;" id="btnSave"
				onclick="btnSaveEva(true)" style="width: 60px">同意</a>
				<a class="easyui-linkbutton" href="javascript:;" id="btnSave"
				onclick="btnSaveEva(false)" style="width: 60px">不同意</a> 
			</div>
	<div style="margin:20px 0;" ></div>
	
	          <div style="width:951px;height:200px;margin: 0 auto">
	          
	          <table id="history" class="easyui-datagrid" title="审批记录"
			style="width:951px;height:200px"
			data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				nowrap:false">
			<thead>
			<tr>
				<th data-options="field:'username',width:80">审核人</th>
				<th data-options="field:'MESSAGE_',width:300">审核意见</th>
				<th data-options="field:'START_TIME_',width:300," formatter="operate_formatter">审核时间</th>
				<th data-options="field:'center_name',width:100">中心</th>
				
			</tr>
			</thead>
		</table>
	          
	          </div>

			</div>
		</div>
		
		
			<div id="centers" class="easyui-window"
			data-options="region:'center',title:'请选择值'" closed="true"
			style="height: 500px; width: 800px"></div>
            </div>

<script type="text/javascript">

function operate_formatter(value, row, index) {
	return new Date(value.time).Format("yyyy-MM-dd hh:mm:ss");
}
   $(function(){
	   //加载历史记录
		var historys = ${history}.history;
		$('#history').datagrid('loadData', historys);
		
		var taxes=${manage_pay_taxes==undefined?1:manage_pay_taxes};
		$("input[name='manage_pay_taxes'][value="+taxes+"]").attr("checked",true);
		var category=${manage_vat_category==undefined?1:manage_vat_category};
		$("input[name='manage_vat_category'][value="+category+"]").attr("checked",true);
   });
   
   
     function btnSaveEva(obj){
    	 var taskid = $("#taskid").val();
         var taskName = $("#taskName").val();
         var options = $("#options").val();
         var username = $("#username").val();  
          if(options==null ||options ==''){
        	  $.messager.alert("提示","请填写意见！");
        	  return;
         }
          $.messager.progress({
				title : '提示',
				msg : '正在处理中，请稍候……',
				text : ''
			});
    	 $.ajax({
 			type : 'POST',
 			url : 'reqfundspass.do?'+$("#jvform").serialize(),
 			data : {
 				'taskid' : taskid,
				'taskName' : taskName,
				'sign':obj,
				'username':username,	
				'options':options,

 			},success : function(data) {
 				if (data.Success) {
					$.messager.progress('close');
					$.messager.alert("提示", data.Msg, "info",
							function() {
								location.href = "findTaskList.do";
							});
				} else {
					$.messager.progress('close');
					$.messager.alert("提示", data.Msg, "error");

				}
 			}
     })
     }
  
  //选取部门 信息
function wen(src) {
	var hrefs = "<iframe id='son' src='"
			+ src
			+ "' allowTransparency='true' style='border:0;width:99%;height:99%;padding-left:2px;' frameBorder='0'></iframe>";
	$("#centers").html(hrefs);
	$("#centers").window("open");
}
   //部门赋值
   function data(rowData) {
		var company_name = rowData.company_name === undefined ? ""
				: rowData.company_name;
		var center_name = rowData.center_name === undefined ? ""
				: rowData.center_name;
		var department_name = rowData.department_name === undefined ? ""
				: rowData.department_name;
		var group=company_name+"、"+center_name+"、"+department_name;
		$("#manage_department").val(group);
	

	}
   
   Date.prototype.Format = function (fmt) { //author: meizz 
	    var o = {
	      "M+": this.getMonth() + 1, //月份 
	      "d+": this.getDate(), //日 
	      "h+": this.getHours(), //小时 
	      "m+": this.getMinutes(), //分 
	      "s+": this.getSeconds(), //秒 
	      "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	      "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	      if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	  }
</script>
</body>
</html>
