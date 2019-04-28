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
				<td><c:if test="${save!='save'}"><a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-save',plain:true" onclick="accept()">保存</a>
					</c:if>
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
							name="marital_status" style="width:150px;">
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
						<td class="form-label">入司日期：</td>
						<td><input value="${pro.incorporation_date}"
							class="easyui-datebox" name="incorporation_date"
							id="incorporation_date" value="" style="width:150px"></td>
						<td class="form-label" style="width:60px;">转正日期：</td>
						<td><input class="easyui-datebox" name="close_time"
							id="close_time" value="${pro.close_time}" style="width:150px"></td>
						<td class="form-label" style="width:60px;">试用期待遇：</td>
						<td style="width:150px"><input type="text" name="on_trial"
							id="on_trial" value="${pro.on_trial}" class="mini-textbox" /></td>
						<td class="form-label" style="width:60px;">转正工资：</td>
						<td style="width:150px"><input type="text" name="worker"
							id="worker" value="${pro.worker}" class="mini-textbox" /></td>

					</tr>
					<tr>

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
						<td class="form-label" style="width:60px;">状态：</td>
						<td style="width:150px"><select id="status" name="status"
							style="width:142px;">
								<option value="0">请选择</option>
								<option value="1">在职</option>
								<option value="2">离职</option>
								<option value="3">试用期</option>
								<option value="4">实习</option>
						</select></td>
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
						<td class="form-label" style="width:60px;">专业：</td>
						<td style="width:150px"><input type="text" name="major"
							id="major" value="${pro.major}" class="mini-textbox" /></td>

						<%-- <td class="form-label" style="width:60px;">备注：</td>
					<td  style="width:150px"><input type="text" name="remarks" id="remarks" value="${pro.remarks}"
						class="mini-textbox" /></td> --%>

					</tr>
					<tr>
						<td class="form-label" style="width:60px;">职业资格证：</td>
						<td style="width:150px"><input name="profl_certificate"
							id="profl_certificate" class="mini-textbox"
							value="${pro.profl_certificate}" /></td>
						<td class="form-label" style="width:60px;">签约时间：</td>
						<td><input class="easyui-datebox" name="sign" id="sign"
							value="${pro.sign}" style="width:150px"></td>
						<td class="form-label" style="width:60px;">年限：</td>
						<td style="width:150px"><input type="text" name="age_limit"
							id="age_limit" value="${pro.age_limit}" class="mini-textbox" /></td>
						<td class="form-label" style="width:60px;">到期时间：</td>
						<td><input class="easyui-datebox" name="expiry" id="expiry"
							value="${pro.expiry}" style="width:150px"></td>
						<td class="form-label" style="width:60px;">续约：</td>
						<td><input class="easyui-datebox" name="renew" id="renew"
							value="${pro.renew}" style="width:150px"></td>


					</tr>
					<tr>
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




	<div id="tab" class="easyui-tabs" style="width:1125px;height:500px">
		<div title="公司职位变动记录" style="padding:10px">
			<table id="dg" class="easyui-datagrid"
				style="width:900px;height:auto"
				data-options="
					iconCls: 'icon-edit',
					singleSelect: true,
			        toolbar: '#tb',
					onClickCell: onClickCell
				">
				<thead>
					<tr>
						<th
							data-options="field:'job_id',width:80,hidden:'hidden',editor:'text'">员工变动编号</th>
						<th
							data-options="field:'begin_date',width:100,editor:{type:'datebox',options:{required:true}}">开始时间</th>
						<th
							data-options="field:'end_date',width:100,editor:{type:'datebox',options:{required:true}}">结束时间</th>
						<th
							data-options="field:'corporate_name',width:120,editor:{type:'text',options:{precision:1}}">公司名称</th>
						<th data-options="field:'job_name',width:80,editor:'text'">职位</th>
						<th data-options="field:'reason',width:250,editor:'text'">变动原因</th>
					</tr>
				</thead>
			</table>
		</div>
		<div title="转正记录" style="padding:10px">
			<table id="dg2" class="easyui-datagrid"
				style="width:900px;height:auto"
				data-options="
							iconCls: 'icon-edit',
						singleSelect: true,">
				<thead>
					<tr>
						<th halign="center" field="bc_id" align="center" sortable="true"
							width="60px">转正编号</th>
						<th halign="center" field="user_id" align="center" sortable="true"
							width="60px">用户编号</th>
						<th halign="center" field="username" align="center"
							sortable="true" width="120px">转正人姓名</th>
						<th halign="center" field="role_name" align="center"
							sortable="true" width="120px">职位</th>
						<th halign="center" field="on_trial" align="center"
							sortable="true" width="120px">试用期待遇</th>
						<th halign="center" field="bc_status" align="center"
							sortable="true" width="120px" formatter="operate_status">状态</th>
						<th halign="center" field="psn" sortable="true" width="120px"
							formatter="operate_formatter">操作</th>
					</tr>
				</thead>
			</table>

			<div class="fieldset-body">
				<div id="w" class="easyui-window" title="审核记录"
					data-options="modal:true,closed:true,iconCls:'icon-save'"
					style="width:800px;height:600px;padding:10px;">
					<table id="datalist" border="0" cellpadding="1" cellspacing="2">

						<tr>
							<td style="width:60px;">姓名：</td>
							<td style="width:150px"><input type="text" id="bc_username" /></td>

							<td style="width:60px;">部门：</td>
							<td style="width:150px"><input type="text"
								id="bc_department" /></td>
							<td style="width:60px;">职位：</td>
							<td style="width:150px"><input type="text" id="bc_role"
								readonly="readonly"/></td>
						</tr>
						<tr>
							<td class="form-label">试用期：</td>
							<td><input id="bc_sta_date" style="width:143px"></td>
							<td class="form-label">至~：</td>
							<td><input id="bc_close_time" style="width:143px"></td>
						</tr>
						<tr>
							<td class="form-label">试用期：</td>
							<td colspan="5">
							<textarea  id="bc_personal" style="width: 573px;height: 70px;resize:none">
							</textarea>
							</td>
						</tr>
					</table>
				<div style="margin-left:67px;">
				<table id="history" class="easyui-datagrid"
					style="width:700px;height:300px"
					data-options="
							iconCls: 'icon-edit',
							singleSelect: true">
						<thead>
						<tr>
							
							<th data-options="field:'name_',width:120">步骤名称</th>
							<th data-options="field:'username',width:80">相关人员</th>
							<th data-options="field:'MESSAGE_',width:300">审核意见</th>
							<th data-options="field:'description_',width:200">转正待遇</th>
			</tr>
		</thead>
	</table></div>
				</div>
			</div>
		</div>
		<div title="薪资调整记录" style="padding:10px">
			<table id="dg3" class="easyui-datagrid"
				style="width:900px;height:auto"
				data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				toolbar: '#tb3',
				onClickCell: onClickCell">
				<thead>
					<tr>


						<th
							data-options="field:'salary_id',width:80,hidden:'hidden',editor:'text'">薪资编号</th>
						<th
							data-options="field:'adjustment_time',width:110,editor:{type:'datebox',options:{required:true}}">调整时间</th>
						<th
							data-options="field:'original_salary',width:80,align:'right',editor:{type:'text',options:{precision:1}}">原薪资</th>
						<th
							data-options="field:'adjusted_salary',width:80,align:'right',editor:'text'">调整后薪资</th>
						<th data-options="field:'reason',width:250,editor:'text'">调整原因</th>
						<th data-options="field:'note_taker',width:80,editor:'text'">记录人</th>
					</tr>
				</thead>


			</table>
		</div>
		<div title="合同期限" style="padding:10px">
			<table id="dg4" class="easyui-datagrid"
				style="width:900px;height:auto"
				data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				toolbar: '#tb4',
				onClickCell: onClickCell">
				<thead>
					<tr>
						<th
							data-options="field:'pact_id',width:80,hidden:'hidden',editor:'text'">合同编号</th>
						<th
							data-options="field:'start_time',width:120,align:'right',editor:{type:'datebox',options:{required:true}}">合同生效时间</th>
						<th
							data-options="field:'out_stime',width:120,align:'right',editor:{type:'datebox',options:{required:true}}">合同截止时间</th>
						<th data-options="field:'remark',width:250,editor:'text'">备注</th>
					</tr>

				</thead>


			</table>

		</div>
		
		<div title="离职记录" style="padding:10px">
			<table id="resign" class="easyui-datagrid"
				style="width:900px;height:auto"
				data-options="
							iconCls: 'icon-edit',
						singleSelect: true,">
				<thead>
					<tr>
						<th halign="center" field="hr_resign_id" align="center"   hidden="true"
							width="60px">离职id</th>
						<th halign="center" field="hr_resign_userid" align="center"  hidden="true"
							width="60px">用户id</th>
						<th halign="center" field="hr_resign_applyDate" align="center"
							sortable="true" width="120px">申请时间</th>
						<th halign="center" field="hr_resign_category" align="center"
							sortable="true" width="120px">离职类型</th>
						<th halign="center" field="hr_resign_realLeave" align="center"
							sortable="true" width="120px">实际离职时间</th>
						<th halign="center" field="hr_resign_reason" align="center"
							sortable="true" width="270px" >离职原因</th>
						<th halign="center" field="psn" sortable="true" width="120px"
							formatter="resign_operate">操作</th>
					</tr>
				</thead>
			</table>

		</div>
		
		
		
	</div>

	<div id="tb" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-add',plain:true" onclick="append()">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-undo',plain:true" onclick="reject('#dg')">取消修改</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-cut',plain:true"
			onclick="removeit('hregister')">删除</a>
	</div>

	<div id="tb3" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-add',plain:true" onclick="add3()">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-undo',plain:true"
			onclick="reject('#dg3')">取消修改</a> <a href="javascript:void(0)"
			class="easyui-linkbutton"
			data-options="iconCls:'icon-cut',plain:true"
			onclick="removeit('salary')">删除</a>



	</div>
	<div id="tb4" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-add',plain:true" onclick="add4()">添加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-undo',plain:true"
			onclick="reject('#dg4')">取消修改</a> <a href="javascript:void(0)"
			class="easyui-linkbutton"
			data-options="iconCls:'icon-cut',plain:true"
			onclick="removeit('contract')">删除</a>


	</div>
	<script type="text/javascript">
	
	layui.use(['table','laypage','layer','jquery'], function(){
		 var table = layui.table
		   ,$ = layui.$ 
		   ,laypage = layui.laypage
		   ,layer = layui.layer;
		 })
		$(function() {
			//数据初始化
			var userid = $("#userid").val();
			$("#sex").val(${pro.sex});
			$("#marital_status").val(${pro.marital_status});
			$("#role_id").val(${pro.role_id});
			$("#education").val(${pro.education});
			$("#level").val(${pro.level});
			$("#blood").val(${pro.blood});
			$("#health").val(${pro.health});
			$("#status").val(${pro.status});

			if (userid != 0) {
				var obj1 = ${rows}.rows1;
				$('#dg').datagrid('loadData', obj1); //表格数据加载

				var obj2 = ${rows}.rows2;
				$('#dg2').datagrid('loadData', obj2);

				var obj3 = ${rows}.rows3;
				$('#dg3').datagrid('loadData', obj3);

				var obj4 = ${rows}.rows4;
				$('#dg4').datagrid('loadData', obj4);
				
				
				var resignRows = ${rows}.resignRows;
				$('#resign').datagrid('loadData', resignRows);
				
			}

			$.extend($.fn.datagrid.methods, {
				editCell : function(jq, param) {
					return jq.each(function() {
						var opts = $(this).datagrid('options');
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

		function operate_formatter(value, row, index) {
			var html = "";

			html += '<a href="javascript:;" class="opt" onclick="findBecome('
					+ row.bc_id + ');"  i="' + index + '">查看</a>&nbsp;&nbsp;';
			return html;
		}
		
		function resign_operate(value, row, index) {
			var html = "";

			html += '<a href="javascript:;" class="opt" onclick="resignView('
					+ row.hr_resign_id + ');"  i="' + index + '">查看</a>&nbsp;&nbsp;';
			return html;
		}
		
		function resignView(hr_resign_id) {
			
			location.href = "resignView.do?hr_resign_id="+hr_resign_id;
		}
		
		
		
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
		//查看历史记录
		function findBecome(bizid) {
			
			$.get('becomehistory.do'
				,{'bizid' : bizid,}
			   ,function(data) {

								$('#w').window('open');
								//加载历史记录
								var dataList = data.dataList;
								var maps = data.maps;
								$("#bc_username").val(maps.username);
								$("#bc_department").val(maps.bc_department);
								$("#bc_role").val(maps.role_name);
								$("#bc_sta_date").val(maps.incorporation_date);
								$("#bc_close_time").val(maps.close_time);
								$("#bc_personal").val(maps.bc_personal);
								
								$('#history').datagrid('loadData',data.dataList);
						
							});
		}
		//取消
		function btnCancel() {
			location.href = "pmuserList.do";
		}

		//撤销新增行
		function reject(obj) {
			$(obj).datagrid('rejectChanges');
			editIndex = undefined;
		}

		var editIndex = undefined;
		function endEditing() {
			if (editIndex == undefined) {
				return true
			}
			if ($("#dg").datagrid('validateRow', editIndex)) {
				$("#dg").datagrid('endEdit', editIndex);
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		}

		function endEditing3() {
			if (editIndex == undefined) {
				return true
			}
			if ($('#dg3').datagrid('validateRow', editIndex)) {

				$('#dg3').datagrid('endEdit', editIndex);
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		}

		function endEditing4() {
			if (editIndex == undefined) {
				return true
			}
			if ($('#dg4').datagrid('validateRow', editIndex)) {

				$('#dg4').datagrid('endEdit', editIndex);
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		}

		function onClickRow(index) {
			if (editIndex != index) {
				if (endEditing()) {
					$('#dg').datagrid('selectRow', index).datagrid('beginEdit',
							index);
					editIndex = index;
				} else {
					$('#dg').datagrid('selectRow', editIndex);
				}
			}
		}
		function append() {
			if (endEditing()) {
				$('#dg').datagrid('appendRow', {
					status : 'P'
				});
				editIndex = $('#dg').datagrid('getRows').length - 1;

				$('#dg').datagrid('selectRow', editIndex).datagrid('beginEdit',
						editIndex);
			}
		}

		function add3() {
			if (endEditing3()) {
				$('#dg3').datagrid('appendRow', {
					status : 'P'
				});
				editIndex = $('#dg3').datagrid('getRows').length - 1;

				$('#dg3').datagrid('selectRow', editIndex).datagrid(
						'beginEdit', editIndex);
			}
		}

		function add4() {
			if (endEditing4()) {
				$('#dg4').datagrid('appendRow', {
					status : 'P'
				});
				editIndex = $('#dg4').datagrid('getRows').length - 1;

				$('#dg4').datagrid('selectRow', editIndex).datagrid(
						'beginEdit', editIndex);
			}
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

			if ($("#center_name").val() == "") {
				alert("中心不可以为空");
				return false;
			}
			if ($("#role_id").val() == 0) {
				alert("职位不可以为空");
				return false;
			}
			var rows = null;
			if (endEditing()) {
				$('#dg').datagrid('acceptChanges');
				rows = $('#dg').datagrid('getRows');
			}

			var rows3 = null;
			if (endEditing3()) {
				$('#dg3').datagrid('acceptChanges');
				rows3 = $('#dg3').datagrid('getRows');
			}
			var rows4 = null;
			if (endEditing4()) {
				$('#dg4').datagrid('acceptChanges');
				rows4 = $('#dg4').datagrid('getRows');
			}
			$.post("savePersonalRecords.do?rows="
					+ encodeURI(JSON.stringify(rows)) + "&rows3="
					+ encodeURI(JSON.stringify(rows3)) + "&rows4="
					+ encodeURI(JSON.stringify(rows4)), $("#head").serialize(),
					function(data) {
						if (data.erro == "") {
							alert("保存成功");
							//location.reload(true);
							location.href = "personalRecords.do?cid="
									+ $("#userid").val() + "&department="
									+ $("#department").val() + "";
						} else {
							alert(data.erro);
						}
					});

		}

		function onClickCell(index, field) {

			if (endEditing()) {
				$("#dg").datagrid('selectRow', index).datagrid('editCell', {
					index : index,
					field : field
				});
				editIndex = index;
			}
			if (endEditing3()) {
				$("#dg3").datagrid('selectRow', index).datagrid('editCell', {
					index : index,
					field : field
				});
				editIndex = index;
			}
			if (endEditing4()) {
				$("#dg4").datagrid('selectRow', index).datagrid('editCell', {
					index : index,
					field : field
				});
				editIndex = index;
			}
		}
		
		
		
		
		//删除处理
		function removeit(Json) {
			var rows = null;
			var ids = [];
			if (Json == "hregister") {
				rows = $("#dg").datagrid("getSelections");//获取表格数据
				if (rows.length == 0) {
					alert("请选择需要删除的行");
					return null;
				}

				for (var i = 0; i < rows.length; i++) {
					ids.push(rows[i].job_id);
				}
			} else if (Json == "salary") {
				rows = $("#dg3").datagrid("getSelections");//获取表格数据
				if (rows.length == 0) {
					alert("请选择需要删除的行");
					return null;
				}

				for (var i = 0; i < rows.length; i++) {
					ids.push(rows[i].salary_id);
				}
			} else if (Json == "contract") {
				rows = $("#dg4").datagrid("getSelections");//获取表格数据
				if (rows.length == 0) {
					alert("请选择需要删除的行");
					return null;
				}

				for (var i = 0; i < rows.length; i++) {
					ids.push(rows[i].pact_id);
				}
			}
			$.ajax({
				type : 'POST',
				url : "deleteContract.do?type=" + Json,
				data : {
					'cid' : JSON.stringify(ids),
				},
				success : function(data) {
					alert("删除成功");
					window.location.reload();
				}
			});
		}
		/* 引用子页面index1.html */
		function wen(src) {
			var hrefs = "<iframe id='son' src='"
					+ src
					+ "' allowTransparency='true' style='border:0;width:99%;height:99%;padding-left:2px;' frameBorder='0'></iframe>";
			$("#centers").html(hrefs);
			$("#centers").window("open");
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
		
		 //打开公司子页面
		 function openCompany(){
			 layer.open({
				  type: 2,
				  title:"部门选择框",
				  area: ['50%','50%'],		
				  maxmin: true,
				  content: 'orgCheck.do',
				  success:function(layero,index){
				
				  },end:function(data){
					
				  }
				});
		 }
		 function setCompany(rowData){
				$("#department").val(rowData.department_id); 
		        $("#department_name").val(rowData.department_name);
		        $("#center_name").val(rowData.center_name);
		        $("#center_id").val(rowData.center_id);
		        $("#company").val(rowData.company_name);
		       
			}
			
		
	</script>
</body>
</html>