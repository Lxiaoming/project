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
  <legend>材料信息</legend>
</fieldset>
 <div class="layui-row">
     <input type="hidden" name="taskid" id="taskid" value="${taskid}" />
     <input type="hidden" name="taskName" id="taskName"	value="${taskName}" />
 </div>

    <div class="layui-row" id="fromdiv">
	<form class="layui-form" action="" id="ownhead" style="margin-right: 3%">
		<div class="layui-form-item">
		  <div class="layui-inline">
		    <label class="layui-form-label"><a href="#" onclick="openCompany()" style="color:red">公司信息 </a></label>
		    <div class="layui-input-inline">
		       <input type="hidden" id="own_purchase_id" name="own_purchase_id" value="${own_purchase_id}" class="layui-input">
		       <input type="text" name="company_name" id="company_name" class="layui-input" value="${company_name}" readonly="readonly">
		       <input type="hidden" name="own_purchase_companyId" id="own_purchase_companyId" value="${own_purchase_companyId}" class="layui-input">
		       <input type="hidden" name="own_purchase_time" id="own_purchase_time" value="${own_purchase_time}" class="layui-input">
		    </div>
		  </div>
		  <div class="layui-inline" style="display: none;">
		    <label class="layui-form-label">项目id</label>
		    <div class="layui-input-inline">
		      <input type="text" name="own_purchase_projectId" id="own_purchase_projectId" autocomplete="off" class="layui-input" value="${own_purchase_projectId}">
		    </div>
		  </div>
		  <div class="layui-inline">
		    <label class="layui-form-label" >项目名称：</label>
		    <div class="layui-input-inline">
		      <input type="text" name="construct_project_name" autocomplete="off" readonly="readonly" class="layui-input" value="${construct_project_name}">
		    </div>
		  </div>
		  <div class="layui-inline">
		    <label class="layui-form-label">计划员：</label>
		    <div class="layui-input-inline">
		      <input type="text" name="own_purchase_planMan" autocomplete="off" class="layui-input" value="${own_purchase_planMan}">
		    </div>
		  </div>
		</div>
		
		<div class="layui-form-item">
		  <div class="layui-inline">
		    <label class="layui-form-label">计划日期：</label>
		    <div class="layui-input-inline">
		      <input type="text" name="own_purchase_planDate" id="own_purchase_planDate" autocomplete="off" class="layui-input" value="${own_purchase_planDate}">
		    </div>
		  </div>
		  <div class="layui-inline">
		    <label class="layui-form-label">希望送达时间：</label>
		    <div class="layui-input-inline">
		      <input type="text" name="own_purchase_arriveDate" autocomplete="off" class="layui-input" value="${own_purchase_arriveDate}">
		    </div>
		  </div>
		  <div class="layui-inline">
		    <label class="layui-form-label"> 品牌：</label>
		    <div class="layui-input-inline">
		      <input type="text" name="own_purchase_brand" autocomplete="off" class="layui-input" value="${own_purchase_brand}">
		    </div>
		  </div>
		</div>

		<div class="layui-form-item">
		  <div class="layui-inline">
		    <label class="layui-form-label"></label>
		    <div class="layui-input-inline" style="width:300px">
		      普通采购<input type="radio" name="own_purchase_type" autocomplete="off" class="layui-input" value="1" checked="checked">
		      材料采购<input type="radio" name="own_purchase_type" autocomplete="off" class="layui-input" value="2">
		    </div>
		  </div>
		</div>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
  <legend>材料单</legend>
  	<c:if test="${own_purchase_status == 3 ||  own_purchase_status == -1}">
  		<button style="margin-left: 2%;" class="layui-btn layui-btn-sm" type="button" id="add" onclick="addrow()">添加行</button>
  	</c:if>
</fieldset>	  
	  <table class="layui-hide" id="materialListID" lay-filter="materialListID"></table>
	</form>
 </div>

