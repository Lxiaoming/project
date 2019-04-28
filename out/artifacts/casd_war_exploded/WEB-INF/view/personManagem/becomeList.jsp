<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		<input type="hidden" name="userid" value="${param.userid}">
		<div id="main" region="center"
			style="background: #eee; padding: 5px; overflow-y: hidden;">

				<div class="easyui-layout" fit="true">
					<div region="north" split="false" border="false"
						style="overflow: hidden; padding: 5px 5px 28px 5px;">
						<div class="easyui-panel" title="查询条件" style="margin-bottom: 5px;">
							<div class="controls">
								<a class="easyui-linkbutton"
									data-options="iconCls:'icon-search'" href="javascript:;"
									id="btnSearch">查询</a>

							</div>

							<div class="search">

								<ul>
								
									<li><label> 姓名：</label><input type="text" name="username"
										class="easyui-validatebox" style="width: 120px" /></li>
								
								</ul>
								<div class="clear"></div>
							<!-- 	<div id="tb">
							
									<a href="#" class="easyui-linkbutton" iconCls="icon-add"
										plain="true" onclick="become_apt();">转正申请</a>
									
								
							</div> -->
							</div>
					
						</div>
					</div>
					<div region="center" split="false" border="false"
						style="overflow: hidden; padding: 0 5px 5px 5px" id="dataList">
						<table id="data" title="" fit="true"
							data-options="collapsible: true,pagination:true,rownumbers:true">
							
							<thead>
								<tr>
									<th halign="center" field="userid" align="center"  
										sortable="true" width="60px">用户编号</th>
									<th halign="center" field="role_id" align="center"  
										sortable="true" width="60px">职位编号</th>
									<th halign="center" field="username" align="center"
										sortable="true" width="120px">姓名</th>
									<th halign="center" field="role_name" align="center"
										sortable="true" width="120px">角色名称</th>
										<th halign="center" field="close_time" align="center"
										sortable="true" width="120px">转正日期</th>
									<th halign="center" field="status" align="center"
										sortable="true" width="120px" formatter="operate_status">状态</th>

									<th halign="center" field="psn" sortable="true"
										width="120px" formatter="operate_formatter">操作</th>
								</tr>
							</thead>

						</table>
					</div>

				</div>
			</div>
		
	</form>

	<script type="text/javascript">
		function operate_formatter(value, row, index) {
			var html = "";

			html += '<a href="javascript:;" class="opt" cmd="Bianji" i="' + index + '">查看</a>';
			if (row.status == 0) {
				//<shop:Auth code="WITHDRAWAL_AUDIT">
			/* 	html += '<a href="javascript:;" class="opt" data-options="iconCls:\'icon-page_break\',plain:true"  cmd="Audit" i="' + index + '">通过</a>'; */

				//</shop:Auth>
			}

			//html += '<a href="javascript:;" class="opt" data-options="iconCls:\'icon-page_break\',plain:true"  cmd="View" i="' + index + '">查看</a>';

			return html;
		}

		function dateformat(value, row, index) {
			if (value == undefined)
				return "";
			var date = new Date(value);
			var pattern = "yyyy-MM-dd hh:mm:ss";
			return date.format(pattern);
		}

		
		function operate_status(value, row, index) {

			if (value == 1) {
				return "已转正";
			} else if (value == 2) {
				return "未转正";
			} else if (value == 3) {
				return "试用期";
			}
		}

		/***处理操作列的功能***/
		function cmdHanlder(cmd, row) {
			if (cmd == "Bianji") {

				location.href = "Become_for.do?" + $.param({
					userid : row.userid,
					status : row.status,
				});

			}

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