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
	<form id="form1" method="post">
		<div id="main" region="center"
			style="background: #eee; padding: 5px; overflow-y: hidden;">
			<div class="easyui-layout" fit="true">
				<div region="north" split="false" border="false"
					style="overflow: hidden; padding: 5px 5px 2px 3px;">

					<div class="search">
						<ul>
							<li><label>公司名称：</label><input type="text" name="companyname"
								id="companyname" value="" class="easyui-validatebox"
								style="width: 120px" /></li>

							<li>&nbsp;&nbsp; <a class="easyui-linkbutton"
								data-options="iconCls:'icon-search'" href="javascript:;"
								id="btnSearch">查询</a></li>
						</ul>
					</div>
					<br />

					<div>
					<shop:permission code="ADD_FIRM">
						<ul>
							<li style="list-style:none;"><a id="btnNew" onclick="btnNew('save');"
								class="easyui-linkbutton" iconCls="icon-add" plain="true">新增公司</a>
								
									<a id="btnUpdate" onclick="btnNew('update');" class="easyui-linkbutton" iconCls="icon-edit"
										plain="true">修改公司信息</a>								
									<a id="btnDele"  onclick="btnDele();" class="easyui-linkbutton" iconCls="icon-cut"
										plain="true">删除公司信息</a>
								</li>
						</ul>
						</shop:permission>
					</div>
				</div>


				<div region="center" split="false" border="false"
					style="overflow: hidden; padding: 0px 5px 5px 5px" id="dataList">
					<div class="newAdd" style="display: none">
					<form id="company" method="post" >
						<table  style="border: 1px solid #151515">
							<tr>
								<td style="display: inline;">
							
								 <input hidden="hidden"
									id="company_id" type="text" name="company_id" value=""></td>
								<td style="display: inline;"><span
									style="display: inline-block; width: 60px">公司名称:</span> <input
									id="company_name" type="text" name="company_name" value=""></td>
							</tr>
							<tr>
								<td style="align:center"><a class="easyui-linkbutton"
									href="javascript:;" onclick="btnSave()" >提交</a>
									&nbsp;&nbsp;&nbsp;&nbsp; <a class="easyui-linkbutton"
									href="javascript:;" onclick="btnCancel()" >取消</a></td>
							</tr>
						</table>
						</form>
					</div>

					<table id="data" title="" fit="true"
						data-options="collapsible: true,pagination:true,onDblClickRow: onDblClickRow">
						<thead>
							<tr>
								<th id="ds" halign="center" field="company_id" align="center"
									sortable="true" width="60px">公司编号</th>
								<th halign="center" field="company_name" align="center"
									sortable="true" width="120px">公司名称</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>

		</div>
	</form>

				
				


	<script type="text/javascript">
		//展开编辑行
		function btnNew(obj) {
			if(obj=="update"){
				var rows = $("#data").datagrid("getSelections");
				if (rows.length == 0) {
					alert("请选择需要修改的行");
					return null;
				}
				if (rows.length > 1) {
					alert("不能选择多行喔");
					return null;
				}
				$("#company_id").attr("value", rows[0].company_id);
				$("#company_name").attr("value", rows[0].company_name);
				$(".newAdd").show();
			}else if(obj=="save"){
				$(".newAdd").show();
			}
		};
			
		//数据提交
		function btnSave() {
			
			var company_id = $("#company_id").val();
			var company_name = $("#company_name").val();

			if (company_name == "") {
				alert("公司名不能为空");
				return false;
			}

			$.ajax({
				type : 'POST',
				url : 'saveCompany.do',
				data : {
					'company_id' : company_id,
					'company_name' : company_name,
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
			window.location.href = "companyList.do";
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
				ids.push(rows[i].company_id);
			}

			$.ajax({
				type : 'POST',
				url : 'deleCompany.do',
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
		$(function() {
			$("#data").admin_grid({
				queryBtn : "#btnSearch",
				excelBtn : "#btnExcel",
				cmdHanlder : cmdHanlder,

			});

		});
		
		
		/***处理操作列的功能***/
		function cmdHanlder(cmd, row) {
		}
		
		
		
		function onDblClickRow (rowIndex, rowData){  
	        $(window.parent.$("#center_companyId").val(rowData.company_id)); //给父页面赋值
	        $(window.parent.$("#company_name").val(rowData.company_name));
	        //$(window.parent.$("#centers").window("close"));
	        
	        $(window.parent.window.data(rowData));
	        $(window.parent.$("#centers").window("close"));
	        
    };
		
	</script>

</body>
</html>