<div id="company" style="display:none;width:100%;height:90%;padding-left: 5%;padding-right: 5%;">
</div>
<div class="layui-row">
    <div class="layui-col-md6">
       <div class="layui-row" style="margin: 20px">
	<div class="layui-form-item layui-form-text">
		<label class="layui-form-label">审核意见</label>
		<div class="layui-input-block">
			<textarea placeholder="请输入内容" id="reason" class="layui-textarea"  width="500px"></textarea>
		</div>
	</div>
    
	<div class="layui-form-item">
		<div class="layui-inline">
		<label class="layui-form-label"><a href="#" style="color: red" onclick="openWindow()">选择审核人</a></label>
			<div class="layui-input-inline">
				<input type="hidden" name="userid" id="userid" class="layui-input">
				<input type="text" name="username" id="username" readonly="readonly" class="layui-input">
			</div>
		</div>
	</div>	    
	<div class="layui-inline">
			<button class="layui-btn" type="button" onclick="btnSave('true')">提交</button>
			<button class="layui-btn" type="button" onclick="btnSave('false')">驳回</button>
	</div>
</div>

    </div>
</div>
<div style="margin-left:  90px;">
  
     <jsp:include page="../../view/checkBox/process-flow.jsp"/>
    
</div>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
  <legend>审核记录</legend>
 
</fieldset>	
<div class="layui-row">
   	<table class="layui-hide" id="demo">
		
	</table>
    
</div>





<script type="text/html" id="delrow">
  <!-- 这里的 checked 的状态只是演示 -->
  <button class="layui-btn layui-btn-normal layui-btn-sm" type="button" lay-event="delrow"><i class="layui-icon"></i></button>
</script>



<script type="text/html" id="indexTpl">
    {{d.LAY_TABLE_INDEX+1}}
   </script>
<!--格式化审核记录时间  -->
<script type="text/html" id="createTime">
	 {{dateformat(d.START_TIME_,"yyyy-MM-dd HH:mm:ss")}}
</script>
<script>

	layui.use(['table','laypage','layer','laydate','jquery'], function(){
	  var table = layui.table
	   ,$ = layui.$ 
	   ,laypage = layui.laypage
	   ,layer = layui.layer
	   ,laydate = layui.laydate;
	  table.render({
	    elem: '#materialListID'
    	,data: ${jsonObject}
	    ,page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
	      layout: ['limit', 'count', 'prev', 'page', 'next', 'skip'] //自定义分页布局
	      //,curr: 5 //设定初始在第 5 页
	      ,groups: 1 //只显示 1 个连续页码
	      ,first: false //不显示首页
	      ,last: false //不显示尾页
	      
	    }
	    ,cols: [[
	      {field:'own_purchase_material', width:'9%', title: '材料名称',edit: 'text'}
	      ,{field:'own_purchase_model', width:'9%', title: '型号规格',edit: 'text'}
	      ,{field:'own_purchase_unit', width:'9%', title: '单位',edit: 'text'}
	      ,{field:'own_purchase_quantities', title: '合同工程量', width: '9%',edit: 'text'}
	      ,{field:'own_purchase_stock', width:'9%', title: '库存', edit: 'text'}
	      ,{field:'own_purchase_applyNum', width:'9%', title: '计划采购量', edit: 'text'}
	      ,{field:'own_purchase_leastPrice', width:'9%', title: '最低单价',edit: 'text'}
	      ,{field:'own_purchase_price', width:'9%', title: '本次单价', edit: 'text'}
	      ,{field:'own_purchase_purchaseTotal', width:'9%', title: '小计', edit: 'text'}
	      ,{field:'own_purchase_status',  title: '操作', templet: function (d) {
	    	  return  openStatus();}
	      }
	    ]]
	    ,page: true
	    ,height: 400
	  }); 

		//展示历史记录
		table.render({
			elem : '#demo',
		
			cols : [[
			         {field:'',width:88,title:'步骤序号',align:'center',templet:'#indexTpl'}
			        ,{field:'name_',title:'步骤名称',width:120}
			        ,{field:'username',title:'相关人员',width:90,}
			        ,{field:'START_TIME_',title:'发生时间',width:160,templet:'#createTime'}
			        ,{field:'MESSAGE_',title:'审核意见',width:200,}
			        ,{field:'description_',title:'内容'}
			        ]]
		           ,data:${historyList}
		           ,even:true
             });
         

	  
	  //监听单元格编辑
	  table.on('edit(materialListID)', function(obj){
	    var value = obj.value //得到修改后的值
	    ,data = obj.data //得到所在行所有键值
	    ,field = obj.field; //得到字段
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
			    	 if(data.own_purchase_entryId != undefined && data.own_purchase_entryId != ""){
			    		 $.ajax({
								type : 'POST',
								url : 'deleteOwnEntry.do',
								data : {'entryId':data.own_purchase_entryId,'type':'entry'},
								success : function(data) {
									
								}
						});  
			    	 }
			    	 layer.closeAll();
      		 	}
	    	})
	    } 
	  });
	  
	});

	
	
