<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>首页</title>
<jsp:include page="../common/css.jsp"></jsp:include>
<jsp:include page="../common/scripts.jsp"></jsp:include>

<script
	src="<%=request.getContextPath()%>/res/admin/scripts/admin_grid.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/res/admin/scripts/ueditor/third-party/codemirror/codemirror.js"
	type="text/javascript"></script>

<style type="text/css">
fieldset {
	border: solid 1px #aaa;
}

.hideFieldset {
	border-left: 0;
	border-right: 0;
	border-bottom: 0;
}

.hideFieldset .fieldset-body {
	display: none;
}

<style type="text/css">
fieldset {
	border: solid 1px #aaa;
}

.hideFieldset {
	border-left: 0;
	border-right: 0;
	border-bottom: 0;
}

.hideFieldset .fieldset-body {
	display: none;
}

		#main{
				
				height:120px;
				border: 1px solid #000;
				margin: 10px auto;	
				float: left;			
			}
			#main .title{
				float: left;
				
			}
			
			#mes-tec{
				height: 120px;
				width:40px;
				border-right: 1px solid #000;
				text-align: center;
			
			}
			
			#person{
				height: 120px;
				width:50px;
				border-right: 1px solid #000;
			}
			
			#person div input{
				height: 120px;
				width:50px;
				border:0;
				padding-left:10px;
				line-height:120px;
			}
			
			
			#option{
				width:170px;
				border-right: 1px solid #000;
				height: 120px;
			}
			#option div input{
				width:170px;
				height: 120px;
				border:0;
				padding-left:10px;
				line-height:120px;
			}

</style>


