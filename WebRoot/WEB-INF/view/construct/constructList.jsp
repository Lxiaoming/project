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
								<shop:permission code="ADD_PROJECT_AP">
								<li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton" iconCls="icon-edit"
								plain="true" onclick="btnUpdate();">修改项目</a></li>
								<li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton" iconCls="icon-add"
									plain="true" onclick="btnAdd();">新增项目</a></li>
								</shop:permission>
								<li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton" iconCls="icon-search"
									plain="true" onclick="btnView();">查看项目</a></li>
								<shop:permission code="Material_Pur">	
								<li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton" iconCls="icon-mini_add"
									plain="true" onclick="purchaseList();">采购申请列表</a></li>
								<!--不要删除，暂时隐藏
								 <li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton" iconCls="icon-help"
									plain="true" onclick="purChange();">材料申购变更申请</a></li> -->
									
								</shop:permission>
								
								<shop:permission code="Operate">			
								<li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton" iconCls="icon-tip"
									plain="true" onclick="conMaterialList();">合同工程量</a></li>	
								<li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton" iconCls="icon-tip"
									plain="true" onclick="findContractData();">请款进度</a></li>
								</shop:permission>	
								
								<shop:permission code="Draw_Money">			
								<li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton" iconCls="icon-help"
									plain="true" onclick="drawmoney();">领款申请</a></li>	
								<li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton" iconCls="icon-help"
									plain="true" onclick="drawmoneyList();">领款列表</a></li>
								</shop:permission>
								
								
								<shop:permission code="Maintain_Project_Ctrl">	
								<li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton" iconCls="icon-lock" plain="true"
									onclick="btnMaintain();">保养项目</a></li>
								</shop:permission>
								<shop:permission code="Maintain_CheckConList">	
								<li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton"  iconCls="icon-lock" plain="true"
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
		
		function purchaseList() {
			var rows = $("#data").datagrid("getSelections");
			if (rows.length == 0) {
				alert("请选择对应项目");
				return null;
			}
			if (rows.length > 1) {
				alert("不能选择多行喔");
				return null;
			}
			//addSubPage("采购申请列表","purchaseList.do?construct_project_id="+rows[0].construct_project_id+"");

			location.href = "purchaseList.do?construct_project_id="+rows[0].construct_project_id;
		}
		function finalPurchaseList() {
			var rows = $("#data").datagrid("getSelections");
			if (rows.length == 0) {
				alert("请选择对应项目");
				return null;
			}
			if (rows.length > 1) {
				alert("不能选择多行喔");
				return null;
			}
			//addSubPage("采购明细列表","finalPurchaseList.do?construct_project_id="+rows[0].construct_project_id+"");
			location.href = "finalPurchaseList.do?construct_project_id="+rows[0].construct_project_id;
			
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
		
		function purChange(){
			var rows = $("#data").datagrid("getSelections");
			if (rows.length == 0) {
				alert("请选择需要查看的行");
				return null;
			}
			if (rows.length > 1) {
				alert("不能选择多行喔");
				return null;
			}
			location.href = "purChangeList.do?construct_project_id="+rows[0].construct_project_id;
			//addSubPage("申购变更申请","purChangeList.do?construct_project_id="+rows[0].construct_project_id+"");
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
		
		//新增合同工程量
		function btnAddMaterial() {
			location.href = "conMaterialList.do";
		}
		
		//领款申请
		function drawmoneyList() {
			var rows = $("#data").datagrid("getSelections");
			 
			if (rows.length == 0) {
				 $.messager.alert("提示","请选择对应项目");	
				return;
			}else if (rows.length > 1) {
				 $.messager.alert("提示","能选择多行");
				return ;
			}else{
				location.href = "drawmoneyList.do?" + $.param({
					'bizid' : rows[0].construct_project_id, 
					
				});
			}
		}	
		//领款申请
		function drawmoney() {
		  var bizid=null;
			var rows = $('#data').datagrid('getSelections');
		
			if(rows.length>1){
				 $.messager.alert("提示","只能选择一个项目");
				 return;
			}
			
			for(var i=0; i<rows.length; i++){
			    bizid=rows[0].construct_project_id;
			}
		    if(bizid==null){
		    	 $.messager.alert("操作提示","请选择项目");
		    	 return;
		    }
			location.href = "drawmoneyView.do?" + $.param({
				'bizid' : bizid, 
				'type' : 'save', 
			});
			
		}	
			
		//工程量
		function conMaterialList(){
			
			var rows = $("#data").datagrid("getSelections");
			 
			if (rows.length == 0) {
				 $.messager.alert("提示","请选择对应项目");	
				return;
			}else if (rows.length > 1) {
				 $.messager.alert("提示","能选择多行");
				return ;
			}else{
				location.href = "conMaterialList.do?" + $.param({
					'construct_project_id' : rows[0].construct_project_id, 
				});
			}
		}
		
		//请款进度
		function findContractData(){
			
			var rows = $("#data").datagrid("getSelections");
			 
			if (rows.length == 0) {
				 $.messager.alert("提示","请选择对应项目");	
				return;
			}else if (rows.length > 1) {
				 $.messager.alert("提示","能选择多行");
				return ;
			}else{
				location.href = "findContractData.do?" + $.param({
				    'type':"view",
				    'biz' : rows[0].construct_project_contractId, 
			});
			}
			
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