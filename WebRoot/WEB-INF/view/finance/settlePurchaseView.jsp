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
	<fieldset id="fd1" style="width:880px;">
		<legend>
			<span>项目信息</span>
		</legend>
		<div class="fieldset-body">
			<table class="form-table" border="0" cellpadding="5" cellspacing="2">
				<tr>
					<td class="form-label" style="width:60px;display: none">项目编号：</td>
					<td style="width:150px;display: none"><input name="construct_project_id"
						id="construct_project_id" class="mini-textbox" value="${construct.construct_project_id}" /></td>
					<td class="form-label" style="width:110px;">项目名称：</td>
					<td style="width:150px"><input type="text" name="flow_name"
						id="flow_name" class="mini-textbox" value="${construct.construct_project_name}" /></td>
					<td class="form-label" style="width:110px;">工程地址：</td>
					<td style="width:150px"><input type="text"
						value="${construct.construct_project_addr}" name="flow_description"
						id="flow_description" class="mini-textbox" /></td>
					<td class="form-label" style="width:110px;">项目经理：</td>
					<td style="width:150px"><input type="text"
						value="${construct.construct_project_leader}" name="flow_description"
						id="flow_description" class="mini-textbox" /></td>
					</tr>
					<tr>	
					<td class="form-label" style="width:110px;">项目经理联系方式：</td>
					<td style="width:150px"><input type="text"
						value="${construct.construct_project_leaderTel}" name="flow_description"
						id="flow_description" class="mini-textbox" /></td>
				</tr>

			</table>
		</div>
	</fieldset>
	
	
	<fieldset id="fd1" style="width:880px;">
		<legend>
			<span>材料信息</span>
		</legend>
		<div class="fieldset-body">
			<table class="form-table" border="0" cellpadding="5" cellspacing="2">
				<tr>
				<td class="form-label" style="display: none">headId：</td>
					<td style="display: none"><input name="construct_purchase_id"
						id="construct_purchase_id" class="mini-textbox" value="${purchaseHead.construct_purchase_id}" /></td>
					<td class="form-label" style="width:110px">计划日期：</td>
					<td style="width:150px"><input name="construct_purchase_planDate"
						id="construct_purchase_planDate" class="easyui-datebox" value="${purchaseHead.construct_purchase_planDate}" /></td>
					<td class="form-label" style="width:110px;">希望送达时间：</td>
					<td style="width:150px"><input type="text" name="construct_purchase_arriveDate"
						id="construct_purchase_arriveDate" class="easyui-datebox" value="${purchaseHead.construct_purchase_arriveDate}" /></td>
					<td class="form-label" style="width:110px;">材料计划员：</td>
					<td style="width:150px"><input type="text"
						value="${purchaseHead.construct_purchase_planMan}" name="construct_purchase_planMan"
						id="construct_purchase_planMan" class="mini-textbox" /></td>
					
					</tr>
					<tr>
					<td class="form-label" style="width:60px;">材料复核员：</td>
					<td style="width:150px"><input type="text"
						value="${purchaseHead.construct_purchase_reviewer}" name="construct_purchase_reviewer"
						id="construct_purchase_reviewer" class="mini-textbox" /></td>	
					<td class="form-label" style="width:60px;">供应商：</td>
					<td style="width:150px"><input type="text"
						value="${purchaseHead.construct_purchase_supplier}" name="construct_purchase_supplier"
						id="construct_purchase_supplier" class="mini-textbox" /></td>
					<td class="form-label" style="width:60px;">供应商联系方式：</td>
					<td style="width:150px"><input type="text"
						value="${purchaseHead.construct_purchase_supplierTel}" name="construct_purchase_supplierTel"
						id="construct_purchase_supplierTel" class="mini-textbox" /></td>				
				</tr>

			</table>
		</div>
	</fieldset>
	
	
	<div style="margin:20px 0;"></div>

	<table id="materialListID" class="easyui-datagrid" title="材料清单"
		style="width:930px;height:auto"
		data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				toolbar: '#tb',onClickCell: onClickCell
				">
			<thead>
		 <thead>
			<tr>
				<th class="123" data-options="field:'construct_purchase_entryId',width:80,hidden:'hidden',editor:'text'">Item ID</th>
				<th
					data-options="field:'construct_purchase_material',width:80,align:'right'">材料名称</th>
				<th
					data-options="field:'construct_purchase_model',width:80,align:'right'">型号规格</th>
				<th
					data-options="field:'construct_purchase_unit',width:80,align:'right'">单位</th>
				<th
					data-options="field:'construct_purchase_quantities',width:80,align:'right'">合同工程量</th>
				<th
					data-options="field:'construct_purchase_approvalNum',width:80,align:'right'">累计审批量</th>		
				<th
					data-options="field:'construct_purchase_applyNum',width:80,align:'right',editor:{type:'text',options:{required:true}}">计划采购量</th>
				<th
					data-options="field:'construct_purchase_contractPrice',width:80,align:'right'">合同单价</th>	
				<th
					data-options="field:'construct_purchase_purchasePrice',width:80,align:'right',editor:{type:'text',options:{required:true}}">采购单价</th>	
				<th
					data-options="field:'construct_purchase_purchaseTotal',width:80,align:'right',editor:{type:'text',options:{required:true}}">采购小计</th>
				<th
					data-options="field:'construct_purchase_brand',width:80,align:'right',editor:{type:'text',options:{required:true}}">材料品牌</th>	
				<th
					data-options="field:'construct_purchase_remarks',width:80,align:'right',editor:{type:'text',options:{required:true}}">备注</th>	
						
							
			</tr>
		</thead>


	</table>



	<script type="text/javascript">
	var editIndex = undefined;
	
	$(function(){
		
		if(${rows}.rows!=undefined){
			var rows = ${rows}.rows; 
			$('#materialListID').datagrid('loadData',rows);
		}
		
		//单元格编辑
		$.extend($.fn.datagrid.methods, {
			editCell: function(jq,param){
				return jq.each(function(){
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
			
	
	
			
			//是否编辑结束
			function endEditing(){
				if (editIndex == undefined){return true}
				if ($('#materialListID').datagrid('validateRow', editIndex)){
					$('#materialListID').datagrid('endEdit', editIndex);
					editIndex = undefined;
					return true;
				} else {
					return false;
				}
			}
			
			
			function onClickCell(index, field){
				$('#materialListID').datagrid('selectRow', index);
				if (endEditing()) {
					$(this).datagrid('beginEdit', index);
				}
			}
			
			
			
			
					
			
			//撤销新增行
			function reject(){
				$('#materialListID').datagrid('rejectChanges');
				editIndex = undefined;
			}
			
		
			
			
			
			
		
			
	</script>
</body>
</html>