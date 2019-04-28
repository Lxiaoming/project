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

									<li><label>员工编号：</label><input type="text" name="userid"
										class="easyui-validatebox" style="width: 120px" /></li>
									<li><label> 姓名：</label><input type="text" name="username"
										class="easyui-validatebox" style="width: 120px" /></li>

									<li><label> 状态：</label> <select id="status" name="status"
										class="easyui-combobox" style="width:120px;">
											<option value="0">请选择</option>
											<option value="1">在职</option>
											<option value="2">离职</option>
											<option value="3">试用期</option>
											<option value="4">实习</option>

									</select></li>

								</ul>
								<div class="clear"></div>
							</div>

							<div id="tb">
								<shop:permission code="DELETE_PERSONNEL">
									<a href="#" class="easyui-linkbutton" iconCls="icon-add"
										plain="true" onclick="addUser();">新增员工</a>
									<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
										plain="true" onclick="btnDele();">删除员工</a>
									<!-- <a href="qiuzhibiao.do" class="easyui-linkbutton"
										iconCls="icon-print" plain="true" onclick="qiuzhibiao()">求职登记表</a> -->

									<a href="#" class="easyui-linkbutton" iconCls="icon-tip"
										plain="true" onclick="becomeList()">未转正人员</a>

									<a href="#" class="easyui-linkbutton" iconCls="icon-tip"
										plain="true" onclick="resign()">离职</a>
									
									<a href="#" class="easyui-linkbutton" iconCls="icon-tip"
										plain="true" onclick="attendList()">打卡记录</a>
										
									<a href="#" class="easyui-linkbutton" iconCls="icon-tip"
										plain="true" onclick="base_Wages()">基本工资</a>
									
								</shop:permission>
							</div>
						</div>
					</div>

					<div region="center" split="false" border="false"
						style="overflow: hidden; padding: 0 5px 5px 5px" id="dataList">
						<table id="data" title="" fit="true"
							data-options="collapsible: true,pagination:true,onDblClickRow:onDblClickRow,rownumbers:true">
							<tr>
								<td><input type="hidden" name="trade_id"
									value="${param.userid}" /></td>
							</tr>
							<thead>
								<tr>
									<!--  <th   halign="center" field="userid" align="center" 
										sortable="true" width="60px">员工编号</th> -->
									<th halign="center" field="user_num" align="center"
										sortable="true" width="60px">工号</th>
									<th halign="center" field="username" align="center"
										sortable="true" width="120px">姓名</th>
									<th halign="center" field="phone_number" align="center"
										sortable="true" width="120px">电话号码</th>
									<th halign="center" field="sex" align="center" sortable="true"
										width="120px" formatter="sex_formatter">性别</th>
									<th halign="center" field="email" align="center"
										sortable="true" width="120px">邮箱</th>
									<th halign="center" field="company_name" align="center"
										sortable="true" width="120px">公司</th>
									<th halign="center" field="center_name" align="center"
										sortable="true" width="120px">中心</th>
									<th halign="center" field="department_name" align="center"
										sortable="true" width="120px">部门</th>

									<th halign="center" field="user_card" align="center"
										sortable="true" width="120px">身份证号码</th>
									<th halign="center" field="card_address" align="center"
										sortable="true" width="120px">身份证地址</th>
									<th halign="center" field="status" align="center"
										sortable="true" width="120px" formatter="condition">状态</th>
									<th halign="center" field="psn" align="center" sortable="true"
										width="120px" formatter="operate_formatter">操作</th>
								</tr>
							</thead>

						</table>
					</div>
					
					
					<div id="baseWages" class="easyui-window"
						data-options="region:'center',title:'工资'" closed="true"
						style="height: auto; width: 420px">
						<div class="easyui-panel"  style="width:400px">
							<div style="padding:10px 60px 20px 60px">
							<form id="wages" method="post">
								<table cellpadding="5">
									<tr style="display: none">
										<td>单据id:</td>
										<td><input  type="text"
											id="uc_wage_id" name="uc_wage_id" readonly="readonly"></input></td>
									</tr>
									<tr>
										<td>基本工资:</td>
										<td><input  type="text"
											id="uc_wage_base" name="uc_wage_base" ></input></td>
									</tr>
									<tr>
										<td>岗位工资:</td>
										<td><input  type="text"
											id="uc_wage_post" name="uc_wage_post" ></input></td>
									</tr>
									<tr>
										<td>绩效工资:</td>
										<td><input  type="text" name="uc_wage_achieve" id="uc_wage_achieve"></input></td>
									</tr>
									<tr style="display: none">
										<td>人员id:</td>
										<td><input  type="text" name="uc_wage_userId" id="uc_wage_userId"></input></td>
									</tr>
									<tr>
										<td>津贴补助:</td>
										<td><input type="text" name="uc_wage_subsidy" id="uc_wage_subsidy"></input></td>
									</tr>
									<tr>
										<td>代扣社保:</td>
										<td><input  type="text" type="text" name="uc_wage_socSec" id="uc_wage_socSec"></input></td>
									</tr>
									<tr>
										<td>代扣公积金:</td>
										<td><input  type="text" name="uc_wage_accFund" id="uc_wage_accFund"></input></td>
									</tr>
								</table>
							</form>

						<div style="text-align:center;padding:5px">
							<a href="javascript:void(0)" class="easyui-linkbutton"
								onclick="submitWages()">更新</a> 
						</div>
					</div>
				</div>
			</div>
					
					
					<div id="win" class="easyui-window"
						data-options="region:'center',title:'请选择'" closed="true"
						style="height: auto; width: 420px">
						<div class="easyui-panel"  style="width:400px">
							<div style="padding:10px 60px 20px 60px">
							<form id="resign" method="post">
								<table cellpadding="5">
									<tr>
										<td>申请日期:</td>
										<td><input  type="text"
											id="hr_resign_applyDate" name="hr_resign_applyDate" readonly="readonly"></input></td>
									</tr>
									<tr>
										<td>预定离公司日期:</td>
										<td><input class="easyui-datebox" type="text" name="hr_resign_schLeave" id="hr_resign_schLeave"></input></td>
									</tr>
									<tr style="display: none">
										<td>人员id:</td>
										<td><input class="easyui-textbox" type="text" name="hr_resign_userid" id="hr_resign_userid"></input></td>
									</tr>
									<tr>
										<td>离职类别:</td>
										<td><select class="easyui-combobox" style="width:124px;"
											id="hr_resign_category" name="hr_resign_category">
												<option value="1">辞职</option>
												<option value="2">辞退</option>										
												<option value="3">合同终止</option>
										</select></td>
									</tr>
								</table>
							</form>


						<div style="text-align:center;padding:5px">
							<a href="javascript:void(0)" class="easyui-linkbutton"
								onclick="submitForm()">确定</a> 
						</div>
					</div>


				</div>
			</div>
		</div>
	</div>
