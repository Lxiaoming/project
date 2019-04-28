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

							<li><label>工人名字：</label><input type="text"
								name="construct_project_quantities_name"  id="construct_project_quantities_name"
								class="easyui-validatebox" style="width: 120px" /></li>

							<li>&nbsp;&nbsp; <a class="easyui-linkbutton"
								data-options="iconCls:'icon-search'" href="javascript:;"
								id="btnSearch">查询</a></li>
							<li><a href="#" class="easyui-linkbutton" iconCls="icon-add"
								plain="true"
								onclick="wen('workerCheck.do?departmentId=${construct_project_workTeam_departmentId}')">添加</a></li>

							<li><a href="#" class="easyui-linkbutton"
								onclick="multiplayer()">批量调动</a></li>

						</ul>
						<div class="clear"></div>
					</div>
				</div>
			</div>



			<div region="center" split="false" border="false"
				style="overflow: hidden; padding: 0 5px 5px 5px" id="dataList">
				<table id="data" title="" fit="true"
					data-options="collapsible: true,pagination:true
						,showFooter: true">
					<thead>
						<tr>
							<th field="ck" checkbox="true"></th>
							<th halign="center" field="supplierMod_worker_projectId"
								align="center" hidden="hidden" sortable="true" width="60px">项目编号</th>
							<th halign="center" field="supplierMod_worker_workTeamId"
								align="center" hidden="hidden" sortable="true" width="60px">班组id</th>
							<th halign="center" field="supplierMod_worker_userId"
								align="center" sortable="true" width="120px">姓名id</th>
							<th halign="center" field="username" align="center"
								sortable="true" width="120px">姓名</th>
							<th halign="center" field="supplierMod_worker_id" align="center" hidden="hidden"
								sortable="true" width="120px">id编号</th>
							<th halign="center" field="phone_number" align="center"
								sortable="true" width="120px">电话</th>
							<th halign="center" field="sex" align="center" sortable="true"
								width="120px" formatter="sex_formatter">性别</th>
							<th halign="center" field="isOnApply" align="center"
								sortable="true" width="120px" formatter="isOnApply_formatter">状态</th>
							<th halign="center" field="psn" align="center" sortable="true"
								width="120px" formatter="operate_formatter">操作</th>
							<!-- <th halign="center" field="construct_project_quantities_unit"
								align="center" sortable="true" width="120px">部门</th> -->
						</tr>
					</thead>
				</table>
			</div>


			<div id="workerApply" class="easyui-window"
				data-options="region:'center',title:'调动申请'" closed="true"
				style="height: auto; width: 420px">
				<div class="easyui-panel" style="width:400px">
					<div style="padding:10px 60px 20px 60px">
						<form id="apply" method="post">
							<table cellpadding="5">
								<tr>
									<td><a href="javascript:void(0)"
										onclick="wen('constructCheck.do')">调动项目(选择):</a></td>
									<td><input type="text" id="suppliermod_worker_apply_pro"
										name="suppliermod_worker_apply_pro" readonly="readonly"></input></td>
								</tr>
								<tr style="display: none">
									<td>调动项目id:</td>
									<td><input type="text" id="suppliermod_worker_apply_proId"
										name="suppliermod_worker_apply_proId" readonly="readonly"></input></td>
								</tr>

								<tr style="display: none">
									<td>班组id:</td>
									<td><input type="text"
										id="suppliermod_worker_apply_teamId"
										name="suppliermod_worker_apply_teamId" readonly="readonly"></input></td>
								</tr>

								<tr style="display: none">
									<td>原项目id:</td>
									<td><input type="text"
										id="suppliermod_worker_apply_oldProId"
										name="suppliermod_worker_apply_oldProId" readonly="readonly"></input></td>
								</tr>

								<tr style="display: none">
									<td>单据id:</td>
									<td><input type="text" class="easyui-textbox"
										id="suppliermod_worker_apply_id"
										name="suppliermod_worker_apply_id" readonly="readonly"></input></td>
								</tr>
								<tr id="reason">
									<td>原因:</td>
									<td><input class="easyui-textbox" type="text"
										name="suppliermod_worker_apply_reason"
										id="suppliermod_worker_apply_reason"></input></td>
								</tr>
								<tr style="display: none">
									<td>人员id:</td>
									<td><input class="easyui-textbox" type="text"
										name="suppliermod_worker_apply_userId"
										id="suppliermod_worker_apply_userId"></input></td>
								</tr>
							</table>
						</form>


						<div style="text-align:center;padding:5px">
							<a href="javascript:void(0)" class="easyui-linkbutton"
								onclick="workerApply()">确定</a>
						</div>
					</div>
				</div>
			</div>


			<div id="win" class="easyui-window"
				data-options="region:'center',title:'请选择值'" closed="true"
				style="height: 400px; width: 700px"></div>
		</div>


	</div>






	<script type="text/javascript">
		var type = "";
		var rows=[];
		$(function() {
			$("#data").admin_grid({
				queryBtn : "#btnSearch",
				excelBtn : "#btnExcel",
				cmdHanlder : cmdHanlder,
			});

		});

		function workerApply() {
			if(type=="apply"){
				var userid = ${userId};
				if (userid == null || userid == '' || userid == 0) {
					$.messager.alert("提示", "项目经理为空，请先完善", "info");
					return;
				}
				$.messager.confirm('提示！', '你确定提交吗？', function(r) {
					if (r) {
						$.post("workerApply.do?" + $("#apply").serialize(), {
							'username' : userid,

						}, function(data) {
							if (data.Success) {
								$.messager.alert("提示", data.Msg, "info",
										function() {
											location.href = "workerApplyList.do?"
													+ $.param({});
											location.reload(true);
										});
							} else {
								$.messager.alert("提示", data.Msg, "error");
							}
						});
					}
				});
			}else{
				
				var ids = [];
				for (var i = 0; i < rows.length; i++) {
					if(rows[i].isOnApply==0){
						ids.push(rows[i].supplierMod_worker_id);
					}
				}
							
				$.ajax({
					type : 'POST',
					url : 'multiplayerApply.do',
					traditional : true,
					data : {
						'suppliermod_worker_apply_proId' : $("#suppliermod_worker_apply_proId").val(),
						'suppliermod_worker_apply_teamId' : $("#suppliermod_worker_apply_teamId").val(),
						'cid' : JSON.stringify(ids)
					},
					success : function(data) {
						if (data.Success) {
							$.messager.alert("提示", data.Msg,"info", function() {
								location.href = "workerApplyList.do?" + $.param({
								});	
						
							});}else {
							$.messager.alert("提示", data.Msg,
									"error");

					}
						}
				});
		}
		}
		function operate_formatter(value, row, index) {
			var html = "";
			html += '<a href="javascript:;" class="opt"    cmd="apply" i="' + index + '">调动申请</a>';
			html += '<a href="javascript:;" class="opt"    cmd="record" i="' + index + '">调动记录</a>';
			return html;
		}

		function sex_formatter(value, row, index) {
			if (value == 1) {
				return "男";
			} else if (value == 2) {
				return "女";
			}
		}

		function isOnApply_formatter(value, row, index) {
			if (value == 0) {
				return "可调动";
			} else {
				return "调动申请中";
			}
		}

		function wen(src) {
			var hrefs = "<iframe id='son' src='"
					+ src
					+ "' allowTransparency='true' style='border:0;width:99%;height:99%;padding-left:2px;' frameBorder='0'></iframe>";
			$("#win").html(hrefs);
			$("#win").window("open");
		}

		function Getdata(rowData) {
			$
					.ajax({
						type : 'POST',
						url : 'save_Worker.do',
						data : {
							'construct_project_id' : ${construct_project_id},
							'construct_project_workTeam_id' : ${construct_project_workTeam_id},
							'supplierMod_worker_userId' : rowData.userid
						},
						success : function(data) {
							if (data.Success) {
								$.messager.alert("提示", data.Msg, "error");
								$('#data').datagrid('reload');
							} else {
								$.messager.alert("提示", data.Msg, "info",
										function() {
											$('#addrow').window('close');
											location.reload();
										});

							}

						}
					});

		}

		//批量调动
		function multiplayer() {
			rows = $("#data").datagrid("getSelections");//获取表格数据
			if (rows.length == 0) {
				alert("请选择需要调动的人");
				return null;
			}
			type = "multiplayer";
			document.getElementById("reason").style.display = "none";
			$("#workerApply").window("open");

		}

		/***处理操作列的功能***/
		function cmdHanlder(cmd, row) {
			if (cmd == "apply") {
				if (row.isOnApply == 0) {
					type = "apply";

					$("#suppliermod_worker_apply_userId").val(
							row.supplierMod_worker_userId);
					$("#suppliermod_worker_apply_id").val(0);
					$("#suppliermod_worker_apply_oldProId").val(
							parseInt(row.supplierMod_worker_projectId));

					$("#workerApply").window("open");
				} else {
					alert("不可调动");
				}

			} else {
				location.href = "workerApplyList.do?" + $.param({
					'supplierMod_worker_userId' : row.supplierMod_worker_userId
				});
			}

		}

		function constructData(rowData) {
			$("#suppliermod_worker_apply_proId").val(
					rowData.construct_project_id);
			$("#suppliermod_worker_apply_pro").val(
					rowData.construct_project_name);
			$("#suppliermod_worker_apply_teamId").val(
					rowData.construct_project_workTeam_id);
		}

		function onDblClickRow(rowIndex, rowData) {

			location.href = "workerAttendList.do?"
					+ $
							.param({
								'supplierMod_worker_userId' : rowData.supplierMod_worker_userId,
							});

		};

		//子页面赋值
		function pryData(data, index) {
			$("#userid").val(data.userid);
			$("#username").val(data.username);
		}
	</script>

</body>
</html>