</head>
<body>
	
	<fieldset id="fd1" style="width:900px;">
		<legend>
			<span>项目信息</span>
		</legend>
		<div class="fieldset-body">
			<form id="head" method="post" >
			<table class="form-table" border="0" cellpadding="5" cellspacing="2">
				<tr>
					<td class="form-label" style="width:60px">付款单号：</td>
					<td style="width:150px"><input
						name="finance_settlepay_id" id="finance_settlepay_id" readonly="readonly"
						class="mini-textbox" value="${head.finance_settlepay_id}" /></td> 
						
					<td class="form-label" style="width:110px;">供应商：</td>
					<td style="width:150px"><input type="text" name="finance_settlepay_supplier"
						id="finance_settlepay_supplier" class="mini-textbox" readonly="readonly"
						value="${head.finance_settlepay_supplier}" /></td>
						
					<td class="form-label" style="width:110px;">项目：</td>
					<td style="width:150px"><input type="text"
						value="${head.construct_project_name}" readonly="readonly"
						name="construct_project_name" id="construct_project_name" class="mini-textbox" /></td>
					<td class="form-label" style="display: none">项目id：</td>
					<td style="display: none"><input type="text"
						value="${head.finance_settlepay_projectid}" readonly="readonly"
						name="finance_settlepay_projectid" id="finance_settlepay_projectid" class="mini-textbox" /></td>
						
					<td class="form-label" style="width:110px;">欠款：</td>
					<td style="width:150px"><input type="text"
						value="${head.finance_settlepay_owe}"
						name="finance_settlepay_owe" id="finance_settlepay_owe" class="mini-textbox" readonly="readonly" /></td>
						
					<td  class="form-label" style="width:110px;display: none">已付：</td>
					<td style="width:150px;display: none"><input type="text"
						value="${head.finance_settlepay_payed}"
						name="finance_settlepay_purNum_payed" id="finance_settlepay_purNum_payed" class="mini-textbox" readonly="readonly" /></td>
				</tr>
				
			</table>
			</form>
		</div>
	</fieldset>
	
	
	
	
		<legend>
			<span>付款记录：</span>
		</legend>
		<div class="fieldset-body">
		
		<table id="pay" class="easyui-datagrid" title="记录表"
		style="width:900px;height:200px"
		data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				toolbar: '#paytb'
				">
			<thead>
				<tr>
					<th data-options="field:'finance_settlepay_payNum_id',width:80,align:'right'">付款编号</th>
					<th data-options="field:'finance_settlepay_purNum_date',width:100,align:'right',editor:'datebox'">时间</th>
					<th
						data-options="field:'finance_settlepay_purNum_payable',width:80,align:'right',editor:{type:'text',options:{required:true}} ">应付</th>
					<th
						data-options="field:'finance_settlepay_purNum_nowpay',width:80,align:'right',editor:{type:'text',options:{required:true}}">本次付款</th>
					<th
						data-options="field:'finance_settlepay_purNum_payed',width:80,align:'right'">累计已付款</th>
						<th
						data-options="field:'finance_settlepay_purNum_owe',width:80,align:'right'">未付款</th>	
				</tr>
	
			</thead>
		</table>

		</div>

	<br />

		<legend>
			<span>采购单明细：</span>
		</legend>
		<div class="fieldset-body">
		<table id="pur" class="easyui-datagrid" title="采购单"
		style="width:900px;height:350px"
		data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				toolbar: '#purtb',showFooter:true,
				">
			<thead>
				<tr>
					<th data-options="field:'finance_settlepay_pur_id',width:80,align:'right',hidden:true">id</th>
					<th data-options="field:'finance_settlepay_pur_parentid',width:80,align:'right',hidden:true">付款单号</th>
					
					<th data-options="field:'finance_settlepay_pur_purchaseid',width:80,align:'right'">采购单号</th>
					
					<th data-options="field:'finance_settlepay_pur_arrivedid',width:80,align:'right'">到货单号</th>
					<th
						data-options="field:'finance_settlepay_pur_supplier',width:80,align:'right'">供应商</th>
					<th
						data-options="field:'construct_project_name',width:250,align:'right'">项目名</th>
					<th
						data-options="field:'finance_settlepay_pur_projectid',width:250,align:'right',hidden:true">项目id</th>	
					<th
						data-options="field:'finance_settlepay_pur_shouldpay',width:80,align:'right'">应付</th>
				</tr>
	
			</thead>
		</table>
			
		</div>

	
	<div id="purtb" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="view()">查看</a>
	</div>
	
	<div id="audit"></div>

	
	<div class="fieldset-body">
	<div>
			<table class="form-table" border="0">
				<tr>
					<td hidden="hidden"><input type="text" value="${taskName}"id="taskName"></td>
					<td class="form-label" style="width:80px;">下一审核人：</td>
					<td ><select name="username"id="username" style="width:120px;">
								<option value="">请选择审批人</option>
								<c:forEach items="${auditor}" var="c">
									<option value="${c.userid}">${c.username}</option>
								</c:forEach>
					</select></td>
					<td class="form-label" style="width:80px;">审核意见：</td>
					<td style="width:150px"><input type="text"
						value="" name="options" 
						id="options" class="mini-textbox" /></td>
						
					<td style="width:150px"><a class="easyui-linkbutton"
					href="javascript:;" id="btnSave" onclick="btnAudit();">${bot}</a>
					&nbsp;&nbsp;&nbsp;&nbsp; 
					<c:if test="${both !=undefined}">
					<a class="easyui-linkbutton"
					href="javascript:;" id="btnBack" onclick="leave_reject()">${both}</a>
					</c:if>
						</td>
						
					</tr>

			</table>
			
	</div>
	<div style="margin:20px 0;">
		
	<table id="history" class="easyui-datagrid" title="审批记录"
			style="width:905px;height:150px"
			data-options="
				iconCls: 'icon-edit',
				singleSelect: true,">
			<thead>
				<tr>
				<th data-options="field:'name_',width:100">步骤名称</th>
				<th data-options="field:'username',width:80">相关人员</th>
				<th data-options="field:'MESSAGE_',width:300">审核意见</th>
				<th data-options="field:'TIME_',width:180" formatter="dateformat">审核时间</th>			
				<th data-options="field:'center_name',width:100">中心</th>	
				<th data-options="field:'department_name',width:200">部门</th>
			</tr>
			</thead>
		</table>
	</div>
	
		</div>

	
	
	
	<script type="text/javascript">
	$(function(){
		//数据初始化
		if(${rows}.settlePurs!=undefined||${rows}.settlePays!=undefined){
			var settlePurs = ${rows}.settlePurs; 
			$('#pur').datagrid('loadData',settlePurs);
			var settlePays = ${rows}.settlePays; 
			$('#pay').datagrid('loadData',settlePays);
			
			//加载审核记录
			var historys = ${history}.history;
			$('#history').datagrid('loadData', historys);
		}

		
	
		
	});
	
	Date.prototype.Format=function(fmt){
        var o={
            "M+":this.getMonth()+1,//月份
            "d+":this.getDate(),//日
            "H+":this.getHours(),//小时
            "m+":this.getMinutes(),//分
            "s+":this.getSeconds(),//秒
            "q+":Math.floor((this.getMonth()+3)/3),//季度
            "S+":this.getMilliseconds()//毫毛
        };
        if(/(y+)/.test(fmt)) fmt=fmt.replace(RegExp.$1,(this.getFullYear()+"").substr(4-RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    };
	
	 function dateformat(value, row, index) {
		if (value == undefined)
			return "";	
	  var  date=new  Date(value.time); 
      var time=date.Format("yyyy-MM-dd HH:mm:ss");
			return time;
            }
	  // 驳回功能
	   function  leave_reject(){
		  
			var options = $("#options").val();
			var taskid = ${taskid};
			if(options==''){
				$.messager.alert("提示","审核意见不能为空！");
				return false;
			}
			
		  $.messager.confirm('提示！', '你确定回退吗？', function(r) {
			if (r) {	
			$.ajax({
				type : 'POST',
				url : 'rejects_leave.do',
				data : {
					'taskid':taskid,
					'options':options,
					},success:function(data){
						if (data.Success) {
							$.messager.alert("提示", data.Msg,"info", function() {
										location.href="findTaskList.do";
									});
						} else {
							$.messager.alert("提示", data.Msg,
									"error");
						}

					}
			});}
		});
						
	   }
	
	
		
	             /*审核单据 */
              function  btnAudit(){
          		var rows=$('#pay').datagrid('getRows');
          		
    			var bizId=rows[rows.length-1].finance_settlepay_payNum_id; //获取业务键
    			
		 		var username = $("#username").val();//获取审核人

		 		var taskName = $("#taskName").val();  //获取节点名称
		 		
		 		var option = $("#options").val(); //获取审核意见
		 		
                 if(username==''|| username ==null){
                	 if(taskName !='财务中心'){
	             		$.messager.alert("提示", "请选择审核人!");
	             		return false;
                	 }
		 		}
                 if(option==''){
                	 if(taskName!='采购部提交'){
         				$.messager.alert("提示", "请填写审核意见");
         				return false;
         			}
 		 		}
		       
		         
							$.ajax({
								type : 'POST',
								url : 'Pum_pass.do',
								data : {'taskid':${taskid},
										'username' : username,
										'taskName' : taskName,
										'options' : option,
										'bizId' : bizId,
										
								},
								success : function(data) {
									if (data.Success) {
										$.messager.alert("提示", data.Msg+data.leva,"info", function() {
													location.href="findTaskList.do";
												});
								      	}else {
										$.messager.alert("提示", data.Msg,
												"error");
									}

										}
								
								
								
							});
						}
				

	//查看
	function view(){
			var rows = $("#pur").datagrid("getSelections");//获取表格数据
			if (rows.length == 0) {
				alert("请选择需要查看的行");
				return null;
			}else if(rows.length>1){
				alert("一次只能查看一行");
				return null;
			}
			location.href = "purchaseView.do?" + $.param({
				'construct_purchase_id' : rows[0].finance_settlepay_pur_purchaseid, 
			});
	}
		
		
	</script>
</body>
</html>