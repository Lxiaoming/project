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
								<li><label>项目名称：</label><input type="text" name="constuct_project_dep_name", id="construct_project_name"
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
								<th id="ds" halign="center" field="projectId" align="center"
									sortable="true" width="60px">项目编号</th> 
								<th halign="center" field="construct_project_name" align="center"
									sortable="true" width="120px">项目名称</th>
								<th halign="center" field="construct_project_addr" align="center" 
									sortable="true" width="120px">工程地址</th>
								<th halign="center" field="construct_project_leader" align="center" 
									sortable="true" width="120px">项目经理</th>
								<th halign="center" field="construct_project_leaderTel" align="center" 
									sortable="true" width="120px">项目经理联系方式</th>
								<th halign="center" field="total" align="center" 
									sortable="true" width="120px">共欠款</th>
								<th halign="center" field="payedTotal" align="center" 
									sortable="true" width="120px">已支付</th>
								<th halign="center" field="owe" align="center" 
									sortable="true" width="120px" formatter="noPay">未支付</th>		
								<th halign="center" field="psn" align="center" sortable="true"
									width="120px" formatter="operate_formatter">操作</th>
							</tr>
						</thead>
					</table>
				</div>

			</div>
		</div>





	<script type="text/javascript">
		function operate_formatter(value, row, index) {
			var html = "";

			html += '<a href="javascript:;" class="opt"    cmd="settlePayList" i="' + index + '">付款单列表</a>';

			return html;
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

		function btnAdd() {
			location.href = "settleMonthList.do";

		}

		function noPay(value, row, index) {
			if(row.payedTotal!=null){
	 			return parseInt(row.total) - parseInt(row.payedTotal);
			}
		}
		
		
		/***处理操作列的功能***/
		function cmdHanlder(cmd, row) {
		
			if (cmd == "settlePayList") {
			
				location.href = "settlePayList.do?" + $.param({
					'construct_project_id' : row.projectId
					
				});

			}
		}

		//批量删除处理
		function btnDele() {
			var rows = $("#data").datagrid("getSelections");//获取表格数据
			if (rows.length == 0) {
				alert("请选择需要删除的行");
				return null;
			}

			var ids = [];
			for (var i = 0; i < rows.length; i++) {
				ids.push(rows[i].flow_id);
			}

			$.ajax({
				type : 'POST',
				url : 'deleFlow.do',
				traditional : true,
				data : {
					'flowids' : JSON.stringify(ids)
				},
				success : function(data) {
					if (data == "") {
						alert("删除成功");
						location.reload();
					} else {
						alert("删除失败");
					}
				}
			});
		};
		
		//新增合同工程量
		function btnAddMaterial() {
			location.href = "conMaterialList.do";
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