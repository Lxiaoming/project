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

							<li><label>节点名字：</label><input type="text"
								name="framework_name"  id="framework_name"
								class="easyui-validatebox" style="width: 120px" /></li>
								
								
							<li><label>公司:</label><input type="text"
								name="company"  id="company"
								class="easyui-validatebox" style="width: 120px" />
							</li>
							<li>&nbsp;&nbsp; <a class="easyui-linkbutton"
								data-options="iconCls:'icon-search'" href="javascript:;"
								id="btnSearch">查询</a></li>
							<li><a href="#" class="easyui-linkbutton" iconCls="icon-add"
								plain="true"
								onclick="addFrameWork();">添加</a></li>
							<li><a href="#" class="easyui-linkbutton" iconCls="icon-add"
								plain="true"
								onclick="deleFrameWork();">删除</a></li>
						</ul>
						<div class="clear"></div>
					</div>
				</div>
			</div>



			<div region="center" split="false" border="false"
				style="overflow: hidden; padding: 0 5px 5px 5px" id="dataList">
				<table id="data" title="" fit="true"
					data-options="collapsible: true,pagination:true,onDblClickRow: onDblClickRow
						,showFooter: true">
					<thead>
						<tr>
							
							<th halign="center" field="uc_framework_parentId"
								align="center" hidden="hidden" sortable="true" width="60px">上一级编号</th>
							<th halign="center" field="uc_framework_company"
								align="center" hidden="hidden" sortable="true" width="60px">公司编号</th>
									
							<th halign="center" field="company_name" align="center" 
								sortable="true" width="120px">公司</th>
							<th halign="center" field="uc_framework_id"
								align="center" hidden="hidden" sortable="true" width="60px">编号</th>
							<th halign="center" field="uc_framework_name" align="center"  sortable="true" width="120px">名字</th>
							<th halign="center" field="lastLev"
								align="center" sortable="true" width="120px">上一级</th>
							<th halign="center" field="uc_framework_describe" align="center"
								sortable="true" width="300px">描述</th>
							
							<th halign="center" field="psn" align="center" sortable="true"
								width="120px" formatter="operate_formatter">操作</th>
						</tr>
					</thead>
				</table>
			</div>


			<div id="frameWork" class="easyui-window"
				data-options="region:'center',title:'编辑'" closed="true"
				style="height: auto; width: 420px">
				<div class="easyui-panel" style="width:400px">
					<div style="padding:10px 60px 20px 60px">
						<form id="frame" method="post">
							<table cellpadding="5">
								<tr style="display: none">
									<td>uc_framework_id:</td>
									<td><input type="text" id="uc_framework_id"
										name="uc_framework_id" readonly="readonly" value="0"></input></td>
								</tr>
								<tr>
									<td><a href="javascript:void(0)"  
										onclick="wen('companyList.do')">公司:</br>(选择)</a></td>
									<td><input type="text" id="company_name"
										name="company_name" readonly="readonly"></input></td>
								</tr>
								<tr style="display: none">
									<td>uc_framework_company:</td>
									<td><input type="text" id="uc_framework_company"
										name="uc_framework_company" readonly="readonly"></input></td>
								</tr>
								<tr>
									<td>名字:</td>
									<td><input class="easyui-textbox" type="text"
										name="uc_framework_name"
										id="uc_framework_name"></input></td>
								</tr>
								<tr>
									<td><a href="javascript:void(0)"
										onclick="wen('frameWorkList.do')">上一级:(选择)</a></td>
									<td><input type="text" id="name"
										name="name" readonly="readonly"></input></td>
								</tr>
								<tr style="display: none">
									<td>parent:</td>
									<td><input type="text" id="uc_framework_parentId" value="0"
										name="uc_framework_parentId" readonly="readonly"></input></td>
								</tr>
							
								<tr>
									<td>描述:</td>
									<td colspan="3"><textarea style="width:200px;height:100px"
									name="uc_framework_describe" id="uc_framework_describe"></textarea></td>
								</tr>
							</table>
						</form>


						<div style="text-align:center;padding:5px">
							<a href="javascript:void(0)" class="easyui-linkbutton"
								onclick="save_frameWork()">确定</a>
						</div>
					</div>
				</div>
			</div>


			<div id="centers" class="easyui-window"
				data-options="region:'center',title:'请选择值'" closed="true"
				style="height: 400px; width: 700px"></div>
		</div>


	</div>






	<script type="text/javascript">
	
		$(function() {
			$("#data").admin_grid({
				queryBtn : "#btnSearch",
				excelBtn : "#btnExcel",
				cmdHanlder : cmdHanlder,
			});
		});

		function operate_formatter(value, row, index) {
			var html = "";
			html += '<a href="javascript:;" class="opt"    cmd="edit" i="' + index + '">修改</a>';
			return html;
		}



		function wen(src) {
			var hrefs = "<iframe id='son' src='"
					+ src
					+ "' allowTransparency='true' style='border:0;width:99%;height:99%;padding-left:2px;' frameBorder='0'></iframe>";
			$("#centers").html(hrefs);
			$("#centers").window("open");
		}
		
		function deleFrameWork(){
			var rows = $("#data").datagrid("getSelections");
			if (rows.length == 0) {
				alert("请选择删除行");
				return null;
			}
			if (rows.length > 1) {
				alert("不能选择多行喔");
				return null;
			}
			$.ajax({
				type : 'POST',
				url : 'deleFrameWork.do',
				traditional : true,
				data : {
					'ids' : rows[0].uc_framework_id
				},
				success : function(data) {
					if (data.Success) {
						alert(data.Msg);
						location.reload();
					} else {
						alert(data.Msg);
					}
				}
			});
			
		}
		
		
		function save_frameWork() {
			if($("#uc_framework_company").val()==0){
				alert("公司不能为空！");
				return false;
			}
			$.post("save_frameWork.do", $("#frame").serialize(), function(data) {
				if (data.erro == "") {
					alert("保存成功");
					location.reload();
					//$('#data').datagrid('reload');
				} else {
					alert(data.erro);
				}
			});
				
 
		}

		//新增
		function addFrameWork() {
			$("#company_name").val("");
			$("#uc_framework_id").val(0);
			$("#uc_framework_name").val("");
			$("#name").val("");
			$("#uc_framework_describe").val("");
			$("#uc_framework_company").val(0);
			$("#uc_framework_parentId").val(0);
			$("#frameWork").window("open");
		}

		/***处理操作列的功能***/
		function cmdHanlder(cmd, row) {
			
			if (cmd == "edit") {
					$("#company_name").val(
							row.company_name);
					$("#uc_framework_id").val(
							row.uc_framework_id);
					$("#uc_framework_name").val(
							row.uc_framework_name);
					$("#name").val(
							row.lastLev);
					$("#uc_framework_describe").val(
							row.uc_framework_describe);
					$("#uc_framework_company").val(
							row.uc_framework_company);
					$("#uc_framework_parentId").val(
							row.uc_framework_parentId);
					$("#frameWork").window("open");
			} 

		}


		function onDblClickRow(rowIndex, rowData) {
			$(window.parent.window.frameData(rowData));
	        $(window.parent.$("#centers").window("close"));

		};

		//子页面赋值
		function frameData(rowData){
			
			$("#name").val(rowData.uc_framework_name);
			$("#uc_framework_parentId").val(rowData.uc_framework_id);
			
		}
		
		
		function data(rowData) {
			
			$("#company_name").val(rowData.company_name);
			$("#uc_framework_company").val(rowData.company_id);
			
			$("#company").val(rowData.company_name);
			$("#companyId").val(rowData.company_id);
		}
	</script>

</body>
</html>