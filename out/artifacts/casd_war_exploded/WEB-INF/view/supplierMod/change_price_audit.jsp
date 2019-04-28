<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>

<head>
<title></title>

<jsp:include page="../common/css.jsp"></jsp:include>
<jsp:include page="../common/scripts.jsp"></jsp:include>

<script
	src="<%=request.getContextPath()%>/res/admin/scripts/admin_grid.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/res/admin/scripts/ueditor/third-party/codemirror/codemirror.js"
	type="text/javascript"></script>

<style type="text/css">
fieldset {
	border: solid 1px #aaa;
}

.hideFieldset {
	border-left: 0;
	border-right: 0;
	border-bottom: 0;
}

.hideFieldset .fieldset-body {
	display: none;
}

#main {
	height: 120px;
	border: 1px solid #000;
	margin: 10px auto;
	float: left;
}

#main .title {
	float: left;
}

#mes-tec {
	height: 120px;
	width: 40px;
	border-right: 1px solid #000;
	text-align: center;
}

#person {
	height: 120px;
	width: 50px;
	border-right: 1px solid #000;
}

#person div input {
	height: 120px;
	width: 50px;
	border: 0;
	padding-left: 10px;
	line-height: 120px;
}

#option {
	width: 170px;
	border-right: 1px solid #000;
	height: 120px;
}

#option div input {
	width: 170px;
	height: 120px;
	border: 0;
	padding-left: 10px;
	line-height: 120px;
}
</style>
</head>
<body>
	<div style="margin:20px 0;"></div>
<div>
<div><input type="text" id="taskName" hidden="hidden" value="${taskName}"/></div>
	<table id="materialListID" class="easyui-datagrid" title="变更材料"
		style="width:970px;height:350px"
		data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				toolbar: '#tb'
				">
		<thead>
	<tr>
							<th halign="center" field="construct_material_priceId"
								align="center" sortable="true" width="60px">单价编号</th>
								<th halign="center" hidden="hidden" field="construct_material_supplierId"
								align="center" sortable="true" width="60px">供应商编号</th>
							<th halign="center" field="construct_material_name"
								align="center" sortable="true" width="120px">材料名称</th>
							<th halign="center" field="construct_material_model"
								align="center" sortable="true" width="120px">型号规格</th>
							<th halign="center" field="construct_material_modelId" hidden="hidden"
								align="center" sortable="true" width="120px">型号规格id</th>	
							<th halign="center" field="construct_material_unit"
								align="center" sortable="true" width="120px">单位</th>							
							<th halign="center" field="construct_material_supplier"
								align="center" sortable="true" width="120px">供应商</th>
							<th halign="center" field="construct_material_supplierTel"
								align="center" sortable="true" width="120px">供应商电话</th>	
							<th halign="center" field="construct_material_price"
								align="center" sortable="true" width="120px">最新单价</th>
							<th halign="center" field="construct_lowest_price"
								align="center" sortable="true" width="120px">最低单价</th>
							<th halign="center" field="construct_latest_price"
								align="center" sortable="true" width="120px">原价</th>	
							<th halign="center" field="construct_material_remarks"
								align="center" sortable="true" width="120px">备注</th>

						</tr>
					</thead>
					
	</table>

	</div>

	<br/>
	<div class="fieldset-body">
		<table class="form-table" border="0" cellpadding="5" cellspacing="2">
			<tr>
				
				<td class="form-label" style="width:80px;">下一审核人：</td>
				<td style="width:150px"><select name="username" id="username"
					style="width:120px;">
						<option value="">请选择审批人</option>
						<c:forEach items="${userList}" var="c">
							<option value="${c.userid}">${c.username}</option>
						</c:forEach>
				</select></td>
				<td class="form-label" style="width:80px;">审核意见：</td>
				<td style="width:150px"><input type="text" value=""
					name="options" id="options" class="mini-textbox" /></td> 
					
					<td style="width:150px"><input id="bot" onclick="change_examine()"
					type="button" value="${bot}"> <c:if
						test="${both !=undefined}">
						<input class="both" onclick="back_price()" type="button"
							value="${both}">
					</c:if></td>    
		    <!--    <td><a href="#" class="easyui-linkbutton" onclick="change_examine();">提交</a></td> -->
		   
			</tr>

		</table>
	</div>

	<div id="audit">

		<table id="history" class="easyui-datagrid" title="审批记录"
			style="width:970px;height:150px"
			data-options="
				iconCls: 'icon-edit',
				singleSelect: true,">
	
			<thead>
                <tr>
				<th data-options="field:'username',width:80">审核人</th>
				<th data-options="field:'MESSAGE_',width:300">审核意见</th>
				<th data-options="field:'center_name',width:100">中心</th>
				<th data-options="field:'department_name',width:200">部门</th>
	
	
			</tr>


			</thead>
		</table>
	</div>
 <script type="text/javascript">
	$(document).ready(function() {
		var dataList = ${dataList}.dataList;
		$('#materialListID').datagrid('loadData', dataList);
		
		var history = ${history}.history;
		$('#history').datagrid('loadData', history);
	});
	
	   function  change_examine(){
		   
		var bizId = ${taskid};        //获取业务键
		var username = $("#username").val();//获取审核人
		var taskName = $("#taskName").val();  //获取节点名称
		var options = $("#options").val(); //获取审核意见
       
			var rows=$('#materialListID').datagrid('getRows');
		
			if (options == "") {
				$.messager.alert("提示","审核意见不能为空!");
				return false;
			}
			if (username == '') {
				if (taskName != '董事会') {
					$.messager.alert("提示", "请选择审核人!");
					return false;
				}
			}
			
		
			
			 $.ajax({
					type : 'post',
					url : 'change_examine.do',
					data : {
						    'taskid':bizId,
						    'username' : username,
							'taskName' : taskName,
							'options' : options,
						
					},
					success : function(data) {
						if (data.Success) {
							$.messager.alert("提示", data.Msg, "info", function() {
								location.href = "findTaskList.do";
								});
						
						} else {
							$.messager.alert("提示", data.Msg, "error");
							$('#materialListID').datagrid('reload');

						}
					}
				});
			 		
	}
	   
	   
	   function back_price(){
		   
			var options = $("#options").val();
			if (options == '') {
				alert("意见不能为空!");
				return false;
			}
			$.ajax({
				type : "POST",
				url : "back_Newprice.do",
				data : {
					'taskid' : ${taskid},

					'options' : options,
				},
				success : function(data) {
					location.href = "findTaskList.do";
				}
			});

		}

		
	   
	</script>
</body>
</html>
