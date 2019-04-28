<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>首页</title>
<jsp:include page="../common/css.jsp"></jsp:include>
<jsp:include page="../common/scripts.jsp"></jsp:include>
<%@ taglib uri="PowerCheck" prefix="shop"%>
<script
	src="<%=request.getContextPath()%>/res/admin/scripts/admin_grid.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/res/admin/scripts/ueditor/third-party/codemirror/codemirror.js"
	type="text/javascript"></script>

</head>
<body class="easyui-layout" style="overflow-y: hidden;" scroll="no">
	<form id="form1" method="post">
		<input type="hidden" name="userid" value="${param.userid}">
		<div id="main" region="center"
			style="background: #eee; padding: 5px; overflow-y: hidden;">


			<div class="easyui-layout" fit="true">
				<div region="north" split="false" border="false"
					style="overflow: hidden; padding: 5px 5px 28px 5px;">
					<div class="easyui-panel" title="查询条件" style="margin-bottom: 5px;">
						<div class="controls">
							<a class="easyui-linkbutton" data-options="iconCls:'icon-search'"
								href="javascript:;" id="btnSearch">查询</a>

						</div>

						<div class="search">

							<ul>
								<li><label>公司：</label><input type="text" name="department" id="department"
									class="easyui-validatebox" style="width: 120px" /></li>
								<li><label> 请假人：</label><input type="text" name="applicant"id="applicant"
									class="easyui-validatebox" style="width: 120px" /></li>
								<!-- <li><label> 状态：</label><input type="text" name="status"id="status"
									class="easyui-validatebox" style="width: 120px" /></li> -->

								<li><label> 状态：</label><select class="easyui-combobox" style="width: 100px"
									name="status" id="status" data-options="editable:false">
										<option value="">所有</option>
										<option value="0">初始录入</option>
										<option value="1">审批不通过</option>
										<option value="2">审核中</option>
										<option value="3">审批通过</option>
								</select></li>
								<li><label> 类型：</label><select class="easyui-combobox" style="width: 100px"
									name="leave_category" id="leave_category" data-options="editable:false">
										<option value="">所有</option>
										<option value="0">事假</option>
										<option value="1">病假</option>
										<option value="2">婚假</option>
										<option value="3">产假</option>
										<option value="4">丧假</option>
										<option value="5">年假</option>
										<option value="6">其他</option>
										
								</select></li>
								<li><label> 请假时间：</label><input class="easyui-datebox" id="start_time"
									name="start_time" style="width: 90px" /> 至 <input
									class="easyui-datebox" name="end_time"
									id="end_time" style="width: 90px"></li>


							</ul>
							<div class="clear"></div>
						</div>
				    <shop:permission code="DELETE_LEAVE">
						<div id="tb">
								<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true"
								onclick="btnDele();">删除请假条</a>
						</div>
						</shop:permission>
					</div>
				</div>
				
				<div region="center" split="false" border="false"
					style="overflow: hidden; padding: 0 5px 5px 5px" id="dataList">
					<table id="data" title="" fit="true"
						data-options="collapsible: true,pagination:true,rownumbers:true,showFooter:true,
						rowStyler: function(index,row){  
                    if (row.id == '合计'){  
                        return 'background-color:#6293BB;color:#fff;font-weight:bold;';  
                    }  
                    }">
						<tr>
							<td><input type="hidden" name="trade_id" value="" /></td>
						</tr>
						<thead>
							<tr>
      					        <th halign="center" field="id" align="center"
									sortable="true" width="60px">单据编号</th>
								<th halign="center" field="applicant" align="center"
									sortable="true" width="80px">请假人</th>
								<th halign="center" field="company_name" align="center" sortable="true"
									width="120px">公司</th>
								<th halign="center" field="position" align="center" sortable="true"
									width="120px">职位</th>
								<th halign="center" field="leave_category" align="center" sortable="true"
									width="80px" formatter="category_formatter">请假类别</th>
										
								<th halign="center" field="createdate" align="center" sortable="true"
									width="80px" >创建时间</th>
											
								<th halign="center" field="start_time" align="center" sortable="true"
									width="120px" formatter="dateformat">起始时间</th>
								<th halign="center" field="end_time" align="center" sortable="true"
									width="120px" formatter="dateformat">结束时间</th>
								<th halign="center" field="day_count" align="center" sortable="true"
									width="60px">请假天数</th>
								<th halign="center" field="reason" align="center" sortable="true"
									width="120px">事由</th>
								
								<th halign="center" field="leaveFile_name" align="center" sortable="true" hidden="hidden"
									width="120px">文件</th>
									
								<th halign="center" field="status" align="center" sortable="true"
									width="120px" formatter="status_formatter">状态</th>	
								<th halign="center" field="psn" align="center" sortable="true"
									width="120px" formatter="operate_formatter">操作</th>
							</tr>
						</thead>
					</table>
				</div>

			</div>
		</div>

	</form>




	<script type="text/javascript">
	
	//重写设置尾行颜色
	var myview = $.extend({}, $.fn.datagrid.defaults.view, {
	    renderFooter: function(target, container, frozen){
	        var opts = $.data(target, 'datagrid').options;
	        var rows = $.data(target, 'datagrid').footer || [];
	        var fields = $(target).datagrid('getColumnFields', frozen);
	        var table = ['<table class="datagrid-ftable" cellspacing="0" cellpadding="0" border="0"><tbody>'];
	         
	        for(var i=0; i<rows.length; i++){
	            var styleValue = opts.rowStyler ? opts.rowStyler.call(target, i, rows[i]) : '';
	            var style = styleValue ? 'style="' + styleValue + '"' : '';
	            table.push('<tr class="datagrid-row" datagrid-row-index="' + i + '"' + style + '>');
	            table.push(this.renderRow.call(this, target, fields, frozen, i, rows[i]));
	            table.push('</tr>');
	        }
	        table.push('</tbody></table>');
	        $(container).html(table.join(''));
	    }
	});
	//重写设置尾行加载视图
	$('#data').datagrid({
	     view:myview
	     });
	
		function operate_formatter(value, row, index) {
			var html = "";

			//html += '<a href="javascript:;" class="opt"    cmd="edit" i="' + index + '">编辑</a>';
			if(row.id!="合计"){
			
			html += '<a href="javascript:;" class="opt"    cmd="view" i="' + index + '">查看</a>';
			}
			return html;
		}

		

		function   formatDate(now)   {     
            var   year=now.getFullYear();     
            var   month=now.getMonth()+1;     
            var   date=now.getDate();     
            return   year+"-"+month+"-"+date;
            }     
       
		
		 function dateformat(value, row, index) {
			if (value == undefined)
				return "";
			 var  now=new   Date(value); 
			return formatDate(now); 
		   
		}
		
		
		 function status_formatter(value, row, index) {
				if (value == 1) {
					return "审批不通过";
				} else if (value == 3) {
					return "审批通过";
				} else if (value ==2) {
					return "审核中";
				}else if(value==0){
					return "初始录入";
				}
			}
		
		function category_formatter(value, row, index){
			if (value == 0) {
				return "事假";
			} else if (value == 1) {
				return "病假";
			} else if (value == 2) {
				return "婚假";
			}else if (value == 3) {
				return "产假";
			}else if (value == 4) {
				return "丧假";
			}else if (value == 5) {
				return "年假";
			}else if (value == 6) {
				return "其他";
			}
		}
		

		/***处理操作列的功能***/
		function cmdHanlder(cmd, row) {
			if (cmd == "edit") {
				location.href = "leave.do?" + $.param({
					'id' : row.id, //获取用户id
				});
			}
			if (cmd == "view") {
				location.href = "Leave_flow.do?" + $.param({
					'bizId' : row.id, //获取用户id
				});
			}
		}

		//批量删除处理
		function btnDele() {
			var rows = $("#data").datagrid("getSelections");//获取表格数据
			if (rows.length == 0) {
				$.messager.alert("提示!","请选择需要删除行","info");	
				return false;
			}else if(rows.length > 1){
				$.messager.alert("提示!","不可以选多行","info");	
				return false;
			}
			
			
				
			$.ajax({
				type : 'POST',
				url : 'deleLeave.do',
				traditional : true,
				data : {
					'leaveId' : rows[0].id,
					'leaveFile_name' : rows[0].leaveFile_name
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