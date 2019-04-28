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
								<li><label>项目名称：</label><input type="text" name="constuct_project_dep_name", id="construct_project_name"
									class="easyui-validatebox" style="width: 120px" /></li>
								<li><label>采购单号：</label><input type="text" name="construct_purchase_id", id="construct_purchase_id"
									class="easyui-validatebox" style="width: 120px" /></li>
								<li><label>供应商：</label><input type="text" name="construct_purchase_supplier", id="construct_purchase_supplier"
									class="easyui-validatebox" style="width: 120px" /></li>		
								<li>&nbsp;&nbsp; <a class="easyui-linkbutton"
									data-options="iconCls:'icon-search'" href="javascript:;"
									id="btnSearch">查询</a></li>
									<shop:permission code="APPLY_FOR_PHE">
								<li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" plain="true"
									onclick="btnDele();">删除采购单</a></li>
									<li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton" iconCls="icon-edit"
									plain="true" onclick="btnUpdate();">修改采购单</a></li>
								<li>&nbsp;&nbsp; <a href="#" class="easyui-linkbutton" iconCls="icon-add"
									plain="true" onclick="btnAdd();">申请采购</a></li>
									</shop:permission>
									
							</ul>
							<div class="clear"></div>
						</div>
					</div>
				</div>
				
				<div region="center" split="false" border="false"
					style="overflow: hidden; padding: 0 5px 5px 5px" id="dataList">
					<table id="data" title="" fit="true"
						data-options="collapsible: true,pagination:true">
						<thead>
							<tr>
								<th id="ds" halign="center" field="construct_purchase_id" align="center"
									sortable="true" width="60px">采购单号</th>
								<th id="ds" halign="center" field="construct_project_id" align="center" hidden="hidden"
									sortable="true" width="60px">项目编号</th>
								<th halign="center" field="construct_project_name" align="center"
									sortable="true" width="120px">项目名称</th>
								<th halign="center" field="construct_project_addr" align="center" 
									sortable="true" width="120px">工程地址</th>
									<th halign="center" field="construct_project_leader" align="center" 
									sortable="true" width="120px">项目经理</th>
									<th halign="center" field="construct_project_leaderTel" align="center" 
									sortable="true" width="120px">项目经理联系方式</th>	
								<th halign="center" field="construct_purchase_planDate" align="center"
									sortable="true" width="120px" formatter="dateformat">计划日期</th>
								<th halign="center" field="construct_purchase_arriveDate" align="center" 
									sortable="true" width="120px" formatter="dateformat">希望送达时间</th>
									<th halign="center" field="construct_purchase_planMan" align="center" 
									sortable="true" width="120px">计划员</th>
									<th halign="center" field="construct_purchase_reviewer" align="center" 
									sortable="true" width="120px">复核员</th>
									<th halign="center" field="construct_purchase_supplier" align="center" 
									sortable="true" width="120px">供应商</th>
									<th halign="center" field="construct_purchase_supplierTel" align="center" 
									sortable="true" width="120px">供应商联系方式</th>
									<th halign="center" field="construct_purchase_projectId" align="center" hidden="hidden"
									sortable="true" width="120px">项目id</th>
									<th halign="center" field="construct_purchase_status" align="center" 
									sortable="true" width="120px" formatter="status_formatter">状态</th>
								<th halign="center" field="psn"  sortable="true"
									width="120px" formatter="operate_formatter">操作</th>
							</tr>
						</thead>
					</table>
				</div>

			</div>
			
			  <!-- 遮罩层 -->
	    <div class="mark"  style="position:fixed;left:0;top:0; opacity:0.4; width:100%;height:100%; background:#000;z-index:998;display:none;">
          <div class="content" >
            <img alt="" style="width: 200px; height: 180px; position: absolute; left:40%; top: 40%;" src="../res/images/loading.gif">
          </div>
       </div>
       <!-- 遮罩层结束 -->
		</div>





	<script type="text/javascript">
		function operate_formatter(value, row, index) {
			var html = "";

			html += '<a href="javascript:;" class="opt"    cmd="view" i="' + index + '">查看</a>';
			if (row.construct_purchase_status ==0) {
				html += '<a href="javascript:;" class="opt"    cmd="start_up" i="' + index + '">启动流程</a>';
		
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
				if (value == 0) {
					return "初始录入";
				} else if (value == 1) {
					return "项目经理";	
				} else if (value == 2) {
					return "专业公司 ";
				} else if (value == 3) {
					return "成本中心";
				} else if (value == 4) {
					return "总裁";
				}else if (value == 5) {
					return "成本中心采购";
				}else if (value == 6) {
					return "供应商";
				}else if (value == 7) {
					return "项目部签收";
				}else if (value == 8) {
					return "成本中心核对";
				}else if (value == 9) {
					return "供应商结算申请";
				}else if (value == 10) {
					return "成本中心结算申请";
				}else if (value == 11) {
					return "财务结算";
				}
		 }

		function btnAdd() {
			location.href ="purchaseNew.do?projectId="+${projectId}+"&construct_purchase_id=";
		}

		/***处理操作列的功能***/
		function cmdHanlder(cmd, row) {
			if (cmd == "view") {

				location.href = "Purchase_payment.do?" + $.param({
					'bizId' : row.construct_purchase_id, 
				});
             
			}else if(cmd =="start_up"){
				 var purchase_id=row.construct_purchase_id;
				 var project_name=row.construct_project_name;
				 var materialSerName=row.construct_purchase_materialSerName;
			
				 
				 $.messager.confirm('提示！', '你确定提交吗？', function(r) {
					 if (r) {
							$('.mark').css("display","block");
					$.post("start_up.do"
						  ,{'purchase_id':purchase_id,
							'project_name':project_name,
							'materialSerName':materialSerName,	
							}
					      ,function(data) {

						if (data.Success) {
							$('.mark').css("display","none");
							$.messager.alert("启动提示", data.Success, "info",
									function() {

										location.href = "findTaskList.do";
									});

						} else {
							$('.mark').css("display","none");
							$.messager.alert("启动提示", data.Msg, "error");
						}
				     });
			        }
				});
					}
					
		}
		
		function btnUpdate(){
			var rows = $("#data").datagrid("getSelections");//获取表格数据
			if (rows.length == 0) {
				alert("请选择需要修改的行");
				return null;
			}else if(rows.length>1){
				alert("一次只能修改一行");
				return null;
			}
			location.href ="purchaseNew.do?projectId="+${projectId}+"&construct_purchase_id="+rows[0].construct_purchase_id+"&construct_purchase_status="+rows[0].construct_purchase_status;
			
		}
		

		//批量删除处理
		function btnDele() {
			var rows = $("#data").datagrid("getSelections");//获取表格数据
			if (rows.length == 0) {
				alert("请选择需要取消的行");
				return null;
			}else if(rows.length>1){
				alert("一次只能取消一行");
				return null;
			}
			 $.messager.confirm('提示！', '你确定提交吗？', function(r) {
				 if (r) {
		 
			$.ajax({
				type : 'POST',
				url : 'purchaseDele.do',
				traditional : true,
				data : {
					'construct_purchase_id' : JSON.stringify(rows[0].construct_purchase_id),
					'construct_purchase_status' :JSON.stringify(rows[0].construct_purchase_status)
				},success : function(data) {
					if (data.Success) {
						$.messager.alert("提示", data.Msg, "info",
								function() {
							  $('#data').datagrid('reload');
								});
						} else {
							$.messager.alert("启动提示", data.Msg, "error");
						}
				}
			}); }})
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