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
							<li><label>材料名称：</label><input type="text" name="construct_material_name"
								id="searchId" value="" class="easyui-validatebox"
								style="width: 120px" /></li>
							

							<li>&nbsp;&nbsp; <a class="easyui-linkbutton"
								data-options="iconCls:'icon-search'" href="javascript:;"
								id="btnSearch">查询</a></li>
								
							<li style="list-style:none;"><a id="btnNew" onclick="btnNew('save');"
								class="easyui-linkbutton" iconCls="icon-add" plain="true">新增材料</a>
								
									<a id="btnUpdate" onclick="btnNew('update');" class="easyui-linkbutton" iconCls="icon-edit"
										plain="true">修改材料</a>								
									<!-- <a id="btnDele"  onclick="btnDele();" class="easyui-linkbutton" iconCls="icon-cut"
										plain="true">删除材料</a> -->
										
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
									id="construct_material_id" type="text" name="construct_material_id" value=""></td>
								<td style="display: inline;"><span
									style="display: inline-block; width: 60px">材料编号:</span> <input
									id="construct_material_num" type="text" name="construct_material_num" value="" readonly="readonly"></td>	
								<td style="display: inline;"><span
									style="display: inline-block; width: 60px">材料名称:</span> <input
									id="construct_material_name" type="text" name="construct_material_name" value=""></td>
								<td style="display: inline;"><span
									style="display: inline-block; width: 90px">描述:</span> <input
									id="construct_material_remarks" type="text" name="construct_material_remarks" value=""></td>
							
							</tr>
							<tr>
								<td style="align:center"><a class="easyui-linkbutton"
									href="javascript:;" onclick="btnSave()" >提交</a>
									&nbsp;&nbsp;&nbsp;&nbsp; <a class="easyui-linkbutton"
									href="javascript:;" onclick="btnCancel()">取消</a></td>
							</tr>
						</table>
					</div>

					<table id="data" title="" fit="true"
						data-options="collapsible: true,pagination:true,">
						<thead>
							<tr>
								<th halign="center" field="construct_material_seriesId" align="center"  hidden="hidden"
									sortable="true" width="60px">材料分类编号</th>
								<th  halign="center" field="construct_material_id" align="center" hidden="hidden"
									sortable="true" width="60px">材料id</th>
								<th halign="center" field="construct_material_num" align="center"
									sortable="true" width="120px">材料编号</th>	
								<th halign="center" field="construct_material_name" align="center"
									sortable="true" width="120px">材料名称</th>
								<th halign="center" field="construct_material_remarks" align="center" 
									sortable="true" width="150px">描述</th>
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
		var seriesID=${seriesID};
		function operate_formatter(value, row, index) {
			var html = "";

			html += '<a href="javascript:;" class="opt"  data-options="iconCls:\'icon-edit\',plain:true"  cmd="edit" i="' + index + '">型号规格</a>';
		
			return html;
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
				$("#construct_material_id").attr("value", rows[0].construct_material_id);
				$("#construct_material_name").attr("value", rows[0].construct_material_name);
				$("#construct_material_remarks").attr("value", rows[0].construct_material_remarks);
				$("#construct_material_num").attr("value", rows[0].construct_material_num);
				$(".newAdd").show();
			}else if(obj=="save"){
				
				$("#construct_material_id").attr("value", "");
				$("#construct_material_name").attr("value", "");
				$("#construct_material_remarks").attr("value", "");
				$("#construct_material_num").attr("value", "");
				$(".newAdd").show();
			}
			};
			
			//数据提交
			function btnSave() {
				
				var material_id = $("#construct_material_id").val();
				var material_name = $("#construct_material_name").val();
				var material_remarks = $("#construct_material_remarks").val();
				var construct_material_num = $("#construct_material_num").val();
				if(material_name!=''&&material_name!=undefined){
				$.ajax({
					type : 'POST',
					url : 'materialset.do',
					data : {'material_id':material_id,
					        'material_name':material_name,
					        'material_remarks':material_remarks,
					        'construct_material_num':construct_material_num,
					        'seriesID':seriesID
					        
					},
					success : function(data) {
						if (data.msg!=undefined) {
							$(".newAdd").hide();
							  $.messager.alert("操作提示", data.msg,"error");
							  location.reload(true);
						} else {
							 $.messager.alert("操作提示","操作成功！");
								$(".newAdd").hide();
								location.reload(true);
						}
					}
				});
		
				}else{
				
					 $.messager.alert("操作提示","规格名称不能为空!");
					 
					 
				}
			
		
			};

		/***处理操作列的功能***/
		function cmdHanlder(cmd, row) {
			if (cmd == "edit") {
				
				location.href = "materialhome.do?" + $.param({
					'construct_material_id' : row.construct_material_id, //获取id
				});

			}
		}

		//批量删除处理
		function btnDele() {
			
		    var rows = $("#data").datagrid("getSelected");//获取表格数据
			var material_id = rows.construct_material_id;
		  
			$.messager.confirm("操作提示", "您确定要执行操作吗？", function(r) {
				if (r) {
					$.ajax({
						type : 'POST',
						url : 'deletejoin.do',
						data : {
							'material_id' : material_id,
						},
						success : function(data) {
							if (data.msg!=undefined) {
								  $.messager.alert("操作提示", data.msg,"error");
								  $('#data').datagrid('reload');
							} else {
								 $.messager.alert("操作提示","操作成功！");
								 $('#data').datagrid('reload');
							}
							}
					});
				}
			});

		}
		
		function btnCancel() {
			window.location.href = "materialList.do?construct_material_seriesID="+seriesID;
		};
		
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