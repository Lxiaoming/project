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
								<li><label>项目名称：</label><input type="text" name="construct_project_name", id="construct_project_name"
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
								<th id="ds" halign="center" field="construct_project_id" align="center" hidden="hidden"
									sortable="true" width="60px">项目编号</th>
								<th halign="center" field="construct_project_name" align="center"
									sortable="true" width="200px">项目名称</th>
								<th halign="center" field="construct_project_addr" align="center" 
									sortable="true" width="280px">工程地址</th>
								<th halign="center" field="construct_project_leader" align="center" 
									sortable="true" width="120px">项目经理</th>
								<th halign="center" field="construct_project_leaderTel" align="center" 
									sortable="true" width="120px">项目经理联系方式</th>
								<th halign="center" field="construct_project_workTeam_category" align="center" 
									sortable="true" width="120px" formatter="operate_category" >施工项目</th>
								<th halign="center" field="username" align="center" 
									sortable="true" width="120px" >班组</th>
								<th halign="center" field="construct_project_workTeam_id" align="center"  hidden="hidden"
									sortable="true" width="120px" >teamId</th>
								
								<th halign="center" field="construct_project_workTeam_departmentId" align="center"  hidden="hidden"
									sortable="true" width="120px" >部门</th>
										
								<!-- <th halign="center" field="psn" align="center" sortable="true"
									width="120px" formatter="operate_formatter">操作</th> -->
							</tr>
						</thead>
					</table>
				</div>

			</div>
		</div>





	<script type="text/javascript">
		
		function operate_category(value, row, index){
			
			if(value==1)
				return "预埋";
			if(value==2)
				return "消防水";
			if(value==3)
				return "消防电";
			if(value==4)
				return "防排烟";
			if(value==5)
				return "消防水电";
			
		}
		
		
		function   formatDate(now)   {     
            var   year=now.getFullYear();     
            var   month=now.getMonth()+1;     
            var   date=now.getDate();     
            return   year+"-"+month+"-"+date;
        }     
       
		
		 function dateformat(value, row, index) {
			if (value == undefined)
				return "";
			 var  now=new   Date(value); 
			return formatDate(now); 
		   
		}
		 
		function status_formatter(value, row, index) {
			if (value == 1) {
				return "启用";
			} else if (value == 2) {
				return "禁用";

			}
		} 



		
		$(function() {
			$("#data").admin_grid({
				queryBtn : "#btnSearch",
				excelBtn : "#btnExcel",

				
				 onLoadSuccess: function(data){
					var mark=1;
					for (var i=1; i <data.rows.length; i++) {
						if (data.rows[i]['construct_project_name'] == data.rows[i-1]['construct_project_name']) {
							mark += 1;
							$(this).datagrid('mergeCells',{ 
								index: i+1-mark, 
								field: 'construct_project_name',
								rowspan:mark
							});
						}else{
							mark=1;     
						}
					}
					
				}, 
				onDblClickRow: function (rowIndex, rowData) {
					if(rowData.construct_project_leader==null||rowData.construct_project_leader==""){
						alert("项目经理为空！");
					} else if(rowData.construct_project_workTeam_id==null||rowData.construct_project_workTeam_id==""){
						alert("施工项目班组为空！");
					
					}else{	
						location.href = "workerList.do?" + $.param({
							'construct_project_workTeam_id' : rowData.construct_project_workTeam_id, 
							'construct_project_workTeam_departmentId':rowData.construct_project_workTeam_departmentId,
							'construct_project_id':rowData.construct_project_id,
							'construct_project_leader':rowData.construct_project_leader
						});
						
					}
				}
			});
			
		});
	</script>

</body>
</html>