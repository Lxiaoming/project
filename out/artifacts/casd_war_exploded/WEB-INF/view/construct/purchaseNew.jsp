<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%@ taglib uri="PowerCheck" prefix="shop"%>
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
					<td style="width:150px;display: none"><input
						name="construct_project_id" id="construct_project_id"
						class="mini-textbox" value="${construct.construct_project_id}" /></td>
					<td class="form-label" style="width:110px;">项目名称：</td>
					<td style="width:150px"><input type="text" name="flow_name" readonly="readonly"
						id="flow_name" class="mini-textbox"
						value="${construct.construct_project_name}" /></td>
					<td class="form-label" style="width:110px;">工程地址：</td>
					<td style="width:150px"><input type="text" readonly="readonly"
						value="${construct.construct_project_addr}"
						name="flow_description" id="flow_description" class="mini-textbox" /></td>
					<td class="form-label" style="width:110px;">项目经理：</td>
					<td style="width:150px"><input type="text" readonly="readonly"
						value="${construct.construct_project_leader}"
						name="flow_description" id="flow_description" class="mini-textbox" /></td>
				</tr>
				<tr>
					<td class="form-label" style="width:110px;">项目经理联系方式：</td>
					<td style="width:150px"><input type="text" readonly="readonly"
						value="${construct.construct_project_leaderTel}"
						name="flow_description" id="flow_description" class="mini-textbox" /></td>
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
						id="construct_purchase_id" class="mini-textbox"
						value="${purchaseHead.construct_purchase_id}" /></td>
					<td class="form-label" style="width:110px">计划日期：</td>
					<td style="width:150px"><input
						name="construct_purchase_planDate"
						id="construct_purchase_planDate" class="easyui-datebox"
						value="${purchaseHead.construct_purchase_planDate}" /></td>
					<td class="form-label" style="width:110px;">希望送达时间：</td>
					<td style="width:150px"><input type="text"
						name="construct_purchase_arriveDate"
						id="construct_purchase_arriveDate" class="easyui-datebox"
						value="${purchaseHead.construct_purchase_arriveDate}" /></td>
					<td class="form-label" style="width:110px;"><a
							href="javascript:void(0)" onclick="wen('userListCheck.do?index=-1')">材料计划员(选择):</a>
						</td>
					<td style="width:150px"><input type="text"
						value="${purchaseHead.construct_purchase_planMan}" readonly="readonly"
						name="construct_purchase_planMan" id="construct_purchase_planMan"
						class="mini-textbox" /></td>

				</tr>
				<tr>
					<td class="form-label" style="width:60px;"><a
							href="javascript:void(0)" onclick="wen('userListCheck.do?index=-2')">复核员(选择):</a>
						</td>
					<td style="width:150px"><input type="text" readonly="readonly"
						value="${purchaseHead.construct_purchase_reviewer}"
						name="construct_purchase_reviewer"
						id="construct_purchase_reviewer" class="mini-textbox" /></td>
					<td class="form-label" style="width:60px;">供应商：</td>
					<td style="width:150px"><input type="text" readonly="readonly"
						value="${purchaseHead.construct_purchase_supplier}"
						name="construct_purchase_supplier"
						id="construct_purchase_supplier" class="mini-textbox" /></td>
					<td class="form-label" style="width:60px;">供应商联系方式：</td>
					<td style="width:150px"><input type="text" readonly="readonly"
						value="${purchaseHead.construct_purchase_supplierTel}"
						name="construct_purchase_supplierTel"
						id="construct_purchase_supplierTel" class="mini-textbox" /></td>
						
					<td class="form-label" style="display: none">状态：</td>
					<td style="display: none"><input type="text"
						value="${purchaseHead.construct_purchase_status}"
						name="construct_purchase_status"
						id="construct_purchase_status" class="mini-textbox" /></td>	
				</tr>
				
			</table>
		</div>
	</fieldset>


	<div style="margin:20px 0;"></div>
	<table>
	<tr>
		<td class="form-label" style="width:110px;"><a href="javascript:void(0)" onclick="wen('materialSeries.do')">材料类别(选择):</a></td>
		<td><input type="text"
		value="${purchaseHead.construct_purchase_materialSerName}" readonly="readonly"
		name="construct_purchase_materialSerName"
		id="construct_purchase_materialSerName" class="mini-textbox" /></td>	
		<td style="display: none"><input type="text"
		value="${purchaseHead.construct_purchase_materialSerId}"
		name="construct_purchase_materialSerId"
		id="construct_purchase_materialSerId" class="mini-textbox" /></td>	
		<td>
		<a class="easyui-linkbutton" iconCls="icon-tip" plain="true" onclick="loadSeries()" > </a>
		</td>
	</tr>
	
	</table>
	<br>
	<table id="materialListID" class="easyui-datagrid" title="材料清单"
		style="width:1200px;height:auto"
		data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				toolbar: '#tb',onDblClickCell: onDblClickCell,onClickCell: onClickCell
				">
		<thead>
			<tr>
			
			<th 
					data-options="field:'construct_purchase_quantitiesId',width:80,hidden:'hidden',editor:'text'">construct_purchase_quantitiesId</th>
				<th 
					data-options="field:'construct_purchase_entryId',width:80,hidden:'hidden',editor:'text'">ItemID</th>
				<th 
					data-options="field:'construct_purchase_quantitiesId',width:80,hidden:'hidden',editor:'text'">construct_purchase_quantitiesId</th>	
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
					data-options="field:'construct_purchase_applyNum',width:80,align:'right',editor:{type:'numberbox'}">计划采购量</th>
				<th
					data-options="field:'construct_purchase_contractPrice',width:80,align:'right'">合同单价</th>
					
				
				<th
					data-options="field:'construct_purchase_purchasePrice',width:80,align:'right'">采购单价</th>
				<th
					data-options="field:'construct_purchase_purchaseTotal',width:80,align:'right'">采购小计</th>
				<th
					data-options="field:'construct_purchase_brand',width:80,align:'right',editor:{type:'text',options:{required:true}}">材料品牌</th>
				
				<th
					data-options="field:'construct_purchase_remarks',width:350,align:'center',editor:{type:'text',options:{required:true}}">备注</th>

			</tr>
		</thead>


	</table>


	<div id="tb" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-add',plain:true" onclick="add()">添加</a> 
			
			<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-undo',plain:true" onclick="reject()">撤销行</a>
			
			<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-cut',plain:true" onclick="removeit()">删除</a>
	

	</div>
	<br/>

	<br />
	<br />
	<div style="text-align:center">
		<tr>
			<td style="align:center">
				&nbsp;&nbsp;&nbsp;&nbsp; <a class="easyui-linkbutton"
				href="javascript:;" id="btnSave" onclick="btnSave()">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
				<a class="easyui-linkbutton" href="javascript:;" id="btnCancel"
				onclick="btnCancel()">取消</a></td>
		</tr>
	</div>

			<div id="series" class="easyui-window"
					data-options="region:'center'" closed="true"
					style="height: 600px; width: 800px;padding: 10px;">
						<table id="seriesData">
								<thead>
									<tr>
										<th data-options="field:'seriesName',width:80">类别</th>
										<th data-options="field:'materialName',width:80">所属材料</th>
									</tr>
								</thead>
						</table>
				</div>		

