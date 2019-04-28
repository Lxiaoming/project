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
									<li><label>项目名称：</label><input type="text" name="username"
										class="easyui-validatebox" style="width: 120px" /></li>		
								</ul>
								<div class="clear"></div>
							</div>		
						</div>
					</div>
					<div region="center" split="false" border="false"
						style="overflow: hidden; padding: 0 5px 5px 5px" id="dataList">
						<table id="data" title="" fit="true"
							data-options="collapsible: true,pagination:true,rownumbers:true">
							<tr>
								<td><input type="hidden" name="trade_id" value="" /></td>
							</tr>
							<thead>
								<tr>
									<th halign="center" field="construct_draw_id" align="center"  
										sortable="true" width="60px">单据编号</th>
									<th halign="center" field="construct_project_name" align="center"
										sortable="true" width="120px">项目名称</th>
									<th halign="center" field="construct_draw_unit" align="center"
										sortable="true" width="120px">领款单位</th>
									<th halign="center" field="construct_draw_payee" align="center"
									    sortable="true" width="120px" >领款人</th>
									<th halign="center" field="construct_draw_status" align="center"
									    sortable="true" width="120px" formatter="drawstatus">状态</th>

									<th halign="center" field="psn" align="center" sortable="true"
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

			html += '<a href="javascript:;" class="opt"  data-options="iconCls:\'icon-cut\',plain:true"  cmd="view" i="' + index + '">查看</a>';
			if (row.construct_draw_status == 0) {
			
				html += '<a href="javascript:;" class="opt" data-options="plain:true"  cmd="delete" i="' + index + '">删除</a>';

				
			}

			//html += '<a href="javascript:;" class="opt" data-options="iconCls:\'icon-page_break\',plain:true"  cmd="View" i="' + index + '">查看</a>';

			return html;
		}

  
	function drawstatus(value, row,index){
		if(value==0){
			return "审核中";
		}else if(value==2){
			return "审核通过";
		}
	
		
	}
		
		/***处理操作列的功能***/
		function cmdHanlder(cmd, row) {
			if (cmd == "delete") {
				  $.messager.confirm('提示！', '你确定删除吗？', function(r){
					if (r){
						$.messager.progress({
							title : '提示',
							msg : '正在处理中，请稍候……',
							text : ''
						});
					$.ajax({
						url : "deleteDrawmoney.do",
						type : 'POST',
						data : {'bizid' : row.construct_draw_id,
						},
						success : function(data) {
							$.messager.progress('close');
							 if (data.Success) {
									location.href = "drawmoneyList.do?" + $.param({
										'bizid' : row.construct_project_id, //传参项目部编号
									});
								} else {
									$.messager.alert("操作提示", data.Msg, "error");
								}
					     	}
					  
				         });
					}
				});	

			}else if(cmd=='view'){
				location.href = "drawmoneyView.do?" + $.param({
					'bizid' : row.construct_draw_id, //传参项目部编号
				   'type':'view',
					
				
			
				});
				
			}

		}

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