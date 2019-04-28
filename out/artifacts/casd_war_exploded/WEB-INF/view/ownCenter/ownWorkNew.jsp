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
		<form id="workHead" method="post">
			<table width="90%" class="content">
				<tr>
					<td><input type="hidden" class="mini-textbox"
						name="maintain_checkPro_id" id="maintain_checkPro_id"
						value="${data.checkPro.maintain_checkPro_id}"/></td>
				</tr>
				<tr>
					<td style="display: none" class="form-label">工作分类id:</td>
					<td style="display: none"><input class="mini-textbox" data-options="required:true"
						name="own_workArrangHead_id" id="own_workArrangHead_id"
						value="${data.workArrangHead.own_workArrangHead_id}"></td>
					<td style="display: none"><input class="mini-textbox" data-options="required:true"
						name="own_workArrangHead_centerId" id="own_workArrangHead_centerId"
						value="${data.workArrangHead.own_workArrangHead_centerId}"></td>
					<td style="display: none"><input class="mini-textbox" data-options="required:true"
						name="own_workArrangHead_companyId" id="own_workArrangHead_companyId"
						value="${data.workArrangHead.own_workArrangHead_companyId}"></td>		
					<td class="form-label">工作分类:</td>
					<td><input class="mini-textbox" data-options="required:true"
						name="own_workArrangHead_category" id="own_workArrangHead_category"
						value="${data.workArrangHead.own_workArrangHead_category}"
						style="width: 500px"></td>
				</tr>
                 <tr>
					<td class="form-label"><a
							href="javascript:void(0)" onclick="wen('projectTableList.do')">项目名称:</a></td>
					<td><input class="mini-textbox" data-options="required:true"
					         id="construct_project_name"
						 style="width: 500px"></td>
					<td><input class="mini-textbox" type="hidden"
						name="own_workArrangHead_projectId" id="own_purchase_projectId" 
						value="${data.workArrangHead.own_workArrangHead_projectId}"></td>	 
					</tr>
			</table>
		</form>
		<br>
		<table id="materialListID" class="easyui-datagrid" title="工作内容"
			style="width:1200px;height:auto"
			data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				toolbar: '#tb',rownumbers:true,onClickCell:onClickCell,onDblClickCell:onDblClickCell
				">
			<thead>
				<tr>
					<th
						data-options="field:'own_workArranged_categoryId',width:80,hidden:'hidden',editor:'text'">父id</th>
					<th
						data-options="field:'own_workArranged_id',width:80,hidden:'hidden',editor:'text'">行id</th>
					<th
						data-options="field:'own_workArranged_content',width:300,align:'center',editor:'text'">工作内容</th>
					<th 
						data-options="field:'own_workArranged_arranger',width:80,hidden:'hidden',align:'center'">指令</th>
					<th 
						data-options="field:'own_workArranged_arranger_name',width:80,align:'center' ">指令</th>	
					<th
						data-options="field:'own_workArranged_sponsor',width:80,hidden:'hidden',align:'center'">主办</th>
					<th
						data-options="field:'own_workArranged_sponsor_name',width:80,align:'center'">主办</th>		
					<!-- <th
						data-options="field:'own_workArranged_coordinator',width:80,hidden:'hidden',align:'center'">协办</th>
					<th
						data-options="field:'own_workArranged_coordinator_name',width:80,align:'center'">协办</th> -->	
					<th
						data-options="field:'own_workArranged_current',width:300,align:'center',editor:'text'">目前情况</th>
					<!-- <th
						data-options="field:'own_workArranged_feedback',width:80,align:'center',editor:'text'">问题反馈</th>
					<th
						data-options="field:'own_workArranged_assist',width:80,align:'center',editor:'text'">协助备注</th> -->
					<th
						data-options="field:'own_workArranged_creatTime',width:100,align:'center',hidden:'hidden',editor:'text'">创建时间</th>
					<th
						data-options="field:'own_workArranged_planTime',width:100,align:'center',editor:'datebox'">计划时间要求</th>			
					<!-- <th
						data-options="field:'own_workArranged_finishTime',width:100,align:'center',editor:'datebox'">实际完成时间</th> -->
					<!-- <th
						data-options="field:'own_workArranged_status',width:80,align:'center'">任务状态</th> -->		
					<th
						data-options="field:'own_workArranged_remarks',width:200,align:'center',editor:'text'">备注</th>

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
			 if (${data}.entry != "") {
				$('#materialListID').datagrid('loadData', ${data.entry});
			}
			var type = ${type};
			if (type != 'update') {
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
			var rows = $('#materialListID').datagrid('getRows');

			$.post("save_workArrang.do?rows=" + encodeURI(JSON.stringify(rows)),
					$("#workHead").serialize(), function(data) {
						if (data == "") {
							alert("保存成功");
							location.reload(true);
						} else {
							alert("保存失败");
						}
					});
		}
		function btnCancel() {

			location.href = "ownWorkList.do";
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
			if (endEditing()) {
				if(field!="own_workArranged_arranger_name"&&field!="own_workArranged_sponsor_name"&&field!="own_workArranged_coordinator_name"){
					$("#materialListID").datagrid('selectRow', index).datagrid(
							'editCell', {
								index : index,
								field : field
					});
				}	
				editIndex = index;
			}
		}
		
		var cellName="";
		function onDblClickCell(index,field,value){
			//if(field==="own_workArranged_arranger_name"||field==="own_workArranged_sponsor_name"||field==="own_workArranged_coordinator_name"){
			if(field==="own_workArranged_sponsor_name"||field==="own_workArranged_coordinator_name"){
				cellName=field;
				var hrefs = "<iframe id='son' src='userListCheck.do?index="
					+ index
					+ "' allowTransparency='true' style='border:0;width:99%;height:99%;padding-left:2px;' frameBorder='0'></iframe>";
			$("#win").html(hrefs);
			$("#win").window("open");
				
			}
		}
		
		//子页面赋值
		function pryData(data, index) {
			$('#materialListID').datagrid('acceptChanges');
			var row = $('#materialListID').datagrid("selectRow", index).datagrid(
					"getSelected");
			if(cellName=="own_workArranged_arranger_name"){
				row.own_workArranged_arranger_name = data.username;
				row.own_workArranged_arranger=data.userid;
			}else if(cellName=="own_workArranged_sponsor_name"){
				row.own_workArranged_sponsor_name = data.username;
				row.own_workArranged_sponsor=data.userid;
			}else if(cellName=="own_workArranged_coordinator_name"){
				row.own_workArranged_coordinator_name = data.username;
				row.own_workArranged_coordinator=data.userid;
			}
			
			$('#materialListID').datagrid('refreshRow', index); //刷新当前行
		}
		
		//新增行
		function add() {
			$('#materialListID').datagrid('appendRow', {
				status : 'P'
			});
			editIndex = $('#materialListID').datagrid('getRows').length - 1;

			$('#materialListID').datagrid('selectRow', editIndex).datagrid(
					'beginEdit', editIndex);
		}

		//撤销新增行
		function reject() {
			var row = $('#materialListID').datagrid('getSelected');
			if (row) {
				var rowIndex = $('#materialListID')
						.datagrid('getRowIndex', row);
				$('#materialListID').datagrid('deleteRow', rowIndex);

			} else {
				$.messager.alert("提示", "请选择删除行!");
			}
		}

		function removeit() {
			var row = $('#materialListID').datagrid('getSelected');
			if (row) {

				$.ajax({
					type : 'POST',
					url : 'delete_workerEntry.do',
					data : {
						'cid' : row.own_workArranged_id
					},
					success : function(data) {
						if (!data.success) {
							  $.messager.alert("操作提示", data.msg,"error");
						} else {
							 $.messager.alert("操作提示","操作成功！");
							 location.reload();
						}
					}
				});

			} else {
				$.messager.alert("提示", "请选择删除行!");
			}

		}
		
		
		/* 引用子页面index1.html */
		function wen(src) {
			var hrefs = "<iframe id='son' src='"
					+ src
					+ "' allowTransparency='true' style='border:0;width:99%;height:99%;padding-left:2px;' frameBorder='0'></iframe>";
			$("#win").html(hrefs);
			$("#win").window("open");
		}
		
		
		//项目名称赋值
		function projectTable(rowData) {
		 var project_name = rowData.construct_project_name;
			var project_id = rowData.construct_project_id;
			$("#own_purchase_projectId").val(project_id);
			$("#construct_project_name").val(project_name); 
			$("#win").window("close");
		}

	</script>

</body>
</html>