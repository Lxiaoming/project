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
							<li><label>中心名称：</label><input type="text" name="center_name"
								id="center_name" value="" class="easyui-validatebox"
								style="width: 120px" /></li>

							<li>&nbsp;&nbsp; <a class="easyui-linkbutton"
								data-options="iconCls:'icon-search'" href="javascript:;"
								id="btnSearch">查询</a></li>
						</ul>
					</div>
					<br />

					<div>
					<shop:permission code="ADD_CENTRALITY">
						<ul>
							<li style="list-style:none;"><a id="btnNew" onclick="btnNew('save');"
								class="easyui-linkbutton" iconCls="icon-add" plain="true">新增中心</a>
								
									<a id="btnUpdate" onclick="btnNew('update');" class="easyui-linkbutton" iconCls="icon-edit"
										plain="true">修改中心信息</a>								
									<a id="btnDele"  onclick="btnDele();" class="easyui-linkbutton" iconCls="icon-cut"
										plain="true">删除中心信息</a>
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
									id="centerid" type="text" name="centerid" value=""></td>
								<td style="display: inline;"><span
									style="display: inline-block; width: 60px">中心名称:</span> <input
									id="center" type="text" name="center" value=""></td>
									
									
								<td class="form-label" style="width:80px;"> 
								<a href="javascript:void(0)" onclick="wen('companyList.do')">所属公司名称:</a> 
								</td>
								<td data-options="region:'north',title:'North Title',split:true" >
					 
					     	<input type="text" name="company_name" id="company_name" readonly="readonly" value="${pro.department_name}"/> </td>	
										
								<td style="display: inline;">
								 <input hidden="hidden"
									id="center_companyId" type="text" name="center_companyId" value=""></td>		
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
						data-options="collapsible: true,pagination:true,onDblClickRow:onDblClickRow">
						<thead>
							<tr>
								<th id="ds" halign="center" field="center_id" align="center"
									sortable="true" width="60px">中心编号</th>
								<th halign="center" field="center_name" align="center"
									sortable="true" width="120px">中心名称</th>
								<th halign="center" field="company_name" align="center"
									sortable="true" width="120px">所属公司</th>
								<th halign="center" field=company_id align="center" hidden="hidden"
									sortable="true" width="120px">公司id</th>		
							</tr>
						</thead>
					</table>
				</div>
			</div>

		</div>
	</form>

				
	<div id="centers" class="easyui-window" data-options="region:'center',title:'请选择值'" closed="true" style="height: 500px; width: 800px" >    

        </div>    			


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
				
				
				$("#center_companyId").attr("value", rows[0].company_id);
				$("#centerid").attr("value", rows[0].center_id);
				$("#center").attr("value", rows[0].center_name);
				$("#company_name").attr("value",rows[0].company_name);

				$(".newAdd").show();
			}else if(obj=="save"){
				$(".newAdd").show();
			}
		};
			
		//数据提交
		function btnSave() {
			
			var centerid = $("#centerid").val();
			var center = $("#center").val();
			var center_companyId = $("#center_companyId").val();
			if (center == "") {
				alert("中心名不能为空");
				return false;
			}

			$.ajax({
				type : 'POST',
				url : 'saveCenter.do',
				data : {
					'center_id' : centerid,
					'center_name' : center,
					'center_companyId' : center_companyId
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
			location.reload();
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
				ids.push(rows[i].center_id);
			}
			
			$.ajax({
				type : 'POST',
				url : 'deleCenter.do',
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
		
		
		function wen(src){    
            var hrefs = "<iframe id='son' src='" + src + "' allowTransparency='true' style='border:0;width:99%;height:99%;padding-left:2px;' frameBorder='0'></iframe>";    
            $("#centers").html(hrefs); 
            $("#centers").window("open");    
        }    
		
		
			function onDblClickRow (rowIndex, rowData){  
			
	        $(window.parent.$("#department_centerId").val(rowData.center_id)); //给父页面赋值
	        $(window.parent.$("#center_name").val(rowData.center_name));
	        $(window.parent.$("#department_companyId").val(rowData.company_id));
	        $(window.parent.$("#company_name").val(rowData.company_name));

	        
	        $(window.parent.$("#win").window("close"));
    };
		
	</script>

</body>
</html>