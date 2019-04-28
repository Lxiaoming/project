<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

<head>
<title></title>

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

	<fieldset id="fd1" style="width:880px;">
		<legend>
			<span>项目信息</span>
		</legend>
		<div class="fieldset-body">
			<table class="form-table" border="0" cellpadding="5" cellspacing="2">
				<tr>
					<td class="form-label" style="width:60px;display: none">项目编号：</td>
					<td style="width:150px;display: none"><input name="construct_project_id" 
						id="construct_project_id" class="mini-textbox" value="${head.construct_project_id}" /></td>
					<td class="form-label" style="width:110px;">项目名称：</td>
					<td style="width:150px"><input type="text" name="flow_name" readonly="readonly"
						id="flow_name" class="mini-textbox" value="${head.construct_project_name}" /></td>
					<td class="form-label" style="width:110px;">工程地址：</td>
					<td style="width:150px"><input type="text"
						value="${head.construct_project_addr}" name="flow_description" readonly="readonly"
						id="flow_description" class="mini-textbox" /></td>
					<td class="form-label" style="width:110px;">项目经理：</td>
					<td style="width:150px"><input type="text" readonly="readonly"
						value="${head.construct_project_leader}" name="flow_description"
						id="flow_description" class="mini-textbox" /></td>
					</tr>
					<tr>	
					<td class="form-label" style="width:110px;">项目经理联系方式：</td>
					<td style="width:150px"><input type="text"  readonly="readonly"
						value="${head.construct_project_leaderTel}" name="flow_description"
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
				<td class="form-label" style="display: none">单据编号：</td>
					<td style="display: none"><input name="construct_purchase_id"
						id="construct_purchase_id" class="mini-textbox" value="${head.construct_purchase_id}" /></td>
					<td style="display: none"><input name="construct_purchase_projectId"
						id="construct_purchase_projectId" class="mini-textbox" value="${head.construct_purchase_projectId}" /></td>
					<td class="form-label" style="width:110px">计划日期：</td>
					<td style="width:150px"><input name="construct_purchase_planDate" readonly="readonly"
						id="construct_purchase_planDate" class="easyui-datebox" value="${head.construct_purchase_planDate}" /></td>
					<td class="form-label" style="width:110px;">希望送达时间：</td>
					<td style="width:150px"><input type="text" name="construct_purchase_arriveDate" readonly="readonly"
						id="construct_purchase_arriveDate" class="easyui-datebox" value="${head.construct_purchase_arriveDate}" /></td>
					<td class="form-label" style="width:110px;">材料计划员：</td>
					<td style="width:150px"><input type="text"
						value="${head.construct_purchase_planMan}" name="construct_purchase_planMan" readonly="readonly"
						id="construct_purchase_planMan" class="mini-textbox" /></td>
					
					</tr>
					<tr>
					<td class="form-label" style="width:60px;">材料复核员：</td>
					<td style="width:150px"><input type="text"
						value="${head.construct_purchase_reviewer}" name="construct_purchase_reviewer" readonly="readonly"
						id="construct_purchase_reviewer" class="mini-textbox" /></td>	
					<td class="form-label" style="width:60px;">供应商：</td>
					<td style="width:150px"><input type="text"
						value="${head.construct_purchase_supplier}" name="construct_purchase_supplier" readonly="readonly"
						id="construct_purchase_supplier" class="mini-textbox" /></td>
					<td class="form-label" style="width:60px;">供应商联系方式：</td>
					<td style="width:150px"><input type="text"
						value="${head.construct_purchase_supplierTel}" name="construct_purchase_supplierTel" readonly="readonly"
						id="construct_purchase_supplierTel" class="mini-textbox" /></td>				
				</tr>

			</table>
		</div>
	</fieldset>
	
	
	<div style="margin:20px 0;"></div>

	<table id="materialListID" class="easyui-datagrid" title="材料清单" 
		style="width:970px;height:350px"
		data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				toolbar: '#tb'
				">
			<thead>
		 <thead>
			<tr>
				<th  data-options="field:'construct_purchase_entryId',width:80,hidden:'hidden',editor:'text'">Item ID</th>
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
				<!-- <th
					data-options="field:'construct_purchase_brand',width:80,align:'right',editor:{type:'text',options:{required:true}}">材料品牌</th>	
				<th
					data-options="field:'construct_purchase_suppliers',width:80,align:'right',editor:{type:'text',options:{required:true}}">供应商</th>	
				
				<th
					data-options="field:'construct_material_supplierTel',width:80,align:'right',editor:{type:'text',options:{required:true}}">供应商联系方式</th> -->
				<th
					data-options="field:'construct_purchase_remarks',width:80,align:'right',editor:{type:'text',options:{required:true}}">备注</th>	
						
							
			</tr>
		</thead>

	</table>

				<div id="tb" style="padding:5px;">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						onclick="chooseSupplier()">选择供应商</a> 
				</div>

	<br />
	<div id="audit">
	
	
	
	
	 </div>
  <div style="margin:20px 0;"></div>
	
	<table class="easyui-datagrid"  style="width:700px;height:250px"
			data-options="singleSelect:true,collapsible:true">
		<thead>
		<c:forEach items="${data}" var="cs">
			<tr>
				<th data-options="field:'flow_auditer',width:80">${cs.flow_auditer}</th>
				<c:if test="${cs.flow_audit_option==undefined}">
				<th data-options="field:'flow_audit_option',width:80">未审核</th>
				</c:if>
				<c:if test="${cs.flow_audit_option!=''}">
					<th data-options="field:'flow_audit_option',width:80">${cs.flow_audit_option}</th>
				</c:if>
			</tr>
		
			</c:forEach>
		</thead>
	</table>
	
	
	<div class="fieldset-body">
			<table class="form-table" border="0" cellpadding="5" cellspacing="2">
				<tr>
					<td class="form-label" style="width:80px;">下一审核人：</td>
					<td style="width:150px"><select name="auditor"id="auditor" style="width:120px;">
								<option value="">请选择审批人</option>
								<c:forEach items="${auditor}" var="c">
									<option value="${c.username}">${c.username}</option>
								</c:forEach>
					</select></td>
					<td class="form-label" style="width:80px;">审核意见：</td>
					<td style="width:150px"><input type="text"
						value="" name="options" 
						id="options" class="mini-textbox" /></td>
						
					</tr>

			</table>
		</div>
	
	
	
	
	<br />
	<div style="text-align: center">
		<a class="easyui-linkbutton" href="javascript:;" id="btnAudit"
					style="margin-top: 10px">审核</a>	
		<a class="easyui-linkbutton" href="javascript:;" id="btnBack"
					style="margin-top: 10px">退回</a>				
		<a class="easyui-linkbutton" href="javascript:;" id="backList"
					style="margin-top: 10px">取消</a>	
	</div>
	
	<div  id="win" class="easyui-window" data-options="region:'center',title:'请选择值'" closed="true" style="height: 500px; width: 800px" >    
				</div>
	
	
	<div id="w" class="easyui-window" title='修改信息'
				data-options="modal:true,closed:true,iconCls:'icon-save'"
				style="width:1100px;height:700px;padding:10px;">
				
				<table id="dg" data-options="
				fitColumns: true,
				singleSelect: true,
				rownumbers: true,
				showFooter: true
			"></table>
				<div class="fieldset-body">
					<table class="form-table" border="0" cellpadding="5" cellspacing="2">
						<tr>
							<td class="form-label" style="width:80px;">供应商：</td>
							<td style="width:150px"><select name="supplier"id="supplier" style="width:120px;">
								<option value="">请选择供应商</option>
								<c:forEach items="${supplier}" var="s">
									<option value="${s.construct_supplier_id}">${s.construct_supplier_name}</option>
								</c:forEach>
							</select></td>
					</tr>
			</table>
		</div>
		
				<br /> <br /> <br /><br /> <br /> <br />
				<div style="text-align:center">
					<a class="easyui-linkbutton" href="javascript:;" id="btnChoose"
						onclick="btnChoose()">确定</a> &nbsp;&nbsp;&nbsp;&nbsp; <a
						class="easyui-linkbutton" href="javascript:;" id="btnCancel"
						onclick="closeDown()">取消</a>

				</div>

			</div>
	
	
	<script type="text/javascript">
	
		
		$(document).ready(function() {
			
				//弹框
				var columnsData=${rows}.columns;
				columnsData=eval(columnsData);
				var $datagrid = {};
				var columns = new Array();
				$datagrid.title = "";
				$datagrid.height = 350;
				$datagrid.width = 1000;
				$datagrid.idField = "id";
				
				for(var key in columnsData[0]){
					columns.push({ "field": columnsData[0][key], "title": key, "width": 80, "sortable": true });
				}
				$datagrid.columns = new Array(columns);
				$('#dg').datagrid($datagrid);
			
				if(${rows}.rows!=undefined){
					var rows = ${rows}.rows; 
					var data = JSON.parse("{}");
					data.footer=${rows}.footer;
					data.rows=rows; 
					$('#dg').datagrid('loadData', data);
				}
			
			//后台数据
			if(${rows}.rows!=undefined){
				var rows = ${rows}.rows; 
				$('#materialListID').datagrid('loadData',rows);
			}
			var auditID = ${auditID};
			var flow_status = ${flow_status};
			var flow_audit_next_auditid = ${flow_audit_next_auditid};
			var flow_audit_last_auditid = ${flow_audit_last_auditid};
			var flow_node_order = ${flow_node_order};
			
			//动态页面
		/* 	if(${audit}!=undefined){
				var realWidth=parseInt(41)+parseInt(222)*${audit}.data.length;
				html = "<div style=\"width:"+realWidth+"px\" id=\"main\">";
				html += "<div id=\"mes-tec\" class=\"title\">";
				html += "<br/><br/>审<br/>批<br/>意<br/>见<br/>";
				html += "</div>";
				for (var i = 0; i < ${audit}.data.length; i++) {
					var json = eval(${audit}.data[i]);
					var option=json.flow_audit_option;
					if(option==undefined){
						option="未审核";
					}
					html += "<div id=\"person\" class=\"title\" >";
					html += "<div>";	
					html += "<input id=\"personInput\" value=\""+json.flow_auditer+"\">";	
					html += "</input>";	
					html += "</div>";	
					html += "</div>";	
					html += "<div id=\"option\" class=\"title\">";	
					html += "<div>";
					html += "<input id=\"optionInput\"  value=\""+option+"\" >";
					html += "</input>";
					html += "</div>";	
					html += "</div>";						
				}
				html += "</div>";
				$("#audit").html(html);
			} */
			
			
			//按钮审核
			$("#btnAudit").click(function() {
				var option = $("#options").val();
				var auditor = $("#auditor").val();
				
				/* $('#materialListID').datagrid('acceptChanges');
				var rows=$('#materialListID').datagrid('getRows'); */
				
				/* if(${length}==0){
					for (var int = 0; int < rows.length; int++) {
						if(rows[int].construct_purchase_suppliers==undefined){
							alert("供应商不能为空!");
							return false;
						}
					}
				} */
				
				if(${length}!=0&&auditor==""){
						alert("审核人不能为空!");
						return false;
				}
			 	if (option == "") {
					alert("审核意见不能为空!");
					return false;
				} 
				 if (confirm("你确定审核通过吗？")) {
					
					var construct_purchase_id = $("#construct_purchase_id").val();
					/* var construct_purchase_planDate = $("#construct_purchase_planDate").datebox("getValue");
					var construct_purchase_arriveDate = $("#construct_purchase_arriveDate").datebox("getValue");
					var construct_purchase_planMan = $("#construct_purchase_planMan").val();
					var construct_purchase_reviewer = $("#construct_purchase_reviewer").val();
					var construct_purchase_projectId = $("#construct_purchase_projectId").val(); */

					$.ajax({
						type : 'POST',
						url : 'purchaseAudit.do',
						data : {
							/* 'rows' : JSON.stringify(rows),
							'construct_purchase_planDate' : construct_purchase_planDate,
							'construct_purchase_arriveDate' : construct_purchase_arriveDate,
							'construct_purchase_planMan' : construct_purchase_planMan,
							'construct_purchase_reviewer' : construct_purchase_reviewer,
							'construct_purchase_projectId' : construct_purchase_projectId, */
							
							'construct_purchase_id' : construct_purchase_id,
							'option' : option,
							'auditID' : auditID,
							'flow_node_order' : flow_node_order,
							'auditor':auditor,
							'flow_audit_next_auditid' :flow_audit_next_auditid
						},
						success : function(data) {
							if (data != "") {
								alert("本单申请通过");
							} else {
								alert("已提交到下一审批人");
								location.href="auditList.do";
							}
						}
					});
				} else {
					return false;
				} 
			});

			//按钮退回
			$("#btnBack").click(function() {
				var option = $("#options").val();
				if (option == "") {
					alert("审核意见不能为空!");
					return false;
				} 
				if (confirm("你确定退回申请吗？")) {
					var id = $("#id").val();
					$.ajax({
						type : 'POST',
						url : 'backLastOne.do',
						data : {
							'id' : id,
							'option' : option,
							'auditID' : auditID,
							'flow_node_order' : flow_node_order,
							'flow_audit_last_auditid' : flow_audit_last_auditid
						},
						success : function(data) {
							if (data == "") {
								alert("成功退回申请");
							} else {
								alert("退回失败");
							}
						}
					});
				} else {
					return false;
				}
			});

			$("#backList").click(function() {
				
				location.href="auditList.do";
			});

			$('#materialListID').datagrid({
				onDblClickCell: function(index,field,value){
					if(field=="construct_purchase_brand"){
						var hrefs = "<iframe id='son' src='materialPriceCheck.do?index="+index+"' allowTransparency='true' style='border:0;width:99%;height:99%;padding-left:2px;' frameBorder='0'></iframe>";    
			            $("#win").html(hrefs); 
			            $("#win").window("open"); 
					}
				}
			});
			
		});
		
		
		//弹框
		function chooseSupplier(){
			$('#w').window('open');
		}
		
		function btnChoose(){
			var supplier = $("#supplier").val();
			var supplierName = $("#supplier").find("option:selected").text();
			var purchase_id = $("#construct_purchase_id").val();
			$('#dg').datagrid('acceptChanges');
			var rows=$('#dg').datagrid('getRows'); 
			
			if (supplier == "") {
				alert("供应商不能为空!");
				return false;
			} 
			if (confirm("你确定选择这个供应商吗？")) {
				$.ajax({
					type : 'POST',
					url : 'chooseSupplier.do',
					data : {
						'supplier' : supplier,
						'supplierName' : supplierName,
						'rows':JSON.stringify(rows),
						'purchase_id':purchase_id
					},
					success : function(data) {
						if (data == "") {
							window.location.reload(true);
						} else {
							alert(data);
						}
					}
				});
			} else {
				return false;
			}
		}
		
		
		
		
		//子窗口调用
		function materialPriceData(data,index){
		 	var row = $('#materialListID').datagrid("selectRow", index).datagrid("getSelected");
		 	
		 	$('#payroll').datagrid('acceptChanges');
		 	var rows=$('#materialListID').datagrid('getRows');
		 	
			row.construct_purchase_material = rows[index].construct_purchase_material;
			row.construct_purchase_model = rows[index].construct_purchase_model;
			row.construct_purchase_unit = rows[index].construct_purchase_unit;
			row.construct_purchase_quantities = rows[index].construct_purchase_quantities;
			row.construct_purchase_contractPrice = rows[index].construct_purchase_contractPrice;
			row.construct_purchase_purchasePrice=data.price;
			row.construct_purchase_brand=data.brand;
			row.construct_purchase_suppliers=data.supplier;
			row.construct_material_supplierTel=data.tel;

			row.construct_purchase_remarks=rows[index].construct_purchase_remarks;
			row.construct_purchase_applyNum=rows[index].construct_purchase_applyNum;
			row.construct_purchase_approvalNum=rows[index].construct_purchase_approvalNum;//累计审批量
			
			var applyNum = rows[index].construct_purchase_applyNum == undefined ? 0 //计划采购量
					: rows[index].construct_purchase_applyNum;
			var purchasePrice = data.price == undefined ? 0 //计划采购量
					: data.price;
			Total = parseInt(applyNum) * parseInt(purchasePrice);
			row.construct_purchase_purchaseTotal=Total;

			$('#materialListID').datagrid('refreshRow', index);	
		}
		
		
		
	</script>

</body>