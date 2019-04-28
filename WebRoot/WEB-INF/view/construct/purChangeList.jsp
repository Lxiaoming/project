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
							<li><label>事宜：</label><input type="text"
								name="constuct_project_dep_name" id="constuct_project_dep_name"
								class="easyui-validatebox" style="width: 120px" /></li>
							<li>&nbsp;&nbsp; <a class="easyui-linkbutton"
								data-options="iconCls:'icon-search'" href="javascript:;"
								id="btnSearch">查询</a></li>
							<li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton"
								iconCls="icon-cancel" plain="true" onclick="btnDele();">删除申请单</a></li>
							<li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton"
								iconCls="icon-edit" plain="true" onclick="btnEdit();">修改申请单</a></li>
							<li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton"
								iconCls="icon-add" plain="true" onclick="btnAdd();">新增申请单</a></li>

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
							<th halign="center" hidden="hidden" field="purChangeTab_id"
								align="center" sortable="true" width="120px">编号</th>
							<th halign="center" field="purChangeTab_proName"
								align="center" sortable="true" width="120px">项目名称</th>
							<th halign="center" field="purChangeTab_matter"
								align="center" sortable="true" width="120px">事宜</th>
							<th halign="center" field="purChangeTab_dep"
								align="center" sortable="true" width="120px">部门</th>
							<th halign="center" field="purChangeTab_proId" hidden="hidden"
								align="center"  sortable="true" width="120px">项目id</th>
							<th halign="center" field="purChangeTab_total"
								align="center" sortable="true" width="120px">总金额</th>
							<th halign="center" field="purChangeTab_status" formatter="status_formatter"
								align="center" sortable="true" width="120px" >状态</th>		
							<th halign="center" field="psn" align="center" sortable="true"
								width="120px" formatter="operate_formatter">操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>



	<script type="text/javascript">
		var editIndex = undefined;
		function operate_formatter(value, row, index) {
			var html = "";

			html += '<a href="javascript:;" class="opt"    cmd="purChangeView" i="' + index + '">查看</a>';

			return html;
		}
		//状态
		function status_formatter(value, row, index) {
			if (value == 0) {
				return "保存";
			} else if (value == 1) {
				return "提交";	
			} else if (value == 2) {
				return "审批中";
				
			} else if (value == 3) {
				return "审核通过";
			
			} else if (value == 4) {
				return "审核不通过";
			
			}
		} 
		
		//新增
		function btnAdd() {
			location.href = "purChangeNew.do";
		}
		
		//修改
		function btnEdit(){
			var rows = $("#data").datagrid("getSelections");//获取表格数据
			if (rows.length == 0) {
				alert("请选择需要修改的行");
				return false;
			} else if (rows.length > 1) {
				alert("一次只能选择一行喔！");
				return false;
			}
			if(rows[0].purChangeTab_status==0||rows[0].purChangeTab_status==4){
				location.href = "purChangeNew.do?purChangeTab_id="+rows[0].purChangeTab_id;
			}else{
				$.messager.alert("提示","审核状态不可修改");
			}
		}
		
		//删除
		function btnDele() {
			var rows = $("#data").datagrid("getSelections");//获取表格数据
				if (rows.length == 0) {
					$.messager.alert("提示","请选择需要删除的行");
					return false;
				} else if (rows.length > 1) {
					$.messager.alert("提示","一次只能选择一行喔！");
					return false;
				}
				if(rows[0].purChangeTab_status==0){
					$.post("delete_purChange.do", {
						purChangeTab_id:rows[0].purChangeTab_id,
					},function(data) {
						if (data.msg != undefined) {
						    $.messager.alert("提示",data.msg,"error");
						}else{
							$.messager.alert("提示","操作成功！");
							$("#data").datagrid("reload");
						}
					});
				}else{
					$.messager.alert("提示","审核状态不可删除");
				}
		}
		
		 
		/***处理操作列的功能***/
		function cmdHanlder(cmd, row) {
			if (cmd == "purChangeView") {

				//addSubPage("申购变更","purChangeView.do?purChangeTab_proId="+row.purChangeTab_proId+"&purChangeTab_id="+row.purChangeTab_id+"");
				
				location.href = "申购变更","purChangeView.do?purChangeTab_proId="+row.purChangeTab_proId+"&purChangeTab_id="+row.purChangeTab_id+"";

				
			}
		}
		

		/**
		 *数据初始化
		 */
		$(function() {
			$("#data").admin_grid({
				queryBtn : "#btnSearch",
				excelBtn : "#btnExcel",
				cmdHanlder : cmdHanlder,

			});

		});
	</script>

</body>
</html>