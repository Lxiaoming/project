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
								<li><label>材料名称：</label><input type="text" name="construct_project_quantities_name", id="construct_project_quantities_name"
									class="easyui-validatebox" style="width: 120px" /></li>
								<li><label>型号规格：</label><input type="text" name="construct_project_quantities_model", id="construct_project_quantities_model"
									class="easyui-validatebox" style="width: 120px" /></li>	
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
								<th id="ds" halign="center" field="construct_project_quantities_id" align="center"
									sortable="true" width="60px">编号id</th>
								<!-- <th halign="center" field="construct_project_quantities_name" align="center"
									sortable="true" width="120px">材料名称</th> -->
									
								<th halign="center" field="construct_material_name" align="center"
									sortable="true" width="120px">材料名称</th>
										
								<!-- <th halign="center" field=construct_project_quantities_model align="center" 
									sortable="true" width="120px">规格型号</th> -->
								
								<th halign="center" field=construct_material_model_name align="center" 
									sortable="true" width="120px">规格型号</th>	
								<!-- <th halign="center" field="construct_project_quantities_unit" align="center" 
									sortable="true" width="120px">单位</th> -->
								<th halign="center" field="construct_material_model_unit" align="center" 
									sortable="true" width="120px">单位</th>
									
								<th halign="center" field="construct_project_quantities_num" align="center" 
									sortable="true" width="120px">数量</th>
								<th halign="center" field="sum" align="center" 
									sortable="true" width="120px">累计审批量</th>	
								<th halign="center" field="construct_project_quantities_price" align="center" 
									sortable="true" width="120px">单价</th>		
								<th halign="center" field="construct_project_quantities_remarks" align="center" 
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
	        /* var construct_project_quantities_name=rowData.construct_project_quantities_name;
	        var construct_project_quantities_model=rowData.construct_project_quantities_model;
	        var construct_project_quantities_unit=rowData.construct_project_quantities_unit;
	        var construct_project_quantities_num=rowData.construct_project_quantities_num;
	        var construct_project_quantities_price=rowData.construct_project_quantities_price;
	        var sum=rowData.sum;
	        var construct_project_quantities_id=rowData.construct_project_quantities_id;
			var index=${index};
	        var retVal = JSON.parse("{}");
	        retVal.name=construct_project_quantities_name;
	        retVal.model=construct_project_quantities_model;
	        retVal.unit=construct_project_quantities_unit;
	        retVal.num=construct_project_quantities_num;
	        retVal.price=construct_project_quantities_price;
	        retVal.sum=sum;
	        retVal.construct_project_quantities_id=construct_project_quantities_id; */
	        var index=${index};
	        $(window.parent.window.materialData(rowData,index));
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