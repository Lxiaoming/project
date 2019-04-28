<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<jsp:include page="../common/css.jsp"></jsp:include>
<jsp:include page="../common/scripts.jsp"></jsp:include>

<script
	src="<%=request.getContextPath()%>/res/admin/scripts/admin_grid.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/res/admin/scripts/ueditor/third-party/codemirror/codemirror.js"
	type="text/javascript"></script>




</head>
<body>
	<div region="north" title="用户基本信息" style="padding: 10px;"
		minHeight="350px">


			<form id="checkPro" method="post">
				<table width="90%" class="content">
					<tr>
						<td><input type="hidden" class="mini-textbox"
							name="maintain_checkPro_id" id="maintain_checkPro_id"
							value="${data.checkPro.maintain_checkPro_id}" /></td>

					</tr>

					<tr>
						<td class="form-label">系统:</td>
						<td><input class="mini-textbox" data-options="required:true"
							name="maintain_checkPro_name"
							id="maintain_checkPro_name"
							value="${data.checkPro.maintain_checkPro_name}" style="width: 800px"></td>
					</tr>
				</table>
			</form>
		<br>
		<table id="materialListID" class="easyui-datagrid" title="检查内容"
			style="width:885px;height:auto"
			data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				toolbar: '#tb',rownumbers:true,onClickCell:onClickCell
				">
			<thead>
				<tr>

					<th
						data-options="field:'maintain_checkContent_parentId',width:80,hidden:'hidden',editor:'text'">父id</th>
					<th
						data-options="field:'maintain_checkContent_id',width:80,hidden:'hidden',editor:'text'">行id</th>
						
					<th
						data-options="field:'maintain_checkContent_name',width:830,align:'center',editor:{type:'text'}">明细</th>
					

				</tr>
			</thead>
		</table>
		<div id="tb" style="height:auto">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-add',plain:true" onclick="add()">添加</a>

			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-undo',plain:true" onclick="reject()">撤销行</a>

			<a href="javascript:void(0)" class="easyui-linkbutton" id="removeit"
				data-options="iconCls:'icon-cut',plain:true" onclick="removeit()">删除</a>

		</div>



		<div style="margin:20px 0;"></div>


		<br /> <br /> <br />
		<div region="south" style="padding: 10px; text-align: center;"
			border="false">
			<a class="easyui-linkbutton"
				data-options="iconCls:'icon-system_save'" href="javascript:;"
				onclick="btnSave()">保存</a>&nbsp;&nbsp;&nbsp;&nbsp; <a
				class="easyui-linkbutton" data-options="iconCls:'icon-system_close'"
				href="javascript:;" onclick="btnCancel()">取消</a>
		</div>
	</div>
	<div id="win" class="easyui-window"
		data-options="region:'center',title:'请选择值'" closed="true"
		style="height: 550px; width: 920px"></div>



	<script type="text/javascript">
		var editIndex = undefined;
		$(function() {
			 if(${data}.entry!=""){
				$('#materialListID').datagrid('loadData', ${data.entry});
			}
			var type=${type};
			if(type!='update'){
				document.getElementById("removeit").style.display = "none";
			} 
			//单元格编辑
			$.extend($.fn.datagrid.methods, {
				editCell : function(jq, param) {
					return jq.each(function() {
						var fields = $(this).datagrid('getColumnFields', true)
								.concat($(this).datagrid('getColumnFields'));
						for (var i = 0; i < fields.length; i++) {
							var col = $(this).datagrid('getColumnOption',
									fields[i]);
							col.editor1 = col.editor;
							if (fields[i] != param.field) {
								col.editor = null;
							}
						}
						$(this).datagrid('beginEdit', param.index);
						for (var i = 0; i < fields.length; i++) {
							var col = $(this).datagrid('getColumnOption',
									fields[i]);
							col.editor = col.editor1;
						}
					});
				}
			});
			
		});
		
		
		
		
		function btnSave() {
			$('#materialListID').datagrid('acceptChanges');
			var rows=$('#materialListID').datagrid('getRows');
			
			$.post("saveCheckCon.do?rows="+encodeURI(JSON.stringify(rows)), $("#checkPro").serialize(), function(data) {
				if (data == "") {
					alert("保存成功");
					location.reload(true);
				} else {
					alert("保存失败");
				}
			});
		}
		function btnCancel() {
			
				location.href = "maintainCheckConList.do";
		}
		
		
		//是否编辑结束
		function endEditing() {
			if (editIndex == undefined) {
				return true;
			}
			if ($('#materialListID').datagrid('validateRow', editIndex)) {
				$('#materialListID').datagrid('endEdit', editIndex);
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		}


		
		function onClickCell(index, field) {
			 if (endEditing()){
			
				$("#materialListID").datagrid('selectRow', index)
						.datagrid('editCell', {index:index,field:field});
				       editIndex = index;
			}
	}
		
		
		
		
		//新增行
		function add() {
				$('#materialListID').datagrid('appendRow', {status : 'P'});
				editIndex = $('#materialListID').datagrid('getRows').length - 1;

				$('#materialListID').datagrid('selectRow', editIndex).datagrid(
						'beginEdit', editIndex);
		}
		
		//撤销新增行
		function reject() {
			var row = $('#materialListID').datagrid('getSelected');
				if (row) {
					var rowIndex = $('#materialListID').datagrid('getRowIndex', row);
					$('#materialListID').datagrid('deleteRow', rowIndex);

				} else {
					$.messager.alert("提示", "请选择删除行!");
				}
		}
		
		function removeit(){
			var row = $('#materialListID').datagrid('getSelected');
			if (row) {
				
				$.ajax({
					type : 'POST',
					url : 'delete_checkCon.do',
					data : {
						'cid' : row.maintain_checkContent_id
					},
					success : function(data) {
						if (data.msg != undefined) {
							//$.messager.alert("提示", data.msg, "error");
							location.reload();
						} else {
							//$.messager.alert("提示", "操作成功！");
							location.reload();
						}
					}
				});
				
			} else {
				$.messager.alert("提示", "请选择删除行!");
			}
			
			
		}
		
	</script>

</body>
</html>