</div></div>

	<script type="text/javascript">
		function operate_formatter(value, row, index) {
			var html = "";
			<shop:permission code="DELETE_PERSONNEL">
			html += '<a href="javascript:;" class="opt"  data-options="iconCls:\'icon-edit\',plain:true"  cmd="edit" i="' + index + '">编辑</a>';
			</shop:permission>

			return html;
		}

		function dateformat(value, row, index) {
			if (value == undefined)
				return "";
			var date = new Date(value);
			var pattern = "yyyy-MM-dd hh:mm:ss";
			return date.format(pattern);
		}

		function return_type(value, row, index) {
			if (value == 0) {
				return "普通退货";
			}
		}

		function sex_formatter(value, row, index) {
			if (value == 1) {
				return "男";
			} else if (value == 2) {
				return "女";

			}
		}

		function condition(value, row, index) {

			if (value == 1) {
				return "在职";
			} else if (value == 2) {
				return "离职";
			} else if (value == 3) {
				return "试用期";
			} else if (value == 4) {
				return "实习";
			}
		}

		function addUser() {
			location.href = "personalRecords.do";

		}
		function psn_formatter(value, row, index) {
			var html = (value + "").replace(/\|/g, ";");
			return html.substr(1);
		}
		/***处理操作列的功能***/
		function cmdHanlder(cmd, row) {
			if (cmd == "edit") {
				location.href = "personalRecords.do?" + $.param({
					'cid' : row.userid, //获取用户id
					'department' : row.department
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
				ids.push(rows[i].userid);
			}

			$.ajax({
				type : 'POST',
				url : 'deleteUser.do',
				traditional : true,
				data : {
					'cid' : JSON.stringify(ids)
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

		//跳转求职表
		function qiuzhibiao() {
			window.location.href = "qiuzhibiao.do";
		}
		
		function submitWages(){
			$.messager.confirm('提示！', '你确定提交吗？', function(r) {
				if (r) {	
			$.post("submitWages.do?",$("#wages").serialize(),function(data) {
				if (data.success) {
					$.messager.alert("提示", "保存成功","info", function() {
						 location.reload(true);
							});
			      	}else {
					$.messager.alert("提示", data.msg,
							"error");
				}
						
			
			}); 	
				}});
			
		}
		
		
		function submitForm(){
			var rows = $("#data").datagrid('getSelected');
		
			$.messager.confirm('提示！', '你确定提交吗？', function(r) {
				if (r) {	
			$.post("addResign.do?username="+rows.username,$("#resign").serialize(),function(data) {
				if (data.Success) {
					$.messager.alert("提示", data.Msg+data.message,"info", function() {
						 location.reload(true);
							});
			      	}else {
					$.messager.alert("提示", data.Msg,
							"error");
				}
						
			
			}); 	
				}});
		}
		
		function resign() {
			var rows = $("#data").datagrid("getSelections");//获取表格数据
			if (rows.length == 0) {
				alert("请选择需要离职的人");
				return null;
			}
			if (rows.length > 1) {
				alert("只能选择一个人喔");
				return null;
			}
			if (confirm("你确定他要离职吗？")) {
				$("#hr_resign_userid").val(rows[0].userid);
				$("#win").window("open");
			} else {
				return false;
			}
		}
		
		function base_Wages(){
			var rows = $("#data").datagrid("getSelections");//获取表格数据
			if (rows.length == 0) {
				alert("请选择人");
				return null;
			}
			if (rows.length > 1) {
				alert("只能选择一个人喔");
				return null;
			}
			
			$.ajax({
				type : 'POST',
				url : 'base_Wages.do',
				traditional : true,
				data : {
					'userid' : rows[0].userid
				},
				success : function(data) {
					if(data.data!=null){
						$("#uc_wage_id").val(data.data.uc_wage_id);
						$("#uc_wage_base").val(data.data.uc_wage_base);
						$("#uc_wage_post").val(data.data.uc_wage_post);
						$("#uc_wage_achieve").val(data.data.uc_wage_achieve);
						$("#uc_wage_subsidy").val(data.data.uc_wage_subsidy);
						$("#uc_wage_socSec").val(data.data.uc_wage_socSec);
						$("#uc_wage_accFund").val(data.data.uc_wage_accFund);
					}else{
						$("#uc_wage_id").val(0);
					}
				}
			});
			
			$("#uc_wage_userId").val(rows[0].userid);
			$("#baseWages").window("open");
			
		}
		
		function attendList(){
		/* 
			console.log(zNodes[0].id) */
	location.href = "workerAttendList.do"; 
			
		}
		
		
		
		function becomeList() {

			location.href = "becomeList.do?" + $.param({

			});
		}
		$(function() {
			$("#data").admin_grid({
				queryBtn : "#btnSearch",
				excelBtn : "#btnExcel",
				cmdHanlder : cmdHanlder,

			});

		});

		function onDblClickRow(rowIndex, rowData) {
			//var rows = $("#data").datagrid('getSelected');
			var namen = rowData.username;
			$(window.parent.$("#fatherId").val(namen));

		};

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
				url : "userTreeList.do",
				error : function() {
					alert('请求失败');
				},
				success : function(data) {
					zNodes = data;
				}
			});

			zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
			
			//获取时间
			function getNowFormatDate() {
			    var date = new Date();
			    var seperator1 = "-";
			    var seperator2 = ":";
			    var month = date.getMonth() + 1;
			    var strDate = date.getDate();
			    if (month >= 1 && month <= 9) {
			        month = "0" + month;
			    }
			    if (strDate >= 0 && strDate <= 9) {
			        strDate = "0" + strDate;
			    }
			    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
			    return currentdate;
			}
			  var mytime=getNowFormatDate();//获取时间变量
		     $("#hr_resign_applyDate").val(mytime);	//给申请离职申请时间赋值
           
		});
	var treeNode=null;
		function zTreeOnClick(event, treeId, treeNode) {
			var nodes = zTreeObj.getSelectedNodes();
			var flag = nodes[0].isParent;
			var parentNode = nodes[0].getParentNode();
			//treeNode=treeNode.name;
			if (flag == true) {
				//第二个节点
				if (parentNode != null) {
					$("#center_name").val(treeNode.name);
					$("#company_name").val("");
					$("#department_name").val("");
					//第一个节点	
				} else {
					$("#company_name").val(treeNode.name);
					$("#center_name").val("");
					$("#department_name").val("");
				}
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