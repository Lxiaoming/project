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
	<form id="form1" method="post">
		<div id="main" region="center"
			style="background: #eee; padding: 5px; overflow-y: hidden;">
			<div class="easyui-layout" fit="true">
				<div region="north" split="false" border="false"
					style="overflow: hidden; padding: 5px 5px 2px 3px;">

					<div class="search">
						<ul>
							<li><label>型号规格 ：</label><input type="text" name="construct_material_model_name"
								id="searchId" value="" class="easyui-validatebox"
								style="width: 120px" /></li>
							

							<li>&nbsp;&nbsp; <a class="easyui-linkbutton"
								data-options="iconCls:'icon-search'" href="javascript:;"
								id="btnSearch">查询</a></li>
								<li style="list-style:none;"><a id="btnNew" onclick="btnNew('save');"
								class="easyui-linkbutton" iconCls="icon-add" plain="true">新增规格</a>
								
									<a id="btnUpdate" onclick="btnNew('update');" class="easyui-linkbutton" iconCls="icon-edit"
										plain="true">修改规格</a>
								
									<a id="btnDele" class="easyui-linkbutton" iconCls="icon-cut"
										plain="true">删除规格</a>
								</li>
						</ul>
					</div>
					<br />

				</div>


				<div region="center" split="false" border="false"
					style="overflow: hidden; padding: 0px 5px 5px 5px" id="dataList">
					<div class="newAdd" style="display: none">
						<table  style="border: 1px solid #151515">
							<tr>
								<td style="display: inline;">
								 	<input hidden="hidden"
										id="model_parentid" type="text" name="construct_material_model_parentid" value="">	
									 <input hidden="hidden"
										id="construct_material_model_id" type="text" name="construct_material_model_id" value="">
									 <input hidden="hidden"
										id="construct_materail_model_num" type="text" name="construct_materail_model_num" value="">
								</td>	
								<td style="display: inline;"><span
									style="display: inline-block; width: 60px">型号名称:</span> <input
									id="construct_material_model_name" type="text" name="construct_material_model_name" value=""></td>
								<td style="display: inline;"><span
									style="display: inline-block; width: 90px">备注:</span> <input
									id="construct_material_model_remarks" type="text" name="construct_material_model_remarks" value=""></td>
								<td style="display: inline;"><span
									style="display: inline-block; width: 60px">单位:</span> <input
									id="construct_material_model_unit" type="text" name="construct_material_model_unit" value=""></td>
							
							
							</tr>
							<tr>
								<td style="align:center"><a class="easyui-linkbutton"
									href="javascript:;" onclick="btnSave()" id="btnSave">提交</a>
									&nbsp;&nbsp;&nbsp;&nbsp; <a class="easyui-linkbutton"
									href="javascript:;" id="btnCancel">取消</a></td>
							</tr>
						</table>
					</div>

					<table id="data" title="" fit="true"
						data-options="collapsible: true,pagination:true,rownumbers:true">
						<thead>
							<tr>
								<th halign="center" field="construct_material_model_id" align="center" sortable="true" hidden="hidden"
									width="60px">型号规格id</th>
								<th halign="center" field="construct_materail_model_num" align="center"
									sortable="true" width="120px">型号编号</th>	
								<th halign="center" field="construct_material_model_name" align="center"
									sortable="true" width="120px">型号规格</th>
								<th halign="center" field="construct_material_name" align="center"
									sortable="true" width="120px">材料名称</th>
									
								<th field="construct_material_model_parentid" hidden="hidden">材料id</th>
								<th halign="center" field="construct_material_model_remarks" align="center"
									sortable="true" width="120px">备注</th>
								<th halign="center" field="construct_material_model_unit" align="center" sortable="true"
									width="120px">单位</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>

		</div>
	</form>


	<script type="text/javascript"> 
	//数据初始化
		var cid=${cid};
		
		$(function() {
			$("#data").admin_grid({
				queryBtn : "#btnSearch",
				excelBtn : "#btnExcel",
				
			});

		});

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
				$("#construct_material_model_name").attr("value", rows[0].construct_material_model_name);
				$("#construct_material_model_remarks").attr("value", rows[0].construct_material_model_remarks);
				$("#construct_material_model_unit").attr("value", rows[0].construct_material_model_unit);
				$("#construct_material_model_id").attr("value", rows[0].construct_material_model_id);
				$("#model_parentid").attr("value", rows[0].construct_material_model_parentid);
				$("#construct_materail_model_num").attr("value", rows[0].construct_materail_model_num);
				$(".newAdd").show();
			}else if(obj=="save"){
				$(".newAdd").show();
			}
				
			};
			
			//数据提交
			function btnSave() {
				
				var model_name = $("#construct_material_model_name").val();
				var model_remarks = $("#construct_material_model_remarks").val();
				var model_unit = $("#construct_material_model_unit").val();
				var model_id = $("#construct_material_model_id").val();
				var construct_materail_model_num = $("#construct_materail_model_num").val();
				var model_parentid = ${cid};
				if(model_name!=''&&model_name!=undefined){
				$.ajax({
					type : 'POST',
					url : 'editMaterial.do',
					data : {'model_name':model_name,
					        'model_remarks':model_remarks,
					        'model_unit':model_unit,
					        'model_id': model_id,
					        'construct_materail_model_num': construct_materail_model_num,
					        'model_parentid': model_parentid,
					},
					success : function(data) {
						if (data.msg!=undefined) {
							$(".newAdd").hide();
							  $.messager.alert("操作提示", data.msg,"error");
							  location.reload(true);
						} else {
							 
							 $.messager.alert("操作提示", "操作成功！");
							 $(".newAdd").hide();
							 location.reload(true);
						}
					}
				});
		
				}else{
				
					 $.messager.alert("操作提示","规格名称不能为空!");
					 
					 
				}
			
		
			};
			//按钮取消
			$("#btnCancel").click(function() {
				window.location.href = "materialhome.do?construct_material_id="+cid;
			});

			//按钮删除
			$("#btnDele").click(function() {
				var rows = $("#data").datagrid("getSelections");
				if (rows.length == 0) {
					 $.messager.alert("操作提示","请选择需要删除的行!");
					return null;
				}
				if (rows.length > 1) {
					 $.messager.alert("操作提示","只能选择一行!");
					return null;
				}
				if(confirm("确定要删除吗，将会删除此规格与单价，请慎重选择！")){
					/* var ids = [];
					for (var i = 0; i < rows.length; i++) {
						ids.push(rows[i].construct_material_model_id);
					} */
					$.ajax({
						type : 'POST',
						url : 'deleteMaterial.do',
						traditional : true,
						data : {
							'menuids' : rows[0].construct_material_model_id
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
			});

		

		
	</script>

</body>
</html>