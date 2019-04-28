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
   .layui-table-cell{
   padding:0 5px !important;
   }
  </style>
</head>
<body> 
<div class="demoTable" style="margin:20px 10px 10px 10px">
 <div class="layui-form">
  <div class="layui-form-item">
    <div class="layui-inline">
       <div class="layui-input-inline">
        <input type="text" class="layui-input" name="username" id="username" placeholder="申请人">
      </div> 
        <div class="layui-input-inline">
        <input type="text" class="layui-input" name="field_personnel_company" id="field_personnel_company" placeholder="公司">
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
  <a class="layui-btn layui-btn-xs" lay-event="btnEdit">编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>
 {{#  if(d.field_personnel_status==0){ }}
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="cancel">取消申请</a>
  {{#  }}}
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
         ,url:'findFieldpLists.do'
    	 ,cols: [[ //标题栏
    	          
    	          //  ,{field: 'field_personnel_id', title:'ID',hidden:'hidden',width: 80}
    	             {field: 'username', title: '申请人', width: 70}
    	            ,{field: 'field_personnel_company', title: '公司名称', width: 100}
    	            ,{field: 'field_personnel_rolename', title: '职位/岗位', width: 110}
    	            ,{field: 'field_personnel_place', title: '外出地址', width: 150,}
    	            ,{field: 'field_personnel_license', title: '车牌号', width: 80}
    	            ,{field: 'field_personnel_car', title: '是否用车', width: 65,templet:'<div>{{operate_car(d.field_personnel_car)}}</div>'}
    	            ,{field: 'field_personnel_driver', title: '驾驶员', width: 70}
    	            ,{field: 'field_personnel_cause', title: '外出事由', width: 120}
    	            ,{field: 'start_time', title: '开始时间', width: 135} 
    	            ,{field: 'end_time', title: '结束时间', width: 135} 
    	            ,{field: 'field_personnel_status', title: '状态', width: 70,templet:'<div>{{status_format(d.field_personnel_status)}}</div>'} 
    	            ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:175}
                ]]
    ,id:'testReload'
    ,page: true
    ,limit:15
    ,height: 750
  });
  
     //监听工具条
       table.on('tool(demo)', function(obj){
         var data = obj.data;
         if(obj.event === 'btnEdit'){
        		location.href="newFieldPersonnel.do?biz="+data.field_personnel_id;
         }else if(obj.event === 'delete'){    	
        	 layer.confirm('确定删除吗？', {
        		  btn: ['确定', '取消'] //可以无限个按钮
        		 ,btn1: function(index, layero){
        			 $.ajax({
 						type : 'POST',
 						url : 'deleteFieldPersonne.do',
 						data : {
 							'biz':data.field_personnel_id
 						},
 						success : function(data) {
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
             }else if(obj.event === 'cancel'){
            	 layer.confirm('确定取消申请吗？', {
           		  btn: ['确定', '取消'] //可以无限个按钮
           		 ,btn1: function(index, layero){
            	  $.ajax({
  					type : 'post',
  					url : 'cancelofps.do',
  					data : {'bizId':data.field_personnel_id},
  					success : function(data) {
  						 if (data.Success) {	 
  							 layer.msg(data.Msg, {icon: 6, time: 1500,shade:[0.3]}, function () {
	                                location.reload();
  	 						          });
  	 							}else {
  	 							 layer.msg(data.Msg, {icon: 5, time: 1000,shade:[0.3]});
  	 					     }
  					      }
  				   });
                 }
             });
             }
       });
       
       var $ = layui.$, active = {
    		   btnAdd: function(){ //新增
  			       var obj='add';
    			  $.ajax({
    					type : 'get',
    					url : 'findaddofp.do',
    					data : {},
    					success : function(data) {
    							if (data.Success) {
    	                           if(data.ofpList<=0){
    								 location.href="newFieldPersonnel.do?biz="+obj;	
    								}else{
    									 layer.msg('该用户有外勤单据未结束，不可再申请', {icon: 5, time: 1000,shade:[0.3]});
    									return;
    								}
    							} else {
    								 layer.msg(data.Msg, {icon: 5, time: 1000,shade:[0.3]});
    								return;
    							}
    						}
    					});
    	   
    		    },reload: function(){ //验证是否全选
    		    	   var username=$('#username').val();
    		    	   var field_personnel_company=$('#field_personnel_company').val();
    		    	   table.reload('testReload', {
     			    	 page: {
     			    		   curr: 1 //重新从第 1 页开始
     			    	 },where: {
     			    		 'username':username
     			    		,'field_personnel_company':field_personnel_company
     			    	 } //设定异步数据接口的额外参数
     			    	 
     			      });
    		      }
    	
    		  
    		  };
       		  //绑定搜索点击事件
		  $('.demoTable .layui-btn').on('click', function(){
		    var type = $(this).data('type');
		    active[type] ? active[type].call(this) : '';
		  });
		 
		  
		});
		
	//格式化状态
	  function status_format(value){
		if(value==0){
			return  '<span style="color: red;">审核中</span>';	
		 }else if(value==2){
			return   '<span style="color: #00CD00;">审核通过</span>';
		 }else if(value==3){
			return '<span style="color: #FF1493;">未通过</span>';
		 }else if(value==4){
		    return "外勤结束";
	      }
	  }
		
	//格式化是否用车
		function operate_car(value){
	      if(value==1){
	      	return "否";
	      }else if(value==2){
	    	  return  '<span style="color: red;">是</span>';
	      }
		}
	  	
		
</script>

</body>
</html>