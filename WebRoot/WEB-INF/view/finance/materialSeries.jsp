<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="PowerCheck" prefix="shop"%>
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
					style="overflow: hidden; padding: 5px 5px 2px 3px;">

					<div class="search">
						<ul>
							<li><label>材料系列：</label><input type="text" name="construct_material_seriesName"
								id="searchId" value="" class="easyui-validatebox"
								style="width: 120px" /></li>
							

							<li>&nbsp;&nbsp; <a class="easyui-linkbutton"
								data-options="iconCls:'icon-search'" href="javascript:;"
								id="btnSearch">查询</a></li>
								
							<li><a href="#" class="easyui-linkbutton"
									iconCls="icon-image_add" plain="true"
									onclick="$('#uploads').window('open')">导入Excel</a>	</li>
							
							<li>
							<shop:permission code="ADD_MATERIAL">
							<a id="btnNew" onclick="btnNew('save');"
								class="easyui-linkbutton" iconCls="icon-add" plain="true">新增材料系列</a>
							</li>
							<li>	
									<a id="btnUpdate" onclick="btnNew('update');" class="easyui-linkbutton" iconCls="icon-edit"
										plain="true">修改材料系列</a>	
							</li>			
							<li>			
							</shop:permission>	
							<shop:permission code="DELETE_MATERIAL">						
									
									 <a id="btnDele"  onclick="btnDele();" class="easyui-linkbutton" iconCls="icon-cut"
										plain="true">删除材料系列</a> 
										</shop:permission>
								</li>		
								
						</ul>
					</div>
					<br />

					<div>
					
						<ul>
						
							
							
							
								
						</ul>
					
					</div>
				</div>

				<div region="center" split="false" border="false"
					style="overflow: hidden; padding: 0px 5px 5px 5px" id="dataList">

					<table id="data" title="" fit="true"
						data-options="collapsible: true,pagination:true,onDblClickRow:onDblClickRow,rownumbers:true">
						<thead>
							<tr>
								<th id="ds" halign="center" field="construct_material_seriesID" align="center" hidden="hidden"
									sortable="true" width="60px">材料系列编号</th>
								<th halign="center" field="construct_material_num" align="center"
									sortable="true" width="120px">系列编号</th>	
								<th halign="center" field="construct_material_seriesName" align="center"
									sortable="true" width="120px">材料系列</th>
								<th halign="center" field="remarks" align="center" 
									sortable="true" width="150px">描述</th>
								<th halign="center" field="psn" align="center" sortable="true"
									width="120px" formatter="operate_formatter">操作</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>

		</div>
		
	<div id="w" class="easyui-window" title='修改信息'
				data-options="modal:true,closed:true,iconCls:'icon-save'"
				style="width:700px;height:400px;padding:10px;">
				<form id="form1" method="post">

					<div style="margin:10px 0;">
						<table>
							<td >材料系列id：<input type="text" 
								name="construct_material_seriesID" id="construct_material_seriesID" readonly="readonly" value="0" /><td>
							<td>材料系列编号：<input type="text"
								name="construct_material_num" id="construct_material_num" readonly="readonly" value="0" /><td>	
							<td>材料系列：<input type="text"
								name="construct_material_seriesName" id="construct_material_seriesName" /><td>
							<td>描述：<input type="text" name="remarks" id="remarks" /><td>
						</table>
					</div>
				</form>

				<table class="easyui-datagrid" id="datas" toolbar="#tb"
					data-options="singleSelect:true,collapsible:true,onDblClickRow:onDblClickRow">
					<thead>
						<tr>
							<th data-options="field:'construct_supplier_id',width:80">供应商编号</th>
							<th data-options="field:'construct_supplier_name',width:80,align:'right'">供应商</th>
						</tr>
					</thead>
				</table>

				<div id="tb" style="padding:5px;">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						onclick="add()">添加</a> <a href="javascript:void(0)"
						class="easyui-linkbutton" onclick="removeit()">删除</a>
				</div>

				<br /> <br /> <br />
				<div style="text-align:center">

					<a class="easyui-linkbutton" href="javascript:;" id="btnSave"
						onclick="btnSaveSeries()">提交</a> &nbsp;&nbsp;&nbsp;&nbsp; <a
						class="easyui-linkbutton" href="javascript:;" id="btnCancel"
						onclick="closeDown()">取消</a>
				</div>

			</div>			

			<div id="win" class="easyui-window" title='赋值'
				data-options="modal:true,closed:true,iconCls:'icon-save'"
				style="width:800px;height:400px;padding:10px;"></div>
			
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
			
			
	<script type="text/javascript">
		var editIndex = undefined;
		var index=0;
		function operate_formatter(value, row, index) {
			var html = "";
			<shop:permission code="ADD_MATERIAL">
			html += '<a href="javascript:;" class="opt"  data-options="iconCls:\'icon-edit\',plain:true"  cmd="materialList" i="' + index + '">材料列表</a>';
			</shop:permission>
			return html;
		}
		
		function uploadPic() {

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
								url : 'cp_material_exl.do',

								onSubmit : function(data) {
									//参数
									//data.construct_material_seriesId =seriesID;

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

		//展开编辑行
		function btnNew(obj) {
			if(obj=="update"){
				var rows = $("#data").datagrid("getSelections");
				if (rows.length == 0) {
					alert("请选择需要修改的行");
					return null;
				}
				if (rows.length > 1) {
					alert("不能选择多行喔");
					return null;
				}
				$("#construct_material_seriesID").attr("value", rows[0].construct_material_seriesID);
				$("#construct_material_seriesName").attr("value", rows[0].construct_material_seriesName);
				$("#remarks").attr("value", rows[0].remarks);
				$("#construct_material_num").attr("value", rows[0].construct_material_num);
				$('#w').window('open');
				$.ajax({
					type : 'GET',
					url : 'selectData.do',
					data : {
						'cid' : rows[0].construct_material_seriesID,
					},
					success : function(data) {
						$('#datas').datagrid('loadData', data.listMap);
					}
				});
			}else if(obj=="save"){
				$('#w').window('open');
			}
			};
			
			//修改 、保存
			function btnSaveSeries() {
				var rows = $("#datas").datagrid("getRows");
				var ids = [];
				if (rows.length > 0 && rows[0].construct_supplier_id != undefined) {
					for (var i = 0; i < rows.length; i++) {
						ids.push(rows[i].construct_supplier_id);
					}
				} else {
					ids.push(0);
				}
			    //数据提交
				$.post("saveSeries.do?rowData="+JSON.stringify(ids), $("#form1").serialize(),function(data) {
					if (data.msg != undefined) {
						$.messager.alert("提示", data.msg,"error");
					} else {
						$.messager.alert("提示", "操作成功!");
						$("#w").window("close");
						location.reload(true);
					}
				});
			}

		/***处理操作列的功能***/
		function cmdHanlder(cmd, row) {
			if (cmd == "materialList") {
				location.href = "materialList.do?" + $.param({
					'construct_material_seriesID' : row.construct_material_seriesID, //获取id
				});
			}
		}

		//批量删除处理
		function btnDele() {
			var rows = $("#data").datagrid("getSelections");//获取表格数据
				if (rows.length == 0) {
					$.messager.alert("提示","请选择需要修改的行");
					return false;
				} else if (rows.length > 1) {
					$.messager.alert("提示","一次只能选择一行喔！");
					return false;
				}
			if(confirm("确定要删除吗，将会删除此系列所有的材料、规格与单价，请慎重选择！")){
				$.post("delete_Series.do", {
					seriesID:rows[0].construct_material_seriesID,
				},function(data) {
					if (data.msg != undefined) {
					    $.messager.alert("提示",data.msg,"error");
					}else{
						$.messager.alert("提示","操作成功！");
						$("#data").datagrid("reload");
					}
				});
				}
			}
		function closeDown(){
			
			$('#w').window('close');
			
			location.reload(true);
			
		}
		
		//双击选择
		function onDblClickRow (rowIndex, rowData){
	        $(window.parent.window.getData(rowData));
	        $(window.parent.$("#win").window("close"));
		}
		
		
		$(function() {
			$("#data").admin_grid({
				queryBtn : "#btnSearch",
				excelBtn : "#btnExcel",
				cmdHanlder : cmdHanlder,

			});
			$('#datas').datagrid({
				onDblClickRow: function(rowIndex,rowData){
					index=rowIndex;
					var hrefs = "<iframe id='son' src='supplierCheck.do"
						+ "' allowTransparency='true' style='border:0;width:99%;height:99%;padding-left:2px;' frameBorder='0'></iframe>";
					$("#win").html(hrefs);
					$("#win").window("open");
				}
			});
		});
		
		//窗口数据
		function supplierData(data){
			var row = $('#datas').datagrid("selectRow", index)
			.datagrid("getSelected");
			row.construct_supplier_id = data.construct_supplier_id;
			row.construct_supplier_name = data.construct_supplier_name;
			$('#datas').datagrid('refreshRow', index);
		}
		
		
		//是否编辑结束
		function endEditing() {
			if (editIndex == undefined) {
				return true;
			}
			if ($('#datas').datagrid('validateRow', editIndex)) {
				$('#datas').datagrid('endEdit', editIndex);
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		}

		//新增行
		function add() {
			if (endEditing()) {
				$('#datas').datagrid('appendRow', {
					statu : 'P'
				});
				editIndex = $('#datas').datagrid('getRows').length - 1;

				$('#datas').datagrid('selectRow', editIndex).datagrid(
						'beginEdit', editIndex);
			}
		}
		//删除行
		function removeit() {
			var row = $('#datas').datagrid('getSelected');
			if (row) {
				var rowIndex = $('#datas').datagrid('getRowIndex', row);
				$('#datas').datagrid('deleteRow', rowIndex);

			} else {
				$.messager.alert("提示", "请选择删除行!");
			}
		}
		
		
	</script>

</body>
</html>