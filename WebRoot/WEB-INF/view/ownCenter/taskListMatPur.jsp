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
<style>
#div_leftmenu div {
	font-size: 15px;
}

#div_leftmenu div ul {
	margin: 10px 10px 10px 10px;
	padding: 0;
	overflow: hidden;
	line-height: 40px;
}

#div_leftmenu div ul li {
	list-style-type: none;
	background-color: #DFE2E3;
	text-align: center;
	margin-bottom: 2px;
}

#div_leftmenu div ul li:last-of-type {
	margin-bottom: 0;
}
</style>

</head>
<body class="easyui-layout" style="overflow-y: hidden;" scroll="no">
	<form id="form1" method="post">

		<div id="main" region="center"
			style="background: #eee; padding: 5px; overflow-y: hidden;">


			<div class="easyui-layout" fit="true">
				<div region="north" split="false" border="false"
					style="overflow: hidden; padding: 5px 5px 28px 5px;">
					<div class="easyui-panel" title="操作" style="margin-bottom: 5px;">
						<!-- <div class="controls">
							<a class="easyui-linkbutton" data-options="iconCls:'icon-search'"
								href="javascript:;" id="btnSearch">查询</a>

						</div>
						<div class="search">
							<ul>
							<li><label>员工编号：</label><input type="text" name="userid"
								class="easyui-validatebox" style="width: 120px" /></li>

							</ul>
							<div class="clear"></div>
						</div> -->

						<div id="tb">

							<a href="#" class="easyui-linkbutton"  onclick="historyTaskList()">历史记录</a>

							<shop:permission code="DELETE_PIT">
								<a href="#" class="easyui-linkbutton" onclick="$('#deletePit').window('open')">删除流程</a>
							</shop:permission>
							
							<a href="#" class="easyui-linkbutton" 
								 onclick="purchase()">采购单</a>
							<a href="#" class="easyui-linkbutton" 
								 onclick="allBill()">待审核单</a>
							
						</div>
					</div>
				</div>
				<div region="center" split="false" border="false"
					style="overflow: hidden; padding: 0 5px 5px 5px" id="dataList">
					<table id="data" title="" fit="true"
						data-options="collapsible: true,pagination:true,">

						<thead>
							<tr>
								<!-- <th halign="center" field="taskid" align="center"
									sortable="true" width="60px">任务ID</th> -->
								<th halign="center" field="name" align="center" sortable="true"
									width="120px">任务的名称</th>
								<th halign="center" field="construct_purchase_creatTime" align="center" sortable="true"
									width="120px">建单时间</th>
								<th halign="center" field="construct_project_name" align="center" sortable="true"
									width="120px">项目名称</th>
								<th halign="center" field="construct_project_leader" align="center" sortable="true"
									width="120px">项目经理</th>		
								<th halign="center" field="title" align="center" sortable="true"
									width="120px">任务类型</th>
								<th halign="center" field="createTime" align="center"
									sortable="true" width="120px" formatter="dateformat">任务创建时间</th>
								<th halign="center" field="assignee" align="center"
									sortable="true" width="120px">任务的办理人</th>
								<!-- <th halign="center" field="processInstanceId" align="center"
									sortable="true" width="120px">流程实例id</th> -->

								<th halign="center" field="caozuo" sortable="true" width="200px"
									formatter="operate_formatter">操作</th>

							</tr>
						</thead>

					</table>
				</div>

			</div>
		</div>

	</form>


	<div id="deletePit" class="easyui-window" title="删除流程实例"
		data-options="modal:true,closed:true,"
		style="width:300px;height:100px;padding:10px;">
		<div>
			<input type="text" id="processInstanceId"
				style="width:100px;height:19px;"> <a
				href="javascript:void(0)" class="easyui-linkbutton"
				onclick="deleteProcessInstance()">提交</a>
		</div>

	</div>


	<script type="text/javascript">
		function operate_formatter(value, row, index) {
			var html = "";
			//  if(row.assignee!=null){
			html += '<a href="javascript:;" class="opt"    cmd="handle" i="' + index + '">办理任务</a>';
			/* 		} if(row.assignee==null){
					html += '<a href="javascript:;" class="opt"    cmd="taskclaim" i="' + index + '">等待签收</a>';
			     } */

			html += '<a href="javascript:;" class="opt"    cmd="to_view" i="' + index + '">查看流程图</a>';
			return html;
		}

		function formatDate(now) {
			var year = now.getFullYear();
			var month = now.getMonth() + 1;
			var date = now.getDate();
			return year + "-" + month + "-" + date;
		}

		function dateformat(value, row, index) {
			if (value == undefined)
				return "";
			var now = new Date(value);
			return formatDate(now);

		}

		function historyTaskList() {

			location.href = "historyTaskList.do";
		}

		function purchase() {

			location.href = "findTaskList.do?type=purchase";
		}
		function allBill() {

			location.href = "findTaskList.do";
		}
		
		
		/***处理操作列的功能***/
		function cmdHanlder(cmd, row) {
			//办理任务
			if (cmd == "handle") {

				location.href = row.url + "?" + $.param({
					'taskid' : row.taskid,
					'processInstanceId' : row.processInstanceId,
					'taskName' : row.name,
				});

			} else if (cmd == "taskclaim") {
				//签收任务  
				/*    $.ajax({
					     url:'taskclaim.do',
					     type:'get',
					     data:{'taskid' : row.taskid,},
					 	success : function(data){
					 		if (data.Success) {
								$.messager.alert("提示", data.Msg, "info", function() {
									location.href = "findTaskList.do";
								});
							} else {
								$.messager.alert("提示", data.Msg, "error");
							}
					 	}
					     
				   }); */

			} else if (cmd == 'to_view') {

				$.messager.alert("提示", "程序暂时未开放!");

			}

		}
		/**
		 *  删除流程实例
		 */

		function deleteProcessInstance() {

			var processInstanceId = $("#processInstanceId").val();
			$.ajax({
				url : 'deleteProcessInstance.do',
				type : 'post',
				data : {
					'processInstanceId' : processInstanceId,
				},
				success : function(data) {
					if (data.Success) {
						$.messager.alert("提示", data.Msg, "info", function() {
							location.href = "findTaskList.do";
						});
					} else {
						$.messager.alert("提示", data.Msg, "error");
					}
				}

			})
		};

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