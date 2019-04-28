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
							<li><label>材料类别：</label><input type="text"
								name="material_category"
								id="material_category"
								class="easyui-validatebox" style="width: 120px" /></li>

							<li><label>材料名称：</label><input type="text"
								name="material_name"
								id="material_name" class="easyui-validatebox"
								style="width: 120px" /></li>
							<li><label>材料规格：</label><input type="text"
								name="material_model_name"
								id="material_model_name" class="easyui-validatebox"
								style="width: 120px" /></li>
							
							
							<li>&nbsp;&nbsp; <a class="easyui-linkbutton"
								data-options="iconCls:'icon-search'" href="javascript:;"
								id="btnSearch">查询</a></li>
							<li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton"
								iconCls="icon-cancel" plain="true" onclick="btnDele();">删除行</a></li>
							<li><a href="#" class="easyui-linkbutton" iconCls="icon-add"
								plain="true" onclick="addRow('add');">新增行</a></li>
							<li><a href="#" class="easyui-linkbutton"
								iconCls="icon-edit" plain="true" onclick="addRow('edit');">修改</a></li>
							<li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton"
								iconCls="icon-image_add" plain="true"
								onclick="$('#uploads').window('open')">导入Excel</a></li>
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
							<th halign="center" field="construct_Aparty_material_constructId"
								align="center" hidden="hidden" sortable="true" width="60px">项目编号</th>
							<th halign="center" hidden="hidden"
								field="construct_Aparty_material_id" align="center"
								sortable="true" width="60px">编号</th>

							<th halign="center" field="construct_Aparty_material_category"
								align="center" sortable="true" width="120px">材料类别</th>

							<th halign="center" field="construct_Aparty_material_name"
								align="center" sortable="true" width="120px">材料名称</th>
							<th halign="center" field="construct_Aparty_material_model"
								align="center" sortable="true" width="600px">型号规格</th>
							<th halign="center" field="construct_Aparty_material_unit"
								align="center" sortable="true" width="120px">单位</th>
							<th halign="center" field="construct_Aparty_material_num"
								align="center" sortable="true" width="120px">合同工程量</th>
							<th halign="center" field="sum"
								align="center" sortable="true" width="120px">已采购数量</th>
							<th halign="center" field="remainNum" formatter="remainNum"
								align="center" sortable="true" width="120px">可采购数量</th>		
							<th halign="center" field="construct_Aparty_material_remark"
								align="center" sortable="true" width="120px">备注</th>
						</tr>
					</thead>
				</table>
			</div>

			<div id="addrow" class="easyui-window" title="新增合同工程量" closed="true"
				modal="true" style="width:700px;height:500px;padding:5px;">

				<div region="north" title="用户基本信息" style="padding: 10px;"
					minHeight="350px">
					<form id="aPartyMaterial" action="" method="post">
						<table width="90%" class="content">
							<tr>
								<td hidden="hidden"><input
									name="construct_Aparty_material_id"
									id="construct_Aparty_material_id" value=""
									type="text"></input></td>
								<td hidden="hidden"><input type="text"
									name="construct_Aparty_material_constructId"
									id="construct_Aparty_material_constructId" value="" /></td>
								<td>材料类别</td>
								<td><input name="construct_Aparty_material_category"
									id="construct_Aparty_material_category" type="text"></input></td>
								<td>材料名称</td>
								<td><input name="construct_Aparty_material_name"
									id="construct_Aparty_material_name" type="text"></input></td>
							</tr>

							<tr>
								<td>型号规格:</td>
								<td><input name="construct_Aparty_material_model"
									id="construct_Aparty_material_model" type="text"></input></td>
								<td>单位:</td>
								<td><input class="easyui-validatebox" type="text"
									name="construct_Aparty_material_unit"
									id="construct_Aparty_material_unit" /></td>

							</tr>
							<tr id="construct_Aparty_material_num_td">
								
							</tr>

						</table>
					</form>
					<br /> <br /> <br />
					<div region="south" style="padding: 10px; text-align: center;"
						border="false">
						<a class="easyui-linkbutton"
							data-options="iconCls:'icon-system_save'" href="javascript:;"
							id="btnSave" onclick="save_aPartyMaterial();">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
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
			</div>
		</div>
	</div>





	<script type="text/javascript">

		function dateformat(value, row, index) {
			if (value == undefined)
				return "";
			var date = new Date(value);
			var pattern = "yyyy-MM-dd hh:mm:ss";
			return date.format(pattern);
		}

		
		function remainNum(value, row, index){
			
			if(row.construct_Aparty_material_num!=undefined){
				return parseInt(row.construct_Aparty_material_num) - parseInt(row.sum==undefined?0:row.sum);
			}else{
				return 0;

			}

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
				var htm = 	"<td>备注:</td>"
							+"<td><input class='easyui-validatebox' type='text'"
							+"name='construct_Aparty_material_remark'"
							+"id='construct_Aparty_material_remark' /></td>"
				  			+"<td></td>"
							+"<td></td>";
				$("#construct_Aparty_material_num_td").html("");
				$("#construct_Aparty_material_num_td").append(htm);
				
				$("#construct_Aparty_material_id").attr("value",
						rows[0].construct_Aparty_material_id);
				$("#construct_Aparty_material_name").attr("value",
						rows[0].construct_Aparty_material_name);
				$("#construct_Aparty_material_unit").attr("value",
						rows[0].construct_Aparty_material_unit);
				$("#construct_Aparty_material_category").attr("value",
						rows[0].construct_Aparty_material_category);
				$("#construct_Aparty_material_num").attr("value",
						rows[0].construct_Aparty_material_num);
				$("#construct_Aparty_material_remark").attr("value",
						rows[0].construct_Aparty_material_remark);
				$("#construct_Aparty_material_constructId").attr("value",
						rows[0].construct_Aparty_material_constructId);
				$("#construct_Aparty_material_model").val(
						rows[0].construct_Aparty_material_model);
				$('#addrow').window('open');
			} else {
				var htm =   "<td >数量:</td>"
					+"<td><input type='text'"
					+"name='construct_Aparty_material_num' id='construct_Aparty_material_num'"
					+"value='0' /></td>"
					+"<td>备注:</td>"
					+"<td><input class='easyui-validatebox' type='text'"
					+"name='construct_Aparty_material_remark'"
					+"id='construct_Aparty_material_remark' /></td>";
				$("#construct_Aparty_material_num_td").html("");
				$("#construct_Aparty_material_num_td").append(htm);
				
				$("#construct_Aparty_material_id").attr("value", 0);
				$("#construct_Aparty_material_name").attr("value", "");
				$("#construct_Aparty_material_unit").attr("value", "");
				$("#construct_Aparty_material_category").attr("value", "");
				$("#construct_Aparty_material_num").attr("value", 0);
				$("#construct_Aparty_material_remark").attr("value", "");
				$("#construct_Aparty_material_constructId").val(${construct_project_id});
				$("#construct_Aparty_material_model").val("");
				
				$('#addrow').window('open');
			}
		}
		
		
		
		/**  
		 * 新增
		 */
		function save_aPartyMaterial() {
			
			var construct_Aparty_material_name=$("#construct_Aparty_material_name").val();
			if(construct_Aparty_material_name==""){
				alert("材料名称不能为空!");
				return false;
			} 
			
			var construct_Aparty_material_unit=$("#construct_Aparty_material_unit").val();
			if(construct_Aparty_material_unit==""){
				alert("单位不能为空!");
				return false;
			} 
			
			var construct_Aparty_material_category=$("#construct_Aparty_material_category").val();
			if(construct_Aparty_material_category==""){
				alert("类别不能为空!");
				return false;
			} 
			
			var formData = new FormData(document.getElementById("aPartyMaterial"));
			$.ajax({
				url : 'save_aPartyMaterial.do',
				type : 'POST',
				data : formData,
				async : false,
				cache : false,
				contentType : false,
				processData : false,
				success : function(data) {
					
					$('#addrow').window('close');
					if (data.Success) {
						$.messager.alert("提示", data.Msg, "info", function() {
							$('#data').datagrid('reload');						});

					} else {
						$.messager.alert("提示", data.Msg, "error");

					}
				},
				error : function(data) {
					$.messager.alert("提示", data.Msg, "");
				}
			});
		}

		/***处理操作列的功能***/
		function cmdHanlder(cmd, rows) {

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
								url : 'cp_aParty_exl.do',

								onSubmit : function(data) {
									//参数
									data.construct_project_id = ${construct_project_id};

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

			var rows = $("#data").datagrid("getSelections");//获取表格数据
			if (rows.length == 0) {
				$.messager.alert("提示", "请选择需要删除的行");
				return false;
			} else {
				var ids = [];
				for (var i = 0; i < rows.length; i++) {
					ids.push(rows[i].construct_Aparty_material_id);
				}

				$.ajax({
					type : 'POST',
					url : 'delete_aPartyMaterial.do',
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
			}
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