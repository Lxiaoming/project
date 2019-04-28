<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <jsp:include page="../common/css.jsp"></jsp:include>
  <jsp:include page="../common/scripts.jsp"></jsp:include>
     <link rel="stylesheet" href="<%=request.getContextPath()%>/res/layui/css/layui.css?HJ_v=<%=Math.random()%>"  media="all">
	<script src="<%=request.getContextPath()%>/res/layui/layui.js?HJ_v=<%=Math.random()%>" charset="utf-8"></script>
	<script src="${CASD_PATH}/res/layui/format/dateformat.js?HJ_v=<%=Math.random()%>" charset="utf-8"></script>
	



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

		<fieldset id="fd1" style="width:860px;">
			<legend>
				<span>项目信息</span>
			</legend>

			<form id="construct" method="post">
				<table width="90%" class="content">
					<tr>
						<td><input type="hidden" class="mini-textbox"
							name="construct_project_id" id="construct_project_id"
							value="${construct.construct.construct_project_id}" /></td>

					</tr>

					<tr>
						<td class="form-label"><a href="javascript:void(0)"
							onclick="open22()">项目名称(选择):</a></td>
						<td><input class="mini-textbox" data-options="required:true"
							readonly="readonly" name="construct_project_name"
							id="construct_project_name"
							value="${construct.construct.manage_contract_name}" style="width: 150px"></td>
						<td style="display: none"><input class="mini-textbox"
							data-options="required:true" readonly="readonly"
							name="construct_project_contractId"
							id="construct_project_contractId"
							value="${construct.construct.construct_project_contractId}"
							style="width: 150px"></td>
						<td class="form-label">工程地址:</td>
						<td><input class="mini-textbox" data-options="required:true"
							readonly="readonly" name="construct_project_addr"
							id="construct_project_addr"
							value="${construct.construct.manage_contract_address}" style="width: 150px"></td>


					</tr>
					<tr>
						<td class="form-label">甲方:</td>
						<td><input class="mini-textbox" data-options="required:true"
							readonly="readonly" name="manage_contract_firstParty"
							id="manage_contract_firstParty"
							value="${construct.construct.manage_contract_firstParty}"
							style="width: 150px"></td>
						<td class="form-label">合同总价:</td>
						<td><input class="mini-textbox" data-options="required:true"
							readonly="readonly" name="total" id="total"
							value="${construct.construct.total}" style="width: 150px"></td>
					</tr>



					<tr>
						<td>合同项目开始日期:</td>
						<td><input class="easyui-datebox"
							name="construct_project_startDate" readonly="readonly"
							value="${construct.construct.manage_contract_startTime}"
							id="construct_project_startDate" style="width: 150px" /></td>
						<td>合同项目结束日期:</td>
						<td><input class="easyui-datebox"
							name="construct_project_endDate" readonly="readonly"
							value="${construct.construct.manage_contract_endTime}"
							id="construct_project_endDate" style="width: 150px" /></td>
					</tr>
					<tr>
						<td class="form-label"><a href="javascript:void(0)"
							onclick="wen('userListCheck.do?index=-3')">项目经理(选择):</a></td>
						<td><input class="mini-textbox" readonly="readonly"
							name="construct_project_leader" id="construct_project_leader"
							value="${construct.construct.construct_project_leader}"
							style="width: 150px" /></td>
						<td>项目经理联系方式:</td>
						<td><input class="mini-textbox" readonly="readonly"
							name="construct_project_leaderTel"
							id="construct_project_leaderTel"
							value="${construct.construct.construct_project_leaderTel}"
							style="width: 150px" data-options="validType:'email'" /></td>
					</tr>

					<tr>
						<td class="form-label"><a href="javascript:void(0)"
							onclick="wen('constructDepCheck.do')">所属项目部(选择):</a></td>
						<td><input class="mini-textbox" readonly="readonly"
							name="depName" id="depName"
							value="${construct.construct.constuct_project_dep_name}"
							style="width: 150px" /></td>	
						<td style="display: none"><input class="mini-textbox"
							readonly="readonly" name="construct_project_dep"
							id="construct_project_dep"
							value="${construct.construct.construct_project_dep}" style="width: 150px" /></td>

						<%-- <td class="form-label"><a href="javascript:void(0)"
							onclick="wen('userListCheck.do?index=-6')">排烟班组(选择):</a></td>
						<td><input class="mini-textbox" readonly="readonly"
							name="construct_project_smoke" id="construct_project_smoke"
							value="${construct.construct_project_smoke}" style="width: 150px" /></td>
						<td class="form-label"><a href="javascript:void(0)"
							onclick="wen('userListCheck.do?index=-4')">水班组(选择):</a></td>
						<td><input class="mini-textbox" readonly="readonly"
							name="construct_project_waterTeam"
							id="construct_project_waterTeam"
							value="${construct.construct_project_waterTeam}"
							style="width: 150px" /></td> --%>
					</tr>
					<%-- <tr>
						<td class="form-label"><a href="javascript:void(0)"
							onclick="wen('userListCheck.do?index=-5')">电班组(选择):</a></td>
						<td><input class="mini-textbox" readonly="readonly"
							name="construct_project_eleTeam" id="construct_project_eleTeam"
							value="${construct.construct_project_eleTeam}"
							style="width: 150px" /></td>
					</tr> --%>
				</table>
			</form>
		</fieldset>

		<table id="materialListID" class="easyui-datagrid" title="班组信息"
			style="width:885px;height:auto"
			data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				toolbar: '#tb',rownumbers:true,onClickCell:onClickCell
				">
			<thead>
				<tr>

					<th
						data-options="field:'construct_project_workTeam_projectId',width:80,hidden:'hidden',editor:'text'">项目id</th>
					<th
						data-options="field:'construct_project_workTeam_id',width:80,hidden:'hidden',editor:'text'">行id</th>
						
					<th id="status"formatter="category"	data-options="field:'construct_project_workTeam_category',width:80,align:'right',
					editor:{ type:'combobox',options:{data:[{'id':'1','text':'预埋'},{'id':'2','text':'消防水'},{'id':'3','text':'消防电'},
							{'id':'4','text':'防排烟'},{'id':'5','text':'消防水电'}], valueField: 'id', textField: 'text' }}">施工项目</th> 
						

					<th
						data-options="field:'construct_project_workTeam_userId',width:80,hidden:'hidden',align:'right'">班组id</th>
					<th data-options="field:'username',width:100,align:'center'">班组</th>
					<th
						data-options="field:'construct_project_workTeam_amount',width:100,align:'center',editor:{type:'text'}">合同金额</th>
					<th
						data-options="field:'construct_project_workTeam_price',width:100,align:'center',editor:{type:'text'}">单价</th>
					<th
						data-options="field:'construct_project_workTeam_departmentId',width:80,hidden:'hidden',align:'right',editor:{type:'numberbox'}">部门id</th>
					

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
	
	layui.use(['table','form','laypage','layer','laydate','jquery'], function(){
		 var table = layui.table
		   ,$ = layui.$ 
		   ,laypage = layui.laypage
		
		   ,layer = layui.layer
		   ,laydate = layui.laydate;
		 
	})
		var editIndex = undefined;
		$(function() {
			if(${construct}.entry!=""){
				$('#materialListID').datagrid('loadData', ${construct.entry});
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
		
		
		
		function category(value, row, index) {
			if(value==1)
				return "预埋";
			if(value==2)
				return "消防水";
			if(value==3)
				return "消防电";
			if(value==4)
				return "防排烟";
			if(value==5)
				return "消防水电";
		}
		
		function btnSave() {
			
			
			
			$('#materialListID').datagrid('acceptChanges');
			var rows=$('#materialListID').datagrid('getRows');
			if(rows.length!=undefined){
				for (var i = 0; i < rows.length; i++) {
					if(rows[i].construct_project_workTeam_departmentId==null){
						alert("班组不能为空且所在部门不能为空");
						return false;
					}
				}
			}
			
			
			
			$.post("saveConstruct.do?rows="+encodeURI(JSON.stringify(rows)), $("#construct").serialize(), function(data) {
				if (data.erro == "") {
					alert("保存成功");
					location.reload(true);
				} else {
					alert(data.erro);
				}
			});
		}
		function btnCancel() {
			
			if(${projectDep}==9999||${projectDep}==10000){
				location.href = "sciAndTecList.do?constuct_project_dep_id="
					+ ${projectDep};
			}else{
				location.href = "constructList.do?constuct_project_dep_id="
					+ ${projectDep};
			}
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
		//选择子页面信息	
		function wen(src) {
			var hrefs = "<iframe id='son' src='"
					+ src
					+ "' allowTransparency='true' style='border:0;width:99%;height:99%;padding-left:2px;' frameBorder='0'></iframe>";
			$("#win").html(hrefs);
			$("#win").window("open");
		}
		
		function getDepData(rowData){
			$("#depName").val(rowData.constuct_project_dep_name);
			$("#construct_project_dep").val(rowData.constuct_project_dep_id);
		}
		
		
		 function open22(){
			 layer.open({
				  type: 2,
				  area: ['50%','50%'],
				  fixed: false, //不固定
				  maxmin: true,
				  content: 'contractList.do',
				  success:function(layero,index){
					 console.log()
				  },end:function(data){
					
				  }
				});
			
		 }
		
		function getData(rowData) {
			$("#construct_project_name").val(rowData.manage_contract_name);
			$("#construct_project_addr").val(rowData.manage_contract_address);
			$("#construct_project_startDate").datebox('setValue',
					rowData.manage_contract_startTime);
			$("#construct_project_endDate").datebox('setValue',
					rowData.manage_contract_endTime);
			$("#construct_project_contractId").val(rowData.manage_contract_id);
			$("#manage_contract_firstParty").val(
					rowData.manage_contract_firstParty);
			$("#total").val((parseFloat(rowData.manage_contract_visaAmount) + parseFloat(rowData.manage_contract_amount))
									.toFixed(2));
		}

		
		function onClickCell(index, field) {
			if(field=="username"){
				
				var hrefs = "<iframe id='son' src='userListCheck.do?index="
					+ index
					+ "' allowTransparency='true' style='border:0;width:99%;height:99%;padding-left:2px;' frameBorder='0'></iframe>";
				$("#win").html(hrefs);
				$("#win").window("open");
	            
			}else if (endEditing()){
			
				$("#materialListID").datagrid('selectRow', index)
						.datagrid('editCell', {index:index,field:field});
				       editIndex = index;
			}
	}
		
		
		//子页面赋值
		function pryData(data, index) {
			$('#materialListID').datagrid('acceptChanges');
			var row = $('#materialListID').datagrid("selectRow", index).datagrid(
					"getSelected");
			var rows=$('#materialListID').datagrid('getRows');
			row.construct_project_workTeam_userId = data.userid;
			row.username = data.username;
			row.construct_project_workTeam_departmentId = data.department_id;
			row.construct_project_workTeam_category =  rows[index].construct_project_workTeam_category;
			$('#materialListID').datagrid('refreshRow', index);	

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
					url : 'delete_WorkTeam.do',
					data : {
						'cid' : row.construct_project_workTeam_id
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