<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
						value="" /></td>
						
					<td class="form-label" style="width:110px;">项目：</td>
					<td style="width:150px"><input type="text"
						value="" readonly="readonly"
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
						
					<td  class="form-label" style="width:110px;display: none">付款时间：</td>
					<td style="width:150px;display: none"><input type="text"
						value="${head.finance_settlepay_paytime}"
						name="finance_settlepay_paytime" id="finance_settlepay_paytime" class="mini-textbox" readonly="readonly" /></td>	
				</tr>
				
			</table>
			</form>
		</div>
	</fieldset>
	
	<br />
	<fieldset id="fd1" style="width:880px;">
		<legend>
			<span>采购单明细：</span>
		</legend>
		<div class="fieldset-body">
		<table id="pur" class="easyui-datagrid" title="采购单"
		style="width:900px;height:450px"
		data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				toolbar: '#purtb',onDblClickCell: onDblClickCell,showFooter:true,
				">
			<thead>
				<tr>
					<th data-options="field:'finance_settlepay_pur_id',width:80,align:'right',hidden:true">id</th>
					<th data-options="field:'finance_settlepay_pur_parentid',width:80,align:'right',hidden:true">付款单号</th>
					
					<th data-options="field:'finance_settlepay_pur_purchaseid',width:80,align:'right'">采购单号</th>
					
					<!-- <th data-options="field:'finance_settlepay_pur_arrivedid',width:80,align:'right'">到货单号</th> -->
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
	</fieldset>
	
	<div id="purtb" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="appendpur()">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cut',plain:true" onclick="removeit('pur')">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="view()">查看</a>
	</div>
	
	<div id="win" class="easyui-window"
		data-options="region:'center',title:'请选择值'" closed="true"
		style="height: 500px; width: 800px"></div>
	
	<div id="enterNum" class="easyui-window" title="填写付款金额" closed="true"
		style="width:350px;height:150px;padding:5px;">
		<br />
		<table class="form-table" border="0" cellpadding="5" cellspacing="2">
			<tr>
				<td class="form-label">本次付款：</td>
				<td><input name="nowPay"
					id="nowPay" class="mini-textbox" value="" /></td>
			</tr>

		</table>
		<br />
		<div style="text-align:center">
			<tr>
				<td style="align:center"><a class="easyui-linkbutton" 
				href="javascript:;"  id="confirm">确定</a>
					&nbsp;&nbsp;&nbsp;&nbsp; <a class="easyui-linkbutton"
					href="javascript:;" onclick="cancel()">返回</a></td>
			</tr>
		</div>
	</div>
	
	<br />
	<br />
	
	<div style="text-align:center">
		<tr>
		<td >
		<a class="easyui-linkbutton"  
		href="javascript:;" onclick="btnSave()">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
		<a class="easyui-linkbutton" href="javascript:;" onclick="btnBack()">返回</a>
		</td>
		</tr>
	</div>
	
	<script type="text/javascript">
	
	var editIndex = undefined;
	//合计
	var total=0;
	var projectId=${projectId};
	function getNowFormatDate() {
        var date = new Date();
        var seperator1 = "-";
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var currentdate = year + seperator1 + month + seperator1 + strDate;
        return currentdate;
    }
	
	$(function(){
		//数据初始化
		$("#construct_project_name").val('${projectName}');
		
		$("#finance_settlepay_supplier").val('${supplierName}');
		
		$("#finance_settlepay_paytime").val(getNowFormatDate());

		if(${rows}.settlePurs!=undefined||${rows}.settlePays!=undefined){
			var settlePurs = ${rows}.settlePurs; 
			$('#pur').datagrid('loadData',settlePurs);
			var settlePays = ${rows}.settlePays; 
			$('#pay').datagrid('loadData',settlePays);
		}
		
		$.extend($.fn.datagrid.methods, {
			editCell: function(jq,param){
				return jq.each(function(){
					var opts = $(this).datagrid('options');
					var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
					for(var i=0; i<fields.length; i++){
						var col = $(this).datagrid('getColumnOption', fields[i]);
						col.editor1 = col.editor;
						if (fields[i] != param.field){
							col.editor = null;
						}
					}
					$(this).datagrid('beginEdit', param.index);
					for(var i=0; i<fields.length; i++){
						var col = $(this).datagrid('getColumnOption', fields[i]);
						col.editor = col.editor1;
					}
				});
			}
		});
	});
		
	
	//返回
	function btnBack(){
		location.href = "settlePayList.do?construct_project_id="+projectId;
	}
	
	
	//提交审核
	function auditClick() {
		if (confirm("你确定提交审核吗？")) {
			
			var selectRole = [];
			selectRole.push(userName);
			for (var i = 0; i < userList.length; i++) {
				var options = $("#" + i + " option:selected");
				selectRole.push(options.val());
			}
			$('#pay').datagrid('acceptChanges');
			var payRows = $('#pay').datagrid('getRows');
			$('#pur').datagrid('acceptChanges');
			var purRows = $('#pur').datagrid('getRows');
			
			$.post("submitSettle.do?payRows="+encodeURI(JSON.stringify(payRows))+"&purRows="
					+encodeURI(JSON.stringify(purRows))+"&selectRole="
					+encodeURI(JSON.stringify(selectRole)),$("#head").serialize(),function(data) {
				 if (data == "") {
						alert("保存成功");
						location.reload(true);
				} else {
						alert("保存失败");
				}
			}); 
		} else {
			return false;
		}
	};
	
		
		function endEditingPay(){
			if (editIndex == undefined){return true}
			if ($("#pay").datagrid('validateRow', editIndex)){
				$("#pay").datagrid('endEdit', editIndex);
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		}
		
		function endEditingPur(){
			if (editIndex == undefined){return true}
			if ($('#pur').datagrid('validateRow', editIndex)){
				$('#pur').datagrid('endEdit', editIndex);
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		}
		
		
		
		
		function appendpur(){
				$('#pur').datagrid('appendRow',{status:'P'});
				editIndex = $('#pur').datagrid('getRows').length-1;
				
			
				$('#pur').datagrid('selectRow', editIndex)
						.datagrid('beginEdit', editIndex);
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
				
				location.href ="purchaseNew.do?construct_purchase_id="+rows[0].finance_settlepay_pur_arrivedid;
		}
		
		
		
		
		//删除行
		function removeit(object) {
			var row = $('#pur').datagrid('getSelected');
			if (row) {
				var rowIndex = $('#pur').datagrid('getRowIndex', row);
				$('#pur').datagrid('deleteRow', rowIndex);
				
				$.post("delePur.do",{'finance_settlepay_pur_id':row.finance_settlepay_pur_id,
				                   	 'purchaseid':row.finance_settlepay_pur_purchaseid,
					                  }
						,function(data) {
					 if (data == "") {
							alert("删除成功");
							
						} else {
							alert("删除失败");
						}
				}); 
				
				
				//计算赋值
				$('#pur').datagrid('acceptChanges');
				var rows = $('#pur').datagrid('getRows');
				total=0;
				for (var int = 0; int < rows.length; int++) {
					total=parseInt(rows[int].finance_settlepay_pur_shouldpay)+parseInt(total);
				}
				var retVal = JSON.parse("{}");
				var data = JSON.parse("{}");
				var totalList = [];
				retVal.finance_settlepay_pur_shouldpay=total;
				retVal.finance_settlepay_pur_purchaseid='合计';
				totalList.push(retVal);
				data.footer=totalList;
				data.rows=rows;
				//采购表格
				$('#pur').datagrid('loadData', data);
				//单头
				$("#finance_settlepay_owe").val(total);
				
			} else {
				$.messager.alert("提示", "请选择删除行!");

			}
		}
		
		//采购单明细双击事件
		function onDblClickCell(index, field, value) {
			var hrefs = "<iframe id='son' src='purSelect.do?index="
					+ index+ "' allowTransparency='true' style='border:0;width:99%;height:99%;padding-left:2px;' frameBorder='0'></iframe>";
			$("#win").html(hrefs);
			$("#win").window("open");
		}
		
		

		
		//子窗口调用
		function purData(data,index){
		 	var row = $('#pur').datagrid("selectRow", index).datagrid("getSelected");
		
			row.finance_settlepay_pur_supplier = data.construct_purchase_supplier;
			row.finance_settlepay_pur_purchaseid = data.construct_purchase_id;
			row.finance_settlepay_pur_shouldpay = data.arrived_money;
			row.finance_settlepay_pur_projectid=data.construct_project_id;
			row.construct_project_name=data.construct_project_name;
			row.finance_settlepay_pur_arrivedid=data.construct_purchase_arrived_id;
			$('#pur').datagrid('refreshRow', index);
			
			$('#pur').datagrid('acceptChanges');
			var rows = $('#pur').datagrid('getRows');
			total=0;
			for (var int = 0; int < rows.length; int++) {
				total=parseInt(rows[int].finance_settlepay_pur_shouldpay)+parseInt(total);
			}
			var retVal = JSON.parse("{}");
			var data = JSON.parse("{}");
			var totalList = [];
			retVal.finance_settlepay_pur_shouldpay=total;
			retVal.finance_settlepay_pur_purchaseid='合计';
			totalList.push(retVal);
			data.footer=totalList;
			data.rows=rows;
			//采购表格
			$('#pur').datagrid('loadData', data);
			//单头
			$("#finance_settlepay_owe").val(total);

		}
		
		
		//保存
		function btnSave(){
			
			var pays=${rows}.settlePays;
			if(pays!=undefined){
			$('#pur').datagrid('acceptChanges');
			var purRows = $('#pur').datagrid('getRows');
			
			 $.post("saveSettlePay.do?purRows="
					+encodeURI(JSON.stringify(purRows)),$("#head").serialize(),function(data) {
				 if (data == "") {
						alert("保存成功");
						location.reload(true);
					} else {
						alert("保存失败");
					}
			}); 
			
			}else{
				$.messager.alert("提示", "已存在付款单据不能修改!");
			}
		}
		
	</script>
</body>
</html>