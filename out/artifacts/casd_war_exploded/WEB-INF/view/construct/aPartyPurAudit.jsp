<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta charset="utf-8">
  <title></title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
     <link rel="stylesheet" href="<%=request.getContextPath()%>/res/layui/css/layui.css?HJ_v=<%=Math.random()%>"  media="all">
	 <script src="<%=request.getContextPath()%>/res/layui/layui.js?HJ_v=<%=Math.random()%>" charset="utf-8"></script>
	 <script src="<%=request.getContextPath()%>/res/jquery-easyui/jquery.min.js" charset="utf-8"></script>
	 <script src="<%=request.getContextPath()%>/res/layui/layui.js" charset="utf-8"></script>
	 <script src="<%=request.getContextPath()%>/res/layui/format/dateformat.js" charset="utf-8"></script>
</head>
<style>
.layui-form-label {
    float: left;
    display: block;
    padding: 9px 15px;
    width: 100px;
    font-weight: 400;
    line-height: 20px;
    /* text-align: right; */
}
</style>
<body>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
  <legend>订单信息</legend>
</fieldset>
    <div class="layui-row" id="fromdiv">
		<form class="layui-form" action="" id="ownhead" style="margin-right: 3%">
		<div class="layui-form-item">
		 <input type="hidden" id="taskid"   value="${taskid}" class="layui-input">
		 <input type="hidden" id="taskName" value="${taskName}" class="layui-input">
    <div class="layui-inline">
      <label class="layui-form-label">项目名称</label>
      <div class="layui-input-inline">
        <input type="hidden" name="construct_Aparty_purchase_constructId" id="construct_project_id" value="${aParty.construct_project_id}" class="layui-input">
        <input type="text" name="construct_project_name" readonly="readonly"id="construct_project_name" value="${aParty.construct_project_name}" class="layui-input">
        
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">收货地址</label>
      <div class="layui-input-inline">
        <input type="text" readonly="readonly" name="construct_project_leader" id="construct_project_leader"  value="${aParty.construct_project_leader}" class="layui-input">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">收货人</label>
      <div class="layui-input-inline">
        <input type="text" readonly="readonly" value="${aParty.construct_project_leader}" name="flow_description" id="flow_description" class="layui-input">
      </div>
    </div>
  </div>
  
  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">联系电话</label>
      <div class="layui-input-inline">
        <input type="text" name="construct_project_leaderTel" id="construct_project_leaderTel" value="${aParty.construct_project_leaderTel}" class="layui-input">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">订单号</label>
      <div class="layui-input-inline">
        <input type="hidden" name="construct_Aparty_purchase_id" id="construct_Aparty_purchase_id" value="${aParty.construct_Aparty_purchase_id}" class="layui-input">
        <input type="text" name="construct_Aparty_purchase_orderNum" id="construct_Aparty_purchase_orderNum" value="${aParty.construct_Aparty_purchase_orderNum}" class="layui-input">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">供货单位</label>
      <div class="layui-input-inline">
        <input type="text" name="construct_Aparty_purchase_supplier" id="construct_Aparty_purchase_supplier" value="${aParty.construct_Aparty_purchase_supplier}" class="layui-input">
      </div>
    </div>
  </div>
  
  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">联系人</label>
      <div class="layui-input-inline">
        <input type="text" value="${aParty.construct_Aparty_purchase_contacts}" name="construct_Aparty_purchase_contacts" id="construct_Aparty_purchase_contacts"  class="layui-input">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">联系电话</label>
      <div class="layui-input-inline">
        <input type="text" value="${aParty.construct_Aparty_purchase_tel}"name="construct_Aparty_purchase_tel" id="construct_Aparty_purchase_tel" class="layui-input">
      </div>
    </div>
    
  </div>
		</form>
	</div>
	  <div>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
  <legend>材料单</legend>
  
    <button style="margin-left: 2%;" class="layui-btn layui-btn-sm" type="button" id="add" onclick="addrow()">添加行</button>

</fieldset>	  
	  <table class="layui-hide" id="materialListID" lay-filter="materialListID"></table>

 </div>

<div id="company" style="display:none;width:100%;height:90%;padding-left: 5%;padding-right: 5%;">

</div>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
  <legend></legend>
  </fieldset>
  <!-- 这里是选择审核开始 -->
 <div class="layui-row" style="margin: 20px">
    ${startForm}
   </div>
 <div style="margin-left:  90px;">
     <jsp:include page="../../view/checkBox/process-flow.jsp"/>
</div>    
   <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
  <legend>审核记录</legend>
  </fieldset>
  <!-- 这里是选择审核开始 -->
 <div class="layui-row" style="margin: 20px">
		<table class="layui-table" lay-size="sm" id="demo"></table>
  </div>
