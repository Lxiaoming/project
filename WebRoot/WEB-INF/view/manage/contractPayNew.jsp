<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%@ taglib uri="PowerCheck" prefix="shop"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="../common/css.jsp"></jsp:include>
<jsp:include page="../common/scripts.jsp"></jsp:include>

<script
	src="<%=request.getContextPath()%>/res/admin/scripts/admin_grid.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/res/admin/scripts/ueditor/third-party/codemirror/codemirror.js"
	type="text/javascript"></script>
<script
	src="<%=request.getContextPath()%>/res/js/echarts.common.min.js"
	type="text/javascript"></script>
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
<div style="float:left;" >
	<fieldset id="fd1" style="width:900px;">
		<legend>
			<span>合同信息</span>
		</legend>
		<div class="fieldset-body">
			<form id="contract" method="post">
			<table class="form-table" border="0" cellpadding="5" cellspacing="2">
				<tr>
					<td class="form-label" style="width:60px;display: none">合同id：</td>
					<td style="width:150px;display: none"><input
						name="manage_contract_id" id="manage_contract_id"
						class="mini-textbox" value="${manage_contract_id}" /></td>
					<td class="form-label" style="width:110px;">合同编号：</td>
					<td style="width:150px"><input type="text" name="manage_contract_num" 
						id="manage_contract_num" class="mini-textbox" 
						value="${manage_contract_num}" /></td>
					<td class="form-label" style="width:110px;">项目名称：</td>
					<td style="width:150px"><input type="text" 
						value="${manage_contract_name}"
						name="manage_contract_name" id="manage_contract_name" class="mini-textbox" /></td>
					<td class="form-label"  style="display: none">所属公司：</td>
					
					<td class="form-label" style="width:110px;"><a href="javascript:void(0)"
						onclick="wen('companyList.do')">公司名称：</a></td>
					<td style="width:150px"><input type="text" name="company_name"
						id="company_name" value="${company_name}" class="mini-textbox" /></td>
					<td style="display: none"><input type="text" name="manage_contract_payCompany"
						id="manage_contract_payCompany" value="${manage_contract_payCompany}" class="mini-textbox" /></td>
						
					<td  style="display: none"><select  id="manage_contract_company" name="manage_contract_company"  style="width:142px;">
							<option value="0">请选择</option>
							<option value="1">建设公司</option>
							<option value="2">科技公司</option>
							<option value="3">加盟合作</option>
					</select></td>	
						
				</tr>
				<tr>
					<td class="form-label" style="width:110px;">发包方（甲方）：</td>
					<td style="width:150px"><input type="text" 
						value="${manage_contract_firstParty}"
						name="manage_contract_firstParty" id="manage_contract_firstParty" class="mini-textbox" /></td>
					
					<td class="form-label" style="width:110px;">合同金额：</td>
					<td style="width:150px"><input type="text" 
						value="${manage_contract_amount}"
						name="manage_contract_amount" id="manage_contract_amount" class="mini-textbox" /></td>
						
					<td class="form-label" style="width:110px;">签证金额：</td>
					<td style="width:150px"><input type="text" 
						value="${manage_contract_visaAmount}"
						name="manage_contract_visaAmount" id="manage_contract_visaAmount" class="mini-textbox" /></td>		
				</tr>
				
				<tr>
					<td class="form-label" style="width:110px;">合同开始时间：</td>
					<td style="width:150px"><input type="text" class="easyui-datebox"
						value="${manage_contract_startTime}"
						name="manage_contract_startTime" id="manage_contract_startTime" class="mini-textbox" /></td>
					
					<td class="form-label" style="width:110px;">合同结束时间：</td>
					<td style="width:150px"><input type="text" class="easyui-datebox"
						value="${manage_contract_endTime}"
						name="manage_contract_endTime" id="manage_contract_endTime" class="mini-textbox" /></td>
						
					<td class="form-label" style="width:110px;">项目地址：</td>
					<td style="width:150px"><input type="text" 
						value="${manage_contract_address}"
						name="manage_contract_address" id="manage_contract_address" class="mini-textbox" /></td>		
				</tr>
				<tr>
					<td class="form-label" style="width:110px;">备注：</td>
					<td style="width:150px"><input type="text" 
						value="${manage_contract_remark}"
						name="manage_contract_remark" id="manage_contract_remark" class="mini-textbox" /></td>
				
				</tr>
				
			</table>
			</form>
		</div>
	</fieldset>

	
	

	<br>
	
	<table id="materialListID" class="easyui-datagrid" title="请款进度"
		style="width:920px;height:auto"
		data-options="
				iconCls: 'icon-edit',
				singleSelect: true,
				toolbar: '#tb',onClickCell:onClickCell,rownumbers:true
				">
		<thead>
			<tr>
				<th 
					data-options="field:'manage_reqfunds_contractId',width:80,hidden:'hidden',editor:'text'">manage_reqfunds_contractId</th>
				<th 
					data-options="field:'manage_reqfunds_id',width:80,hidden:'hidden',editor:'text'">ItemID</th>
				<th
					data-options="field:'manage_reqfunds_time',width:80,align:'right',editor:'datebox'">请款时间</th>
				<th
					data-options="field:'manage_reqfunds_amount',width:80,align:'right',editor:'text' ">请款金额</th>
				<th
					data-options="field:'manage_reqfunds_ticketDate',width:80,align:'right',editor:'datebox'">开票日期</th>
				<th
					data-options="field:'manage_reqfunds_ticketAmount',width:80,align:'right',editor:'text' ">开票金额</th>
				<th
					data-options="field:'manage_reqfunds_receiveDate',width:80,align:'right',editor:'datebox'">付款日期</th>
				<th
					data-options="field:'manage_reqfunds_receiveAmount',width:80,align:'right',editor:'text' ">付款金额</th>
				<th
					data-options="field:'manage_reqfunds_remark',width:80,align:'right' ,editor:'text' ">备注</th>
			</tr>
		</thead>


	</table>


	<div id="tb" style="height:auto">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-add',plain:true" onclick="add()">添加</a> 
			
			<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-undo',plain:true" onclick="reject()">撤销新增行</a>			
			<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-cut',plain:true" onclick="removeit()">删除</a>
	</div>
	<br/>
	<br/>
	<br/>
	<div style="text-align:center">
		<tr>
			<td style="align:center">
				&nbsp;&nbsp;&nbsp;&nbsp; <a class="easyui-linkbutton"
				href="javascript:;" id="btnSave" onclick="btnSave()">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
				<a class="easyui-linkbutton" href="javascript:;" id="btnCancel"
				onclick="btnCancel()">取消</a></td>
		</tr>
	</div>
