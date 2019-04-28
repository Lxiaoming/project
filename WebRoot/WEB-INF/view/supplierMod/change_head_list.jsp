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

</head>
<body class="easyui-layout" style="overflow-y: hidden;" scroll="no">
		<div id="main" region="center"
			style="background: #eee; padding: 5px; overflow-y: hidden;">
			<div class="easyui-layout" fit="true">
				<div region="north" split="false" border="false"
					style="overflow: hidden; padding: 5px 5px 2px 3px;">
					<div class="search">
						<ul>
							<li><label>创建时间：</label><input type="text" name="change_price_name"
								id="searchId" value="" class="easyui-validatebox"
								style="width: 120px" /></li>
							
							<li>&nbsp;&nbsp; <a class="easyui-linkbutton"
								data-options="iconCls:'icon-search'" href="javascript:;"
								id="btnSearch">查询</a></li>
						</ul>
					</div>
					
					
					<br/>

					<div>
						<ul>
						  <shop:permission code="PRICE_NEWCHANGE">
							<li style="list-style:none;"><a id="btnNew" onclick="btnNew('save');"
								class="easyui-linkbutton" iconCls="icon-add" plain="true">新增单据</a>			
								</li>
								</shop:permission>
						</ul>
					</div>
				</div>
				<div region="center" split="false" border="false"
					style="overflow: hidden; padding: 0px 5px 5px 5px" id="dataList">

					<table id="data" title="" fit="true"
						data-options="collapsible: true,pagination:true,onDblClickRow:onDblClickRow">
						<thead>
							<tr>
								<th halign="center" field="id" align="center"
									sortable="true" width="60px">编号</th>
								<th halign="center" field="change_price_name" align="center"
									sortable="true" width="120px">创建时间</th>
								<th halign="center" field="describe" align="center" 
									sortable="true" width="150px">描述</th>
								<th halign="center" field="status" align="center" 
									sortable="true" width="150px" formatter="operate_status">状态</th>
								<th halign="center" field="psn" sortable="true"
									width="120px" formatter="operate_formatter">操作</th>
							</tr>
						</thead>
					</table>
				</div>
			</div><div id="w" class="easyui-window" title='修改信息'
				data-options="modal:true,closed:true,iconCls:'icon-save'"
				style="width:400px;height:200px;padding:10px;">
				<form id="form1" action="#" method="post">


						<table>
	    		<tr>
	    			<td>编号:</td>
	    			<td><input class="easyui-textbox" type="text" name="id" value="0" readonly="readonly"></input></td>
	    		</tr>
	    		<!-- <tr>
	    			<td>名称:</td>
	    			<td><input class="easyui-textbox" type="text" name="change_price_name" ></input></td>
	    		</tr> -->
	    		<tr>
	    			<td hidden="hidden">状态:</td>
	    			<td hidden="hidden"><input class="easyui-textbox" type="text" name="status" value="0"></input></td>
	    		</tr>
	    		<tr>
	    			<td>描述:</td>
	    				<td><input class="easyui-textbox" type="text" name="describe"></input></td>
	    			
	    		</tr>
	    		
						</table>
					
				</form>
				<div style="text-align:center">

					<a class="easyui-linkbutton" href="javascript:;" id="btnSave"
						onclick="btnSaveSeries()">提交</a> &nbsp;&nbsp;&nbsp;&nbsp; <a
						class="easyui-linkbutton" href="javascript:;" id="btnCancel"
						onclick="closeDown()">取消</a>
				</div>
			
		</div>
		
	
			</div>			

			<div id="win" class="easyui-window" title='赋值'
				data-options="modal:true,closed:true,iconCls:'icon-save'"
				style="width:800px;height:400px;padding:10px;"></div>

	<script type="text/javascript">
		var editIndex = undefined;
		var index=0;
		function operate_formatter(value, row, index) {
			var html = "";

			html += '<a href="javascript:;" class="opt",plain:true"  cmd="edit" i="' + index + '">查看</a>';
		
			return html;
		}
	
		
		function operate_status(value, row, index) {
			if (value == 0) {
				return "初始录入";
			} else if (value == 1) {
				return "审核中";
			} else if (value == 2) {
				return "退回";
			}else if (value == 3) {
				return "已审核";
			}else {
				return "其他";
			}
			
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
				/* var rows = $("#datas").datagrid("getRows");
				var ids = [];
				if (rows.length > 0 && rows[0].construct_supplier_id != undefined) {
					for (var i = 0; i < rows.length; i++) {
						ids.push(rows[i].construct_supplier_id);
					}
				} else {
					ids.push(0);
				} */
			    //数据提交
				$.post("add_change_head.do",$("#form1").serialize(),function(data) {
					if (data.Success) {
						$.messager.alert("提示", data.Msg, "info", function() {
						
							$("#w").window("close");
							location.reload(true);
							});
					} else {
						$.messager.alert("提示", data.Msg,"error");
					
					}
				});
			}

		/***处理操作列的功能***/
		function cmdHanlder(cmd, row) {
			/** 查看变更数据*/
			if (cmd == "edit") {
				location.href = "change_price.do?" + $.param({
					'change_headId' : row.id, //获取id
					'status' : row.status, //获取id
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