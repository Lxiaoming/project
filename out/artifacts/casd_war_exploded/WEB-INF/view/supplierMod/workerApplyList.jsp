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
								<li><label>申请时间：</label><input type="text" name="suppliermod_worker_apply_creatTime" id="suppliermod_worker_apply_creatTime"
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
						data-options="collapsible: true,pagination:true,rownumbers:true">
						<thead>
							<tr>
								<th halign="center" field="suppliermod_worker_apply_id" align="center" hidden="hidden"
									sortable="true" width="60px">单据编号</th>
								<th halign="center" field="username" align="center"
									sortable="true" width="120px">姓名</th>
								<th halign="center" field="suppliermod_worker_apply_creatTime" align="center" 
									sortable="true" width="120px">申请时间</th>
								<th halign="center" field="projectName" align="center" 
									sortable="true" width="120px">所在项目</th>
								<th halign="center" field="construct_project_name" align="center" 
									sortable="true" width="120px">申请调动项目</th>
								<th halign="center" field="suppliermod_worker_apply_reason" align="center" 
									sortable="true" width="120px" >申请原因</th>
								<th halign="center" field="suppliermod_worker_apply_status" align="center" 
									sortable="true" width="120px" formatter="status" >状态</th>
							</tr>
						</thead>
					</table>
				</div>

			</div>
		</div>



	<script type="text/javascript">
		
		function status(value, row, index){
			
			if(row.suppliermod_worker_apply_status==0)
				return "审批中";
			if(row.suppliermod_worker_apply_status==3)
				return "审批不通过";
			if(row.suppliermod_worker_apply_status==2)
				return "审批通过";
		}
		
		$(function() {
			$("#data").admin_grid({
				queryBtn : "#btnSearch",
				excelBtn : "#btnExcel",
			});
		});
	</script>

</body>
</html>