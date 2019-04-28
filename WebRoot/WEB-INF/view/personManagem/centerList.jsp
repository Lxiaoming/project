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
							<li><label>供应商名称：</label><input type="text" name="construct_supplier_name"
								id="construct_supplier_name" value="" class="easyui-validatebox"
								style="width: 120px" /></li>
							

							<li>&nbsp;&nbsp; <a class="easyui-linkbutton"
								data-options="iconCls:'icon-search'" href="javascript:;"
								id="btnSearch">查询</a></li>
						</ul>
					</div>
					<br />

					<div>
						<ul>
							<li style="list-style:none;"><a id="btnNew" onclick="btnNew('save');"
								class="easyui-linkbutton" iconCls="icon-add" plain="true">新增供应商</a>
								
									<a id="btnUpdate" onclick="btnNew('update');" class="easyui-linkbutton" iconCls="icon-edit"
										plain="true">修改供应商</a>								
									<a id="btnDele"  onclick="btnDele();" class="easyui-linkbutton" iconCls="icon-cut"
										plain="true">删除供应商</a>
								</li>
						</ul>
					</div>
				</div>


				<div region="center" split="false" border="false"
					style="overflow: hidden; padding: 0px 5px 5px 5px" id="dataList">
					<div class="newAdd" style="display: none">
					<form id="supplier" method="post" >
						<table  style="border: 1px solid #151515">
							<tr>
								<td style="display: inline;">
							
								 <input hidden="hidden"
									id="construct_supplier_id" type="text" name="construct_supplier_id" value=""></td>
								<td style="display: inline;"><span
									style="display: inline-block; width: 60px">名称:</span> <input
									id="construct_supplier_name" type="text" name="construct_supplier_name" value=""></td>
								<td style="display: inline;"><span
									style="display: inline-block; width: 60px">地址:</span> <input
									id="construct_supplier_addr" type="text" name="construct_supplier_addr" value=""></td>	
								<td class="form-label" style="width:110px">电话：</td>
								<td style="width:150px"><input name="construct_supplier_tel"
									id="construct_supplier_tel" class="mini-textbox"  value="" />
								</td>		
							</tr>
							<tr>
								<td style="align:center"><a class="easyui-linkbutton"
									href="javascript:;" onclick="btnSave()" >提交</a>
									&nbsp;&nbsp;&nbsp;&nbsp; <a class="easyui-linkbutton"
									href="javascript:;" onclick="btnCancel()" >取消</a></td>
							</tr>
						</table>
						</form>
					</div>

					<table id="data" title="" fit="true"
						data-options="collapsible: true,pagination:true,">
						<thead>
							<tr>
								<th id="ds" halign="center" field="construct_supplier_id" align="center"
									sortable="true" width="60px">供应商编号</th>
								<th halign="center" field="construct_supplier_name" align="center"
									sortable="true" width="120px">供应商名称</th>
								<th halign="center" field=construct_supplier_addr align="center" 
									sortable="true" width="150px">供应商地址</th>
								<th halign="center" field="construct_supplier_tel" align="center" 
									sortable="true" width="150px">供应商电话</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>

		</div>
	</form>

				
				


	<script type="text/javascript">
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
				$("#construct_supplier_id").attr("value", rows[0].construct_supplier_id);
				$("#construct_supplier_name").attr("value", rows[0].construct_supplier_name);
				$("#construct_supplier_addr").attr("value", rows[0].construct_supplier_addr);
				$("#construct_supplier_tel").attr("value", rows[0].construct_supplier_tel);
				$(".newAdd").show();
			}else if(obj=="save"){
				$(".newAdd").show();
			}
		};
			
		//数据提交
		function btnSave() {
			
			var construct_supplier_id = $("#construct_supplier_id").val();
			var construct_supplier_name = $("#construct_supplier_name").val();
			var construct_supplier_addr = $("#construct_supplier_addr").val();
			var construct_supplier_tel = $("#construct_supplier_tel").val();

			if (construct_supplier_name == "") {
				alert("菜单名不能为空");
				return false;
			}

			$.ajax({
				type : 'POST',
				url : 'saveSupplier.do',
				data : {
					'construct_supplier_id' : construct_supplier_id,
					'construct_supplier_name' : construct_supplier_name,
					'construct_supplier_addr' : construct_supplier_addr,
					'construct_supplier_tel' : construct_supplier_tel
				},

				success : function(data) {
					if (data == "") {
						$(".newAdd").hide();
						location.reload();
					} else {
						alert("提交失败");
					}
				}
			});

			
		};

		function btnCancel() {
			window.location.href = "supplierList.do";
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
			var rows = $("#data").datagrid("getSelections");
			if (rows.length == 0) {
				alert("请选择需要删除的行");
				return null;
			}

			var ids = [];
			for (var i = 0; i < rows.length; i++) {
				ids.push(rows[i].construct_supplier_id);
			}

			$.ajax({
				type : 'POST',
				url : 'deleSupplier.do',
				traditional : true,
				data : {
					'ids' : JSON.stringify(ids)
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