</div>

<div id="main" style="float:right;width: 500px;height:500px;"></div>

	<div  id="centers" class="easyui-window" data-options="region:'center',title:'请选择值'" closed="true" style="height: 500px; width: 800px" >    
				</div>

	<script type="text/javascript">
		var editIndex = undefined;
		
		$(function() {
			//加载数据
			if(${rows}.rows!=null){
		
			var type=${type};
			if(type=='view'){
				document.getElementById("btnSave").style.display = "none";
				document.getElementById("tb").style.display = "none";
			}
			
			$("#manage_contract_company").val(${manage_contract_company});
			$("#manage_contract_amount").val(${manage_contract_amount});
			//$("#manage_contract_company").attr("disabled","disabled").css("background-color","#EEEEEE;");
			
			var datas=${rows}.rows;
			if(datas.length!=0){
				$('#materialListID').datagrid('loadData',datas);
			} 
			var totalAmount=parseFloat(${manage_contract_amount})+parseFloat(${manage_contract_visaAmount});
			var receiveAmt=0;
			for(var i=0;i<datas.length;i++){
				receiveAmt=parseFloat(receiveAmt)+parseFloat(datas[i].manage_reqfunds_receiveAmount);
			}
			var noreceiveAmt=parseFloat(totalAmount)-parseFloat(receiveAmt);
			var classificaOne="已付款";
			var classificaTwo="未付款"; 
			//加载饼图
			var myChart = echarts.init(document.getElementById('main'));
			 option = {
					    title : {
					        text: '合同总金额：'+totalAmount,
					        x:'center'
					    },
					    tooltip : {
					        trigger: 'item',
					        formatter: "{a} <br/>{b} : {c} ({d}%)"
					    },
					    legend: {
					        orient : 'vertical',
					        x : 'left',
					        data:[classificaOne,classificaTwo]
					    },
					    toolbox: {
					        show : true,
					        feature : {
					            mark : {show: true},
					            dataView : {show: true, readOnly: false},
					            magicType : {
					                show: true, 
					                type: ['pie', 'funnel'],
					                option: {
					                    funnel: {
					                        x: '25%',
					                        width: '50%',
					                        funnelAlign: 'left',
					                        max: 1548
					                    }
					                }
					            },
					            restore : {show: true},
					            saveAsImage : {show: true}
					        }
					    },
					    calculable : true,
					    series : [
					        {
					            name:'金额（百分比）',
					            type:'pie',
					            radius : '55%',
					            center: ['50%', '60%'],
					            data:[
					                {value:receiveAmt.toFixed(2), name:'已付款'},
					                {value:noreceiveAmt.toFixed(2), name:'未付款'}
					            ]
					        }
					    ]
					};
			 myChart.setOption(option);
			
			}
			
			//单元格编辑
			$.extend($.fn.datagrid.methods, {
				editCell : function(jq, param) {
					return jq.each(function() {
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
		
		//打开子窗口
		function wen(src) {
			var hrefs = "<iframe id='son' src='"
					+ src
					+ "' allowTransparency='true' style='border:0;width:99%;height:99%;padding-left:2px;' frameBorder='0'></iframe>";
			$("#centers").html(hrefs);
			$("#centers").window("open");
		}
		
		function data(pamData){
			
			$("#manage_contract_payCompany").val(pamData.company_id);
			$("#company_name").val(pamData.company_name);
			
		}
		
		
		
		//删除处理
		function removeit(){
			rows = $("#materialListID").datagrid("getSelections");//获取表格数据
			if(rows.length==0){
				alert("请选择需要删除的行");
				return null;
			}else if(rows.length>1){
				alert("只能选一行");
				return null;
			}
			var id=rows[0].manage_reqfunds_id;
			$.ajax({
				type : 'POST',
				url :"delete_Pay_Reqfunds.do",
				data :{'id':id        
				},
				success : function(data){
					alert("删除成功");
					window.location.reload();
				}
			});
			
		};
		
		//新增行
		function add() {
			$('#materialListID').datagrid('appendRow', {
				status : 'P'
			});
			editIndex = $('#materialListID').datagrid('getRows').length - 1;

			$('#materialListID').datagrid('selectRow', editIndex).datagrid(
					'beginEdit', editIndex);
		}
		//是否编辑结束
		function endEditing() {
			if (editIndex == undefined) {
				return true;
			}
			if ($('#materialListID').datagrid('validateRow', editIndex)) {
				$('#materialListID').datagrid('endEdit', editIndex);
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		}

		function onClickCell(index, field) {
			$('#materialListID').datagrid('selectRow', index);
			if (endEditing()) {
				$(this).datagrid('beginEdit', index);
			}
		}


		//撤销新增行
		function reject() {
			
			$(materialListID).datagrid('rejectChanges');
			editIndex = undefined;
		
		}
			
			

		//取消
		function btnCancel() {
			location.href = "contractPayList.do?";
		}

		//保存
		function btnSave() {
			
			if($("#manage_contract_visaAmount").val()===""){
				$("#manage_contract_visaAmount").val("0.00");
			}
			if($("#company_name").val()===""){
				alert("公司不可以为空");
				return false;
			}
			var rows=null;
			if(endEditing()){
				$('#materialListID').datagrid('acceptChanges');
				rows=$('#materialListID').datagrid('getRows');
			}
			$.post("saveContract.do?"+$("#contract").serialize()
					,{'rows':JSON.stringify(rows),
				      'type':'sava',
				       }
			        ,function(data) {
				 if (data == "") {
						alert("保存成功");
						location.href='contractPayList.do';
				} else {
					alert("保存失败");
				}
			}); 	
			
		}


	</script>
</body>
</html>