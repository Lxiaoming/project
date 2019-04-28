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
	<link rel="stylesheet" href="<%=request.getContextPath()%>/res/layui/css/layui.css?HJ_v=<%=Math.random()%>"  media="all">
	<script src="<%=request.getContextPath()%>/res/layui/layui.js?HJ_v=<%=Math.random()%>" charset="utf-8"></script>

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
</style>
</head>
<body>
	<div>
		<table>
			<tr>
				<td width="950px">
					<h1>广东诚安时代人事档案表</h1>
				</td>
				<td><a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-save',plain:true" onclick="accept()">保存</a>
				</td>
				<td><a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-back',plain:true" onclick="btnCancel()">取消</a>
				</td>
			</tr>
		</table>

	</div>
		
	<fieldset id="fd1" style="width:1100px;">
		<legend>
			<span>人事档案</span>
		</legend>
		<div class="fieldset-body">
			<form id="head" method="post">
				<table class="form-table" border="0" cellpadding="1" cellspacing="2">
					<tr>
						<td><input style="display: none" name="userid" id="userid"
							value="${pro.userid}" /></td>
						<td><input style="display: none" name="password" id="password"
							value="${pro.password}" /></td>	
					</tr>
					<tr>
						<td class="form-label" style="width:60px;">姓名：</td>
						<td style="width:150px"><input type="text" name="username"
							id="username" value="${pro.username}" class="mini-textbox" /></td>
						<td class="form-label" style="width:60px;">性别：</td>
						<td><select id="sex" name="sex" style="width:142px;">
								<option value="1">男</option>
								<option value="2">女</option>
						</select></td>
						<td class="form-label">年龄：</td>
						<td><input name="age" id="age" value="${pro.age}"
							class="mini-spinner" readonly="readonly" /></td>

						<td class="form-label" style="width:60px;">出生日期：</td>
						<td><input value="${pro.birth_date}" class="easyui-datebox"
							name="birth_date" id="birth_date" value="" style="width:150px"></td>

						<td class="form-label" style="width:60px;">血型：</td>
						<td style="width:150px"><select id="blood" name="blood"
							style="width:142px;">
								<option value="0">请选择</option>
								<option value="1">O型</option>
								<option value="2">A型</option>
								<option value="3">B型</option>
								<option value="4">AB型</option>
						</select></td>
					</tr>
					<tr>
						<td class="form-label" style="width:60px;">民族：</td>
						<td style="width:150px"><input type="text" name="nation"
							id="nation" value="${pro.nation}" class="mini-textbox" /></td>
						<td class="form-label" style="width:60px;">体重：</td>
						<td style="width:150px"><input type="text" name="weight"
							id="weight" value="${pro.weight}" class="mini-textbox" /></td>
						<td class="form-label" style="width:60px;">生日：</td>
						<td style="width:150px"><input type="text" name="birth"
							id="birth" value="${pro.birth}" class="mini-textbox" /></td>
						<td class="form-label" style="width:60px;">健康状态：</td>
						<td style="width:150px"><select id="health" name="health"
							style="width:142px;">
								<option value="0">请选择</option>
								<option value="1">健康</option>
								<option value="2">良好</option>
								<option value="3">较差</option>
						</select></td>

						<td class="form-label">婚姻状况：</td>
						<td style="width:150px"><select id="marital_status"
							name="marital_status" style="width:142px;">
								<option value="1">未婚</option>
								<option value="2">已婚</option>
						</select></td>

					</tr>
					<tr>
						<td class="form-label">户籍地：</td>
						<td><textarea id="card_address"
								style="overflow-x:hidden;overflow-y:hidden" name="card_address">${pro.card_address}
					</textarea></td>
						<td class="form-label" style="width:60px;">籍贯：</td>
						<td style="width:150px"><input type="text"
							name="native_place" id="native_place" value="${pro.native_place}"
							class="mini-textbox" /></td>
						<td class="form-label" style="width:60px;">学历：</td>
						<td style="width:150px"><select id=education name="education"
							style="width:143px;">
								<option value="0" selected>请选择</option>
								<option value="1" selected>初中</option>
								<option value="2" selected>高中</option>
								<option value="3" selected>中专</option>
								<option value="4" selected>大专</option>
								<option value="5" selected>本科</option>
								<option value="6" selected>硕士</option>
								<option value="7" selected>博士</option>

						</select></td>

						<td class="form-label">身份证号：</td>
						<td><input name="user_card" id="user_card"
							class="mini-textarea" style="width:142px;"
							value="${pro.user_card}" /></td>
						<td class="form-label">职位：</td>
						<td style="width:150px"><select id="role_id" name="role_id"
							style="width:143px;">
								<option value="0" selected>请选择</option>
								<c:forEach items="${roles}" var="c">

									<option value="${c.role_id}">${c.role_name}</option>
								</c:forEach>
						</select></td>
					</tr>


					<tr>
						<td class="form-label" style="width:60px;">邮箱：</td>
						<td style="width:150px"><input name="email" id="email"
							class="mini-textbox" value="${pro.email}" /></td>
						<td class="form-label" style="width:60px;">联系电话：</td>
						<td style="width:150px"><input name="phone_number"
							id="phone_number" value="${pro.phone_number}"
							class="mini-textbox" /></td>
						<td class="form-label" style="width:60px;">紧急联系人：</td>
						<td style="width:150px"><input name="emergent_contact"
							id="emergent_contact" class="mini-textbox"
							value="${pro.emergent_contact}" /></td>
						<td class="form-label" style="width:60px;">紧急联系人电话：</td>
						<td style="width:150px"><input type="text"
							name="emergent_phone" id="emergent_phone"
							value="${pro.emergent_phone}" class="mini-textbox" /></td>
						<td class="form-label">常住地址：</td>
						<td><textarea style="overflow-x:hidden"
								id="permanent_address" name="permanent_address">${pro.permanent_address}
					</textarea></td>

					</tr>


					<tr>
						<td class="form-label" style="width:60px;">公司：</td>
						<td style="width:150px"><input name="company" id="company"
							readonly="readonly" class="mini-textbox"
							value="${pro.company_name}" /></td>
						<td class="form-label" style="width:60px;">部门：</td>
						<td style="width:150px"><input name="department_name"
							id="department_name" readonly="readonly" class="mini-textbox"
							value="${pro.department_name}" /></td>

						<td class="form-label" style="display: none">部门id：</td>
						<td style="display: none"><input name="department"
							id="department" class="mini-textbox" value="${pro.department_id}" /></td>
						<td class="form-label" style="display: none">中心id：</td>
						<td style="display: none"><input name="center_id"
							id="center_id" class="mini-textbox" value="${pro.center_id}" /></td>
						<td class="form-label" style="width:70px;"><a
							href="javascript:void(0)" onclick="openCompany()">中心(选择):</a>
						</td>
						<td data-options="region:'north',title:'North Title',split:true"
							style="height:50px;"><input type="text" name=center_name
							id="center_name" readonly="readonly" value="${pro.center_name}" />
						</td>	
						<td class="form-label">入司日期：</td>
							<td><input value="${pro.incorporation_date}"
							class="easyui-datebox" name="incorporation_date"
							id="incorporation_date" value="" style="width:150px"></td>
						<td class="form-label" style="width:60px;">级别：</td>
						<td style="width:150px"><select id="level" name="level"
							style="width:142px;">
								<option value="0">请选择</option>
								<option value="1">一级</option>
								<option value="2">二级</option>
								<option value="3">三级</option>
								<option value="4">四级</option>
								<option value="5">五级</option>
								<option value="6">六级</option>
								<option value="7">七级</option>
								<option value="8">八级</option>
								<option value="9">九级</option>
								<option value="10">十级</option>
								<option value="11">十一级</option>
						</select></td>	
					</tr>
					<tr>

						
						<td class="form-label" style="width:60px;">状态：</td>
						<td style="width:150px"><select id="status" name="status" style="width:142px">
								<option value="0">请选择</option>
								<option value="1">在职</option>
								<option value="2">离职</option>
								<option value="3">试用期</option>
						</select></td>
						


					</tr>
				 	<tr style="display: none">
						<td class="form-label" style="width:60px;">工号：</td>
						<td style="width:150px"><input type="text" name="user_num"
							id="user_num" value="${pro.user_num}" class="mini-textbox" /></td>
					</tr>

				</table>
			</form>
		</div>


		<div id="centers" class="easyui-window"
			data-options="region:'center',title:'请选择值'" closed="true"
			style="height: 500px; width: 800px"></div>
	</fieldset>




	<script type="text/javascript">
	
	layui.use(['form','layer','layedit','laydate'], function(){
		  var form = layui.form
		  ,layer = layui.layer
		  ,layedit = layui.layedit
		  ,laydate = layui.laydate;
		  
		  
		  //各种基于事件的操作，下面会有进一步介绍
		});
	
		var center_name=${center_name};	

		$(function() {
			//数据初始化
			$("#sex").val(${pro.sex});
			$("#marital_status").val(${pro.marital_status});
			$("#role_id").val(${pro.role_id});
			$("#education").val(${pro.education});
			$("#level").val(${pro.level});
			$("#blood").val(${pro.blood});
			$("#health").val(${pro.health});
			$("#status").val(${pro.status});

		});

		
		
		
		
		function operate_status(value, row, index) {
			if (row.bc_status == 0) {
				return "初始录入";
			}
			if (row.bc_status == 1) {
				return "审核中";
			}
			if (row.bc_status == 2) {
				return "正常转正";
			}
			if (row.bc_status == 3) {
				return "延迟转正";
			}
			if (row.bc_status == 4) {
				return "辞退";
			}

		}
		//取消
		function btnCancel() {
			location.href = "workerUserList.do?center_name="+center_name;
		}

		function data(rowData){
			$("#department").val(rowData.department_id); 
	        $("#department_name").val(rowData.department_name);
	        $("#center_name").val(rowData.center_name);
	        $("#center_id").val(rowData.center_id);
	        $("#company").val(rowData.company_name);
	        $("#centers").window("close");
		}

		//添加时处理
		function accept() {

			//校验邮箱
			if ($("#email").val() != "") {
				var myReg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
				if (!myReg.test($("#email").val())) {
					alert("邮箱格式错咯！");
					$("#email").focus();
					return false;
				}
			}

			if ($("#center_name").val() == ""||$("#center_name").val() != center_name) {
				alert("中心不正确");
				return false;
			}
			if($("#company").val() != "分供方"){
				
				alert("公司选择不正确");
				return false;
			}
			
			
			$.post("savePersonalRecords.do", $("#head").serialize(),
					function(data) {
						if (data.erro == "") {
							alert("保存成功");
							//location.reload(true);
							location.href = "workerUserNew.do?cid="
									+ $("#userid").val() + "&department="
									+ $("#department").val() + "&center_name="
									+ center_name+ "";
						} else {
							alert(data.erro);
						}
					});

		}


		//打开公司子页面
		 function openCompany(){
			 layer.open({
				  type: 2,
				  title:"部门选择框",
				  area: ['50%','50%'],
				  fixed: false, //不固定
				  maxmin: true,
				  content: 'orgCheck.do',
				  success:function(layero,index){
				
				  },end:function(data){
					
				  }
				});
		 }
		//子页面传值事件
		 function setCompany(data){
			$("#company").val(data.company_name);
			$("#department").val(data.department_id); 
			$("#department_name").val(data.department_name);
			$("#center_id").val(data.center_id);
			$("#center_name").val(data.center_name);
		  }
		

		$('#birth_date')
				.datebox(
						{
							onSelect : function(date) {
								var d = new Date(date);
								var Y = new Date().getFullYear();
								var M = new Date().getMonth() + 1;
								if (parseInt(Y) - parseInt(d.getFullYear()) < 0
										|| parseInt(Y)
												- parseInt(d.getFullYear()) == 0) {
									alert("请输入正确的时间日期");
									return false;
								}
								if (M > parseInt(d.getMonth() + 1)
										|| M == parseInt(d.getMonth() + 1)) {//过了生日
									if (parseInt(new Date().getDate()) > parseInt(d
											.getDate())) {
										$("#age")
												.val(
														parseInt(Y)
																- parseInt(d
																		.getFullYear()));
									} else {
										$("#age").val(
												parseInt(Y)
														- parseInt(d
																.getFullYear())
														- 1);
									}
								} else {
									$("#age").val(
											parseInt(Y)
													- parseInt(d.getFullYear())
													- 1);
								}

							}
						});
	</script>
</body>
</html>