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
								<li><label>系列名称：</label><input type="text" name="construct_material_seriesName", id="construct_material_seriesName"
									class="easyui-validatebox" style="width: 80px" /></li>
								<li><label>材料名称：</label><input type="text" name="construct_material_name", id="construct_material_name"
									class="easyui-validatebox" style="width: 80px" /></li>
								<li><label>规格名称：</label><input type="text" name="construct_material_model_name", id="construct_material_model_name"
									class="easyui-validatebox" style="width: 80px" /></li>	
								<li>&nbsp;&nbsp; <a class="easyui-linkbutton"
									data-options="iconCls:'icon-search'" href="javascript:;"
									id="btnSearch">查询</a></li>
							</ul>
							<div class="clear"></div>
						</div>
					</div>
				</div>
				
				<div region="center" split="false" border="false"
					style="overflow: hidden; padding: 0 5px 5px 5px" id="dataList">
					<table id="data" title="" fit="true"
						data-options="collapsible: true,pagination:true,onDblClickRow: onDblClickRow">
						<thead>
							<tr>
								<th id="ds" halign="center" field="construct_material_id" align="center"
									sortable="true" width="60px">材料编号</th>
								<th halign="center" field=construct_material_seriesName align="center"
									sortable="true" width="120px">系列名称</th>	
								<th halign="center" field="construct_material_name" align="center"
									sortable="true" width="120px">材料名称</th>
								<th halign="center" field="construct_material_model_name" align="center" 
									sortable="true" width="120px">规格型号</th>
									<th halign="center" field="construct_material_model_unit" align="center" 
									sortable="true" width="120px">单位</th>
								<th halign="center" field="construct_material_model_id" align="center" 
									sortable="true" width="120px">规格编号</th>	
								<th halign="center" field="construct_material_model_remarks" align="center" 
									sortable="true" width="120px">描述</th>	
							</tr>
						</thead>
					</table>
				</div>

			</div>
		</div>





	<script type="text/javascript">
		function operate_formatter(value, row, index) {
			var html = "";

			return html;
		}

		


		function onDblClickRow (rowIndex, rowData){    
	        var construct_material_name=rowData.construct_material_name;
	        var construct_material_model_name=rowData.construct_material_model_name;
	        var construct_material_model_unit=rowData.construct_material_model_unit;

	        $(window.parent.$("#construct_project_quantities_name").val(construct_material_name)); //给父页面赋值
	        $(window.parent.$("#construct_project_quantities_model").val(construct_material_model_name));
	        $(window.parent.$("#construct_material_modelId").val(rowData.construct_material_model_id));
	        $(window.parent.$("#construct_project_quantities_unit").val(construct_material_model_unit));
			
	        //采购变更  purchangnew
	        //$(window.parent.window.materialDatas(rowData));
	        
	        $(window.parent.$("#win").window("close"));
	        
	    };

		$(function() {
			$("#data").admin_grid({
				queryBtn : "#btnSearch",
				excelBtn : "#btnExcel",

			});

		});
	</script>

</body>
</html>