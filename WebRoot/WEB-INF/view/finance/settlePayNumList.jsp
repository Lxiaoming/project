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

<style type="text/css">
fieldset {
	border: solid 1px #aaa;
}

.hideFieldset {
	border-left: 0;
	border-right: 0;
	border-bottom: 0;
}
</style>

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
							<li><label>付款时间：</label><input type="text"
								name="constuct_project_dep_name"
								, id="constuct_project_dep_name" class="easyui-validatebox"
								style="width: 120px" /></li>
							<li>&nbsp;&nbsp; <a class="easyui-linkbutton"
								data-options="iconCls:'icon-search'" href="javascript:;"
								id="btnSearch">查询</a></li>
							<li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton"
								iconCls="icon-edit" plain="true" onclick="addRow('edit');">修改付款</a></li>
							<li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton"
								iconCls="icon-add" plain="true" onclick="addRow('add');">新增付款</a></li>
							<li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton"
								iconCls="icon-cut" plain="true" onclick="btnDele();">删除付款</a></li>
							<li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton"
								iconCls="icon-ok" plain="true" onclick="btnConfirm();">确认付款</a>
							</li>
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
							<th halign="center" field="finance_settlepay_purNum_parentid"
								align="center" hidden="hidden" sortable="true" width="60px">批次号</th>
							<th halign="center" field="finance_settlepay_payNum_id"
								align="center" sortable="true" width="60px">付款号</th>
							<th halign="center" field="finance_settlepay_purNum_date"
								align="center" sortable="true" width="120px">付款时间</th>
							<th halign="center" field="finance_settlepay_purNum_payable"
								align="center" sortable="true" width="120px">应付</th>
							<th halign="center" field="finance_settlepay_purNum_nowpay"
								align="center" sortable="true" width="120px">本次申请付款</th>
							<th halign="center" field="finance_settlepay_purNum_paytype"
								align="center" sortable="true" width="120px" formatter="payType">本次付款类型</th>
							<th halign="center" field="finance_settlepay_purNum_paypercent"
								align="center" sortable="true" width="120px">本次付款百分比</th>
							<th halign="center" field="finance_settlepay_purNum_payed"
								align="center" sortable="true" width="180px">累计已付（不包含本次申请付款）</th>
							<th halign="center" field="finance_settlepay_purNum_owe"
								align="center" sortable="true" width="120px">未付</th>
							<th halign="center" field="finance_settlepay_purNum_status"
								align="center" sortable="true" width="120px"
								formatter="status_formatter">状态</th>
							<th halign="center" field="psn" sortable="true" width="120px"
								formatter="operate_formatter">操作</th>
						</tr>
					</thead>
				</table>
			</div>

			<div id="addrow" class="easyui-window" title="编辑页面" closed="true"
				modal="true" style="width:700px;height:500px;padding:5px;">

				<div region="north" title="用户基本信息" style="padding: 10px;"
					minHeight="350px">
					<form id="from1" method="POST">
						<table width="90%" class="content">

							<tr>

								<!-- <td ><input name="finance_settlepay_purNum_parentid"
									id="finance_settlepay_purNum_parentid" type="text"></input></td> -->

								<td>付款号:</td>
								<td><input name="finance_settlepay_payNum_id"
									readonly="readonly" id="finance_settlepay_payNum_id"
									type="text"></input></td>

								<td>付款时间:</td>
								<td><input name="finance_settlepay_purNum_date"
									id="finance_settlepay_purNum_date" class="easyui-datebox"></input></td>
							</tr>

							<tr>
								<td>付款类型：</td>
								<td><select id="finance_settlepay_purNum_paytype"
									name="finance_settlepay_purNum_paytype" style="width:142px;">
										<option value="0">选择类型</option>
										<option value="1">货到付款</option>
										<option value="2">安装完成</option>
										<option value="3">验收支付</option>
										<option value="4">质保金</option>
								</select></td>
								<td>付款百分比(%):</td>
								<td><input class="easyui-validatebox" onblur="changeData()"
									id="finance_settlepay_purNum_paypercent"
									name="finance_settlepay_purNum_paypercent" value="" /></td>

							</tr>
							<tr>

								<td>应付:</td>
								<td><input class="easyui-validatebox" type="text"
									name="finance_settlepay_purNum_payable" readonly="readonly"
									id="finance_settlepay_purNum_payable" /></td>
								<td>本次付款:</td>
								<td><input type="text" readonly="readonly"
									name="finance_settlepay_purNum_nowpay"
									id="finance_settlepay_purNum_nowpay" /></td>
							</tr>
							<tr>
								<td>累计已付（不包含本次申请付款）:</td>
								<td><input class="easyui-validatebox" type="text"
									readonly="readonly" name="finance_settlepay_purNum_payed"
									value="0" id="finance_settlepay_purNum_payed" /></td>
								<td>未付:</td>
								<td><input class="easyui-validatebox" type="text"
									readonly="readonly" name="finance_settlepay_purNum_owe"
									id="finance_settlepay_purNum_owe" /></td>
							</tr>

						</table>
					</form>


					<div region="south" style="padding: 10px; text-align: center;"
						border="false">
						<!-- <a class="easyui-linkbutton" 
							href="javascript:;" id="btnAudit" onclick="btnAudit()">提交审核</a>&nbsp;&nbsp;&nbsp;&nbsp; -->

						<a class="easyui-linkbutton" href="javascript:;" id="btnSave"
							onclick="conserve();">保存</a>&nbsp;&nbsp;&nbsp;&nbsp; <a
							class="easyui-linkbutton" href="javascript:;" id="btnCancel"
							onclick="$('#addrow').window('close')">取消</a>
					</div>
				</div>

				<div id="win" class="easyui-window"
					data-options="region:'center',title:'请选择值'" closed="true"
					style="height: 330px; width: 600px"></div>

			</div>

			<div id="viewShow" class="easyui-window" title="编辑页面" closed="true"
				modal="true" style="width:800px;height:600px;padding:5px;">

				<div region="north" title="用户基本信息" style="padding: 10px;"
					minHeight="300px">
					<table width="90%" class="content">

						<tr>
							<td>付款号:</td>
							<td><input name="finance_settlepay_payNum_id"
								readonly="readonly" id="view_finance_settlepay_payNum_id"
								type="text"></input></td>

							<td>付款时间:</td>
							<td><input name="finance_settlepay_purNum_date"
								id="view_finance_settlepay_purNum_date" class="easyui-datebox"></input></td>
						</tr>

						<tr>
							<td>付款类型：</td>
							<td><select id="view_finance_settlepay_purNum_paytype"
								name="finance_settlepay_purNum_paytype" style="width:142px;">
									<option value="0">选择类型</option>
									<option value="1">货到付款</option>
									<option value="2">安装完成</option>
									<option value="3">验收支付</option>
									<option value="4">质保金</option>
							</select></td>
							<td>付款百分比(%):</td>
							<td><input class="easyui-validatebox" onblur="changeData()"
								id="view_finance_settlepay_purNum_paypercent"
								name="finance_settlepay_purNum_paypercent" value="" /></td>

						</tr>


						<tr>

							<td>应付:</td>
							<td><input name="finance_settlepay_purNum_payable"
								readonly="readonly" id="view_finance_settlepay_purNum_payable"
								type="text"></input></td>

							<td>本次付款:</td>
							<td><input type="text"
								name="finance_settlepay_purNum_nowpay"
								id="view_finance_settlepay_purNum_nowpay" value="0" /></td>
						</tr>
						<tr>
							<td>累计已付（不包含本次申请付款）:</td>
							<td><input class="easyui-validatebox" type="text"
								readonly="readonly" name="finance_settlepay_purNum_payed"
								value="0" id="view_finance_settlepay_purNum_payed" /></td>
							<td>未付:</td>
							<td><input class="easyui-validatebox" type="text"
								readonly="readonly" name="finance_settlepay_purNum_owe"
								id="view_finance_settlepay_purNum_owe" /></td>
						</tr>

					</table>
					<br /> <br />
					<div id="audit">

						<div data-options="region:'center',border:false,"
							style="width:out;">

							<table id="history" class="easyui-datagrid"
								style="width:out;height:150px"
								data-options="iconCls: 'icon-edit',singleSelect: true,">
								<thead>


									<tr>
										<th data-options="field:'username',width:80">审核人</th>
										<th data-options="field:'MESSAGE_',width:300">审核意见</th>
										<th data-options="field:'center_name',width:100">中心</th>
										<th data-options="field:'department_name',width:200">部门</th>

									</tr>

								</thead>


							</table>

						</div>

						<div region="south" style="padding: 10px; text-align: center;"
							border="false">
							<a class="easyui-linkbutton" href="javascript:;"
								onclick="$('#viewShow').window('close')">返回</a>
						</div>
					</div>
				</div>


			</div>
		</div>





		<script type="text/javascript">
			var finance_settlepay_id = ${finance_settlepay_id};
			var payOwe = ${payOwe};
			function operate_formatter(value, row, index) {
				var html = "";

				html += '<a href="javascript:;" class="opt"    cmd="view" i="' + index + '">查看</a>&nbsp;';
				if (row.finance_settlepay_purNum_status == 0) {
					html += '<a href="javascript:;" class="opt"    cmd="payNumStart" i="' + index + '">启动流程</a>';
				}

				return html;
			}

			function changeData(e) {

				var payPercent = $("#finance_settlepay_purNum_paypercent")
						.val();
				if (payPercent != "") {
					var finance_settlepay_purNum_payable = $(
							"#finance_settlepay_purNum_payable").val();
					$("#finance_settlepay_purNum_nowpay")
							.attr(
									"value",
									parseInt(payPercent)
											* parseInt(finance_settlepay_purNum_payable)
											/ 100);
				}
			}

			function payType(value, row, index) {

				if (value == 1) {
					return "货到付款";
				} else if (value == 2) {
					return "安装完成";

				} else if (value == 3) {
					return "验收支付";

				} else if (value == 4) {
					return "质保金";

				}
			}

			//打开窗口
			function addRow(obj) {
				var allRows = $('#data').datagrid('getRows');
				var payed = 0;
				var isNew = 0;
				for (var int = 0; int < allRows.length; int++) {
					if (parseInt(allRows[int].finance_settlepay_purNum_status) != 0) {
						payed = parseInt(payed)
								+ parseInt(allRows[int].finance_settlepay_purNum_nowpay);
					}
					if (parseInt(5) != parseInt(allRows[int].finance_settlepay_purNum_status)) { //单据全部已经付款了才可以新增弹框
						isNew = 1;
					}
				}
				$("#finance_settlepay_purNum_payable").attr("value", payOwe);

				if (obj == 'edit') {
					var rows = $("#data").datagrid("getSelections");
					if (rows.length == 0) {
						$.messager.alert("提示", "请选择需要修改的行");
						return false;
					}
					if (rows.length > 1) {
						$.messager.alert("提示", "不能选择多行喔");
						return false;
					}
					if (rows[0].finance_settlepay_purNum_status == 0
							|| rows[0].finance_settlepay_purNum_status == 4) {
						$("#finance_settlepay_purNum_date").datebox("setValue",
								rows[0].finance_settlepay_purNum_date);
						$("#finance_settlepay_payNum_id").attr("value",
								rows[0].finance_settlepay_payNum_id);
						$("#finance_settlepay_purNum_date").attr("value",
								rows[0].finance_settlepay_purNum_date);

						$("#finance_settlepay_purNum_paytype").val(
								rows[0].finance_settlepay_purNum_paytype);
						$("#finance_settlepay_purNum_paypercent").val(
								rows[0].finance_settlepay_purNum_paypercent);

						$("#finance_settlepay_purNum_nowpay").attr("value",
								rows[0].finance_settlepay_purNum_nowpay);
						$("#finance_settlepay_purNum_payed").attr("value",
								payed);

						$("#finance_settlepay_purNum_owe").attr("value",
								parseInt(payOwe) - parseInt(payed));
						$('#addrow').window('open');
					} else {
						$.messager.alert("提示", "单据已提交不能修改！");
					}
				} else {
					if (isNew == 0) {
						$("#finance_settlepay_payNum_id").attr("value", "0");
						$("#finance_settlepay_purNum_date").attr("value", "");
						$("#finance_settlepay_purNum_nowpay").attr("value", "");
						$("#finance_settlepay_purNum_paytype")
								.attr("value", "");
						$("#finance_settlepay_purNum_paypercent").attr("value",
								"");
						$("#finance_settlepay_purNum_payed").attr("value",
								payed);
						$("#finance_settlepay_purNum_owe").attr("value",
								parseInt(payOwe) - parseInt(payed));
						$('#addrow').window('open');
					} else {
						$.messager.alert("提示", "存在未付款单不能新增");
					}
				}
			}

			/**  
			 * 确认付款
			 */
			function btnConfirm() {
				var rows = $("#data").datagrid("getSelections");
				if (rows.length == 0) {
					$.messager.alert("提示", "请选择需要确认的行");
					return false;
				}
				if (rows.length > 1) {
					$.messager.alert("提示", "不能选择多行喔");
					return false;
				}
				if (rows[0].finance_settlepay_purNum_status == 3) {
					var noPay = parseInt(rows[0].finance_settlepay_purNum_owe)
							- parseInt(rows[0].finance_settlepay_purNum_nowpay);
					$
							.post(
									"confirmPay.do",
									{
										finance_settlepay_payNum_id : rows[0].finance_settlepay_payNum_id,
										noPay : noPay
									}, function(result) {
										$('#data').datagrid('reload');
									});
				} else {
					$.messager.alert("提示", "单据不是审核状态不能点击此按钮");
				}
			}

			/**  
			 * 保存
			 */
			function conserve() {
				var finance_settlepay_purNum_nowpay = $(
						"#finance_settlepay_purNum_nowpay").val();
				var finance_settlepay_purNum_owe = $(
						"#finance_settlepay_purNum_owe").val();
				if (finance_settlepay_purNum_nowpay == ""
						|| parseInt(finance_settlepay_purNum_nowpay) == 0) {
					$.messager.alert("提示", "本次付款不能为空");

					return false;
				}
				if (parseInt(finance_settlepay_purNum_nowpay) > parseInt(finance_settlepay_purNum_owe)) {
					$.messager.alert("提示", "本次付款不能大于未付");

					return false;
				}
				$.post("add_payNumList.do?id=" + finance_settlepay_id, $(
						"#from1").serialize(), function(data) {
					if (data.msg != undefined) {
						$.messager.alert("提示", data.msg, "error");
						$('#data').datagrid('reload');
					} else {
						$.messager.alert("提示", "操作成功");

						$('#addrow').window('close');
						location.reload();
					}
				});
			}

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

				} else if (value == 5) {
					return "已付款";

				}
			}

			/***处理操作列的功能***/
			function cmdHanlder(cmd, row) {
				if (cmd == "view") {
					$.get("auditOption.do", {
						'bizId' : row.finance_settlepay_payNum_id
					}, function(data) {

						$('#history').datagrid('loadData', data);
					});
					$("#view_finance_settlepay_purNum_date").datebox(
							"setValue", row.finance_settlepay_purNum_date);
					$("#view_finance_settlepay_payNum_id").attr("value",
							row.finance_settlepay_payNum_id);
					$("#view_finance_settlepay_purNum_nowpay").attr("value",
							row.finance_settlepay_purNum_nowpay);

					$("#view_finance_settlepay_purNum_paytype").val(
							row.finance_settlepay_purNum_paytype);
					$("#view_finance_settlepay_purNum_paypercent").val(
							row.finance_settlepay_purNum_paypercent);
					$("#view_finance_settlepay_purNum_payed").attr("value",
							row.finance_settlepay_purNum_payed);
					$("#view_finance_settlepay_purNum_payable").attr("value",
							payOwe);
					$("#view_finance_settlepay_purNum_owe").attr("value",
							row.finance_settlepay_purNum_owe);
					$('#viewShow').window('open');

					/* 启动流程 */
				} else if (cmd == "payNumStart") {
					var bizId = row.finance_settlepay_payNum_id;

					$.messager
							.confirm(
									'提示!',
									'你确定启动吗？',
									function(r) {
										if (r) {
											$
													.post(
															"payNumStart.do",
															{
																'bizId' : bizId,
															},
															function(data) {
																if (data.Success) {
																	$.messager
																			.alert(
																					"提示",
																					data.Msg,
																					"info",
																					function() {
																						location.href = "findTaskList.do";
																					});
																} else {
																	$.messager
																			.alert(
																					"提示",
																					data.Msg,
																					"error");
																	return false;
																}
															});
										}
									});
				}

			}

			//批量删除处理
			function btnDele() {
				var rows = $("#data").datagrid("getSelections");//获取表格数据
				if (rows.length == 0) {
					$.messager.alert("提示", "请选择需要删除的行");
					return false;
				}
				if (rows[0].finance_settlepay_purNum_status == 0
						|| rows[0].finance_settlepay_purNum_status == 4) {
					var ids = [];
					for (var i = 0; i < rows.length; i++) {
						ids.push(rows[i].finance_settlepay_payNum_id);
					}
					$.ajax({
						type : 'POST',
						url : 'delect_payNum.do',
						data : {
							'cid' : JSON.stringify(ids)
						},
						success : function(data) {
							if (data.msg != undefined) {
								$.messager.alert("提示", data.msg, "error");
								$('#data').datagrid('reload');
							} else {
								$.messager.alert("提示", "操作成功！");
								$('#data').datagrid('reload');
							}
						}
					});
				} else {
					$.messager.alert("提示", "此单不能删除！");
				}
			}

			$(function() {

				$("#data").admin_grid({
					queryBtn : "#btnSearch",
					cmdHanlder : cmdHanlder,

				});
			});

			function wen(src) {
				var hrefs = "<iframe id='son' src='"
						+ src
						+ "' allowTransparency='true' style='border:0;width:99%;height:99%;padding-left:2px;' frameBorder='0'></iframe>";
				$("#win").html(hrefs);
				$("#win").window("open");
			}
		</script>
</body>
</html>