<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
	<div>
		<table width="90%" class="content">

			<tr>
				<td style="display:none"><input class="easyui-validatebox" type="text"
					data-options="required:true" name="id" id="id"
					value="${role.id}" style="width: 150px" /></td>
				<td style="display:none">职位id:</td>
				<td style="display:none"><input class="easyui-validatebox" type="text"
					data-options="required:true" name="roleId" id="roleId"
					value="${role.role_id}" style="width: 150px" /></td>
				<td>职位名:</td>
				<td><input class="easyui-validatebox" type="text"
					data-options="required:true" name="roleName" id="roleName"
					value="${role.role_name}" style="width: 150px" /></td>
				<%-- <td>code:</td>
				<td><input class="easyui-validatebox" type="text"
					 name="code" id="code"
					value="${role.code}" style="width: 150px"></td> --%>
					<td>状态:</td>
					<td><select  id="state" onchange="selectcity()" name="state" style="width:150px">  
            							<option value ="1">启用</option>  
            							<option value ="2">禁用</option>  
  					</select>
					</td>
				<%-- <td><input class="easyui-validatebox" type="text" name="state"
					id="state" value="${role.state}" style="width: 150px" />(1-启用  2-禁用)</td> --%>
			</tr>
		</table>

	</div>

	<jsp:include page="../uc/editRoleTree.jsp"></jsp:include>

	<div>
		<tr>
			<td>菜单权限:</td>
		</tr>
	</div>

	<div>
		<ul id="treeDemo" class="ztree"></ul>
	</div>

	<div style="padding: 10px; text-align: center;" border="false">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-system_save'"
			href="javascript:;" id="btnSave">保存</a> <a class="easyui-linkbutton"
			data-options="iconCls:'icon-system_close'" href="javascript:;"
			id="btnCancel">取消</a>
	</div>


</body>


<script type="text/javascript">
	function formSub(id,roleName,state) {
		var menuId=$("#id").val();
		var checkedNode = zTreeObj.getCheckedNodes();
		
		var nodes="";
		for (var i = 0; i < checkedNode.length; i++) {
			var childer=checkedNode[i].childer;
			if((checkedNode.length-1)==i){
				nodes+=checkedNode[i].id;
			}else{
				nodes+=checkedNode[i].id+",";
			}
		}
		$.ajax({
			url : "saveRole.do",
			type : "post",
			data : {
				'menuId' : menuId,
				'roleId' : id,
				'roleName' : roleName,
				'state' : state,
				'nodes' : nodes
			},

			success : function(data) {
				alert("保存成功");
				window.location.href = "roleList.do";

			}
		});

	}

	$(function() {
		$("#btnSave").click(function() {
			
			var id = $("#roleId").val();
			var roleName = $("#roleName").val();
			var state = $("#state option:selected").val();
			formSub(id,roleName,state);
		});
		$("#btnCancel").click(function() {
			window.location.href = "roleList.do";
			
		});
	});
	
	
</script>


</html>