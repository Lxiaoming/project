<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>首页</title>
<jsp:include page="../common/css.jsp"></jsp:include>
<jsp:include page="../common/scripts.jsp"></jsp:include>
<%@ taglib uri="PowerCheck" prefix="shop"%>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/res/css/demo.css" type="text/css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/res/css/zTreeStyle.css"
	type="text/css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/res/js/jquery.ztree.core.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/res/js/jquery.ztree.exedit.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/res/js/jquery.ztree.excheck.min.js"></script>


<script
	src="<%=request.getContextPath()%>/res/admin/scripts/admin_grid.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/res/admin/scripts/ueditor/third-party/codemirror/codemirror.js"
	type="text/javascript"></script>
<style>
#tree  ul {
	background-color: #F0F8FF
}

ul.ztree {
	height: 100%;
	with: 100%;
}

.ztree * {
	font-size: 15px;
}
</style>

</head>
<body class="easyui-layout" style="overflow-y: hidden;" scroll="no">

	<div id="main" region="center"
		style="background: #eee; padding: 5px; overflow-y: hidden;">

		<div class="easyui-layout" fit="true">

			<div id="tree" data-options="region:'west',split:true" title="菜单"
				style="width:200px;height:500px; position:relative;">
				<ul style=" margin:0; padding:0;" id="treeDemo" class="ztree"></ul>
			</div>


			<div data-options="region:'center',title:'列表',iconCls:'icon-ok'">


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
									<li style="display: none"><label>公司：</label><input
										type="text" name="company_name" id="company_name"
										class="easyui-validatebox" style="width: 120px" /></li>
									<li style="display: none"><label>中心：</label><input
										type="text" name="center_name" id="center_name"
										class="easyui-validatebox" style="width: 120px" /></li>
									<li style="display: none"><label>部门：</label><input
										type="text" name="department_name" id="department_name"
										class="easyui-validatebox" style="width: 120px" /></li>

									<li><label> 姓名：</label><input type="text" name="username"
										class="easyui-validatebox" style="width: 120px" /></li>


								</ul>
								<div class="clear"></div>
							</div>

							<div id="tb">
								
									<a href="#" class="easyui-linkbutton" iconCls="icon-add"
										plain="true" onclick="addUser();">新增员工</a>

								 <shop:permission code="DELETE_PERSONNEL">
									<a href="#" class="easyui-linkbutton" iconCls="icon-tip"
										plain="true" onclick="attendAdd()">打卡记录</a>	
								</shop:permission>
							</div>
						</div>
					</div>

					<div region="center" split="false" border="false"
						style="overflow: hidden; padding: 0 5px 5px 5px" id="dataList">
						<table id="data" title="" fit="true"
							data-options="collapsible: true,pagination:true,rownumbers:true">
							
							<thead>
								<tr>
									 <th   halign="center" field="userid" hidden="hidden"
										sortable="true" width="60px">员工编号</th>
									<th halign="center" field="user_num" align="center"
										sortable="true" width="60px">工号</th>
									<th halign="center" field="username" align="center"
										sortable="true" width="120px">姓名</th>
									<th halign="center" field="phone_number" align="center"
										sortable="true" width="120px">电话号码</th>
									<th halign="center" field="sex" align="center" sortable="true"
										width="120px" formatter="sex_formatter">性别</th>
									<th halign="center" field="company_name" align="center"
										sortable="true" width="120px">公司</th>
									<th halign="center" field="center_name" align="center"
										sortable="true" width="120px">中心</th>
									<th halign="center" field="center_id" align="center"
										sortable="true" width="120px">中心id</th>	
									<th halign="center" field="department_name" align="center"
										sortable="true" width="120px">部门</th>

									<th halign="center" field="user_card" align="center"
										sortable="true" width="120px">身份证号码</th>
									<th halign="center" field="card_address" align="center"
										sortable="true" width="120px">身份证地址</th>
									<th halign="center" field="status" align="center"
										width="120px" formatter="operate_formatter">操作</th>
								</tr>
							</thead>

						</table>
					</div>

		</div>
		</div></div></div>
		
		<div id="worker_att" class="easyui-window"
				data-options="region:'center',title:'补考勤'" closed="true"
				style="height: auto; width: 420px">
				<div class="easyui-panel" style="width:400px">
					<div style="padding:10px 60px 20px 60px">
						<form id="workerAtt" method="post">
							<table cellpadding="5">
								<tr>
									<td>姓名:</td>
									<td><input type="text" readonly="readonly"
										id="username"
										name="username" ></input></td>
								</tr>
								<tr>
									<td>打卡日期:</td>
									<td><input type="text" id="hr_attend_date" class="easyui-datebox"
										name="hr_attend_date" ></input></td>
								</tr>

								<!-- <tr>
									<td>工作时长（天）:</td>
									<td><input type="number" 
										id="hr_attend_WTLength"
										name="hr_attend_WTLength" min="1"></input></td>
								</tr> -->

								<tr style="display: none">
									<td>userId:</td>
									<td><input type="text"
										id="userid"
										name="userid" readonly="readonly"></input></td>
								</tr>

								<tr style="display: none">
									<td>项目id:</td>
									<td><input type="text" class="easyui-textbox"
										id="supplierMod_worker_projectId"
										name="supplierMod_worker_projectId" readonly="readonly"></input></td>
								</tr>
								<tr style="display: none">
									<td>班组id:</td>
									<td><input class="easyui-textbox" type="text"
										name="supplierMod_worker_workTeamId"
										id="supplierMod_worker_workTeamId" readonly="readonly"></input></td>
								</tr>
							</table>
						</form>

						<div style="text-align:center;padding:5px">
							<a href="javascript:void(0)" class="easyui-linkbutton"
								onclick="workerAtt()">确定</a>
						</div>
					</div>
				</div>
			</div>

	<script type="text/javascript">
	
		var center_name=${center_name};	
	
		function operate_formatter(value, row, index) {
			var html = "";
			html += '<a href="javascript:;" class="opt"  data-options="iconCls:\'icon-edit\',plain:true"  cmd="edit" i="' + index + '">编辑</a>';

			return html;
		}

		function dateformat(value, row, index) {
			if (value == undefined)
				return "";
			var date = new Date(value);
			var pattern = "yyyy-MM-dd hh:mm:ss";
			return date.format(pattern);
		}


		function sex_formatter(value, row, index) {
			if (value == 1) {
				return "男";
			} else if (value == 2) {
				return "女";

			}
		}


		function addUser() {
			location.href = "workerUserNew.do?center_name="+center_name;

		}
		/***处理操作列的功能***/
		function cmdHanlder(cmd, row) {
			if (cmd == "edit") {
				location.href = "workerUserNew.do?" + $.param({
					'cid' : row.userid, //获取用户id
					'department' : row.department,
					'center_name':center_name
				});

			}

		}

		
		
		//劳动力补卡
		function attendAdd() {
			var rows = $('#data').datagrid('getSelections');
			if (rows.length == 0) {
				$.messager.alert("提示", "请选择需要修改的行");
				return ;
			}
			if (rows.length > 1) {
				$.messager.alert("提示", "不能选择多行喔");
				return ;
			}
			
			$.ajax({
				type : 'get',
				url : 'getworkerUser.do',
				data : {
					'userid' :rows[0].userid
				},
				success : function(data) {
					if(data==''||data==null||data==undefined){
						alert("请先添加人员分配！");
					}else{
						
						$("#worker_att").window("open");
						$('#workerAtt').form('load', data);
						console.log(data)
					}
				
				}
			});
	
		}
		
		function workerAtt(){
			$.ajax({
				url : "saveWorkerAtt.do",
				type : 'POST',
				data :{ 'hr_attend_date':$("#hr_attend_date").datetimebox("getValue"),
						'hr_attend_WTLength':$("#hr_attend_WTLength").val(),
						'hr_attend_userId':$("#userid").val(),
						'hr_attend_projectId':$("#supplierMod_worker_projectId").val(),
						'hr_attend_workTeamId':$("#supplierMod_worker_workTeamId").val()
				       
				},success : function(data) {
					 if (data.ero == "") {
						 $.messager.alert('保存成功');	
							window.location.reload();
					} else {
						 $.messager.alert('保存失败');
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


		//左树
		var zTreeObj;
		var data = $("#roleName").val();

		var setting = {
			check : {
				//enable: true,
				chkStyle : "checkbox",
				chkboxType : {
					"Y" : "p",
					"N" : "s"
				},
				chkDisabledInherit : true
			},
			callback : {
				onClick : zTreeOnClick
			},
		};

		var zNodes;
		$(document).ready(function() {

			$.ajax({
				async : false,

				type : 'get',
				dataType : "json",
				data : {
					'roleName' : data
				},
				url : "workerUserTreeList.do?center_name="+center_name,
				error : function() {
					alert('请求失败');
				},
				success : function(data) {
					zNodes = data;
				}
			});

			zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);


			var treeObj = $.fn.zTree.getZTreeObj("treeDemo"); 
			treeObj.expandAll(true); 
		});
		


		
		
		function zTreeOnClick(event, treeId, treeNode) {
			var nodes = zTreeObj.getSelectedNodes();
			var flag = nodes[0].isParent;
			var parentNode = nodes[0].getParentNode();
			if (flag == true) {
				//第二个节点
				if (parentNode != null) {
					$("#center_name").val(treeNode.name);
					$("#company_name").val("");
					$("#department_name").val("");
					//第一个节点	
				} /* else {
					$("#company_name").val(treeNode.name);
					$("#center_name").val("");
					$("#department_name").val("");
				} */
				//第三个节点
			} else {
				if (parentNode.parentTId != null) {
					$("#department_name").val(treeNode.name);
					$("#center_name").val("");
					$("#company_name").val("");
				} else {
					$("#center_name").val(treeNode.name);
					$("#company_name").val("");
					$("#department_name").val("");
				}

			}

			//IE
			if (document.all) {
				document.getElementById("btnSearch").click();
			}
			// 其它浏览器
			else {
				var e = document.createEvent("MouseEvents");
				e.initEvent("click", true, true); //这里的click可以换成你想触发的行为
				document.getElementById("btnSearch").dispatchEvent(e); //这里的clickME可以换成你想触发行为的DOM结点
			}

		};
	</script>

</body>


</html>