//打开选择人
function openWindow(){
	 layer.open({
		  type: 2,
		  area: ['50%','50%'],
		  maxmin: true,
		  content: 'userListCheck2.do',
		  success:function(layero,index){
			 console.log()
		  },end:function(data){
			
		  }
		});
}
	
	
//审核单据
function btnSave(type) {

	var taskid=$("#taskid").val();
	var userid=$("#userid").val();
	var reason=$("#reason").val().trim();

      if(reason==''){
    		 layer.msg("审核意见不能为空!", {icon: 6, time: 1500,shade:[0.3]})
    	  return;
      }
      if(type=='true'){
          if(userid==''){
     		 layer.msg("审核人不能为空!", {icon: 6, time: 1500,shade:[0.3]})
     	  return;
      }
     
   }
      
      var arr = $("#ownhead").serialize() + "&taskid=" + taskid + "&userid=" + userid + "&type=" + type + "&reason=" + reason;
	$.ajax({
		url : "ownHeadPass.do",
		type : 'POST',
		data : arr,
		success : function(data) {
			if (data.Success) {
				 layer.msg(data.Msg, {icon: 6, time: 1500,shade:[0.3]}, function () {
                   location.href="findTaskList.do";
		    	});
			}else{
				 layer.msg(data.Msg, {icon: 5, time: 1000,shade:[0.3]});
				}
		
		
	    
	   }
	});
}
	
	
	//添加行
	function addrow(){
		var table = layui.table;
		var oldData =  table.cache["materialListID"];
		var data1 = {"own_purchase_entryId":"","own_purchase_material":"",
				"own_purchase_model":"","own_purchase_unit":"",
				"own_purchase_quantities":"","own_purchase_stock":"",
				"own_purchase_applyNum":"","own_purchase_leastPrice":"",
				"own_purchase_price":"","own_purchase_purchaseTotal":"",
				"own_purchase_remarks":""};
		oldData.push(data1);
		table.reload('materialListID',{
            data : oldData
        }); 
	}
	
	  //打开公司子页面
	 function openCompany(){
		 layer.open({
			  type: 2,
			  area: ['50%','50%'],
			  fixed: false, //不固定
			  maxmin: true,
			  content: 'companyCheck.do',
			  success:function(layero,index){
			
			  },end:function(data){
				
			  }
			});
		
	 }
	  
	 //子页面传值事件
	 function setCompany(data){
		$("#own_purchase_companyId").val(data.company_id);
		$("#company_name").val(data.company_name);
	
	  }
	 

	  //选择审核人赋值
	 function setUserName(data){
		  $("#userid").val(data[0].id);
		  $("#username").val(data[0].name);
		 
	 }
	
	  
		//时间格式化工具
		Date.prototype.Format = function(fmt) {
			var o = {
				"M+" : this.getMonth() + 1,//月份
				"d+" : this.getDate(),//日
				"H+" : this.getHours(),//小时
				"m+" : this.getMinutes(),//分
				"s+" : this.getSeconds(),//秒
				"q+" : Math.floor((this.getMonth() + 3) / 3),//季度
				"S+" : this.getMilliseconds()
			//毫毛
			};
			if (/(y+)/.test(fmt))
				fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
						.substr(4 - RegExp.$1.length));
			for ( var k in o)
				if (new RegExp("(" + k + ")").test(fmt))
					fmt = fmt.replace(RegExp.$1,
							(RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k])
									.substr(("" + o[k]).length)));
			return fmt;
		};
		//时间格式化
		function dateformat(value, fmt) {
			if (value == undefined)
				return "";
			var date = new Date(value.time);
			var time = date.Format(fmt);
			return time;
		}

	    function openStatus(){
	    	var status = ${own_purchase_status};
	    	if(status==3 || status == -1){
	    		return '<button class="layui-btn layui-btn-normal layui-btn-sm" type="button" lay-event="delrow"> <i class="layui-icon"></i></button>';
	    	}else{
	    		
	    	  return "";
	    	}
	    	
	    }
		//单选框赋值
		var car=${own_purchase_type==undefined?1:own_purchase_type};
		$("input[name='own_purchase_type'][value="+car+"]").attr("checked",true);
</script>

</body>
</html>