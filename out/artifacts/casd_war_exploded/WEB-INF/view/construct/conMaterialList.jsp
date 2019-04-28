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

<script
	src="<%=request.getContextPath()%>/res/admin/scripts/jquery.ajaxfileupload.js"
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
							<li><label>材料系列：</label><input type="text"
								name="construct_material_seriesName" , id="construct_material_seriesName"
								class="easyui-validatebox" style="width: 120px" /></li>
							
							<li><label>材料名称：</label><input type="text"
								name="construct_project_quantities_name"  id="name"
								class="easyui-validatebox" style="width: 120px" /></li>
							<li><label>材料规格：</label><input type="text"
								name="construct_project_quantities_model"  id="model_name"
								class="easyui-validatebox" style="width: 120px" /></li>
									
							<li>&nbsp;&nbsp; <a class="easyui-linkbutton"
								data-options="iconCls:'icon-search'" href="javascript:;"
								id="btnSearch">查询</a></li>
							<!-- <li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton"
								iconCls="icon-cancel" plain="true" onclick="btnDele();">删除行</a></li> -->
							<li><a href="#" class="easyui-linkbutton" iconCls="icon-add"
								plain="true" onclick="addRow('add');">新增行</a></li>
							<li><a href="#" class="easyui-linkbutton"
								iconCls="icon-edit" plain="true" onclick="addRow('edit');">修改</a></li>
							<!-- <li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton"
								iconCls="icon-image_add" plain="true"
								onclick="$('#uploads').window('open')">导入Excel</a></li> -->
						</ul>
						<div class="clear"></div>
					</div>
				</div>
			</div>



			<div region="center" split="false" border="false"
				style="overflow: hidden; padding: 0 5px 5px 5px" id="dataList">
				<table id="data" title="" fit="true"
					data-options="collapsible: true,pagination:true,rownumbers:true
						,showFooter: true">
					<thead>
						<tr>
							<th id="ds" halign="center"
								field="construct_project_quantities_conId" align="center"
								hidden="hidden" sortable="true" width="60px">项目编号</th>
							<th id="ds" halign="center"
								field="construct_project_quantities_id" align="center"
								sortable="true" width="60px">工程量编号</th>
								
							<th halign="center" field="construct_material_seriesName"
								align="center" sortable="true" width="120px">材料系列</th>		
								
							<th halign="center" field="construct_material_name"
								align="center" sortable="true" width="120px">材料名称</th>
							<th halign="center" field="construct_material_model_name"
								align="center" sortable="true" width="120px">型号规格</th>
							
							<th halign="center" field="oldMaterial"
								align="center" sortable="true" width="120px">原材料名称与规格</th>
							
							<th halign="center" field="construct_project_quantities_modelId"
								hidden="hidden" align="center" sortable="true" width="120px">型号规格id</th>


							<th halign="center" field="construct_material_model_unit"
								align="center" sortable="true" width="120px">单位</th>
							<th halign="center" field="construct_project_quantities_num"
								align="center" sortable="true" width="120px">主材数量</th>
							<th halign="center" field="construct_project_quantities_auxiliaryNum"
								align="center" sortable="true" width="120px">辅材数量</th>
							<th halign="center" field="purNum"
								align="center" sortable="true" width="120px">已采购量</th>	
							<th halign="center" field="construct_project_quantities_price"
								align="center" sortable="true" width="120px">合同单价</th>
							<th halign="center" field="construct_project_quantities_remarks"
								align="center" sortable="true" width="120px">备注</th>
							<th halign="center" field="psn" align="center" sortable="true"
								width="120px" formatter="operate_formatter">操作</th>
						</tr>
					</thead>
				</table>
			</div>

			<div id="addrow" class="easyui-window" title="新增合同工程量" closed="true"
				modal="true" style="width:700px;height:500px;padding:5px;">

				<div region="north" title="用户基本信息" style="padding: 10px;"
					minHeight="350px">
				<form id="ff" action="" method="post">
						<table width="90%" class="content">

							<tr>
								<td hidden="hidden"><input
									name="construct_project_quantities_conId"
									id="construct_project_quantities_conId" value="${project_id}"
									type="text"></input></td>
								<td hidden="hidden"><input type="text"
									name="construct_project_quantities_id"
									id="construct_project_quantities_id" value="0" /></td>



								<td><a href="javascript:void(0)"
									onclick="wen('materialListCheck.do')">材料名称(选择):</a></td>
								<td><input name="construct_project_quantities_name"
									id="construct_project_quantities_name" type="text"></input></td>
								<td style="display: none">型号规格编号:</td>
								<td style="display: none"><input
									name="construct_project_quantities_modelId"
									id="construct_material_modelId" type="text"></input></td>

								<td>型号规格:</td>
								<td><input name="construct_project_quantities_model"
									id="construct_project_quantities_model" type="text"></input></td>
							</tr>

							<tr>
								<td>主材数量:</td>
								<td><input type="text"
									name="construct_project_quantities_num" id="quantities_num"
									value="0" /></td>
								<td>辅材数量:</td>
								<td><input  type="text"
									name="construct_project_quantities_auxiliaryNum"
									id="construct_project_quantities_auxiliaryNum" value="0"/></td>
							</tr>
							<tr>
								<td>单位:</td>
								<td><input class="easyui-validatebox" type="text"
									name="construct_project_quantities_unit"
									id="construct_project_quantities_unit" /></td>
								<td>合同单价:</td>
								<td><input class="easyui-validatebox" type="text"
									name="construct_project_quantities_price" value="0"
									id="quantities_price" /></td>
							</tr>
							<tr>
								<td>备注:</td>
								<td><input class="easyui-validatebox" type="text"
									name="construct_project_quantities_remarks"
									id="quantities_remarks" /></td>
								<td></td>
								<td></td>
							</tr>

						</table>
					</form>
					<br /> <br /> <br />
					<div region="south" style="padding: 10px; text-align: center;"
						border="false">
						<a class="easyui-linkbutton"
							data-options="iconCls:'icon-system_save'" href="javascript:;"
							id="btnSave" onclick="conserve();">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
						<a class="easyui-linkbutton"
							data-options="iconCls:'icon-system_close'" href="javascript:;"
							id="btnCancel" onclick="$('#addrow').window('close')">取消</a>
					</div>
				</div>
				<div id="uploads" class="easyui-window"
					data-options="region:'center',title:'请选择值'" closed="true"
					style="height: 100px; width: 400px;padding: 10px;">

					<form id="jvForm" action="" method="post"
						enctype="multipart/form-data">
						<table>
							<tbody>

								<tr>

									<td width="80%"><input name="pic" type="file" id="file" />
										<a class="easyui-linkbutton"
										data-options="iconCls:'icon-system_save'"
										onclick="uploadPic();" href="javascript:;" id="btnSaveExp">导入Excel</a>

									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</div>
				<div id="win" class="easyui-window"
					data-options="region:'center',title:'请选择值'" closed="true"
					style="height: 400px; width: 700px"></div>
			</div>


			<div id="view" class="easyui-window" title="查看同工程量" closed="true"
				modal="true" style="width:700px;height:450px;padding:5px;">

				<div region="north" style="padding: 10px;" minHeight="350px">
					<form id="from1" action="##" method="POST">
						<table width="90%" class="content">

							<tr>
								<td hidden="hidden"><input name="quantities_conId"
									id="quantities_conId" value="${project_id}" type="text"></input></td>
								<td hidden="hidden"><input type="text" name="quantities_id"
									id="quantities_id" value="0" /></td>


								<td>材料名称:</td>
								<td><input name="quantities_name" id="quantities_name"
									type="text"></input></td>

								<td style="display: none">型号规格编号:</td>
								<td style="display: none"><input name="quantities_modelId"
									id="quantities_modelId" type="text"></input></td>

								<td>型号规格:</td>
								<td><input name="quantities_model" id="quantities_model"
									type="text"></input></td>
							</tr>

							<tr>
								<td>主材数量:</td>
								<td><input type="text" name="num" id="num" value="0" /></td>
								<td>辅材数量:</td>
								<td><input type="text"
									name="auxiliaryNum" id="auxiliaryNum" /></td>
							</tr>
							<tr>
								<td>单位:</td>
								<td><input class="easyui-validatebox" type="text"
									name="quantities_unit" id="quantities_unit" /></td>
								<td>合同单价:</td>
								<td><input class="easyui-validatebox" type="text"
									name="price" value="0" id="price" /></td>
							</tr>
							<tr>
								<td>备注:</td>
								<td><input class="easyui-validatebox" type="text"
									name="remarks" id="remarks" /></td>
								<td></td>
								<td></td>
							</tr>

						</table>
						<br/>
						<!-- <div id="exist">
							<table class="easyui-datagrid" style="width:555px;height:250px" id="oldData"
								data-options="singleSelect:true,collapsible:true">
					<thead>
						<tr>
							<th  halign="center"
								field="construct_project_quantities_conId" align="center"
								hidden="hidden" sortable="true" width="60px">项目编号</th>
							<th  halign="center"
								field="construct_project_quantities_id" align="center"
								sortable="true" width="60px">编号</th>
								
							<th halign="center" field="construct_project_quantities_name"
								align="center" sortable="true" width="120px">材料名称</th>
							<th halign="center" field="construct_project_quantities_model"
								align="center" sortable="true" width="120px">型号规格</th>

							<th halign="center" field="construct_project_quantities_modelId"
								hidden="hidden" align="center" sortable="true" width="120px">型号规格id</th>
							
							<th halign="center" field="construct_project_quantities_unit"
								align="center" sortable="true" width="120px">单位</th>
							<th halign="center" field="construct_project_quantities_num"
								align="center" sortable="true" width="120px">数量</th>
							<th halign="center" field="construct_project_quantities_price"
								align="center" sortable="true" width="120px">合同单价</th>
							<th halign="center" field="construct_project_quantities_remarks"
								align="center" sortable="true" width="120px">备注</th>
						</tr>
					</thead>
								
							</table>
							</div> -->
					</form>
				</div>
			</div>


		</div>
	</div>





	<script type="text/javascript">
		function operate_formatter(value, row, index) {
			var html = "";

			html += '<a href="javascript:;" class="opt"  plain:true"  cmd="view" i="' + index + '">查看</a>';
			return html;
		}
		
		
		function dateformat(value, row, index) {
			if (value == undefined)
				return "";
			var date = new Date(value);
			var pattern = "yyyy-MM-dd hh:mm:ss";
			return date.format(pattern);
		}   
		
		//打开窗口
		function addRow(obj) {
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

				$("#construct_project_quantities_id").attr("value",
						rows[0].construct_project_quantities_id);
				$("#construct_material_modelId").attr("value",
						rows[0].construct_project_quantities_modelId);				
				$("#construct_project_quantities_name").attr("value",
						rows[0].construct_material_name);
				$("#construct_project_quantities_model").attr("value",
						rows[0].construct_material_model_name);
				$("#construct_project_quantities_unit").attr("value",
						rows[0].construct_material_model_unit);
				$("#quantities_num").attr("value",
						rows[0].construct_project_quantities_num);
				$("#construct_project_quantities_auxiliaryNum").val(rows[0].construct_project_quantities_auxiliaryNum==undefined?0:rows[0].construct_project_quantities_auxiliaryNum);
				$("#quantities_remarks").attr("value",
						rows[0].construct_project_quantities_remarks);
				$("#quantities_price").val(
						rows[0].construct_project_quantities_price);
			//	console.log(rows[0].construct_project_quantities_num);
				if(rows[0].construct_project_quantities_num != 0){
					$("#quantities_num").prop("readonly","readonly");
				}else{
					$("#quantities_num").removeProp("readonly","readonly");
				}
				$('#addrow').window('open');
			} else {
				$("#construct_project_quantities_id").attr("value", "0");
				$("#construct_project_quantities_name").attr("value", "");
				$("#construct_project_quantities_model").attr("value", "");
				$("#construct_project_quantities_unit").attr("value", "");
				$("#quantities_num").attr("value", "0");
				$("#construct_project_quantities_auxiliaryNum").val("0");
				$("#quantities_remarks").attr("value", "");
				$("#quantities_price").val("0");
				$("#quantities_num").removeProp("readonly","readonly");
				$('#addrow').window('open');
			}
		}

		/**  
		 * 新增
		 */
		 function conserve() {
			var conId=$("#construct_project_quantities_conId").val();
			
			var id=$("#construct_project_quantities_id").val();
			
			var name=$("#construct_project_quantities_name").val();
			
			var model=$("#construct_project_quantities_model").val();
			
			var modelId=$("#construct_material_modelId").val();
			
			var unit=$("#construct_project_quantities_unit").val();
			
			var num=$("#quantities_num").val();
			
			var auxiliaryNum=$("#construct_project_quantities_auxiliaryNum").val();
			
			var price=$("#quantities_price").val();

			var remarks=$("#quantities_remarks").val();
			
				$.ajax({
					type : 'POST',
					url : 'add_quantities.do',
					data : {'conId':conId,
						    'id':id,
						    'name':name,
						    'model':model,
						    'modelId':modelId,
						    'unit':unit,
						    'num':num,
						    'auxiliaryNum':auxiliaryNum,
						    'price':price,
						    'remarks':remarks,
				       	},success : function(data) {

								if (data.Success==0) {
									alert(data.Msg);
									$('#data').datagrid('reload');
								} else if (data.Success==1) {
									$.messager.confirm('继续操作', data.Msg, function(r) {
										if (r) {
											$.ajax({
												type : 'POST',
												url : 'sum_quantities.do',
												data : { 
														'conId':conId,
													    'modelId':modelId,
													    'num':num,
													    'auxiliaryNum':auxiliaryNum
											       	},
											       	success : function(data) {
											       		alert("已合并");
														$('#data').datagrid('reload');
											       	}
											       	
											});
										}else{
											alert("已取消");
										}
								
									});
								}else{
									alert(data.Msg);
								}
							}
							});
			}

		/***处理操作列的功能***/
		function cmdHanlder(cmd, rows) {
			if (cmd == "view") {
				$("#quantities_id").attr("value",
						rows.construct_project_quantities_id);
				$("#quantities_name").attr("value",
						rows.construct_material_name);
				$("#quantities_model").attr("value",
						rows.construct_material_model_name);
				$("#quantities_unit").attr("value",
						rows.construct_material_model_unit);
				$("#num").attr("value", rows.construct_project_quantities_num);
				$("#auxiliaryNum").val(rows.construct_project_quantities_auxiliaryNum);
				$("#remarks").attr("value",
						rows.construct_project_quantities_remarks);
				$("#price").val(rows.construct_project_quantities_price);
				$("#quantities_modelId").val(
						rows.construct_project_quantities_modelId);

						/* $.ajax({
							type : 'POST',
							url : 'exis_repetition.do',
							data : {
								'modelId' : rows.construct_project_quantities_modelId,
								'quantities_id' : rows.construct_project_quantities_id,
								'quantities_conId' : rows.construct_project_quantities_conId
							},
							success : function(data) {
								if (data.data.length != 0) {
									$('#exist').show();
									$('#oldData').datagrid('loadData', data.data);
								}else{
									$('#exist').hide();
								}
							}
						}); */

				$('#view').window('open');
			}

		}

		function uploadPic() {
			var quantities_conId = $("#construct_project_quantities_conId")
					.val();

			var fileName = $("#file").val();
			if (fileName == "") {
				$.messager.alert("提示", "请选择文件！");
				return false;
			}
			var file_typename = fileName.substring(fileName.lastIndexOf('.'),
					fileName.length);

			if (file_typename != '.xlsx') {
				$.messager.alert("提示", "文件格式错误，请选择文件格式为.xlsx");
				return false;
			}

			$.messager.confirm('继续操作', '确定导入吗?', function(r) {
				if (r) {
					// 上传设置  
					$.messager.progress();
					$('#jvForm').form(
							'submit',
							{
								url : 'cp_quantities_exl.do',

								onSubmit : function(data) {
									//参数
									data.quantities_conId = quantities_conId;

									//导入成功后的要处理的操作
								},
								success : function(data) {

									$.messager.progress("close");
									var data = eval('(' + data + ')');
									if (data.Success) {

										$.messager.alert("提示", "操作成功！", "info",
												function() {
													location.reload();
												});
									} else {
										$.messager.alert("系统提示", "上传出错，"
												+ data.Msg, "info");
									}
								}
							});
				}
			});
		}

		//批量删除处理
		function btnDele() {
			
			var rows = $("#data").datagrid("getSelections");
			if (rows.length == 0) {
				 $.messager.alert("操作提示","请选择需要删除的行!");
				return null;
			}
			if (rows.length > 1) {
				 $.messager.alert("操作提示","只能选择一行!");
				return null;
			}
			if(confirm("确定要删除吗，请慎重选择！")){
				$.ajax({
					type : 'POST',
					url : 'delete_quantities.do',
					traditional : true,
					data : {
						'cid' : rows[0].construct_project_quantities_id
					},
					success : function(data) {
						if (data.msg!=undefined) {
							$.messager.alert("操作提示",data.msg);
							$('#data').datagrid('reload');
						} else {
							
						    $.messager.alert("操作提示","操作成功！");
						    $('#data').datagrid('reload');
						}
					}
				});
			}
			
			/* var rows = $("#data").datagrid("getSelections");//获取表格数据
			if (rows.length == 0) {
				$.messager.alert("提示", "请选择需要删除的行");
				return false;
			} else {
				var ids = [];
				for (var i = 0; i < rows.length; i++) {
					ids.push(rows[i].construct_project_quantities_id);
				}

				$.ajax({
					type : 'POST',
					url : 'delete_quantities.do',
					data : {
						'cid' : JSON.stringify(ids)
					},
					success : function(data) {
						if (data.msg != undefined) {
							$.messager.alert("提示", data.msg, "error");
							$('#data').datagrid('reload');
						} else {
							$.messager.alert("提示", "操作成功！");
							$('#addrow').window('close');
							$('#data').datagrid('reload');
						}
					}
				});
			} */
		};

		$(function() {
			$("#data").admin_grid({
				queryBtn : "#btnSearch",
				excelBtn : "#btnExcel",
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