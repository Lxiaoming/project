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
	<script src="${CASD_PATH}/res/layui/format/dateformat.js?HJ_v=<%=Math.random()%>" charset="utf-8"></script>
	<script src="<%=request.getContextPath()%>/res/layui/jquery.min.js?HJ_v=<%=Math.random()%>" charset="utf-8"></script>
  <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
  <style type="text/css">
 .layui-table td, .layui-table th{
      font-size: 13px !important;
      color: #3a3a3a;
  }
  /*设置隔行颜色  */
   table tr:nth-child(2n){
   background-color:#FFF0F5;
   }
  .layui-input, .layui-select, .layui-textarea{
      height: 35px !important;
  }
   .layui-table-cell .layui-form-checkbox[lay-skin=primary]{
       top: 1px !important;
   }
  </style>
</head>
<body> 
<div class="demoTable" style="margin:20px 10px 10px 10px">
 <div class="layui-form">
  <div class="layui-form-item">
    <div class="layui-inline">
       <div class="layui-input-inline">
        <input type="text" class="layui-input" id="company_name" placeholder="公司名称">
      </div> 
      <div class="layui-input-inline">
        <input type="text" class="layui-input" id="construct_project_name" placeholder="项目名称">
      </div> 
      <div class="layui-input-inline">
        <input type="text" class="layui-input" id="own_purchase_planMan" placeholder="姓名">
      </div> 
      <button class="layui-btn" data-type="reload" style="margin-left: 10px;height: 37px">搜索</button>
    </div>
  </div>
</div>
 <div class="layui-btn-group demoTable">
  <button class="layui-btn layui-btn-sm" data-type="btnAdd">新增</button>
</div>
</div>
 <div style="margin: 10px 10px">
  <table class="layui-hide" id="lay_table_user" lay-filter="demo"></table>
</div>   



<script type="text/html" id="barDemo"> 

<div class="layui-btn-group">
 
  <a class="layui-btn layui-btn-xs" lay-event="view">查看</a>
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="printing">打印</a>
  {{#  if(d.own_purchase_status === 0){ }}
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>
  {{#  } else { }}
 
  {{#  } }}
   
  </div>
  

</script>
              
<script>
/* document.body.parentNode.style.overflow ="hidden";//禁止纵向滚动条 */
layui.use(['table','laypage','layer','laydate','jquery'], function(){
	 var table = layui.table
	   ,$ = layui.$ 
	   ,laypage = layui.laypage
	   ,layer = layui.layer
	   ,laydate = layui.laydate;
    //方法级渲染
    table.render({
    elem: '#lay_table_user'
         ,url:'ownHeadLists.do'
    	 ,cols: [[ //标题栏
    	          	{field: 'own_purchase_id', title: '单据编号', width: 90}
    	            ,{field: 'company_name', title: '公司名称', width: 95}
    	            ,{field: 'construct_project_name', title: '项目名称', width: 200}
    	            ,{field: 'own_purchase_time', title: '建单时间', width: 152,}
    	            ,{field: 'own_purchase_planDate', title: '计划日期', width: 105}
    	            ,{field: 'own_purchase_arriveDate', title: '希望送达日期', width: 110}
    	            ,{field: 'own_purchase_planMan', title: '计划员', width: 100}
    	            ,{field: 'own_purchase_type', title: '采购类型', width: 100,templet:'<div>{{category(d.own_purchase_type)}}</div>'}
    	            ,{field: 'own_purchase_status', title: '状态', width: 100,templet:'<div>{{operate_status(d.own_purchase_status)}}</div>'} 
    	            ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:140}
                ]]
    ,id:'testReload'
    ,page: true
    ,limit:15
    ,height: 750
  });
  
     //监听工具条
       table.on('tool(demo)', function(obj){
         var data = obj.data;
         if(obj.event === 'view'){
        	 location.href = "ownHeadView.do?" + $.param({
				 'type':'view',
				 'bizId':data.own_purchase_id,
				  
			 });
         }else if(obj.event === 'delete'){    	
        	 layer.confirm('确定删除吗？', {
        		  btn: ['确定', '取消'] //可以无限个按钮
        		 ,btn1: function(index, layero){
        			 $.ajax({
 						type : 'POST',
 						url : 'deleteOwnEntry.do',
 						data : {'headId':data.own_purchase_id,
						        'type':'head',
 							
 						},success : function(data) {
 							if (data.Success) {
 								 layer.msg(data.Msg, {icon: 6, time: 1000,shade:[0.3]}, function () {
 	                                location.reload();
 	                            });
 							} else {
 								 layer.msg(data.Msg, {icon: 5, time: 1000,shade:[0.3]});
 							}
 							}
 					});
        		  }
        		});
             }else if(obj.event === 'printing'){
            	//打印
            	
     			location.href ="ownpurchaseExcel.do?"+$.param({
     				'bizId':data.own_purchase_id,
     			});
            	 
             }
       });
       
       var $ = layui.$, active = {
    		   
    		   reload: function(){
    			      var company_name = $('#company_name').val();
    			      var construct_project_name = $('#construct_project_name').val();
    			      var own_purchase_planMan = $('#own_purchase_planMan').val();
    			      //执行重载
    			      table.reload('testReload', {
    			        page: {
    			          curr: 1 //重新从第 1 页开始
    			        }
    			        ,where: {
    			         'company_name':company_name
    			        ,'construct_project_name':construct_project_name
    			        ,'own_purchase_planMan':own_purchase_planMan
    			        }
    			        
    			      });
    			    },btnAdd: function(){ //新增
    			         location.href = "ownHeadView.do?" + $.param({
    					 'type':'save',
    				 });
    		    }
    		      		  
    		  };
       

		  //绑定搜索点击事件
		  $('.demoTable .layui-btn').on('click', function(){
		    var type = $(this).data('type');
		    active[type] ? active[type].call(this) : '';
		  });
		 
		  
		});
		
		//类型格式话
		function category(value) {
			if (value == 1) {
				return "普通采购";
			} else if (value == 2) {
				return'<span style="color: #F581B1;">材料采购</span>';
			}
		}
				
		//状态格式化
		function operate_status(value,row,index){
        	 if(value==0){
        	     return '<span style="color: #F581B1;">审核中</span>';
        	 }else if(value==2){
        		 return '<span style="color: red;">审批不通过</span>';
        	 }else if(value==1){
        		 return "审核通过";
        	 } else if(value==3){
        		 return '<span style="color: red;">被驳回</span>';
        	 }
         }
	
		
		
</script>

</body>
</html>