<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="PowerCheck" prefix="shop"%>
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
<style>
#div_leftmenu div {
	font-size: 15px;
}

#div_leftmenu div ul {
	margin: 10px 10px 10px 10px;
	padding: 0;
	overflow: hidden;
	line-height: 40px;
}

#div_leftmenu div ul li {
	list-style-type: none;
	background-color: #DFE2E3;
	text-align: center;
	margin-bottom: 2px;
}

#div_leftmenu div ul li:last-of-type {
	margin-bottom: 0;
}
</style>

</head>
<body class="easyui-layout" style="overflow-y: hidden;" scroll="no">
	<form id="form1" method="post">
		<div id="main" region="center"
			style="background: #eee; padding: 5px; overflow-y: hidden;">
				<div class="easyui-layout" fit="true">
					<div region="north" split="false" border="false"
						style="overflow: hidden; padding: 5px 5px 28px 5px;">
						<div class="easyui-panel" title="查询条件" style="margin-bottom: 5px;">
							<div class="controls">
								<a class="easyui-linkbutton"
									data-options="iconCls:'icon-search'" href="javascript:;"
									id="btnSearch">查询</a>
						
							</div>

							<div class="search">

								<ul>
									<li><label>部门名称：</label><input type="text" name="department_name"
										class="easyui-validatebox" style="width: 120px" /></li>
							
		                           <li><label>中心名称：</label><input type="text" name="center_name"
										class="easyui-validatebox" style="width: 120px" /></li>
								   <li><label>公司名称：</label><input type="text" name="company_name"
										class="easyui-validatebox" style="width: 120px" /></li>


								</ul>
								<div class="clear"></div>
							</div>
							<div id="tb">
							<shop:permission code="ADD_DEPARTENT">
								<a href="#" class="easyui-linkbutton" iconCls="icon-add"
									plain="true" onclick="$('#addrow').window('open')">新增部门</a> <a href="#"
									class="easyui-linkbutton" iconCls="icon-cancel" plain="true"
									onclick="btnDele();">删除部门</a>
									<a href="#"
									class="easyui-linkbutton" iconCls="icon-edit" plain="true"
									onclick="edit()">修改部门</a>
                          </shop:permission>
							</div>
						</div>
					</div>
					<div region="center" split="false" border="false"
						style="overflow: hidden; padding: 0 5px 5px 5px" id="dataList">
						<table id="data" title="" fit="true"
							data-options="collapsible: true,pagination:true,onDblClickRow:onDblClickRow">
							<tr>
								
							</tr>
							<thead>
								<tr>
									<th id="ds" halign="center" field="department_id" align="center"
										sortable="true" width="60px">部门编号</th>
									<th halign="center" field="department_name" align="center"
										sortable="true" width="120px">部门名称</th>
												<th halign="center" field="center_name" align="center"
										sortable="true" width="120px">所属中心</th>
												<th halign="center" field="company_name" align="center"
										sortable="true" width="120px">所属公司</th>
										
										<th halign="center" field=company_id align="center" hidden="hidden"
										sortable="true" width="120px">中心id</th>	
										<th halign="center" field=center_id align="center" hidden="hidden"
										sortable="true" width="120px">公司id</th>	
						
								<!-- 	<th halign="center" field="psn" align="center" sortable="true"
										width="120px" formatter="operate_formatter">操作</th> -->
								</tr>
							</thead>

						</table>
					</div>
                        
				</div>
			</div>
		
	</form>

	<div id="addrow" class="easyui-window" title="部门信息" closed="true"
			modal="true" style="width:700px;height:500px;padding:5px;">

			<div region="north" style="padding: 10px;"
				minHeight="350px">
				<table width="90%" class="content">

					<tr>
						<td style="display: none">部门编号:</td>
						<td style="display: none"><input class="easyui-validatebox"
							type="text" name="department_id" 
							id="department_id" value="" /></td>
						<td style="width:80px;">部门名称:</td>
						<td ><input class="easyui-validatebox"
							type="text" name="department_name" style="width: 150px"
							id="department_name" value="" /></td>

						<td class="form-label" style="width:100px;"><a
							href="javascript:void(0)" onclick="wen('centerHome.do')">中心名称(选择):</a>
						</td>
						<td data-options="region:'north',title:'North Title',split:true"
							style="height:50px;"><input class="easyui-validatebox"
							type="text" name="center_name"
							id="center_name" value="" readonly="readonly"
							style="width: 150px"></td>
							
						<td style="display: none"><input class="easyui-validatebox" type="text"
							name="department_centerId"
							id="department_centerId" value=""
							style="width: 150px"></td>
						
					</tr>
						<td  style="height:70px;">公司名称:</td>
						<td><input class="easyui-validatebox" type="text"
							name="company_name"
							id="company_name" value=""
							style="width: 150px"></td>
							
						<td style="display: none"><input class="easyui-validatebox" type="text"
							name="department_companyId"
							id="department_companyId" value=""
							style="width: 150px"></td>	
					<tr>
					
					</tr>
					
				</table>

				<br /> <br /> <br />
				<div region="south" style="padding: 10px; text-align: center;"
					border="false">
					<a class="easyui-linkbutton"
						data-options="iconCls:'icon-system_save'" href="javascript:;"
						id="btnSave" onclick="btnSave()">保存</a>&nbsp;&nbsp;&nbsp;&nbsp; <a
						class="easyui-linkbutton"
						data-options="iconCls:'icon-system_close'" href="javascript:;"
						id="btnCancel" onclick="btnCancel()">取消</a>
				</div>
			</div>


			<div id="win" class="easyui-window"
				data-options="region:'center',title:'请选择值'" closed="true"
				style="height: 330px; width: 600px"></div>
		</div>
	

	<script type="text/javascript">

       //初始化数据
		$(function() {
			$("#data").admin_grid({
				queryBtn : "#btnSearch",
				excelBtn : "#btnExcel",
			
			});

		});
        
       
		//数据提交
		function btnSave() {
			
			var department_id = $("#department_id").val();
			var department_name = $("#department_name").val();
			
			var department_centerId = $("#department_centerId").val();

			var department_companyId = $("#department_companyId").val();

			if (department_name == "") {
				alert("部门名不能为空");
				return false;
			}

			$.ajax({
				type : 'POST',
				url : 'saveDepartment.do',
				data : {
					'department_id' : department_id,
					'department_name' : department_name,
					'department_centerId' : department_centerId,
					'department_companyId' : department_companyId

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
			window.location.href = "departmentList.do";
		};
		

		//批量删除处理
		function btnDele() {
			var rows = $("#data").datagrid("getSelections");
			if (rows.length == 0) {
				alert("请选择需要删除的行");
				return null;
			}

			var ids = [];
			for (var i = 0; i < rows.length; i++) {
				ids.push(rows[i].department_id);
			}

			$.ajax({
				type : 'POST',
				url : 'deleDepartment.do',
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
       
		function edit() {

			var rows = $("#data").datagrid("getSelections");
			if (rows.length == 0) {
				alert("请选择需要修改的行");
				return null;
			}
			if (rows.length > 1) {
				alert("不能选择多行喔");
				return null;
			}
			
			$("#department_id").attr("value",
					rows[0].department_id);
			$("#department_name").attr("value",
					rows[0].department_name);
			$("#center_name").attr("value",
					rows[0].center_name);
			$("#company_name").attr("value",
					rows[0].company_name);
			$("#department_centerId").attr("value",
					rows[0].center_id);
			$("#department_companyId").attr("value",
					rows[0].company_id);
			$('#addrow').window('open');
		}
		
		function wen(src) {
			var hrefs = "<iframe id='son' src='"
					+ src
					+ "' allowTransparency='true' style='border:0;width:99%;height:99%;padding-left:2px;' frameBorder='0'></iframe>";
			$("#win").html(hrefs);
			$("#win").window("open");
		}
		
       
	    function onDblClickRow (rowIndex, rowData){    
	        var namen=rowData.department_id;
	        $(window.parent.$("#department").val(namen)); //给父页面赋值
	        
	        $(window.parent.$("#centers").window("close"));
	    };
		
	</script>

</body>
</html>