<div  id="win" class="easyui-window" data-options="region:'center',title:'请选择值'" closed="true" style="height: 500px; width: 800px" >    
				</div>

	<script type="text/javascript">
		var editIndex = undefined;

		$(function() {

			if (${rows}.rows != undefined) {
				var rows = ${rows}.rows;
				$('#materialListID').datagrid('loadData', rows);
			}
			
			//单元格编辑
			$.extend($.fn.datagrid.methods, {
				editCell : function(jq, param) {
					return jq.each(function() {
						var fields = $(this).datagrid('getColumnFields', true)
								.concat($(this).datagrid('getColumnFields'));
						for (var i = 0; i < fields.length; i++) {
							var col = $(this).datagrid('getColumnOption',
									fields[i]);
							col.editor1 = col.editor;
							if (fields[i] != param.field) {
								col.editor = null;
							}
						}
						$(this).datagrid('beginEdit', param.index);
						for (var i = 0; i < fields.length; i++) {
							var col = $(this).datagrid('getColumnOption',
									fields[i]);
							col.editor = col.editor1;
						}
					});
				}
			});

		});
		
		function loadSeries(){
			
			var form = $("<form>");     
		    $('body').append(form);    
		        form.attr('style','display:none');     
		        form.attr('target','');  
		        form.attr('method','post');  
		        form.attr('action',"downloadSeries.do");//下载文件的请求路径  
		          
		        form.submit();      
		      
		}
		
		
		//删除处理
		function removeit(){
			var row = $('#materialListID').datagrid('getSelected');
			
			var construct_purchase_status = $("#construct_purchase_status").val();
			
			//判断状态是否在审核中还是已审核
		if(construct_purchase_status==""||construct_purchase_status=="1"||construct_purchase_status=="0"){
			if (!row){
				$.messager.alert("提示", "请选择删除行!");
				return false;
			}
         //是否要删除
		$.messager.confirm('提示！', '你确定删除吗？', function(r) {
				if (r) {	
			 $.post("deleteEntry.do",{
				 'bizId':row.construct_purchase_entryId, 
			   },function(data) {
				   
					if (data.Success) {
						$.messager.alert("提示", data.Msg,"info", function() {
							 location.reload(true);
								});
				      	}else {
						$.messager.alert("提示", data.Msg,"error");
					    
						}

			 });
			 }
			});

		}else{
			$.messager.alert('警告','该单在审核中或是已审核完毕，不可删除!','warning');
			return false;
		}
			
		};
		//新增行
		function add() {
			var construct_purchase_materialSerId = $("#construct_purchase_materialSerId")
			.val();
			if (endEditing()&&construct_purchase_materialSerId!="") {
				$('#materialListID').datagrid('appendRow', {
					status : 'P'
				});
				editIndex = $('#materialListID').datagrid('getRows').length - 1;

				$('#materialListID').datagrid('selectRow', editIndex).datagrid(
						'beginEdit', editIndex);
			}else{
				alert("请先填写采购材料类别");
			}
		}
		//是否编辑结束
		function endEditing() {
			if (editIndex == undefined) {
				return true;
			}
			if ($('#materialListID').datagrid('validateRow', editIndex)) {
				$('#materialListID').datagrid('endEdit', editIndex);
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		}

		function onClickCell(index, field) {
			$('#materialListID').datagrid('selectRow', index);
			if (endEditing()) {
				$(this).datagrid('beginEdit', index);
			}
		}

		function onDblClickCell(index, field, value) {
			var construct_purchase_materialSerId = $("#construct_purchase_materialSerId").val();
			
			if (field == "construct_purchase_material") {
				var hrefs = "<iframe id='son' src='proQuantyCheck.do?index="
						+ index +"&materialSerId="+construct_purchase_materialSerId+"&projectId="+${projectId}+"' allowTransparency='true' style='border:0;width:99%;height:99%;padding-left:2px;' frameBorder='0'></iframe>";
				$("#win").html(hrefs);
				$("#win").window("open");
			}
		}
		//子窗口调用
		function materialData(data, index) {
			var row = $('#materialListID').datagrid("selectRow", index)
					.datagrid("getSelected");

			$('#payroll').datagrid('acceptChanges');
			var rows = $('#materialListID').datagrid('getRows');
			
			for (var int = 0; int < rows.length; int++) {
				if(rows[int].construct_purchase_quantitiesId==data.construct_project_quantities_id){
					alert("此材料已存在！");
					return false;
				}
			}
			
			row.construct_purchase_material = data.construct_material_name;
			row.construct_purchase_model = data.construct_material_model_name;
			row.construct_purchase_unit = data.construct_material_model_unit;
			
			
			row.construct_purchase_quantities = data.construct_project_quantities_num;
			row.construct_purchase_contractPrice = data.construct_project_quantities_price;
			row.construct_purchase_purchasePrice = rows[index].construct_purchase_purchasePrice;
			row.construct_purchase_purchaseTotal = rows[index].construct_purchase_purchaseTotal;
			row.construct_purchase_brand = rows[index].construct_purchase_brand;
			row.construct_purchase_remarks = rows[index].construct_purchase_remarks;
			row.construct_purchase_applyNum = rows[index].construct_purchase_applyNum;
			if(data.sum!=null){
				row.construct_purchase_approvalNum = data.sum;//累计审批量
			}else{
				row.construct_purchase_approvalNum = 0;
			}
			row.construct_purchase_quantitiesId=data.construct_project_quantities_id;
			$('#materialListID').datagrid('refreshRow', index);
		}

		//撤销新增行
		function reject() {
			
			var row = $('#materialListID').datagrid('getSelected');
				if (row) {
					var rowIndex = $('#materialListID').datagrid('getRowIndex', row);
					$('#materialListID').datagrid('deleteRow', rowIndex);

				} else {
					$.messager.alert("提示", "请选择删除行!");
				}
		}

		//取消
		function btnCancel() {
			location.href = "purchaseList.do?construct_project_id="+${projectId};
			//location.href = "purchaseList.do?construct_project_id=";
		}

		//保存
		function btnSave() {
			var construct_purchase_id = $("#construct_purchase_id").val();
			var construct_purchase_planDate = $("#construct_purchase_planDate")
					.datebox("getValue");
			var construct_purchase_arriveDate = $(
					"#construct_purchase_arriveDate").datebox("getValue");
			var construct_purchase_planMan = $("#construct_purchase_planMan")
					.val();
			var construct_purchase_reviewer = $("#construct_purchase_reviewer")
					.val();
			var construct_purchase_supplier = $("#construct_purchase_supplier")
					.val();
			var construct_purchase_materialSerId = $("#construct_purchase_materialSerId")
					.val();
			var construct_purchase_materialSerName = $("#construct_purchase_materialSerName")
			.val();
			
			var construct_purchase_supplierTel = $(
					"#construct_purchase_supplierTel").val();
			var construct_project_id = $("#construct_project_id").val();
			
			var construct_purchase_status = $("#construct_purchase_status").val();
			if(construct_purchase_status==""||construct_purchase_status=="1"||construct_purchase_status=="0"||construct_purchase_status=="2"){
			
			if(construct_purchase_materialSerId==""){
				alert("材料类别不能为空");
				return false;
			}	
				
			var rows = null;
			if (endEditing()) {
				$('#materialListID').datagrid('acceptChanges');
				 rows = $('#materialListID').datagrid('getRows');
			}
			
			for (var int = 0; int < rows.length; int++) {
				if (rows[int].construct_purchase_applyNum=="") {
					alert("计划采购量不能为空");
					return false;
				} 
				if(parseInt(rows[int].construct_purchase_quantities)<parseInt(rows[int].construct_purchase_applyNum)+parseInt(rows[int].construct_purchase_approvalNum)){
					alert("计划采购量累计不能大于合同工程量");
					return false;
				}
				
			}
			$.ajax({
						type : 'POST',
						url : 'submitPurchase.do',
						data : {
							'rows' : JSON.stringify(rows),
							'construct_purchase_id' : construct_purchase_id,
							'construct_purchase_planDate' : construct_purchase_planDate,
							'construct_purchase_arriveDate' : construct_purchase_arriveDate,
							'construct_purchase_planMan' : construct_purchase_planMan,
							'construct_purchase_reviewer' : construct_purchase_reviewer,
							'construct_purchase_supplier' : construct_purchase_supplier,
							'construct_purchase_supplierTel' : construct_purchase_supplierTel,
							'construct_project_id' : construct_project_id,
							'construct_purchase_materialSerId' : construct_purchase_materialSerId,
							'construct_purchase_materialSerName' : construct_purchase_materialSerName
							
						},
						success : function(data) {
							if (data == "") {
								alert("保存成功");
								location.href="purchaseList.do?" + $.param({							
									'construct_project_id' : construct_project_id, //传参项目部编号
								});

								
							} else {
								alert("保存失败");
							}
						}
					});
			}else{
				alert("审批中，不可修改！");
				return false;
			}
		}


		//选择子页面信息	
		function wen(src) {
			$('#materialListID').datagrid('acceptChanges');
			 rows = $('#materialListID').datagrid('getRows');
			 if(src=="materialSeries.do"&&rows.length!=0){
				   $.messager.alert("提示", "请清空材料清单!");
			 }else{
				   var hrefs = "<iframe id='son' src='"
					+ src
					+ "' allowTransparency='true' style='border:0;width:99%;height:99%;padding-left:2px;' frameBorder='0'></iframe>";
					$("#win").html(hrefs);
					$("#win").window("open");
			}
		}
		
		//保存且提交
		function btnAudit() {
			var construct_purchase_id = $(
					"#construct_purchase_id").val();
			var construct_purchase_planDate = $(
					"#construct_purchase_planDate").datebox(
					"getValue");
			var construct_purchase_arriveDate = $(
					"#construct_purchase_arriveDate").datebox(
					"getValue");
			var construct_purchase_planMan = $(
					"#construct_purchase_planMan").val();
			var construct_purchase_reviewer = $(
					"#construct_purchase_reviewer").val();
			var construct_purchase_supplier = $(
					"#construct_purchase_supplier").val();
			var construct_purchase_supplierTel = $(
					"#construct_purchase_supplierTel").val();
			var construct_project_id = $(
					"#construct_project_id").val();
			var construct_purchase_materialSerId = $("#construct_purchase_materialSerId")
					.val();
			var construct_purchase_materialSerName = $("#construct_purchase_materialSerName")
					.val();
			var auditor = $("#auditor").val();
			
			var construct_purchase_status = $("#construct_purchase_status").val();
			if(construct_purchase_status==""||construct_purchase_status=="0"){
				
			if(auditor==""){
				alert("审核人不可为空");
				return false;
			}
			if(construct_purchase_materialSerId==""){
				alert("材料类别不能为空");
				return false;
			}	
			var rows = null;
			if (endEditing()) {
				$('#materialListID').datagrid('acceptChanges');
				rows = $('#materialListID').datagrid(
						'getRows');
			}
			for (var int = 0; int < rows.length; int++) {
				if (rows[int].construct_purchase_applyNum=="") {
					alert("计划采购量不能为空");
					return false;
				} 
				if(parseInt(rows[int].construct_purchase_quantities)<parseInt(rows[int].construct_purchase_applyNum)+parseInt(rows[int].construct_purchase_approvalNum)){
					alert("计划采购量累计不能大于合同工程量");
					return false;
				}
				
			}
			if (confirm("提交后将不可修改,你确定提交审核吗？")) {
				
				$.ajax({
					type : 'POST',
					url : 'auditPurchase.do',
					data : {
						'rows' : JSON.stringify(rows),
						'construct_purchase_id' : construct_purchase_id,
						'construct_purchase_planDate' : construct_purchase_planDate,
						'construct_purchase_arriveDate' : construct_purchase_arriveDate,
						'construct_purchase_planMan' : construct_purchase_planMan,
						'construct_purchase_reviewer' : construct_purchase_reviewer,
						'construct_purchase_supplier' : construct_purchase_supplier,
						'construct_purchase_supplierTel' : construct_purchase_supplierTel,
						'construct_project_id' : construct_project_id,
						'construct_purchase_materialSerId':construct_purchase_materialSerId,
						'auditor' : auditor,
						'construct_purchase_materialSerName' : construct_purchase_materialSerName
					},
					success : function(data) {
						if (data == "") {
							alert("提交成功");
							location.reload(true);
						} else {
							alert("提交失败");
						}
					}
			});
			} else {
				return false;
			}
			}else{
				alert("已经存在流程，不可再次提交！");
				return false;
			}
		}
		
		function getData(data){
			
			 $("#construct_purchase_materialSerId").val(data.construct_material_seriesID); 
			 $("#construct_purchase_materialSerName").val(data.construct_material_seriesName); 
		}
		
	</script>
</body>
</html>