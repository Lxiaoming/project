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

</head>
<body class="easyui-layout" style="overflow-y: hidden;" scroll="no">
	<div id="main" region="center"
		style="background: #eee; padding: 5px; overflow-y: hidden;">


		<div class="easyui-layout" fit="true">
			<div region="north" split="false" border="false"
				style="overflow: hidden; padding: 5px 5px 28px 5px;">
				<div class="easyui-panel" title="查询条件" style="margin-bottom: 5px;">

					<div class="search">
						<ul>
							<li><label>材料名称：</label><input type="text"
								name="construct_material_name"  id="construct_material_name"
								class="easyui-validatebox" style="width: 120px" /></li>
								<li><label>型号规格：</label><input type="text"
								name="construct_material_model"  id="construct_material_model"
								class="easyui-validatebox" style="width: 120px" /></li>
							<li>&nbsp;&nbsp; <a class="easyui-linkbutton"
								data-options="iconCls:'icon-search'" href="javascript:;"
								id="btnSearch">查询</a></li>
					
					         <c:if test="${status==0}">
							<li>&nbsp;&nbsp; <a href="javascript:void(0)" plain="true"
								iconCls="icon-add" class="easyui-linkbutton" onclick="add_price();">添加数据</a>
							</li>
					</c:if>
						</ul>
						<div class="clear"></div>
					</div>
				</div>
			</div>
	
	<div data-options="region:'center',iconCls:'icon-ok'">
			<table id="data" title="" fit="true"
					data-options="collapsible: true,pagination:true,rownumbers:true">
					<thead>
						<tr>
							<th halign="center" field="construct_material_priceId"
								align="center" sortable="true" width="60px">单价编号</th>
								<th halign="center" hidden="hidden" field="construct_material_supplierId"
								align="center" sortable="true" width="60px">供应商编号</th>
							<th halign="center" field="construct_material_name"
								align="center" sortable="true" width="120px">材料名称</th>
							<th halign="center" field="construct_material_model"
								align="center" sortable="true" width="120px">型号规格</th>
							<th halign="center" field="construct_material_modelId" hidden="hidden"
								align="center" sortable="true" width="120px">型号规格id</th>	
							<th halign="center" field="construct_material_unit"
								align="center" sortable="true" width="120px">单位</th>							
							<th halign="center" field="construct_material_supplier"
								align="center" sortable="true" width="120px">供应商</th>
							<th halign="center" field="construct_material_supplierTel"
								align="center" sortable="true" width="120px">供应商电话</th>	
							<th halign="center" field="construct_material_price"
								align="center" sortable="true" width="120px">最新单价</th>
							<th halign="center" field="construct_lowest_price"
								align="center" sortable="true" width="120px">最低单价</th>
							<th halign="center" field="construct_latest_price"
								align="center" sortable="true" width="120px">原价</th>	
							<th halign="center" field="construct_material_remarks"
								align="center" sortable="true" width="120px">备注</th>						
						</tr>
					</thead>
				</table>
		</div>
	<div data-options="region:'south',split:true" style="height:300px;">
			<div class="easyui-layout" data-options="fit:true">
		<!-- 	<div data-options="region:'east',split:true,border:false" style="width:400px">
			

		<table id="history" class="easyui-datagrid" title="审批记录"
			style="width:970px;height:150px"
			data-options="
				iconCls: 'icon-edit',
				singleSelect: true,">
			<thead>
			<thead>

				<tr>
						<th data-options="field:'userId',width:80">审核人</th>
					<th data-options="field:'fullMessage',width:300">审核意见</th>

				</tr>

			</thead>


		</table>
		
			</div>
				 -->
				<div data-options="region:'center',border:false,"style="width:900px">
				     
					<table id="tt"  class="easyui-datagrid" 
					toolbar="#tb"
			       url="findchangeprice.do?cid=${bizId}"
			      iconCls="icon-save" pagination="true">
		     <thead>
						<tr>
							<th halign="center" field="construct_change_id"
								align="center" sortable="true" width="60px">单价编号</th>
								<th halign="center" hidden="hidden" field="construct_material_supplierId"
								align="center" sortable="true" width="60px">供应商编号</th>
							<th halign="center" field="construct_material_name"
								align="center" sortable="true" width="120px">材料名称</th>
							<th halign="center" field="construct_material_model"
								align="center" sortable="true" width="120px">型号规格</th>
							<th halign="center" field="construct_material_modelId" hidden="hidden"
								align="center" sortable="true" width="120px">型号规格id</th>	
							<th halign="center" field="construct_material_unit"
								align="center" sortable="true" width="120px">单位</th>							
							<th halign="center" field="construct_material_supplier"
								align="center" sortable="true" width="120px">供应商</th>
							<th halign="center" field="construct_material_supplierTel"
								align="center" sortable="true" width="120px">供应商电话</th>	
							<th halign="center" field="construct_material_price"
								align="center" sortable="true" width="120px">最新单价</th>
							<th halign="center" field="construct_lowest_price"
								align="center" sortable="true" width="120px">最低单价</th>
							<th halign="center" field="construct_latest_price"
								align="center" sortable="true" width="120px">原价</th>	
							<th halign="center" field="construct_material_remarks"
								align="center" sortable="true" width="120px">备注</th>
							<th halign="center" field="psn" sortable="true"
								width="120px" formatter="operate_formatter">操作</th>
							
						</tr>
					</thead>
	           </table>
	           <div id="tb">
	          <c:if test="${status==0}">
          	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="start_change_price()">提交</a>
	      </c:if>
          </div>
          <div id="new_price" class="easyui-window" title="价格修改" data-options="modal:true,closed:true" style="width:300px;height:100px;padding:10px;">
		            <label></label><input type="hidden" name="construct_material_supplierId" id="material_supplierId">
		           <label>价格:</label><input type="number" name="add_price" id="add_price">
		         <a href="#" class="easyui-linkbutton"  onclick="new_price();">提交</a>
		           
	          </div>
				</div>
				
			</div>
		</div>
		
		</div></div>


		<script type="text/javascript">		
		function operate_formatter(value, row, index) {
			
			var html = "";
			if( ${status}==0 || ${status}==2){
			html += '<a href="javascript:;" class="opt",plain:true" onclick="open_price();"  cmd="edit" i="' + index + '">编辑</a>&nbsp;&nbsp;';
			html += '<a href="javascript:;" class="opt", onclick="deleteNewPrice();"  i="' + index + '">删除</a>';
			}
			return html;
		}

		//启动流程
		function  start_change_price(){
			
		     var bizId=${bizId};
		 	$.messager.confirm('提示！', '你确定提交吗?', function(r){
				if (r){
			 $.ajax({
					type : 'post',
					url : 'start_change_price.do',
					data : {'bizId':bizId,
					},
					success : function(data) {
						if (data.Success) {
							$.messager.alert("提示", data.Msg, "info", function() {
								   location.href="findTaskList.do";
								});
						
						} else {
							$.messager.alert("提示", data.Msg, "error");
							$('#data').datagrid('reload');

						}
					
					}
				});
				}});
			
		}
		
		 function open_price(id){
			 var row = $('#tt').datagrid('getSelected');
	        $("#material_supplierId").val(row.construct_material_priceId);
	        $('#new_price').window('open');
			 
		 }
		function new_price(){
			var id=$("#material_supplierId").val();
			var new_price=$("#add_price").val();	
			   $.ajax({
					type : 'post',
					url : 'quoted_price.do',
					data : {'id':id,
						    'new_price':new_price,
					},success : function(data) {
						if (data.Success) {
							$.messager.alert("提示", data.Msg, "info", function() {
								   $('#tt').datagrid('reload');
							        $('#new_price').window('close');
								});					
						} else {
							$.messager.alert("提示", data.Msg, "error");
							$('#tt').datagrid('reload');

						}
					
						
					}
			   });
		}
		//添加变更数据
	   function add_price(){
		   var rows = $('#data').datagrid('getSelections');   
		   var bizId=${bizId};
		   $.ajax({
				type : 'post',
				url : 'add_price.do',
				data : {
					'datas' : JSON.stringify(rows),
					'bizId':bizId,
				},
				success : function(data) {
					if (data.Success) {
						$.messager.alert("提示", data.Msg, "info", function() {
							$('#tt').datagrid('reload');
							});
					
					} else {
						$.messager.alert("提示", data.Msg, "error");
					

					}
				
				
					
				}
			});
		   
	     }
	
		
	  function deleteNewPrice(){
		  var ids = [];
		  var rows = $('#tt').datagrid('getSelections');
		  if (rows.length > 1 ) {
			  $.messager.alert("提示","不能选择多行");
				return false;
		  }else if(rows.length==0){
			  $.messager.alert("提示","请选择删除行!");
				return false;
		  } 
		  for(var i=0; i<rows.length; i++){
		  	ids.push(rows[i].construct_change_id);
		  }
		 
		
		  $.ajax({
				type : 'post',
				url : 'deleteNewPrice.do',
				data : {'id':JSON.stringify(ids),
                	},success : function(data) {
                		if (data.Success) {
							$.messager.alert("提示", data.Msg, "info", function() {
								   $('#tt').datagrid('reload');
							       
								});
						
						} else {
							$.messager.alert("提示", data.Msg, "error");
							$('#tt').datagrid('reload');

						}
					
				}
			});
		  
		  
	  }
			
			
			$(function() {
				$("#data").admin_grid({
					queryBtn : "#btnSearch",
					

				});

				var history = ${history}.history;
				$('#history').datagrid('loadData', history);

			});
	
		
		</script>
</body>
</html>