<script>
var varTable;
layui.use(['table','laypage','layer','laydate','jquery'], function(){
	  var table = layui.table
	   ,$ = layui.jquery
	   ,laypage = layui.laypage
	   ,layer = layui.layer
	   ,laydate = layui.laydate;
	  //展示已知数据
	  table.render({
	    elem: '#materialListID'
	    	   ,cols: [[ //标题栏
	    	   	       {field: 'construct_Aparty_purEntry_id', title: '采购id',width:'9%',}
	    	   	      ,{field: 'construct_Aparty_purEntry_materialId', title: '材料id', width:'9%',}
	    	   	      ,{field: 'construct_Aparty_category', title: '材料分类', width:'9%'}
	    	   	      ,{field: 'construct_Aparty_entryName', title: '材料名称', width:'9%',event:'setSign'}
	    	   	      ,{field: 'construct_Aparty_model', title: '型号规格', width:'9%'}
	    	   	      ,{field: 'construct_Aparty_unit', title: '单位', width:'9%'}
	    	   	      ,{field: 'construct_Aparty_material_num', title: '工程量', width:'9%'}
	    	   	      ,{field: 'construct_aParty_byedNum', title: '累计审批量', width:'9%'}
	    	   	      ,{field: 'construct_Aparty_purEntry_num', title: '计划采购量', width:'9%',edit: 'text'}
	    	   	      ,{field: 'construct_Aparty_purEntry_remark', title: '备注',width:'9%',edit: 'text'}
	    	   	      ,{field: 'experience', title: '积分',width:'12%',templet: function (d) {
	    	   	    	  return  openStatus();}
	    	   	      }
	    	   	    ]]
	    ,data: ${entries}.entries
	    //,skin: 'line' //表格风格
	    ,even: true
	    ,height:400
	    ,page: true //是否显示分页
	    //,limits: [5, 7, 10]
	   ,limit: 40 //每页默认显示的数量
	  });
	  
	//展示历史记录
		table.render({
			elem : '#demo'
			,height:300
			,cols : [[
			         {field:'',width:88,title:'步骤序号',align:'center',type:'numbers'}
			        ,{field:'name_',title:'步骤名称',width:150}
			        ,{field:'username',title:'相关人员',width:90,}
			        ,{field:'START_TIME_',title:'发生时间',width:160,templet:function(res){
			        	
			        return  dateFormat(res.START_TIME_.time, "yyyy-MM-dd HH:mm:ss");
			        	
			        }}
			        ,{field:'MESSAGE_',title:'审核意见',width:200,}
			        ,{field:'description_',title:'内容'}
			        ]]
		           ,data:${history}
		           ,even:true
       });
          
	  //监听工具条删除按钮
	  table.on('tool(materialListID)', function(obj){ //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
	    var data = obj.data; //获得当前行数据
	    var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
	    var tr = obj.tr; //获得当前行 tr 的DOM对象
	   
	    if(layEvent === 'delrow'){ //删除行
	    	layer.confirm('确定删除吗？', {
      		  	btn: ['确定', '取消'] //可以无限个按钮
      		 	,btn1: function(index, layero){
			    	 obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
			    	 //编号不为空，需要到数据库删除
			    	/*  if(data.own_purchase_entryId != undefined && data.own_purchase_entryId != ""){
			    		 $.ajax({
								type : 'POST',
								url : 'deleteOwnEntry.do',
								data : {'entryId':data.own_purchase_entryId,'type':'entry'},
								success : function(data) {
									
								}
						});  
			    	 } */
			    	 layer.close(index);
      		 	}
	    	})
	    }else if(layEvent === 'saverow'){ //修改行
	    	layer.confirm('确定修改吗？', {
      		  	btn: ['确定', '取消'] //可以无限个按钮
      		 	,btn1: function(index, layero){
			    	 obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
			    	 //编号不为空，需要到数据库删除
			    /* 	 if(data.own_purchase_entryId != undefined && data.own_purchase_entryId != ""){
			    		 $.ajax({
								type : 'POST',
								url : 'updateOwnPurchaseEntry.do',
								data : data,
								success : function(data) {
									
								}
						});  
			    	 } */
			    	 location.reload();
      		 	}
	    	})
	    }else if(layEvent=='setSign'){
	    	varTable = obj;
	  
	    	 layer.open({
				  type: 2,
				  area: ['50%','50%'], 
				  maxmin: true,
				  content: 'aPartyMaterialCheck.do',
				  success:function(layero,index){
					
				  },end:function(data){
					
				  }
				});
	    	
	    	
	    }
	  });
	  
	  
	  
	});
	
	//添加行
	function addrow(){

		//添加行
	
			var table = layui.table;
			var oldData =  table.cache["materialListID"];
			
         
		var data1 = {"construct_Aparty_purEntry_id":'',
					 "construct_Aparty_purEntry_materialId":'',
			         "construct_Aparty_category":'',
			         "construct_Aparty_entryName":'',
				     "construct_Aparty_model":'',
					 "construct_Aparty_unit":'',
					 "construct_Aparty_material_num":'',
					 "construct_aParty_byedNum":'',
					 "construct_Aparty_purEntry_num":'',
					 "construct_Aparty_purEntry_remark":'',
			
					};
			oldData.push(data1);
			table.reload('materialListID',{
		        data : oldData
		    }); 
			
	}	
	
	
	  //删除
    function openStatus(){
    		return '<button class="layui-btn layui-btn-normal layui-btn-sm" type="button" lay-event="saverow"><i class="layui-icon"></i></button> ' +
    		'<button class="layui-btn layui-btn-normal layui-btn-sm" type="button" lay-event="delrow"> <i class="layui-icon"></i></button>';
    	
    	
    }
	  
	  //打开审核人窗口
	  function openWindow(){
		  layer.open({
			  type: 2,
			  area: ['50%','50%'],
			  maxmin: true,
			  content: 'userListCheck2.do',
			  success:function(layero,index){
				
			  },end:function(data){
				
			  }
			});
		  
	  }
	
	  //选择审核人赋值
	 function setUserName(data){
		  $("#userid").val(data[0].id);
		  $("#username").val(data[0].name);
		 
	 }
	  
	  
	     /**
	     *
	     *    提交审核单据
	     */
		function btnSave(type) {
	
		var data=layui.table.cache.materialListID;
		var userid=$("#userid").val();
		var taskid=$("#taskid").val();
		var taskName=$("#taskName").val();
		var reason=$("#reason").val();
		
		if((taskName == '项目助理' && type !='false') ||  taskName !='专业公司总经理'){
			if(userid=='' && type !='false'){
				layer.msg("审核人不能为空", {icon: 5, time: 1500,shade:[0.3]});
				return
			   }
		    }
		if(reason==''){
			 layer.msg("意见不能为空!", {icon: 5, time: 1500,shade:[0.3]})
			 return
		}
	
			for (var int = 0; int < data.length; int++) {
				if (data[int].construct_Aparty_purEntry_num==undefined || data[int].construct_Aparty_purEntry_num=='') {
					 layer.msg("第"+(int+1)+"行计划采购量不能为空", {icon: 6, time: 1500,shade:[0.3]})
					return false;
				} 
		       var construct_aParty_byedNum = data[int].construct_aParty_byedNum ==undefined?0:data[int].construct_aParty_byedNum;
				if(parseInt(data[int].construct_Aparty_material_num)<parseInt(construct_aParty_byedNum)+parseInt(data[int].construct_Aparty_purEntry_num)){
					 layer.msg("第"+(int+1)+"行计划采购量累计不能大于合同工程量", {icon: 6, time: 1500,shade:[0.3]});
					return false;
				}
				
			}
			
			 var arr = "data="+JSON.stringify(data)+ "&taskid=" + taskid + "&taskName=" + taskName +"&userid=" + userid + "&type=" + type + "&reason=" + reason;
			 $.ajax({
					url : "aPartyPurPass.do",
					type : 'POST',
					data : arr,
					success : function(data) {
						if (data.Success) {
							 layer.msg(data.Msg, {icon: 6, time: 1000,shade:[0.3]}, function () {
								  location.href="findTaskList.do";
		                   });
						} else {
							 layer.msg(data.Msg, {icon: 5, time: 1000,shade:[0.3]});
						}
					}

			});
			
		}

	  
		
		//子窗口调用
		function setaPartyMateData(data) {
			var rows=layui.table.cache.materialListID;
		    var table = layui.table;
		    var oldData =  table.cache["materialListID"];
		 for (var int = 0; int < oldData.length; int++) {
				if(rows[int].construct_Aparty_purEntry_materialId==data.construct_Aparty_material_id){
					alert("此材料已存在！");
					return false;
				}
			}
		
		varTable.update({
			"construct_Aparty_purEntry_parentId":data.construct_Aparty_purEntry_parentId,
		    "construct_Aparty_purEntry_materialId":data.construct_Aparty_material_id,
            "construct_Aparty_category":data.construct_Aparty_material_category,
            "construct_Aparty_entryName":data.construct_Aparty_material_name,
			"construct_Aparty_model":data.construct_Aparty_material_model,
			"construct_Aparty_unit":data.construct_Aparty_material_unit,
			"construct_Aparty_material_num":data.construct_Aparty_material_num,
			"construct_aParty_byedNum":data.sum,
			"construct_Aparty_purEntry_num":data.construct_Aparty_purEntry_num,
			"construct_Aparty_purEntry_remark":data.construct_Aparty_material_remark,
		});

		
	
			
		}

	
</script>

</body>
</html>