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
								<li><label>付款时间：</label><input type="text" name="constuct_project_dep_name", id="constuct_project_dep_name"
									class="easyui-validatebox" style="width: 120px" /></li>
								<li>&nbsp;&nbsp; <a class="easyui-linkbutton"
									data-options="iconCls:'icon-search'" href="javascript:;"
									id="btnSearch">查询</a></li>
									<li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton" iconCls="icon-edit"
									plain="true" onclick="btnUpdate();">修改付款单批次</a></li>
									<!-- <li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton" iconCls="icon-cut"
									plain="true" onclick="btnDele();">删除付款单批次</a></li> -->
								<li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton" iconCls="icon-add"
									plain="true" onclick="btnAdd();">新增付款批次</a></li>
								<li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton" iconCls="icon-tip"
									plain="true" onclick="payNumList();">付款明细</a></li>		
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
								<th id="ds" halign="center" field="finance_settlepay_id" align="center"
									sortable="true" width="60px">付款单号</th>	
								<th  halign="center" field="finance_settlepay_paytime" align="center"
									sortable="true" width="60px">创建时间</th>	
								<th id="ds" halign="center" field="finance_settlepay_supplier" align="center"
									sortable="true" width="60px">供应商</th>
								<th id="ds" halign="center" field="construct_project_name" align="center"
									sortable="true" width="250px">项目</th>
								<th halign="center" field="finance_settlepay_owe" align="center"
									sortable="true" width="120px">欠款</th>
								<th halign="center" field="payed" align="center" 
									sortable="true" width="120px">已付</th>
								<th halign="center" field="finance_settlepay_percent" align="center" 
									sortable="true" width="120px" formatter="percent">已付百分比</th>	
								<th halign="center" field="finance_settlepay_noPay" align="center" 
									sortable="true" width="120px" formatter="noPay">未付</th>	
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

			html += '<a href="javascript:;" class="opt"    cmd="pay" i="' + index + '">查看明细</a>';

			return html;
		}

		function noPay(value, row, index) {
			if(row.payed!=undefined){
	 			return parseInt(row.finance_settlepay_owe) - parseInt(row.payed);
			}else{
				return 0;
			}
		}
		
		function percent(value, row, index) {
			if(row.payed!=undefined){
				var num=  parseInt(row.payed)/parseInt(row.finance_settlepay_owe);
				str=Number(num*100).toFixed(1);
			    str+="%";
			    return str;
			}else{
				return "0%";
			}
		}
		//新增
		function btnAdd() {
			
		
		//location.href = "settlePayNew.do?id=";
			
			location.href = "settlePayNew.do?" + $.param({
				'id':'',
				/* 'construct_project_id' : ${construct_project_id},
				'construct_supplier_id':${construct_supplier_id},  */
			});

		}
		
		
		//修改
		function btnUpdate(){
			var rows = $("#data").datagrid("getSelections");//获取表格数据
			if (rows.length == 0) {
				alert("请选择需要修改的行");
				return null;
			} else if (rows.length > 1) {
				alert("一次只能选择一行喔！");
				return null;
			} else if(rows[0].payed==0||rows[0].payed==undefined) {
				var id = rows[0].finance_settlepay_id;
				location.href = "settlePayNew.do?" + $.param({
					'id':id,
				});
				//location.href = "settlePayNew.do?id=" + id + "";
			}else{
				$.messager.alert("提示","已经有过付款不能修改！");
			}	
		}
		
		/***处理操作列的功能***/
		function cmdHanlder(cmd, row) {
			if (cmd == "pay") {
				location.href = "settlePayView.do?" + $.param({
					'id' : row.finance_settlepay_id, //传参项目部编号
				});
			}
		}

		//新增付款明细
		function payNumList() {
			var rows = $("#data").datagrid("getSelections");//获取表格数据
			if (rows.length == 0||rows.length > 1) {
				alert("请选择需要付款的一行");
				return null;
			}
			location.href = "settlePayNumList.do?id="+rows[0].finance_settlepay_id+"&payOwe="+rows[0].finance_settlepay_owe;
		}
		
		
		
		
		function btnDele() {
			var rows = $("#data").datagrid("getSelections");//获取表格数据
			if (rows.length == 0) {
				alert("请选择需要删除的行");
				return null;
			} else if (rows.length > 1) {
				alert("一次只能选择一行喔！");
				return null;
			}
			if(rows[0].payed==0||rows[0].payed==undefined) {
				$.post("delectPayList.do?finance_settlepay_id="
						+rows[0].finance_settlepay_id,function(data) {
					 if (data == "") {
							alert("删除成功");
							location.reload(true);
						} else {
							alert("删除失败");
						}
				}); 
			}else{
				$.messager.alert("提示","已经有过付款不能删除！");
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