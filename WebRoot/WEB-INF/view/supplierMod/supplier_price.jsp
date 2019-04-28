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
						
							<li>&nbsp;&nbsp; <a href="javascript:void(0)" plain="true"
								iconCls="icon-redo" class="easyui-linkbutton" onclick="change_head();">数据变更</a>
							</li>
					
						</ul>
						<div class="clear"></div>
					</div>
				</div>
			</div>

			<div region="center" split="false" border="false"
				style="overflow: hidden; padding: 0 5px 5px 5px" id="dataList">
				<table id="data" title="" fit="true"
					data-options="collapsible: true,pagination:true,rownumbers:true">
					<thead>
						<tr>
							<th halign="center" field="construct_material_priceId"
								align="center" sortable="true" width="60px">单价编号</th>
							<th halign="center" field="construct_material_name"
								align="center" sortable="true" width="120px">材料名称</th>
							<th halign="center" field="construct_material_model"
								align="center" sortable="true" width="120px">型号规格</th>
							<th halign="center" field="construct_material_modelId" hidden="hidden"
								align="center" sortable="true" width="120px">型号规格id</th>	
							<th halign="center" field="construct_material_unit"
								align="center" sortable="true" width="120px">单位</th>
							
							<th halign="center" field="construct_supplier_name"
								align="center" sortable="true" width="120px">供应商</th>
							<th halign="center" field="construct_supplier_tel"
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

		</div>




		</div>



		<script type="text/javascript">

		function change_head(){	
			location.href="change_head_list.do";	
		}
		
			
			
			/***处理操作列的功能***/
			function cmdHanlder(cmd, row) {

			}

			$(function() {
				$("#data").admin_grid({
					queryBtn : "#btnSearch",
				
					cmdHanlder : cmdHanlder,

				});

			});
	
		
		</script>
</body>
</html>