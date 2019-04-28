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
					style="overflow: hidden; padding: 5px 5px 28px 5px;">
					<div class="easyui-panel" title="查询条件" style="margin-bottom: 5px;">

						<div class="search">
							<ul>
								<li><label>项目名称：</label><input type="text" name="construct_project_name" id="construct_project_name"
									class="easyui-validatebox" style="width: 120px" /></li>
								<li>&nbsp;&nbsp; <a class="easyui-linkbutton"
									data-options="iconCls:'icon-search'" href="javascript:;"
									id="btnSearch">查询</a></li>
								<!-- 不要删除，暂时隐藏
								<li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true"
									onclick="btnDele();">删除项目</a></li> -->
								<shop:permission code="Maintain_Project_New">
									<li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton" iconCls="icon-edit"
									plain="true" onclick="btnUpdate();">修改项目</a></li>
								<li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton" iconCls="icon-add"
									plain="true" onclick="btnAdd();">新增项目</a></li>
								</shop:permission>
								<li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton" iconCls="icon-search"
									plain="true" onclick="btnView();">查看项目</a></li>
								<shop:permission code="Maintain_Project_Ctrl">	
								<li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton" 
									onclick="btnMaintain();">保养项目</a></li>
								</shop:permission>
								<shop:permission code="Maintain_CheckConList">	
								<li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton" 
									onclick="btnCheckConList();">项目保养内容</a></li>
								</shop:permission>		
							</ul>
							<div class="clear"></div>
						</div>
					</div>
				</div>
				
				<div region="center" split="false" border="false"
					style="overflow: hidden; padding: 0 5px 5px 5px" id="dataList">
					<table id="data" title="" fit="true"
						data-options="collapsible: true,pagination:true,rownumbers:true">
						<thead>
							<tr>
								<th id="ds" halign="center" field="construct_project_id" align="center"
									sortable="true" width="60px">项目编号</th>
								<th id="ds" halign="center" field="manage_contract_id" align="center" hidden="hidden"
									sortable="true" width="60px">合同编号</th>	
								<th halign="center" field="construct_project_name" align="center"
									sortable="true" width="220px">项目名称</th>
								
								<th halign="center" field="manage_contract_firstParty" align="center"
									sortable="true" width="250px">甲方</th>
									
								<th halign="center" field="construct_project_addr" align="center" 
									sortable="true" width="250px">工程地址</th>
								<th halign="center" field="construct_project_leader" align="center" 
									sortable="true" width="120px">项目经理</th>
								<th halign="center" field="construct_project_leaderTel" align="center" 
									sortable="true" width="120px">项目经理联系方式</th>
								<th halign="center" field="manage_contract_startTime" align="center" 
									sortable="true" width="120px" formatter="startDate">合同项目开始日期</th>
								<th halign="center" field="manage_contract_endTime" align="center" 
									sortable="true" width="120px" formatter="endDate">合同项目结束日期</th>
							</tr>
						</thead>
					</table>
				</div>

			</div>
		</div>





	<script type="text/javascript">
		function operate_formatter(value, row, index) {
			var html = "";

			html += '<a href="javascript:;" class="opt"    cmd="taskList" i="' + index + '">项目作业</a>';

			return html;
		}

		//新增
		function btnAdd() {
			location.href = "constructNew.do?projectDep="+${projectDep}+"&construct_project_id=";
		}
		
		function   formatDate(now)   {     
            var   year=now.getFullYear();     
            var   month=now.getMonth()+1;     
            var   date=now.getDate();     
            return   year+"-"+month+"-"+date;
        }     
       
		
		 function endDate(value, row, index) {
			if (value == undefined||value=="")
				return "";
			 var  now=new   Date(value); 
			return formatDate(now); 
		   
		}
		 function startDate(value, row, index) {
				if (value == undefined||value=="")
					return "";
				 var  now=new   Date(value); 
				return formatDate(now); 
			   
			}
		
		 function btnCheckConList(){
			 location.href = "maintainCheckConList.do";
		 }
		 
		 function btnMaintain() {
				var rows = $("#data").datagrid("getSelections");
				if (rows.length == 0) {
					alert("请选择需要保养的的项目");
					return null;
				}
				if (rows.length > 1) {
					alert("不能选择多个喔");
					return null;
				}
				
				location.href = "maintainCheckRecordList.do?construct_project_id="+rows[0].construct_project_id+"&manage_contract_id="+rows[0].manage_contract_id;
			}
		 
		function btnView() {
			var rows = $("#data").datagrid("getSelections");
			if (rows.length == 0) {
				alert("请选择需要查看的行");
				return null;
			}
			if (rows.length > 1) {
				alert("不能选择多行喔");
				return null;
			}
			
			location.href = "constructView.do?projectDep="+${projectDep}+"&construct_project_id="+rows[0].construct_project_id;
		}
		
		function btnUpdate(){
			var rows = $("#data").datagrid("getSelections");
			if (rows.length == 0) {
				alert("请选择需要修改的行");
				return null;
			}
			if (rows.length > 1) {
				alert("不能选择多行喔");
				return null;
			}
			
			location.href = "constructNew.do?projectDep="+${projectDep}+"&construct_project_id="+rows[0].construct_project_id;
		}
		
		

		/***处理操作列的功能***/
		function cmdHanlder(cmd, row) {
			if (cmd == "taskList") {
				
				location.href = "taskList.do?" + $.param({
					'construct_project_id' : row.construct_project_id, //传参项目部编号
				});

			}
		}

		//批量删除处理
		function btnDele() {
			var rows = $("#data").datagrid("getSelections");//获取表格数据
			if (rows.length == 0) {
				alert("请选择需要删除的行");
				return null;
			}

			var ids = [];
			for (var i = 0; i < rows.length; i++) {
				ids.push(rows[i].construct_project_id);
			}

			$.ajax({
				type : 'POST',
				url : 'delePro.do',
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